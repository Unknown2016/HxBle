package com.hexin.apicloud.ble.printer.mpl3000;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.ImgUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;

import android.graphics.Bitmap;
/**
 * 打印图片
 * 实现类
 * @author jundao
 */
public class PrintImageItem implements IPrintTemplateItem {

	/**
	 * x 偏移 X 坐标的量,单位为 mm
	 * y 偏移 Y 坐标的量,单位为 mm
	 * bmpSrc 图片 
	 * rotate 旋转
	 */
	@Override
	public void printItem(final Printer printer,final Template template,final Pagedetails pagedetails,Trade trade) throws Exception{
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
			        	//InputStream bmpis =  new URL(pagedetails.getImageUrl()).openStream();
			        	//Bitmap bitmap = BitmapFactory.decodeStream(bmpis);
						Bitmap bitmap = ImgUtil.getImage(pagedetails.getImageUrl());
						printer.draw_bitmap(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bitmap,false);
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
