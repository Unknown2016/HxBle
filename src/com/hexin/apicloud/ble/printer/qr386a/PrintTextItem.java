package com.hexin.apicloud.ble.printer.qr386a;
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
import com.qr.printer.Printer;
/**
 * 打印文本
 * 实现类
 * @author jundao
 */
public class PrintTextItem implements IPrintTemplateItem {

	/**
	 * text_x - 起始横坐标
	 * text_y - 起始纵坐标
	 * width - 文本框宽度
	 * height - 文本框高度
	 * str - 文字内容
	 * fontsize - 字体大小 1:16 点阵; 2:24 点阵; 3:32 点阵; 4:24 点阵放大一 倍; 5:32 点阵放大一倍 6:24 点阵放大两倍; 7:32 点阵放大两倍;8: 20 点阵 9: 56 点阵(8 和 9 中通申通适用);其他:24 点阵
	 * rotate - 旋转角度 0:不旋转; 1:90 度; 2:180°; 3:270° 
	 * bold - 是否粗体 0:否; 1:是
	 * underline - 是否有下划线 false:没有 true:有
	 * reverse - 是否反白 false:不反白;true:反白
	 * 
	 */
	@Override
	public void printItem(Printer iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
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
				 sb.append(" ");
			 }else{
				 //todo
			 }
		 }
		 if(!"null".equals(sb.toString())&&!"".equals(sb.toString())){
			 int fontsize = FontSizeEnum.valueOf(pagedetails.getFontSize()).getIndex();
			 iPrinter.drawText(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()) ,NumberUtil.mm2Dot(pagedetails.getHeight()),sb.toString(), fontsize, 0 , pagedetails.isFontBold()?1:0,pagedetails.isTextUnderline() , false);
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
		    }else if(fontSize <= FontSizeEnum.HEIGHT96.getFontSize()){
		    	return FontSizeEnum.HEIGHT96;
		    }else{
		    	return FontSizeEnum.HEIGHT24;
		    }
	    }
	   
	}
	
}
