package com.hexin.apicloud.ble.printer.qr380;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import printpp.printpp_yt.PrintPP_CPCL;
/**
 * 打印空白项
 * 实现类
 * @author jundao
 */
public class PrintBlankItem implements IPrintTemplateItem {

	@Override
	public void printItem(PrintPP_CPCL iPrinter,Template template,Pagedetails pagedetails,Trade trade){
		//nothing;
	}

}
