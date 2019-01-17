package com.hexin.apicloud.ble.printer.qr380;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
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
import com.hexin.apicloud.ble.printer.qr380.PrintBitmapItem;
import com.qr.printer.Printer;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient.ImageOption;
import android.graphics.Bitmap;
import android.widget.Toast;
import printpp.printpp_yt.PrintPP_CPCL;

/**
 * 启锐380打印机
 * @author 军刀
 *
 */
public class Qr380Printer implements IPrinter{
	
	/**
	 * 已连接打印机
	 */
	private Map<String, UZModuleContext> mConnectPrinterMap;
	
	private static PrintPP_CPCL printPP_cpcl;
	
    public Qr380Printer() {
    	printPP_cpcl = new PrintPP_CPCL();
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
			printPP_cpcl.connect(deviceName,address);
			boolean ret = printPP_cpcl.isConnected();
			if(ret){
				mConnectPrinterMap.put(address, moduleContext);
			}else{
				printPP_cpcl.disconnect();
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
			printPP_cpcl.disconnect();
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
			status = printPP_cpcl.printerStatus();
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
			printPP_cpcl.pageSetup(Integer.valueOf(width),Integer.valueOf(height)); 
			printPP_cpcl.drawGraphic(0, 0, bitmap.getWidth(), bitmap.getHeight(),bitmap);
			printPP_cpcl.print(Integer.parseInt(printType),0);
		} catch (Exception e) {
			throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
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
	public void printByCommand(final UZModuleContext moduleContext, Template template, final Trade trade,String printType) throws BleException {
		String status = "OK";
		try {
			status = printPP_cpcl.printerStatus();
		}catch (Exception e) {
			throw BleException.STATUS_EXCEPTION;
		}
		if("OK".equalsIgnoreCase(status) || "Printing".equalsIgnoreCase(status)){
			try {
				printPP_cpcl.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
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
							iPrintTemplateItem.printItem(printPP_cpcl,template,pagedetails, trade);
						}
					}
					for(Pagedetails pagedetails : page.getPageDetails()){
						// 网络图片 异步处理
						if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
							bitmapItem = new PrintBitmapItem(printPP_cpcl,template, pagedetails,imgNum,Integer.parseInt(printType));
							APICloudHttpClient httpClient = APICloudHttpClient.createInstance(moduleContext.getContext());
							ImageOption imageOption = APICloudHttpClient.builder(pagedetails.getImageUrl());
							httpClient.getImage(imageOption,bitmapItem); 
						}else{
							if(pagedetails.getItemType() != PrintItemEnum.WATERMARK.ordinal()){
								IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
								iPrintTemplateItem.printItem(printPP_cpcl,template,pagedetails, trade);
							}
						}
						
					}
				}
				// 0:正常打印 1：旋转180度
				if(bitmapItem != null && !PrintBitmapItem.flag){
					//nothing;
				}else{
					//skip - 0:打印结束后不定位,直接停止; 1:打印结束后定位到标签分割线,如果 无缝隙,最大进纸 30mm 后停止
					printPP_cpcl.print(Integer.parseInt(printType),1);
				}
			} catch (Exception e) {
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
		}else{
			if("CoverOpened".equalsIgnoreCase(status)){
				throw BleException.OPEN_EXCEPTION;
			}
			if("NoPaper".equalsIgnoreCase(status)){
				throw BleException.LACK_PAPER_EXCEPTION;
			}
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
	public void printByCommand(final UZModuleContext moduleContext, Template template, final List<Trade> trades,String printType) throws BleException {
		String status = "OK";
		try {
			status = printPP_cpcl.printerStatus();
		}catch (Exception e) {
			throw BleException.STATUS_EXCEPTION;
		}
		if("CoverOpened".equalsIgnoreCase(status)){
			throw BleException.OPEN_EXCEPTION;
		}
		if("NoPaper".equalsIgnoreCase(status)){
			throw BleException.LACK_PAPER_EXCEPTION;
		}
		if("OK".equalsIgnoreCase(status)){
			try {
				for(Trade trade : trades){
					printPP_cpcl.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
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
							// 网络图片 异步处理
							if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
								bitmapItem = new PrintBitmapItem(printPP_cpcl,template, pagedetails,imgNum,Integer.parseInt(printType));
								APICloudHttpClient httpClient = APICloudHttpClient.createInstance(moduleContext.getContext());
								ImageOption imageOption = APICloudHttpClient.builder(pagedetails.getImageUrl());
								httpClient.getImage(imageOption,bitmapItem); 
							}else{
								IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
								iPrintTemplateItem.printItem(printPP_cpcl,template,pagedetails, trade);
							}
							
						}
					}
					// 0:正常打印 1：旋转180度
					if(bitmapItem != null && !PrintBitmapItem.flag){
						//nothing;
					}else{
						//0:正常打印,不旋转; 1:整个页面顺时针旋转 180°后,再打印 skip - 0:打印结束后不定位,直接停止; 1:打印结束后定位到标签分割线,如果 无缝隙,最大进纸 30mm 后停止
						printPP_cpcl.print(Integer.parseInt(printType),1);
					}
				}
			} catch (Exception e) {
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
		}else{
			Toast.makeText(moduleContext.getContext(), "打印异常:"+status,Toast.LENGTH_LONG).show();
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
		printPP_cpcl.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
		printPP_cpcl.drawBarCode(0 , 0, trade.getOutSid(), 1 ,0,Integer.parseInt(type),56);
		printPP_cpcl.print(0,0);
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
		printPP_cpcl.pageSetup(template.getTemplateWidth()*8 ,template.getTemplateHeight()*8);
		printPP_cpcl.drawQrCode(0,0,"http://www.baidu.com", 0,Integer.valueOf(type), 5);
		printPP_cpcl.print(0,0);
	}
	
	@Override
	public boolean sendCmd(String cmd,String printType) throws BleException{
		String status = "OK";
		try {
			status = printPP_cpcl.printerStatus();
		}catch (Exception e) {
			throw BleException.STATUS_EXCEPTION;
		}
		if("OK".equalsIgnoreCase(status) || "Printing".equalsIgnoreCase(status)){
			try {
//				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(cmd.getBytes(Charset.forName("UTF-8"))),Charset.forName("UTF-8")));
//				String line;
				// 反射调用sdk中发送指令接口
				Method method = PrintPP_CPCL.class.getDeclaredMethod("portSendCmd",String.class);
				method.setAccessible(true);
				method.invoke(printPP_cpcl,cmd);
//				while((line = bufferReader.readLine()) != null){
//					method.invoke(printPP_cpcl,line);
//				}
				return true;
			} catch (Exception e) {
				throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
			}
		}else{
			if("CoverOpened".equalsIgnoreCase(status)){
				throw BleException.OPEN_EXCEPTION;
			}
			if("NoPaper".equalsIgnoreCase(status)){
				throw BleException.LACK_PAPER_EXCEPTION;
			}
			throw BleException.OTHER_EXCEPTION;
		}
	}
}
