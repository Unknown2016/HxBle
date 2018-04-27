package com.hexin.apicloud.ble.printer.snbc4bplz;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
/**
 * 打印文本
 * 实现类
 * @author jundao
 */
public class PrintWaterMarkItem implements IPrintTemplateItem {

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
		 for(TextItems textItem : textItems){
			 String base64Img = textItem.getText();
				// 转成RGB565彩色模式的Bitmap
				byte[] bitmapArray = Base64.decode(base64Img,Base64.DEFAULT);
				BitmapFactory.Options options =new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				InputStream	input = new ByteArrayInputStream(bitmapArray);
				Bitmap bitmap = BitmapFactory.decodeStream(input,null,options);
				labelEdit.printImage(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bitmap);
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
