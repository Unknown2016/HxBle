package com.hexin.apicloud.ble.printer.fujitsu;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;
import com.fujitsu.sdk.LPK130;
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
	public void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
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
			 int fontsize = FontSizeEnum.valueOf(pagedetails.getFontSize()).getIndex();
			 // 打印文字
			 printTextAutoLine(lpk130,sb.toString().trim(),NumberUtil.mm2Dot(pagedetails.getWidth()),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),fontsize,pagedetails.isFontBold()?1:0);
		 }
		 
	}
	
	/**
	 * 字体大小类型
	 * 枚举
	 * 
	 */
	public enum FontSizeEnum {
		
		HEIGHT16(1,8),HEIGHT24(2,14),HEIGHT32(3,32),HEIGHT48(4,48),HEIGHT64(5,64),HEIGHT72(6,72),
	    
		HEIGHT96(7,96);
	    
		//启锐386 fontsize
	    private int index;
	   
	    //模板数据中fontsize
	    private int fontSize;
	   
	    private FontSizeEnum(int index,int fontSize) {
	    	 this.index = index;
	    	 this.fontSize = fontSize;
	    }
	      
		public int getFontSize() {
			return fontSize;
		}
		public void setFontSize(int fontSize) {
			this.fontSize = fontSize;
		}


		public int getIndex() {
		    return index;
		}

		public void setIndex(int index) {
		    this.index = index;
		}
	 
	    public static FontSizeEnum valueOf(int fontSize) {
		    if(fontSize <= FontSizeEnum.HEIGHT16.getFontSize()){
		    	return FontSizeEnum.HEIGHT16;
		    }else if(fontSize <= FontSizeEnum.HEIGHT24.getFontSize()){
		    	return FontSizeEnum.HEIGHT24;
		    }else if(fontSize <= FontSizeEnum.HEIGHT32.getFontSize()){
		    	return FontSizeEnum.HEIGHT32;
		    }else if(fontSize <= FontSizeEnum.HEIGHT48.getFontSize()){
		    	return FontSizeEnum.HEIGHT48;
		    }else if(fontSize <= FontSizeEnum.HEIGHT64.getFontSize()){
		    	return FontSizeEnum.HEIGHT64;
		    }else if(fontSize <= FontSizeEnum.HEIGHT72.getFontSize()){
		    	return FontSizeEnum.HEIGHT72;
		    }else{
		    	return FontSizeEnum.HEIGHT96;
		    }
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
	private void printTextAutoLine(LPK130 lpk130,String content,int width,int startX,int startY,int fontsize,int fontbold) throws Exception{
		int lineWidth = 0;
	    int lastSubStrIndex = 0; 
	    // 匹配双字节
	    Pattern dWordReg = Pattern.compile("^[^x00-xff]$");
	    for(int i = 0,j = content.length();i < j;i++){
	    	String word = content.substring(i, i+1);
	    	Matcher matcher = dWordReg.matcher(word);
	    	int fontWidth = 0;
	    	switch (fontsize){
	    		case 1:
	    			fontWidth = 16;
	    			break;
	    		case 2:
	    			fontWidth = 24;
	    			break;
	    		case 3:
	    			fontWidth = 32;
	    			break;
	    		case 4:
	    			fontWidth = 48;
	    			break;
	    		case 5:
	    			fontWidth = 64;
	    			break;
	    		case 6:
	    			fontWidth = 96;
	    			break;
	    		case 7:
	    			fontWidth = 128;
	    			break;
	    	}
	    	if(matcher.matches() && !" ".equals(word)){
	    		lineWidth += fontWidth;
	    	}else{
	    		lineWidth += fontWidth/2;
	    	}
	    	 //防止边界出现并且排除i=0的情况（竖着打印 一行只有一个字）
	        if(lineWidth >= width-fontWidth && i != 0){
	        	lpk130.NFCP_Page_setText(startX,startY, content.substring(lastSubStrIndex,i), fontsize, 0, fontbold, false, false);
	            // 行间距 5
	        	startY += fontWidth + 5;
	            lineWidth = 0;
	            lastSubStrIndex = i;
	        } 
	        if(i == content.length()-1){
	        	lpk130.NFCP_Page_setText(startX,startY, content.substring(lastSubStrIndex,i+1), fontsize, 0, fontbold, false, false);
	        }
	    }
	}
}
