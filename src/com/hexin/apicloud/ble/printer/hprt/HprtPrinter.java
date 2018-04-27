package com.hexin.apicloud.ble.printer.hprt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Pages;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.common.BleException;
import com.hexin.apicloud.ble.printer.IPrinter;
import com.hexin.apicloud.ble.printer.hprt.IPrintTemplateItem;
import com.hexin.apicloud.ble.printer.hprt.TemplateItemFactory;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import HPRTAndroidSDK.HPRTPrinterHelper;
import android.graphics.Bitmap;

/**
 * 汉印打印机
 * @author 军刀
 *
 */
public class HprtPrinter implements IPrinter{
	
	/**
	 * 已连接打印机
	 */
	private Map<String, UZModuleContext> mConnectPrinterMap;
	
    public HprtPrinter() {
		mConnectPrinterMap = new HashMap<String, UZModuleContext>();
	}
    
    /**
	 * 连接
	 * @param moduleContext
	 * @param address
	 * @param deviceName
	 */
	@Override
	public boolean connect(UZModuleContext moduleContext, String address,String deviceName) throws BleException{
		if (address == null || address.length() == 0) {
			throw BleException.PARAM_EXCEPTION;
		}
		try {
			new HPRTPrinterHelper(moduleContext.getContext(),deviceName);
			int portOpen = HPRTPrinterHelper.PortOpen("Bluetooth,"+address);
			if(portOpen == 0){
				mConnectPrinterMap.put(address, moduleContext);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
		}
		
	}

	/**
	 * 断开
	 * @param address
	 */
	@Override
	public boolean disconnect() throws BleException{
		try {
			if(!mConnectPrinterMap.isEmpty()){
				mConnectPrinterMap.clear();
			}
			if(HPRTPrinterHelper.IsOpened()){
				boolean isClose = HPRTPrinterHelper.PortClose();
				if(isClose){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		} catch (Exception e) {
			throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
		}
		
	}
	
	/**
	 * 是否连接
	 * @param moduleContext
	 * @param address
	 */
	@Override
	public boolean isconnected(String address) throws BleException{
		try {
			if(mConnectPrinterMap.containsKey(address)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
		}
		
	}

	/**
	 * 图片打印订单数据
	 * @param moduleContext
	 * @param width
	 * @param height
	 * @param bitmap
	 * @param printType
	 */
	@Override
	public void printByImg(UZModuleContext moduleContext,String width,String height,Bitmap bitmap,String printType) throws BleException{
		int status = 0;
		try {
			status = HPRTPrinterHelper.getstatus();
		}catch (Exception e) {
			throw new BleException(BleException.STATUS_EXCEPTION.getCode(),e);
		}
		//Toast.makeText(moduleContext.getContext() , "打印机状态:" + status , Toast.LENGTH_SHORT).show();
		switch (status) {
			// 打印机准备就绪
			case 0:
			try {
				HPRTPrinterHelper.printAreaSize("0", width, height, height, "1");
//				HPRTPrinterHelper.Expanded("0", "0",bitmap,(byte)0);
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"0","0","5","5","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"1","0","5","35","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"2","0","5","85","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"3","0","5","155","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"4","0","5","265","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"7","0","5","375","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"8","0","5","495","Hello 世界");
				HPRTPrinterHelper.SetMag("2","2");//对下面的字体进行放大(如不需要不用添加)
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"8","0","5","595","Hello 世界");
				HPRTPrinterHelper.SetMag("1","1");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"20","0","5","695","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"24","0","5","795","Hello 世界");
				HPRTPrinterHelper.Text(HPRTPrinterHelper.TEXT,"55","0","5","895","Hello 世界");
				HPRTPrinterHelper.Form();
				// 反打
				if("1".equals(printType)){
					HPRTPrinterHelper.PoPrint();
				}else{
					 HPRTPrinterHelper.Print();
				}
			} catch (Exception e) {
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
				break;
			// 打印机缺纸
			case 2:
				throw BleException.LACK_PAPER_EXCEPTION;
			// 打印机开盖
			case 6:
				throw BleException.OPEN_EXCEPTION;
			// 打印机出错
			default:
				throw BleException.OTHER_EXCEPTION;
		}
	}

	/**
	 * 指令打印订单数据
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param printType
	 */
	@Override
	public void printByCommand(UZModuleContext moduleContext, Template template, Trade trade,String printType) throws BleException {
		int status = 0;
		try {
			status = HPRTPrinterHelper.getstatus();
		}catch (Exception e) {
			throw new BleException(BleException.STATUS_EXCEPTION.getCode(),e);
		}
		switch (status) {
			// 打印机准备就绪
			case 0: case 1:
			try {
				HPRTPrinterHelper.printAreaSize("0","" + template.getTemplateWidth()*8, "" + template.getTemplateHeight()*8,"" + template.getTemplateHeight()*8, "1");
				List<Pages> pagesList = template.getPages();
				for(Pages page : pagesList){
					for(Pagedetails pagedetails : page.getPageDetails()){
						IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
						iPrintTemplateItem.printItem(template,pagedetails, trade);
					}
				}
				HPRTPrinterHelper.Form();
				// 反打
				if("1".equals(printType)){
					HPRTPrinterHelper.PoPrint();
				}else{
					 HPRTPrinterHelper.Print();
				}
			} catch (Exception e) {
				//Toast.makeText(moduleContext.getContext(), "打印异常:"+e.getMessage(),Toast.LENGTH_LONG).show();
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
				break;
			// 打印机缺纸
			case 2:
				throw BleException.LACK_PAPER_EXCEPTION;
			// 打印机开盖
			case 6:
				throw BleException.OPEN_EXCEPTION;
			// 打印机出错
			default:
				throw BleException.OTHER_EXCEPTION;
		}
	}
	
	
	/**
	 * 指令打印订单数据
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param printType
	 */
	@Override
	public void printByCommand(UZModuleContext moduleContext, Template template, List<Trade> trades,String printType) throws BleException {
		int status = 0;
		try {
			status = HPRTPrinterHelper.getstatus();
		}catch (Exception e) {
			throw new BleException(BleException.STATUS_EXCEPTION.getCode(),e);
		}
		switch (status) {
			// 打印机准备就绪
			case 0:
			try {
				for(Trade trade : trades){
					HPRTPrinterHelper.printAreaSize("0","" + template.getTemplateWidth()*8, "" + template.getTemplateHeight()*8,"" + template.getTemplateHeight()*8, "1");
					List<Pages> pagesList = template.getPages();
					for(Pages page : pagesList){
						for(Pagedetails pagedetails : page.getPageDetails()){
							IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
							iPrintTemplateItem.printItem(template,pagedetails, trade);
						}
					}
					HPRTPrinterHelper.Form();
					// 反打
					if("1".equals(printType)){
						HPRTPrinterHelper.PoPrint();
					}else{
						 HPRTPrinterHelper.Print();
					}
				}
			} catch (Exception e) {
				//Toast.makeText(moduleContext.getContext(), "打印异常:"+e.getMessage(),Toast.LENGTH_LONG).show();
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
				break;
			// 打印机缺纸
			case 2:
				throw BleException.LACK_PAPER_EXCEPTION;
			// 打印机开盖
			case 6:
				throw BleException.OPEN_EXCEPTION;
			// 打印机出错
			default:
				throw BleException.OTHER_EXCEPTION;
		}
	}
	
	
	/**
	 * 打印条码
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param type
	 */
	@Override
	public void printBarcode(UZModuleContext moduleContext,Template template,Trade trade,String type) throws BleException{
		//nothing;
	}
	
	/**
	 * 打印二维码
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param type
	 */
	@Override
	public void printQrcode(UZModuleContext moduleContext,Template template,Trade trade,String type) throws BleException{
		//nothing;
	}
	
}
