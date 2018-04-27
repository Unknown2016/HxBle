package com.hexin.apicloud.ble.printer.hprt;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.ImgUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import HPRTAndroidSDK.HPRTPrinterHelper;
import android.graphics.Bitmap;
/**
 * 打印图片
 * 实现类
 * @author jundao
 */
public class PrintImageItem implements IPrintTemplateItem {

	@Override
	public void printItem(final Template template,final Pagedetails pagedetails,Trade trade) throws Exception{
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
			        	//InputStream bmpis =  new URL(pagedetails.getImageUrl()).openStream();
			        	//Bitmap bitmap = BitmapFactory.decodeStream(bmpis);
						Bitmap bitmap = ImgUtil.getImage(pagedetails.getImageUrl());
						HPRTPrinterHelper.Expanded("" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"" + NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),bitmap,(byte)0);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
