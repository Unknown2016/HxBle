package com.hexin.apicloud.ble.printer.qr386a;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Pages;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.common.BleException;
import com.hexin.apicloud.ble.enums.PrintItemEnum;
import com.hexin.apicloud.ble.printer.IPrinter;
import com.qr.printer.Printer;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient.ImageOption;
import android.graphics.Bitmap;

/**
 * 启锐386A打印机
 * @author 军刀
 *
 */
public class Qr386aPrinter implements IPrinter{
	
	/**
	 * 已连接打印机
	 */
	private Map<String, UZModuleContext> mConnectPrinterMap;
	
	private static Printer iPrinter;
	
    public Qr386aPrinter() {
    	iPrinter = new Printer();
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
			iPrinter.connect(deviceName,address);
			boolean ret = iPrinter.isConnected();
			if(ret){
				mConnectPrinterMap.put(address, moduleContext);
			}else{
				iPrinter.disconnect();
			}
			return ret;
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
			iPrinter.disconnect();
			return true;
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
		String status = "OK";
		try {
			status = iPrinter.printerStatus();
		}catch (Exception e) {
			throw BleException.STATUS_EXCEPTION;
		}
		if("CoverOpened".equalsIgnoreCase(status)){
			throw BleException.OPEN_EXCEPTION;
		}
		if("NoPaper".equalsIgnoreCase(status)){
			throw BleException.LACK_PAPER_EXCEPTION;
		}
		if(!"OK".equalsIgnoreCase(status)){
			throw BleException.OTHER_EXCEPTION;
		}
		try {
			iPrinter.pageSetup(Integer.valueOf(width),Integer.valueOf(height)); 
			iPrinter.drawGraphic(0, 0, bitmap.getWidth(), bitmap.getHeight(),bitmap);
			iPrinter.print(Integer.parseInt(printType),0);
		} catch (Exception e) {
			throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
		}
	}
	
