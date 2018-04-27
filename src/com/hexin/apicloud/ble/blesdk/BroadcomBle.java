package com.hexin.apicloud.ble.blesdk;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import com.broadcom.bt.gatt.BluetoothGatt;
import com.broadcom.bt.gatt.BluetoothGattAdapter;
import com.broadcom.bt.gatt.BluetoothGattCallback;
import com.hexin.apicloud.ble.bean.BleDeviceInfo;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

/**
 * 博通Ble
 * @author 军刀
 *
 */
public class BroadcomBle implements IBle {
	
	private BluetoothGatt mBluetoothGatt;
	private Map<String, UZModuleContext> mConnectCallBackMap;
	private Map<String, UZModuleContext> mConnectsCallBackMap;
	private Map<String, BleDeviceInfo> mScanBluetoothDeviceMap;
	private boolean mIsScanning;

	public BroadcomBle(Context context) {
		BluetoothGattAdapter.getProfileProxy(context, mProfileServiceListener,BluetoothGattAdapter.GATT);
		mScanBluetoothDeviceMap = new HashMap<String, BleDeviceInfo>();
		mConnectCallBackMap = new HashMap<String, UZModuleContext>();
		mConnectsCallBackMap = new HashMap<String, UZModuleContext>();
	}

	/**
	 * 开始搜索蓝牙4.0设备
	 * 
	 */
	@Override
	public void scan() {
		if (mBluetoothGatt != null) {
			mBluetoothGatt.startScan();
			mIsScanning = true;
		}
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
	@Override
	public void stopScan() {
		if (mBluetoothGatt != null) {
			mBluetoothGatt.stopScan();
			mIsScanning = false;
		}
	}

	private final BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {
		@Override
		public void onServiceConnected(int profile, BluetoothProfile proxy) {
			mBluetoothGatt = (BluetoothGatt) proxy;
			mBluetoothGatt.registerApp(mGattCallbacks);
		}

		@Override
		public void onServiceDisconnected(int profile) {
			for (BluetoothDevice d : mBluetoothGatt.getConnectedDevices()) {
				mBluetoothGatt.cancelConnection(d);
			}
			mBluetoothGatt = null;
		}
	};

	private final BluetoothGattCallback mGattCallbacks = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothDevice device, int status,
				int newState) {
			String address = device.getAddress();
			if (mConnectsCallBackMap.containsKey(address)) {
				if (status != BluetoothGatt.GATT_SUCCESS) {
					connectsCallBack(mConnectsCallBackMap.get(address), false,
							-1, address);
					return;
				}
				if (newState == BluetoothProfile.STATE_CONNECTED) {
					connectsCallBack(mConnectsCallBackMap.get(address), true,
							0, address);
				} else {
					connectsCallBack(mConnectsCallBackMap.get(address), false,
							-1, address);
				}
				return;
			}
			UZModuleContext moduleContext = mConnectCallBackMap.get(address);
			if (status != BluetoothGatt.GATT_SUCCESS) {
				connectCallBack(moduleContext, false, -1);
				return;
			}
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				connectCallBack(moduleContext, true, 0);
			} else {
				mConnectCallBackMap.remove(address);
				connectCallBack(moduleContext, false, -1);
			}
		}

		@Override
		public void onScanResult(BluetoothDevice device, int rssi, byte[] arg2) {
			mScanBluetoothDeviceMap.put(device.getAddress(), new BleDeviceInfo(device, rssi));
		}
	};

	private void connectCallBack(UZModuleContext moduleContext, boolean status,
			int errCode) {
		JSONObject ret = new JSONObject();
		JSONObject err = new JSONObject();
		try {
			ret.put("status", status);
			if (status) {
				moduleContext.success(ret, false);
			} else {
				err.put("code", errCode);
				moduleContext.error(ret, err, false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void connectsCallBack(UZModuleContext moduleContext,boolean status, int errCode, String uuid) {
		JSONObject ret = new JSONObject();
		JSONObject err = new JSONObject();
		try {
			ret.put("status", status);
			if (status) {
				ret.put("peripheralUUID", uuid);
				moduleContext.success(ret, false);
			} else {
				err.put("code", errCode);
				moduleContext.error(ret, err, false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
