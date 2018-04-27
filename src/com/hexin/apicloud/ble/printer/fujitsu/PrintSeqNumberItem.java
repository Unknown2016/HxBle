package com.hexin.apicloud.ble.printer.fujitsu;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;

/**
 * 打印序号
 * 实现类
 * @author jundao
 */
public class PrintSeqNumberItem implements IPrintTemplateItem {

	@Override
	public void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		//nothing;
	}

}
