package com.hexin.apicloud.ble.printer.mpl3000;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;
/**
 * 打印空白项
 * 实现类
 * @author jundao
 */
public class PrintBlankItem implements IPrintTemplateItem {

	@Override
	public void printItem(Printer printer,Template template,Pagedetails pagedetails,Trade trade){
		//nothing;
	}

}
