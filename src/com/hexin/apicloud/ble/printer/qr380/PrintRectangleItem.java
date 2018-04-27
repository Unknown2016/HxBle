package com.hexin.apicloud.ble.printer.qr380;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import printpp.printpp_yt.PrintPP_CPCL;

/**
 * 打印矩形
 * 实现类
 * @author jundao
 */
public class PrintRectangleItem implements IPrintTemplateItem {

	/**
	 * lineWidth - 边框线条宽度
	 * top_left_x - 矩形框左上角 x 坐标 
	 * top_left_y - 矩形框左上角 y 坐标 
	 * bottom_right_x - 矩形框右下角 x 坐标 
	 * bottom_right_y - 矩形框右下角 y 坐标		
	 */
	@Override
	public void printItem(PrintPP_CPCL iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		//iPrinter.drawBox(lineWidth, top_left_x, top_left_y, bottom_right_x, bottom_right_y);
		
	}

}
