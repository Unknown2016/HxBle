package com.hexin.apicloud.ble.printer.snbc4bplc;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;

/**
 * 打印快递单项(新北洋打印机)
 * 接口
 * @author jundao
 */
public interface IPrintTemplateItem {
	
	/**
	 * 打印数据
	 * @param labelEdit
	 * @param pagedetails
	 * @param trade
	 */
	void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception;
}
