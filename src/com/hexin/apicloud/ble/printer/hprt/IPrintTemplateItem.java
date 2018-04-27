package com.hexin.apicloud.ble.printer.hprt;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;

/**
 * 打印快递单项(汉印打印机)
 * 接口
 * @author jundao
 */
public interface IPrintTemplateItem {
	
	/**
	 * 打印数据
	 * @param pagedetails
	 * @param trade
	 */
	void printItem(Template template,Pagedetails pagedetails,Trade trade) throws Exception;
}
