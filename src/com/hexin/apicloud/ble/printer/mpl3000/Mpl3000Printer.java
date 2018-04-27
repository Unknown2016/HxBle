package com.hexin.apicloud.ble.printer.mpl3000;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Pages;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.common.BleException;
import com.hexin.apicloud.ble.printer.IPrinter;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import android.graphics.Bitmap;

/**
 * 富士通MPL3000打印机
 * @author 军刀
 *
 */
public class Mpl3000Printer implements IPrinter{
	
	/**
	 * 已连接打印机
	 */
	private Map<String, UZModuleContext> mConnectPrinterMap;
	
	private static Printer printer;
    public Mpl3000Printer() {
    	printer = new Printer();
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
			boolean ret = printer.open(address);
			if(ret){
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
			boolean isClose = printer.close();
			if(isClose){
				return true;
			}else{
				return false;
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
		printer.close();
//		 if (lpk130.openDevice("00:0C:B6:03:33:CA") >= 0){
//			 lpk130.NFCP_createPage(576, 1260);
//				try {
//					lpk130.NFCP_Page_setText(5,5, "Hello 世界", 1, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,35, "Hello 世界", 1, 0, 0, false, false);
//					lpk130.NFCP_Page_setText(5,85, "Hello 世界", 2, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,115, "Hello 世界", 2, 0, 0, false, false);
//					lpk130.NFCP_Page_setText(5,155, "Hello 世界", 3, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,205, "Hello 世界", 3, 0, 0, false, false);
//					lpk130.NFCP_Page_setText(5,265, "Hello 世界", 4, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,305, "Hello 世界", 4, 0, 0, false, false);
//					lpk130.NFCP_Page_setText(5,375, "Hello 世界", 5, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,425, "Hello 世界", 5, 0, 0, false, false);
//					lpk130.NFCP_Page_setText(5,495, "Hello 世界", 6, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,595, "Hello 世界", 6, 0, 0, false, false);
//					lpk130.NFCP_Page_setText(5,695, "Hello 世界", 7, 0, 1, false, false);
//					lpk130.NFCP_Page_setText(5,905, "Hello 世界", 7, 0, 0, false, false);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				lpk130.NFCP_printPage(0, 0);
//		 }else{
//			 Toast.makeText(moduleContext.getContext(), "蓝牙连接失败",Toast.LENGTH_LONG).show();
//		 }
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
		// 查询打印机状态
		// 操作结果 1：打印头过热；2：缺纸 ；3:纸舱盖打开； －1:读取错误
//		lpk130.closeDevice();
//		 if (lpk130.openDevice("00:0C:B6:03:33:CA") >= 0){
				try {
					if(printer.get_state(5000) == Printer.PrinterState.Nomal){
						printer.page_creat(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8,1);
						List<Pages> pagesList = template.getPages();
						for(Pages page : pagesList){
							for(Pagedetails pagedetails : page.getPageDetails()){
								IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
								iPrintTemplateItem.printItem(printer,template,pagedetails, trade);
							}
						}
						// 反打 富士通不支持180反转 暂时只支持90
						if("1".equals(printType)){
							printer.page_print(0);
						}else{
							 printer.page_print(0);
						}
					}else{
						throw BleException.STATUS_EXCEPTION;
					}
				}catch (Exception e) {
					throw BleException.OTHER_EXCEPTION;
				}
//		 }
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
//		int status = 0;
//		try {
//			status = HPRTPrinterHelper.getstatus();
//		}catch (Exception e) {
//			throw new BleException(BleException.STATUS_EXCEPTION.getCode(),e);
//		}
//		switch (status) {
//			// 打印机准备就绪
//			case 0:
//			try {
//				for(Trade trade : trades){
//					HPRTPrinterHelper.printAreaSize("0","" + template.getTemplateWidth()*8, "" + template.getTemplateHeight()*8,"" + template.getTemplateHeight()*8, "1");
//					List<Pages> pagesList = template.getPages();
//					for(Pages page : pagesList){
//						for(Pagedetails pagedetails : page.getPageDetails()){
//							IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
//							iPrintTemplateItem.printItem(template,pagedetails, trade);
//						}
//					}
//					HPRTPrinterHelper.Form();
//					// 反打
//					if("1".equals(printType)){
//						HPRTPrinterHelper.PoPrint();
//					}else{
//						 HPRTPrinterHelper.Print();
//					}
//				}
//			} catch (Exception e) {
//				//Toast.makeText(moduleContext.getContext(), "打印异常:"+e.getMessage(),Toast.LENGTH_LONG).show();
//				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
//			}
//				break;
//			// 打印机缺纸
//			case 2:
//				throw BleException.LACK_PAPER_EXCEPTION;
//			// 打印机开盖
//			case 6:
//				throw BleException.OPEN_EXCEPTION;
//			// 打印机出错
//			default:
//				throw BleException.OTHER_EXCEPTION;
//		}
		//NFCP_feed
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
		/**
		 * x - 打印的起始横坐标
		 * y - 打印的起始纵坐标
		 * str - 字符串
		 * barcodetype - 条码类型 0：CODE39；1：CODE128；2：CODE93；3：CODEBAR；4：EAN8；5：EAN13；6：UPCA ;7:UPC-E;8:ITF
		 * rotate - 旋转角度 0：不旋转；1：90度；2：180°；3:270°
		 * barWidth - 条码宽度
		 * barHeight - 条码高度
		 * 
		 */
//		lpk130.closeDevice();
//		 if (lpk130.openDevice("00:0C:B6:03:33:CA") >= 0){
//			 lpk130.NFCP_createPage(576, 260);
//				try {
//					lpk130.NFCP_Page_drawBar(5,5,trade.getOutSid(), 1,0,Integer.parseInt(type),56);
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				lpk130.NFCP_printPage(0, 0);
//		 }else{
//			 Toast.makeText(moduleContext.getContext(), "蓝牙连接失败",Toast.LENGTH_LONG).show();
//		 }
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
//		lpk130.closeDevice();
//		 if (lpk130.openDevice("00:0C:B6:03:33:CA") >= 0){
//			 lpk130.NFCP_createPage(576, 260);
//				try {
//					lpk130.NFCP_Page_printQrCode(5, 5, 0, Integer.parseInt(type), 3, "http://www.baidu.com");
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				lpk130.NFCP_printPage(0, 0);
//		 }else{
//			 Toast.makeText(moduleContext.getContext(), "蓝牙连接失败",Toast.LENGTH_LONG).show();
//		 }
	}
	
}
