package com.hexin.apicloud.ble.printer.qr386a;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.qr.printer.Printer;
/**
 * 打印快递单项(启锐386打印机)
 * 接口
 * @author jundao
 */
public interface IPrintTemplateItem {
	
	/**
	 * 打印数据
	 * @param pagedetails
	 * @param trade
	 */
	void printItem(Printer iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception;
}
