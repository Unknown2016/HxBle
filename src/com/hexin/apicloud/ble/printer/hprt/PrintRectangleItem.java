package com.hexin.apicloud.ble.printer.hprt;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;

/**
 * 打印矩形
 * 实现类
 * @author jundao
 */
public class PrintRectangleItem implements IPrintTemplateItem {

	@Override
	public void printItem(Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		/**
		 * X0:左上角的 X 坐标。
		 * Y0:左上角的 Y 坐标。
		 * X1:右下角的 X 坐标。 
		 * Y1:右下角的 Y 坐标。 
		 * Width:线条的单位宽度
		 */
		// HPRTPrinterHelper.Box("" + NumberUtil.double2int(pagedetails.getX()),"" + NumberUtil.double2int(pagedetails.getY()),"150","150","1");
	}

}
