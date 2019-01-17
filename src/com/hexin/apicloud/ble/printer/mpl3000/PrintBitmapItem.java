package com.hexin.apicloud.ble.printer.mpl3000;
import java.util.HashSet;
import java.util.Set;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient.BitmapListener;
import android.graphics.Bitmap;
/**
 * 打印位图
 * 实现类
 * @author jundao
 */
public class PrintBitmapItem implements BitmapListener {
	
	/**
	 * 富士通MPL3000打印工具类
	 */
	private Printer printer;
	
	/**
	 * 模板
	 */
	private Template template;
	
	/**
	 * 网络图片模板数据
	 */
	private Pagedetails pagedetails;
	
	/**
	 * 模板中包含的图片数量
	 */
	private int  imgNum;
	
	/**
	 * 打印方式  0:正常打印 1：旋转180度
	 */
	//private int printType;
	
	/**
	 * 保存图片 用于判断数量是否跟imgNum 相等
	 */
	public static  Set<String> imgSet = new HashSet<String>();
	
	/**
	 * 第一次加载网络图片后设置成true,之后不调用图片这里的打印
	 */
	public static boolean flag;
	
	
	public PrintBitmapItem(Printer printer,Template template,Pagedetails pagedetails,int imgNum,int printType) {
		super();
		this.printer = printer;
		this.template = template;
		this.pagedetails = pagedetails;
		this.imgNum = imgNum;
		//this.printType = printType;
	}

	@Override
	public void onError(int arg0) {
		// nothing;
	}

	/**
	 * 监听完成
	 */
	@Override
	public void onFinish(Bitmap bmp, boolean arg1) {
		if(bmp != null){
			printer.draw_bitmap(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), bmp,false);
			if(!imgSet.contains(pagedetails.getImageUrl())){
				imgSet.add(pagedetails.getImageUrl());
				if(imgSet.size() == imgNum && !flag){
					flag = true;
					printer.page_print(Printer.MarkNone);
				}
			}
		}

	}

}
