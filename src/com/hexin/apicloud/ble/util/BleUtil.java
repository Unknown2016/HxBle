package com.hexin.apicloud.ble.util;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.pm.PackageManager;
/**
 * 蓝牙4.0工具类
 * @author 军刀
 *
 */
public class BleUtil {
	
	/**
	 * 是否有Ble权限
	 * @param context
	 * @return
	 */
	public static boolean isBlePermission(Context context) {
		PackageManager pm = context.getPackageManager();
		boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.BLUETOOTH",context.getPackageName()));
		return permission;
	}

	/**
	 * 是否支持Ble
	 * @param context
	 * @return
	 */
	public static boolean isBleSupported(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			// android 4.3
			return true;
		}
		ArrayList<String> libraries = new ArrayList<String>();
		for (String i : context.getPackageManager()
				.getSystemSharedLibraryNames()) {
			libraries.add(i);
		}
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			// android 4.2.2
			if (libraries.contains("com.samsung.android.sdk.bt")) {
				return true;
			} else if (libraries.contains("com.broadcom.bt")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取Ble sdk
	 * 兼容主流：Google Samsung Broadcom SDK
	 * @param context
	 * @return
	 */
	public static BLESDK getBleSDK(Context context) {
		// 支持Ble
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			// android 4.3
			return BLESDK.ANDROID;
		}

		List<String> libraries = new ArrayList<String>();
		for (String i : context.getPackageManager().getSystemSharedLibraryNames()) {
			libraries.add(i);
		}

		// 当前系统的android版本号
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			// android 4.2.2
			if (libraries.contains("com.samsung.android.sdk.bt")) {
				return BLESDK.SAMSUNG;
			} else if (libraries.contains("com.broadcom.bt")) {
				return BLESDK.BROADCOM;
			}
		}
		return BLESDK.NOT_SUPPORTED;
	}

	/**
	 * 主流Ble SDK
	 *
	 */
	public enum BLESDK {
		// 不支持
		NOT_SUPPORTED,
		// 谷歌
		ANDROID, 
		// 三星
		SAMSUNG, 
		// 博通
		BROADCOM
	}
}
