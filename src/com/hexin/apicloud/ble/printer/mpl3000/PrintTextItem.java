package com.hexin.apicloud.ble.printer.mpl3000;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.TextItems;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.bean.Trade2Template;
import com.hexin.apicloud.ble.common.Constants;
import com.hexin.apicloud.ble.enums.TextItemTypeEnum;
import com.hexin.apicloud.ble.util.BeanUtil;
import com.hexin.apicloud.ble.util.DateUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.hexin.apicloud.ble.util.StringUtil;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;
/**
 * 打印文本
 * 实现类
 * @author jundao
 */
public class PrintTextItem implements IPrintTemplateItem {

	/**
	 * x - 打印文字起始点横坐标
	 * y - 打印文字起始点纵坐标
	 * str - 打印的文字
	 * fontsize - 字体大小 1：16点阵；2：24点阵；3：32点阵；4：24点阵放大一倍；5：32点阵放大一倍 6：24点阵放大两倍；7：32点阵放大两倍；其他：24点阵
	 * rotate - 旋转角度 0：不旋转；1：90度；2：180°；3:270°
	 * bold - 是否粗体 0：取消；1：设置
	 * underline - 是有有下划线 false:没有；true：有
	 * reverse - 是否反白 false：不反白；true：反白
	 */
	@Override
	public void printItem(Printer printer,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		 List<TextItems> textItems = pagedetails.getTextItems();
		 Trade2Template trade2Template = new Trade2Template();
		 BeanUtil.copyProperty(trade, trade2Template);
		 JSONObject tradeJson = (JSONObject) JSONObject.toJSON(trade2Template);
		 StringBuffer sb = new StringBuffer();
		 for(TextItems textItem : textItems){
			 if(TextItemTypeEnum.PRINT.ordinal() == textItem.getType()){
				 if(!StringUtil.isEmpty(textItem.getCode())){
					//有格式化的 截掉后面格式化部分 例:addedServices.servInsureAmount|money
					 String code = textItem.getCode();
					 Pattern formatReg = Pattern.compile("^.+\\|\\w+$");
					 Matcher matcher = formatReg.matcher(code);
					 if(matcher.matches()){
						 int endIndex = textItem.getCode().lastIndexOf("|");
						 if(endIndex > 0){
							 code = code.substring(0, endIndex);
						 }
						 if(Constants.TEMPLATE_DATE_ITEM.equalsIgnoreCase(code)){
							 String format = textItem.getCode().substring(endIndex + 1);
							 String  nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWYMD_PATTERN);
							 switch (format){
							 case Constants.TEMPLATE_DATE_NOWYMDT:
								 nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWYMDT_PATTERN);						 
								 break;
							 case Constants.TEMPLATE_DATE_NOWYMD:
								 nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWYMD_PATTERN);					
								 break;
							 case Constants.TEMPLATE_DATE_NOWY:
								 nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWY_PATTERN);					
								 break;
							 case Constants.TEMPLATE_DATE_NOWM:
								 nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWM_PATTERN);					
								 break;
							 case Constants.TEMPLATE_DATE_NOWD:
								 nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWD_PATTERN);					
								 break;
							 case Constants.TEMPLATE_DATE_NOWT:
								 nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWT_PATTERN);					
								 break;
							 }
							 sb.append(nowDate);
						 }
					 }
					 if(!StringUtil.isEmpty(tradeJson.getString(code))){
						 sb.append(tradeJson.getString(code));
						 sb.append(" ");
					 }
				 }
			 }else if(TextItemTypeEnum.CUSTOM.ordinal() == textItem.getType()){
				 sb.append(textItem.getText());
				 sb.append(" ");
			 }else{
				 //todo
			 }
		 }
		 if(!"null".equals(sb.toString())&&!"".equals(sb.toString())){
			 // 打印文字
			 printTextAutoLine(printer,sb.toString(),NumberUtil.mm2Dot(pagedetails.getWidth()),pagedetails.getX().add(template.getCalibrationX()),pagedetails.getY().add(template.getCalibrationY()),pagedetails.getFontSize(),pagedetails.isFontBold()?1:0);
		 }
		 
	}
	
	/**
	 * 自动换行
	 * @param lpk130
	 * @param content
	 * @param width
	 * @param startX
	 * @param startY
	 * @param fontsize
	 * @param fontbold
	 * @throws Exception
	 */
	private void printTextAutoLine(Printer printer,String content,int width,BigDecimal startX,BigDecimal startY,int fontsize,int fontbold) throws Exception{
		int lineWidth = 0;
	    int lastSubStrIndex = 0; 
	    // 匹配双字节
	    Pattern dWordReg = Pattern.compile("^[^x00-xff]$");
	    for(int i = 0,j = content.length();i < j;i++){ 
	    	Matcher matcher = dWordReg.matcher(content.substring(i, i+1));
	    	if(matcher.matches()){
	    		lineWidth += fontsize;
	    	}else{
	    		lineWidth += fontsize/2;
	    	}
	    	// -10 防止边界出现
	        if(lineWidth > width-10){
	        	/**
	        	 * x 偏移 X 坐标的量,单位为 mm 
	        	 * y 偏移 Y 坐标的量,单位为 mm
	        	 * fontSize 字体大小
	        	 * text 文本
	        	 * rotate 旋转
	        	 */
	        	printer.draw_text(startX.intValue(), startY.intValue(),fontsize, content.substring(lastSubStrIndex,i),0);
	            // 行间距 5
	        	startY = startY.add(new BigDecimal((fontsize + 5)/8));
	            lineWidth = 0;
	            lastSubStrIndex = i;
	        } 
	        if(i == content.length()-1){
	        	printer.draw_text(startX.intValue(), startY.intValue(),fontsize, content.substring(lastSubStrIndex,i+1),0);
	        }
	    }
	}
}
