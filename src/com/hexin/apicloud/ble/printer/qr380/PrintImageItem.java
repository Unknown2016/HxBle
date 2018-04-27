package com.hexin.apicloud.ble.printer.qr380;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.ImgUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import android.graphics.Bitmap;
import printpp.printpp_yt.PrintPP_CPCL;
/**
 * 打印图片
 * 实现类
 * @author jundao
 * Volley框架 https://my.oschina.net/zhangqie/blog/849019
 */
public class PrintImageItem implements IPrintTemplateItem {
	

	public PrintImageItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * start_x - 图片起始点 x 坐标 
	 * start_y - 图片起始点 y 坐标 
	 * bmp_size_x - 图片的宽度 
	 * bmp_size_y - 图片的高度 
	 * bmp - 图片 
	 * 
	 */
	@Override
	public void printItem(final PrintPP_CPCL iPrinter,final Template template,final Pagedetails pagedetails,Trade trade) throws Exception{
		new Thread(new Runnable() {
			@Override
			public void run() {
		        try {
		            Bitmap bmp = ImgUtil.getImage(pagedetails.getImageUrl());
		            iPrinter.drawGraphic(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bmp.getWidth(), bmp.getHeight(),bmp);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}).start();
		
	}
	
}
