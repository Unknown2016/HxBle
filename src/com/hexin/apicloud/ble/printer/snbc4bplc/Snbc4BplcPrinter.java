package com.hexin.apicloud.ble.printer.snbc4bplc;
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
import com.snbc.sdk.barcode.BarInstructionImpl.BarPrinter;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import com.snbc.sdk.barcode.enumeration.InstructionType;
import com.snbc.sdk.barcode.enumeration.PrinterDirection;
import com.snbc.sdk.connect.connectImpl.BluetoothConnect;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient;
import com.uzmap.pkg.uzkit.request.APICloudHttpClient.ImageOption;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;

/**
 * 新北洋打印机
 * P32 P33（BPLC指令）
 * @author 军刀
 *
 */
public class Snbc4BplcPrinter implements IPrinter{
	
	/**
	 * 蓝牙适配器
	 */
	private BluetoothAdapter mBluetoothAdapter;
	
	/**
	 * 已连接打印机
	 */
	private Map<String, UZModuleContext> mConnectPrinterMap;
	
	/**
	 * SNBC 打印机
	 */
	private BluetoothConnect bluetoothConnect;
	
    private BarPrinter barPrinter;
    
    public Snbc4BplcPrinter() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
			bluetoothConnect = new BluetoothConnect(mBluetoothAdapter, address);
			bluetoothConnect.DecodeType("GB18030");
			bluetoothConnect.connect();
			BarPrinter.BarPrinterBuilder builder = new BarPrinter.BarPrinterBuilder();
			builder.buildDeviceConnenct(bluetoothConnect);
			builder.buildInstruction(InstructionType.BPLC);
			barPrinter = builder.getBarPrinter();
			mConnectPrinterMap.put(address, moduleContext);
			return true;
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
			if (bluetoothConnect != null) {
				bluetoothConnect.disconnect();
			}
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
		try {
			// 180度反打
			if("1".equals(printType)){
				barPrinter.labelConfig().setPrintDirection(PrinterDirection.Rotation180);
			}else{
				barPrinter.labelConfig().setPrintDirection(PrinterDirection.Normal);
			}
			ILabelEdit labelEdit = barPrinter.labelEdit();
			labelEdit.setColumn(1,8);
			labelEdit.setLabelSize(Integer.parseInt(width),Integer.parseInt(height));
			labelEdit.printImage(0, 0, bitmap);
			barPrinter.labelControl().print(1,1);
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
	public void printByCommand(UZModuleContext moduleContext, Template template, Trade trade,String printType) throws BleException {
		int prt_st;	
		byte pszCommand[] = {0x1D,0x61,0x0f};
		byte[] recbuf = new byte[64];
		int readBytes = 0;
		
		try {
			//发送查询状态指令
			bluetoothConnect.write(pszCommand);
			//查询状态
			readBytes = bluetoothConnect.read(recbuf);
		}catch (Exception e) {
			throw BleException.SYS_EXCEPTION;
		}
		if(readBytes <= 0){	
			 prt_st = 0;
		}
		//判断打印机是否缺纸
		if((recbuf[2] & 0x0C) == 0x0C){
			prt_st = 1;
			throw BleException.LACK_PAPER_EXCEPTION;
		}
		//判断打印机仓盖是否打开
		if((recbuf[0] & 0x20) == 0x20){
			prt_st = 2;
			throw BleException.OPEN_EXCEPTION;
		}
		try {
			 // 180度反打
			if("1".equals(printType)){
				barPrinter.labelConfig().setPrintDirection(PrinterDirection.Rotation180);
			}else{
				barPrinter.labelConfig().setPrintDirection(PrinterDirection.Normal);
			}
			ILabelEdit labelEdit = barPrinter.labelEdit();
			//定位指令
			String set = "GAP-SENSE\r\nFORM\r\n";
			labelEdit.setColumn(1,0);
			labelEdit.setLabelSize(template.getTemplateWidth()*8,template.getTemplateHeight()*8);
			List<Pages> pagesList = template.getPages();
			PrintBitmapItem bitmapItem = null;
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
						iPrintTemplateItem.printItem(labelEdit,template,pagedetails, trade);
					}
				}
				for(Pagedetails pagedetails : page.getPageDetails()){
					// 网络图片 异步处理
					if(pagedetails.getItemType() == PrintItemEnum.IMAGE.ordinal()){
						bitmapItem = new PrintBitmapItem(labelEdit,barPrinter,template,pagedetails,imgNum,Integer.parseInt(printType));
						APICloudHttpClient httpClient = APICloudHttpClient.createInstance(moduleContext.getContext());
						ImageOption imageOption = APICloudHttpClient.builder(pagedetails.getImageUrl());
						httpClient.getImage(imageOption,bitmapItem); 
					}else{
						IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
						iPrintTemplateItem.printItem(labelEdit,template,pagedetails, trade);
					}
					
				}
			}
			bluetoothConnect.write(set.getBytes("GB18030"));
			// 0:正常打印 1：旋转180度
			if(bitmapItem != null && !PrintBitmapItem.flag){
				//nothing;
			}else{
				barPrinter.labelControl().print(1,1);
			}
		} catch (Exception e) {
			//Toast.makeText(moduleContext.getContext(), "打印异常:"+ErrorUtil.getStackMsg(e),Toast.LENGTH_LONG).show();
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
	public void printByCommand(UZModuleContext moduleContext, Template template, List<Trade> trades,String printType) throws BleException {
		int prt_st;	
		byte pszCommand[] = {0x1D,0x61,0x0f};
		byte[] recbuf = new byte[64];
		int readBytes = 0;
		
		try {
			//发送查询状态指令
			bluetoothConnect.write(pszCommand);
			//查询状态
			readBytes = bluetoothConnect.read(recbuf);
		}catch (Exception e) {
			throw BleException.SYS_EXCEPTION;
		}
		if(readBytes <= 0){	
			 prt_st = 0;
		}
		//判断打印机是否缺纸
		if((recbuf[2] & 0x0C) == 0x0C){
			prt_st = 1;
			throw BleException.LACK_PAPER_EXCEPTION;
		}
		//判断打印机仓盖是否打开
		if((recbuf[0] & 0x20) == 0x20){
			prt_st = 2;
			throw BleException.OPEN_EXCEPTION;
		}
		try {
			for(Trade trade : trades){
				 // 180度反打
				if("1".equals(printType)){
					barPrinter.labelConfig().setPrintDirection(PrinterDirection.Rotation180);
				}else{
					barPrinter.labelConfig().setPrintDirection(PrinterDirection.Normal);
				}
				ILabelEdit labelEdit = barPrinter.labelEdit();
				//定位指令
				String set = "GAP-SENSE\r\nFORM\r\n";
				labelEdit.setColumn(1,0);
				labelEdit.setLabelSize(template.getTemplateWidth()*8,template.getTemplateHeight()*8);
				List<Pages> pagesList = template.getPages();
				for(Pages page : pagesList){
					for(Pagedetails pagedetails : page.getPageDetails()){
						IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
						iPrintTemplateItem.printItem(labelEdit,template,pagedetails, trade);
					}
				}
				bluetoothConnect.write(set.getBytes("GB18030"));
				barPrinter.labelControl().print(1,1);
			}
		} catch (Exception e) {
			//Toast.makeText(moduleContext.getContext(), "打印异常:"+ErrorUtil.getStackMsg(e),Toast.LENGTH_LONG).show();
			throw new BleException(BleException.SYS_EXCEPTION.getCode(),e);
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
	
	@Override
	public boolean sendCmd(String cmd,String printType) throws BleException{
		return false;
	}
}
