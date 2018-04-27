package com.hexin.apicloud.ble.printer.fujitsu;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
/**
 * 打印表格
 * 实现类
 * @author jundao
 */
public class PrintGridItem implements IPrintTemplateItem {

	@Override
	public void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) {
		//nothing;
	}

}
