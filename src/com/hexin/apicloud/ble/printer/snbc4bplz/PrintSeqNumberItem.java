package com.hexin.apicloud.ble.printer.snbc4bplz;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;

/**
 * 打印序号
 * 实现类
 * @author jundao
 */
public class PrintSeqNumberItem implements IPrintTemplateItem {

	@Override
	public void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		//nothing;
	}

}
