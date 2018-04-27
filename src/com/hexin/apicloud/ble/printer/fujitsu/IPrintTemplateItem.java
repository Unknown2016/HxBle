package com.hexin.apicloud.ble.printer.fujitsu;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;

/**
 * 打印快递单项(富士通打印机)
 * 接口
 * @author jundao
 */
public interface IPrintTemplateItem {
	
	/**
	 * 打印数据
	 * @param pagedetails
	 * @param trade
	 */
	void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) throws Exception;
}
