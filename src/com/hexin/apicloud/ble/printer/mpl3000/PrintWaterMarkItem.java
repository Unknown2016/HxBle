package com.hexin.apicloud.ble.printer.mpl3000;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.TextItems;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
/**
 * 打印水印
 * 实现类
 * @author jundao
 */
public class PrintWaterMarkItem implements IPrintTemplateItem {

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
	public void printItem(Printer printer,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		 List<TextItems> textItems = pagedetails.getTextItems();
		 for(TextItems textItem : textItems){
			 String base64Img = textItem.getText();
				// 转成RGB565彩色模式的Bitmap
				byte[] bitmapArray = Base64.decode(base64Img,Base64.DEFAULT);
				BitmapFactory.Options options =new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				InputStream	input = new ByteArrayInputStream(bitmapArray);
				Bitmap bitmap = BitmapFactory.decodeStream(input,null,options);
				printer.draw_bitmap(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bitmap,false);
		 }
		 
	}
	
	/**
	 * 字体枚举
	 * 模板字体和汉印字体 转换
	 *
	 */
	public enum FontSizeEnum {
		
		HEIGHT8(3,8),HEIGHT15(8,15),HEIGHT30(4,30);
	    
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
		    if(fontSize <= FontSizeEnum.HEIGHT8.getFontSize()){
		    	return FontSizeEnum.HEIGHT8;
		    }else if(fontSize <= FontSizeEnum.HEIGHT15.getFontSize()){
		    	return FontSizeEnum.HEIGHT15;
		    }else if(fontSize <= FontSizeEnum.HEIGHT30.getFontSize()){
		    	return FontSizeEnum.HEIGHT30;
		    }else{
		    	return FontSizeEnum.HEIGHT15;
		    }
	    }
	}
	
}
