package com.hexin.apicloud.ble.printer.qr380;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import printpp.printpp_yt.PrintPP_CPCL;
/**
 * 打印快递单项(启锐380打印机)
 * 接口
 * @author jundao
 */
public interface IPrintTemplateItem {
	
	/**
	 * 打印数据
	 * @param pagedetails
	 * @param trade
	 */
	void printItem(PrintPP_CPCL iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception;
}
