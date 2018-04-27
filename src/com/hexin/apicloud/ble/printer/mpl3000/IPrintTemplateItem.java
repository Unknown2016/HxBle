package com.hexin.apicloud.ble.printer.mpl3000;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;

/**
 * 打印快递单项(富士通MPL3000打印机)
 * 接口
 * @author jundao
 */
public interface IPrintTemplateItem {
	
	/**
	 * 打印数据
	 * @param pagedetails
	 * @param trade
	 */
	void printItem(Printer printer,Template template,Pagedetails pagedetails,Trade trade) throws Exception;
}
