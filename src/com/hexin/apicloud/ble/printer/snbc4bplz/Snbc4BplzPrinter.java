package com.hexin.apicloud.ble.printer.snbc4bplz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Pages;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.common.BleException;
import com.hexin.apicloud.ble.printer.IPrinter;
import com.snbc.sdk.barcode.BarInstructionImpl.BarPrinter;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import com.snbc.sdk.barcode.enumeration.InstructionType;
import com.snbc.sdk.barcode.enumeration.PrinterDirection;
import com.snbc.sdk.connect.connectImpl.BluetoothConnect;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import com.snbc.sdk.barcode.BarInstructionImpl.*;

/**
 * 新北洋打印机
 * P36 L640H V540L（BPLZ指令）
 * @author 军刀
 *
 */
public class Snbc4BplzPrinter implements IPrinter{
	
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
    
    public Snbc4BplzPrinter() {
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
			builder.buildInstruction(InstructionType.BPLZ);
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
		int code = 1001;
		try {
			BPLZQueryPrinterStatusUtil queryUtil = new BPLZQueryPrinterStatusUtil(bluetoothConnect,true);
			if(queryUtil.getPrinterStatus() == 0){
				if(queryUtil.isHeadOpened()){
					code = BleException.OPEN_EXCEPTION.getCode();
					throw BleException.OPEN_EXCEPTION;
				}
				if(queryUtil.isPaperOut()){
					code = BleException.LACK_PAPER_EXCEPTION.getCode();
					throw BleException.LACK_PAPER_EXCEPTION;
				}
				if(queryUtil.isPaused()){
					code = BleException.PAUSE_EXCEPTION.getCode();
					throw BleException.PAUSE_EXCEPTION;
				}
				if(!queryUtil.isReadyToPrint()){
					code = BleException.OTHER_EXCEPTION.getCode();
					throw BleException.OTHER_EXCEPTION;
				}
			}else{
				code = BleException.STATUS_EXCEPTION.getCode();
				throw BleException.STATUS_EXCEPTION;
			}
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
			throw new BleException(code,e);
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
		int code = 1001;
		try {
			BPLZQueryPrinterStatusUtil queryUtil = new BPLZQueryPrinterStatusUtil(bluetoothConnect,true);
			if(queryUtil.getPrinterStatus() == 0){
				if(queryUtil.isHeadOpened()){
					code = BleException.OPEN_EXCEPTION.getCode();
					throw BleException.OPEN_EXCEPTION;
				}
				if(queryUtil.isPaperOut()){
					code = BleException.LACK_PAPER_EXCEPTION.getCode();
					throw BleException.LACK_PAPER_EXCEPTION;
				}
				if(queryUtil.isPaused()){
					code = BleException.PAUSE_EXCEPTION.getCode();
					throw BleException.PAUSE_EXCEPTION;
				}
//				if(!queryUtil.isReadyToPrint()){
//					code = BleException.OTHER_EXCEPTION.getCode();
//					throw BleException.OTHER_EXCEPTION;
//				}
			}else{
				code = BleException.STATUS_EXCEPTION.getCode();
				throw BleException.STATUS_EXCEPTION;
			}
			 // 180度反打
			if("1".equals(printType)){
				barPrinter.labelConfig().setPrintDirection(PrinterDirection.Rotation180);
			}else{
				barPrinter.labelConfig().setPrintDirection(PrinterDirection.Normal);
			}
			ILabelEdit labelEdit = barPrinter.labelEdit();
			labelEdit.setColumn(1,0);
			labelEdit.setLabelSize(template.getTemplateWidth()*8,template.getTemplateHeight()*8);
			labelEdit.selectPrinterCodepage(26);
			List<Pages> pagesList = template.getPages();
			for(Pages page : pagesList){
				for(Pagedetails pagedetails : page.getPageDetails()){
					IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
					iPrintTemplateItem.printItem(labelEdit,template,pagedetails,trade);
				}
			}
			barPrinter.labelControl().print(1,1);
		} catch (Exception e) {
			throw new BleException(code,e);
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
		int code = 1001;
		try {
			BPLZQueryPrinterStatusUtil queryUtil = new BPLZQueryPrinterStatusUtil(bluetoothConnect,true);
			if(queryUtil.getPrinterStatus() == 0){
				if(queryUtil.isHeadOpened()){
					code = BleException.OPEN_EXCEPTION.getCode();
					throw BleException.OPEN_EXCEPTION;
				}
				if(queryUtil.isPaperOut()){
					code = BleException.LACK_PAPER_EXCEPTION.getCode();
					throw BleException.LACK_PAPER_EXCEPTION;
				}
				if(queryUtil.isPaused()){
					code = BleException.PAUSE_EXCEPTION.getCode();
					throw BleException.PAUSE_EXCEPTION;
				}
//				if(!queryUtil.isReadyToPrint()){
//					code = BleException.OTHER_EXCEPTION.getCode();
//					throw BleException.OTHER_EXCEPTION;
//				}
			}else{
				code = BleException.STATUS_EXCEPTION.getCode();
				throw BleException.STATUS_EXCEPTION;
			}
			for(Trade trade : trades){
				 // 180度反打
				if("1".equals(printType)){
					barPrinter.labelConfig().setPrintDirection(PrinterDirection.Rotation180);
				}else{
					barPrinter.labelConfig().setPrintDirection(PrinterDirection.Normal);
				}
				ILabelEdit labelEdit = barPrinter.labelEdit();
				labelEdit.setColumn(1,0);
				labelEdit.setLabelSize(template.getTemplateWidth()*8,template.getTemplateHeight()*8);
				labelEdit.selectPrinterCodepage(26);
				List<Pages> pagesList = template.getPages();
				for(Pages page : pagesList){
					for(Pagedetails pagedetails : page.getPageDetails()){
						IPrintTemplateItem iPrintTemplateItem = TemplateItemFactory.getInstance().createTemplateItem(pagedetails.getItemType());
						iPrintTemplateItem.printItem(labelEdit,template,pagedetails,trade);
					}
				}
				barPrinter.labelControl().print(1,1);
			}
			
		} catch (Exception e) {
			throw new BleException(code,e);
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
