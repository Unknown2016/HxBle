package com.hexin.apicloud.ble.printer.snbc4bplc;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;

/**
 * 打印矩形
 * 实现类
 * @author jundao
 */
public class PrintRectangleItem implements IPrintTemplateItem {

	@Override
	public void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		/**
		 *	x	横坐标，单位：点。 
		 *	y	纵坐标，单位：点。 
		 *	width	矩形宽度，单位：点。 
		 *	height	矩形高度，单位：点。 
		 *	thickness	线条宽度，单位：点。 
		 */
       //labelEdit.printRectangle(NumberUtil.double2int(pagedetails.getX()),NumberUtil.double2int(pagedetails.getY()), 576, 1300, 1);
	}

}
