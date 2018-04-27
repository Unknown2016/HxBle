package com.hexin.apicloud.ble.printer.qr386a;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.qr.printer.Printer;
/**
 * 打印空白项
 * 实现类
 * @author jundao
 */
public class PrintBlankItem implements IPrintTemplateItem {

	@Override
	public void printItem(Printer iPrinter,Template template,Pagedetails pagedetails,Trade trade){
		//nothing;
	}

}
