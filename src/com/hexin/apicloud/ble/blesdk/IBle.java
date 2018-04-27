package com.hexin.apicloud.ble.blesdk;
import java.util.Map;
import com.hexin.apicloud.ble.bean.BleDeviceInfo;

/**
 * Ble接口
 * @author 军刀
 *
 */
public interface IBle {
	
	/**
	 * 开始搜索蓝牙4.0设备
	 * 
	 */
	void scan();

	/**
	 * 获取当前扫描到的所有外围设备信息
	 * @return
	 */
	Map<String, BleDeviceInfo> getPeripherals();

	/**
	 * 判断是否正在扫描
	 * @return
	 */
	boolean isScanning();

	/**
	 * 停止搜索附近的蓝牙设备，并清空已搜索到的记录在本地的外围设备信息
	 */
	void stopScan();

}
