package com.hexin.apicloud.ble.blesdk;
import java.util.HashMap;
import java.util.Map;
import com.hexin.apicloud.ble.bean.BleDeviceInfo;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
/**
 * 谷歌Ble
 * @author 军刀
 *
 */
@SuppressLint("NewApi")
public class AndroidBle implements IBle {
	
	/**
	 * 蓝牙适配器
	 */
	private BluetoothAdapter mBluetoothAdapter;
	
	/**
	 * BLE 扫描
	 */
	private BluetoothLeScanner mBluetoothLeScanner;
	
	/**
	 * 蓝牙外围设备Map
	 */
	private Map<String, BleDeviceInfo> mScanBluetoothDeviceMap;
	
	/**
	 * 是否正在扫描
	 */
	private boolean mIsScanning;
	
	public AndroidBle(Context context) {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
		mScanBluetoothDeviceMap = new HashMap<String, BleDeviceInfo>();
	}

	/**
	 * 开始搜索蓝牙4.0设备
	 * 
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void scan() {
		// 4.3
		if(Build.VERSION.SDK_INT < 21){
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		}else{ // 5.0
			mBluetoothLeScanner.startScan(leCallback);
		}
		mIsScanning = true;
	}

	/**
	 * 获取当前扫描到的所有外围设备信息
	 * @return
	 */
	@Override
	public Map<String, BleDeviceInfo> getPeripherals() {
		return mScanBluetoothDeviceMap;
	}

	/**
	 * 判断是否正在扫描
	 * @return
	 */
	@Override
	public boolean isScanning() {
		return mIsScanning;
	}

	/**
	 * 停止搜索附近的蓝牙设备，并清空已搜索到的记录在本地的外围设备信息
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void stopScan() {
		mScanBluetoothDeviceMap.clear();
		// 4.3
		if(Build.VERSION.SDK_INT < 21){
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}else{ // 5.0
			mBluetoothLeScanner.stopScan(leCallback);
		}
		mIsScanning = false;
	}
	
	/**
	 * 安卓4.3 < 5.0 系统 扫描回调
	 */
	private LeScanCallback mLeScanCallback = new LeScanCallback() {
		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			mScanBluetoothDeviceMap.put(device.getAddress(), new BleDeviceInfo(device, rssi));
		}
	};

	/**
	 * 安卓 >= 5.0系统 扫描回调
	 */
	private ScanCallback leCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            mScanBluetoothDeviceMap.put(device.getAddress(), new BleDeviceInfo(device,result.getRssi()));
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };
}