	/**
	 * 指令打印订单数据
	 * @param moduleContext`
	 * @param template
	 * @param trade
	 * @param printType
	 */
	@Override
	public void printByCommand(final UZModuleContext moduleContext, Template template, Trade trade,final String printType) throws BleException {
		String status = "OK";
		try {
			status = iPrinter.printerStatus();
		}catch (Exception e) {
			throw BleException.STATUS_EXCEPTION;
		}
		if("CoverOpened".equalsIgnoreCase(status)){
			throw BleException.OPEN_EXCEPTION;
		}
		if("NoPaper".equalsIgnoreCase(status)){
			throw BleException.LACK_PAPER_EXCEPTION;
		}
		if("OK".equalsIgnoreCase(status) || "Printing".equalsIgnoreCase(status)){
			try {
				iPrinter.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
				List<Pages> pagesList = template.getPages();
				PrintBitmapItem bitmapItem = null;
				
				/**
				 * 启锐处理打印机制与汉印 新北洋不同，启锐是所有数据接收完后打印，其他品牌打印机是边接收数据边打印
				 * 打印电子面单数据中含有网络图片时，不能在当前主线程中运行，需要异步操作，第一次加载耗时，要禁用程序末端的打印，必须要等bitmap转换完成后直接调用PrintBitmapItem中的打印
				 * 后面打印时直接取的缓存中的bitmap,速度比第一次加载时快，这时禁用PrintBitmapItem中的打印，调用程序末端的打印
				 */
				for(Pages page : pagesList){
					int imgNum = 0;
					for(Pagedetails pagedetails : page.getPageDetails()){
						if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
							imgNum ++;
						}
					}
					for(Pagedetails pagedetails : page.getPageDetails()){
						if(pagedetails.getItemType() == PrintItemEnum.WATERMARK.ordinal()){
							IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
							iPrintTemplateItem.printItem(iPrinter,template,pagedetails, trade);
						}
					}
					for(Pagedetails pagedetails : page.getPageDetails()){
						// 网络图片 异步处理
						if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
							bitmapItem = new PrintBitmapItem(iPrinter,template,pagedetails,imgNum,Integer.parseInt(printType));
							APICloudHttpClient httpClient = APICloudHttpClient.createInstance(moduleContext.getContext());
							ImageOption imageOption = APICloudHttpClient.builder(pagedetails.getImageUrl());
							httpClient.getImage(imageOption,bitmapItem); 
						}else{
							IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
							iPrintTemplateItem.printItem(iPrinter,template,pagedetails, trade);
						}
					}
				}
				// 0:正常打印 1：旋转180度
				if(bitmapItem != null && !PrintBitmapItem.flag){
					//nothing;
				}else{
					iPrinter.print(Integer.parseInt(printType),0);
				}
				
			} catch (Exception e) {
				//Toast.makeText(moduleContext.getContext() , "exception---------- "+getStackMsg(e) , Toast.LENGTH_SHORT).show();
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
		}else{
			throw BleException.OTHER_EXCEPTION;
		}
	}
	
	
	/**
	 * 指令打印订单数据
	 * @param moduleContext`
	 * @param template
	 * @param trade
	 * @param printType
	 */
	@Override
	public void printByCommand(final UZModuleContext moduleContext, Template template, List<Trade> trades,final String printType) throws BleException {
		String status = "OK";
		try {
			status = iPrinter.printerStatus();
		}catch (Exception e) {
			throw BleException.STATUS_EXCEPTION;
		}
		if("CoverOpened".equalsIgnoreCase(status)){
			throw BleException.OPEN_EXCEPTION;
		}
		if("NoPaper".equalsIgnoreCase(status)){
			throw BleException.LACK_PAPER_EXCEPTION;
		}
		if("OK".equalsIgnoreCase(status)|| "Printing".equalsIgnoreCase(status)){
			try {
				for(Trade trade : trades){
					iPrinter.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
					List<Pages> pagesList = template.getPages();
					PrintBitmapItem bitmapItem = null;
					
					/**
					 * 启锐处理打印机制与汉印 新北洋不同，启锐是所有数据接收完后打印，其他品牌打印机是边接收数据边打印
					 * 打印电子面单数据中含有网络图片时，不能在当前主线程中运行，需要异步操作，第一次加载耗时，要禁用程序末端的打印，必须要等bitmap转换完成后直接调用PrintBitmapItem中的打印
					 * 后面打印时直接取的缓存中的bitmap,速度比第一次加载时快，这时禁用PrintBitmapItem中的打印，调用程序末端的打印
					 */
					for(Pages page : pagesList){
						int imgNum = 0;
						for(Pagedetails pagedetails : page.getPageDetails()){
							if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
								imgNum ++;
							}
						}
						for(Pagedetails pagedetails : page.getPageDetails()){
							if(pagedetails.getItemType() == PrintItemEnum.WATERMARK.ordinal()){
								IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
								iPrintTemplateItem.printItem(iPrinter,template,pagedetails, trade);
							}
						}
						for(Pagedetails pagedetails : page.getPageDetails()){
							// 网络图片 异步处理
							if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
								bitmapItem = new PrintBitmapItem(iPrinter,template,pagedetails,imgNum,Integer.parseInt(printType));
								APICloudHttpClient httpClient = APICloudHttpClient.createInstance(moduleContext.getContext());
								ImageOption imageOption = APICloudHttpClient.builder(pagedetails.getImageUrl());
								httpClient.getImage(imageOption,bitmapItem); 
							}else{
								IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
								iPrintTemplateItem.printItem(iPrinter,template,pagedetails, trade);
							}
						}
					}
					// 0:正常打印 1：旋转180度
					if(bitmapItem != null && !PrintBitmapItem.flag){
						//nothing;
					}else{
						iPrinter.print(Integer.parseInt(printType),0);
					}
				}
			} catch (Exception e) {
				//Toast.makeText(moduleContext.getContext() , "exception---------- "+getStackMsg(e) , Toast.LENGTH_SHORT).show();
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
		}else{
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
		iPrinter.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
		iPrinter.drawBarCode(0 , 0, trade.getOutSid(), 1 ,0,Integer.parseInt(type),56);
		iPrinter.print(0,0);
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
		iPrinter.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
		iPrinter.drawQrCode(0,0,"http://www.baidu.com", 0,Integer.valueOf(type), 5);
		iPrinter.print(0,0);
	}

}
