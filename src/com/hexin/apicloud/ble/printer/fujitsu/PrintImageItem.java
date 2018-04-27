package com.hexin.apicloud.ble.printer.fujitsu;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.ImgUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import android.graphics.Bitmap;
/**
 * 打印图片
 * 实现类
 * @author jundao
 */
public class PrintImageItem implements IPrintTemplateItem {

	/**
	 * 页模式下打印位图
     * 参数:
     * x - 起始横坐标
 	 * y - 起始纵坐标
	 * bitmap - 位图
	 */
	@Override
	public void printItem(final LPK130 lpk130,final Template template,final Pagedetails pagedetails,Trade trade) throws Exception{
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
			        	//InputStream bmpis =  new URL(pagedetails.getImageUrl()).openStream();
			        	//Bitmap bitmap = BitmapFactory.decodeStream(bmpis);
						Bitmap bitmap = ImgUtil.getImage(pagedetails.getImageUrl());
						lpk130.NFCP_Page_printBitmap(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bitmap);
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
