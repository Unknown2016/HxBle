package com.hexin.apicloud.ble.printer.hprt;
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
import HPRTAndroidSDK.HPRTPrinterHelper;
/**
 * 打印文本
 * 实现类
 * @author jundao
 */
public class PrintTextItem implements IPrintTemplateItem {

	/**
	 * X:文字的起始的 x 坐标。 
	 * Y:文字的起始的 y 坐标。 
	 * Width:一行打印的宽度(单位:8=1mm) 
	 * Size:字体。
	 * isbole:加粗。 true:加粗。alse:不加粗。 
	 * isdouble:放大两倍字体。true:放大。false:不放大。 
	 * Str:要打印的文本。
	 */
	@Override
	public void printItem(Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		 List<TextItems> textItems = pagedetails.getTextItems();
		 Trade2Template trade2Template = new Trade2Template();
		 //*注：打包后在app上运行 程序直接崩溃 怀疑是cglib跟apicloud环境jar包有冲突
//		 BeanCopier copier = BeanCopier.create(expressBill.getClass(),bean.getClass(), false);
//		 copier.copy(expressBill, bean, null);
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
//			 if("left".equalsIgnoreCase(pagedetails.getTextAlign())){
//				 HPRTPrinterHelper.Align(HPRTPrinterHelper.LEFT);
//			 }else if("center".equalsIgnoreCase(pagedetails.getTextAlign())){
//				 HPRTPrinterHelper.Align(HPRTPrinterHelper.CENTER);
//			 }else if("right".equalsIgnoreCase(pagedetails.getTextAlign())){
//				 HPRTPrinterHelper.Align(HPRTPrinterHelper.RIGHT);
//			 }
			 printTextAutoLine(sb.toString().trim(),NumberUtil.mm2Dot(pagedetails.getWidth()),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),fontsize,pagedetails.isFontBold());
//			 HPRTPrinterHelper.Align(HPRTPrinterHelper.LEFT);
		 }
		 
	}
	
	/**
	 * 字体大小类型
	 * 枚举
	 * 
	 */
	public enum FontSizeEnum {
		
		HEIGHT16(20,8),HEIGHT24(24,16),HEIGHT32(4,22),HEIGHT48(8,96);
	    
		//汉印 fontsize
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
		    }else{
		    	return FontSizeEnum.HEIGHT48;
		    }
	    }
	   
	}
	
	/**
	 * 自动换行
	 * @param content
	 * @param width
	 * @param startX
	 * @param startY
	 * @param fontsize
	 * @param fontbold
	 * @throws Exception
	 */
	private void printTextAutoLine(String content,int width,int startX,int startY,int fontsize,boolean fontbold) throws Exception{
		int lineWidth = 0;
	    int lastSubStrIndex = 0; 
	    // 匹配双字节
	    Pattern dWordReg = Pattern.compile("^[^x00-xff]$");
	    for(int i = 0,j = content.length();i < j;i++){
	    	String word = content.substring(i, i+1);
	    	Matcher matcher = dWordReg.matcher(word);
	    	int fontWidth = 0;
	    	switch (fontsize){
	    		case 20:
	    			fontWidth = 16;
	    			break;
	    		case 24:
	    			fontWidth = 24;
	    			break;
	    		case 4:
	    			fontWidth = 32;
	    			break;
	    		case 8:
	    			fontWidth = 48;
	    			break;
	    	}
	    		
	    	if(matcher.matches() && !" ".equals(word)){
	    		lineWidth += fontWidth;
	    	}else{
	    		lineWidth += fontWidth/2;
	    	}
	    	if(fontbold){
    			HPRTPrinterHelper.SetBold("1");
    		}
	    	//宽高放大2倍
	    	if(fontsize == 8){
	    		HPRTPrinterHelper.SetMag("2","2");
	    	}
	    	 //防止边界出现并且排除i=0的情况（竖着打印 一行只有一个字）
	        if(lineWidth >= width-fontWidth && i != 0){
	        	HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,fontsize+"","0",startX+"",startY+"",content.substring(lastSubStrIndex,i));
	            // 行间距 5
	        	startY += fontWidth + 5;
	            lineWidth = 0;
	            lastSubStrIndex = i;
	        } 
	        if(i == content.length()-1){
	        	HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,fontsize+"","0",startX+"",startY+"",content.substring(lastSubStrIndex,i+1));
	        }
	        if(fontsize == 8){
	    		HPRTPrinterHelper.SetMag("1","1");
	    	}
	        if(fontbold){
    			HPRTPrinterHelper.SetBold("0");
    		}
	    }
	}
	
}
