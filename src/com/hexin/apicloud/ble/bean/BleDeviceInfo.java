package com.hexin.apicloud.ble.bean;
import android.bluetooth.BluetoothDevice;
/**
 * 蓝牙外围设备信息
 * @author 军刀
 *
 */
public class BleDeviceInfo {
	
	/**
	 * 蓝牙设备
	 */
	private BluetoothDevice bluetoothDevice;
	
	/**
	 * 信号强度指示
	 */
	private int rssi;

	public BleDeviceInfo(BluetoothDevice bluetoothDevice, int rssi) {
		this.bluetoothDevice = bluetoothDevice;
		this.rssi = rssi;
	}

	public BluetoothDevice getBluetoothDevice() {
		return bluetoothDevice;
	}

	public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
		this.bluetoothDevice = bluetoothDevice;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
}
