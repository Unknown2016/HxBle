package com.hexin.apicloud.ble.printer.snbc4bplz;
import java.io.InputStream;
import java.net.URL;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * 打印图片
 * 实现类
 * @author jundao
 */
public class PrintImageItem implements IPrintTemplateItem {

	@Override
	public void printItem(final ILabelEdit labelEdit,final Template template,final Pagedetails pagedetails,Trade trade) throws Exception{
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bitmap bmp = null;
		        try {
		        	InputStream bmpis =  new URL(pagedetails.getImageUrl()).openStream();
		            bmp = BitmapFactory.decodeStream(bmpis);
		            labelEdit.printImage(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bmp);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}).start();
	}

}
