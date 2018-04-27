package com.hexin.apicloud.ble.printer.snbc4bplz;
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
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import com.snbc.sdk.barcode.enumeration.Rotation;
/**
 * 打印水印
 * 实现类
 * @author jundao
 */
public class PrintTextItem implements IPrintTemplateItem {

	/**
	 *	x	横坐标，单位点。 
	 *	y	纵坐标，单位点。 
	 *	fontName	字体名称。 
	 *	content	待打印的字符串。 
	 *	angle	打印旋转角度。 
	 *	horiSize	文字横向放大倍数或点数。 
	 *	vertSize	文字纵向放大倍数货点数。 
	 *	Reverse	当设置TRUE时，打印方式黑底白字；设置为FALSE时，打印方式为白底黑字 
	 */
	@Override
	public void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
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
							 String  nowDate = DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_NOWYMD_PATTERN);					;
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
			 }else{
				 //do nothing;
			 }
		 }
		 if(!StringUtil.isEmpty(sb.toString())){
			//字体大小
			 // int fontSize = FontSizeEnum.valueOf(pagedetails.getFontSize()).getIndex();
			 int fontSize = 0;
			 if(pagedetails.getFontSize() > 15){
				 fontSize = pagedetails.getFontSize()*2;
			 }else{
				 fontSize = (int) (pagedetails.getFontSize()*2.6);
			 }
			printTextAutoLine(labelEdit,sb.toString(),NumberUtil.mm2Dot(pagedetails.getWidth()),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),fontSize);
		 }
	}
	
	/**
	 * 自动换行
	 * @param labelEdit
	 * @param content
	 * @param width
	 * @param startX
	 * @param startY
	 * @param fontSize
	 * @throws Exception
	 */
	private void printTextAutoLine(ILabelEdit labelEdit,String content,int width,int startX,int startY,int fontSize) throws Exception{
		int lineWidth = 0;
	    int lastSubStrIndex = 0; 
	    // 匹配双字节
	    Pattern dWordReg = Pattern.compile("^[^x00-xff]$");
	    for(int i = 0,j = content.length();i < j;i++){ 
	    	Matcher matcher = dWordReg.matcher(content.substring(i, i+1));
	    	if(matcher.matches()){
	    		lineWidth += fontSize;
	    	}else{
	    		lineWidth += fontSize/2;
	    	}
	    	// -10 防止边界出现
	        if(lineWidth > width-10){
	        	labelEdit.printText(startX,startY, "MHEIGB18.TTF",content.substring(lastSubStrIndex,i), Rotation.Rotation0, fontSize, fontSize, 0);
	            // 行间距 5
	        	startY += fontSize + 5;
	            lineWidth = 0;
	            lastSubStrIndex = i;
	        } 
	        if(i == content.length()-1){
	        	labelEdit.printText(startX,startY, "MHEIGB18.TTF",content.substring(lastSubStrIndex,i+1), Rotation.Rotation0, fontSize, fontSize, 0);
	        }
	    }
	}
	
}
