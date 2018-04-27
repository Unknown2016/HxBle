package com.hexin.apicloud.ble.printer;
import java.util.List;

import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.common.BleException;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import android.graphics.Bitmap;

/**
 * 打印机接口
 * @author 军刀
 */
public interface IPrinter {
	
	/**
	 * 连接
	 * @param moduleContext
	 * @param address
	 * @param deviceName
	 */
	boolean connect(UZModuleContext moduleContext, String address,String deviceName) throws BleException;
	
	/**
	 * 断开
	 * @param address
	 */
	boolean disconnect() throws BleException;
	
	/**
	 * 是否连接
	 * @param moduleContext
	 * @param address
	 */
	boolean isconnected(String address) throws BleException;
	
	/**
	 * 图片打印订单数据
	 * @param moduleContext
	 * @param width
	 * @param height
	 * @param bitmap
	 * @param printType
	 */
	void printByImg(UZModuleContext moduleContext,String width,String height,Bitmap bitmap,String printType) throws BleException;
	
	/**
	 * 指令打印订单数据
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param printType
	 * 
	 */
	void printByCommand(UZModuleContext moduleContext,Template template,Trade trade,String printType) throws BleException;
	
	/**
	 * 指令打印订单数据
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param printType
	 */
	void printByCommand(UZModuleContext moduleContext,Template template,List<Trade> trade,String printType) throws BleException;
	
	/**
	 * 打印条码
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param type
	 */
	void printBarcode(UZModuleContext moduleContext,Template template,Trade trade,String type) throws BleException;
	
	/**
	 * 打印二维码
	 * @param moduleContext
	 * @param template
	 * @param trade
	 * @param type
	 */
	void printQrcode(UZModuleContext moduleContext,Template template,Trade trade,String type) throws BleException;
	
}
