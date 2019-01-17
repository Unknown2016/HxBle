package com.hexin.apicloud.ble;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Base64;
import android.widget.Toast;
import com.hexin.apicloud.ble.bean.BleDeviceInfo;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.blesdk.AndroidBle;
import com.hexin.apicloud.ble.blesdk.BroadcomBle;
import com.hexin.apicloud.ble.blesdk.IBle;
import com.hexin.apicloud.ble.blesdk.SamsungBle;
import com.hexin.apicloud.ble.common.BleException;
import com.hexin.apicloud.ble.enums.BleCodeEnum;
import com.hexin.apicloud.ble.enums.PrinterModelEnum;
import com.hexin.apicloud.ble.printer.IPrinter;
import com.hexin.apicloud.ble.printer.PrinterFactory;
import com.hexin.apicloud.ble.util.BleUtil;
import com.hexin.apicloud.ble.util.BleUtil.BLESDK;
import com.hexin.apicloud.ble.util.ErrorUtil;
import com.hexin.apicloud.ble.util.StringUtil;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

/**
 * 
 * BLE
 * AC 模块
 * @author 军刀
 *
 */
@SuppressLint("NewApi")
public class UzBle extends UZModule {
	
	/**
	 * 蓝牙4.0 SDK 接口
	 */
	private IBle mIBle;
	
	/**
	 * 蓝牙适配器
	 */
	private BluetoothAdapter mBluetoothAdapter;
	
	/**
	 * 蓝牙服务是否存在
	 */
	private static boolean mIsBleServiceAlive;
	
	/**
	 * 广播是否注册
	 */
	private static boolean mIsRegistered;
	
	/**
	 * 打印机型号接口
	 */
	private static IPrinter mIPrinter;
	
	public UzBle(UZWebView webView) {
		super(webView);
	}

	/**
	 * 初始化蓝牙4.0管理器
	 * @param moduleContext
	 */
	public void jsmethod_initManager(UZModuleContext moduleContext) {
		try {
			if (!BleUtil.isBlePermission(mContext)) {
				initCallBack(moduleContext, BleCodeEnum.UNAUTHORIZED.getCode());
			} else if (!BleUtil.isBleSupported(mContext)) {
				initCallBack(moduleContext, BleCodeEnum.UNSUPPORTED.getCode());
			} else {
				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				switch (mBluetoothAdapter.getState()){
					case BluetoothAdapter.STATE_OFF:
						initCallBack(moduleContext, BleCodeEnum.POWEREDOFF.getCode());
						break;
					case BluetoothAdapter.STATE_ON:
						initCallBack(moduleContext, BleCodeEnum.POWEREDON.getCode());
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
					case BluetoothAdapter.STATE_TURNING_ON:
					case BluetoothAdapter.STATE_CONNECTING:
					case BluetoothAdapter.STATE_DISCONNECTING:
						initCallBack(moduleContext, BleCodeEnum.RESETTING.getCode());
						break;
					default:
						initCallBack(moduleContext, BleCodeEnum.UNKNOWN.getCode());
						break;
				}
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}
	
	/**
	 * 蓝牙是否打开
	 * @param moduleContext
	 */
	public void jsmethod_isOpened(UZModuleContext moduleContext) {
		try {
			boolean isEnabled = mBluetoothAdapter.isEnabled();
			retCallback(moduleContext,isEnabled);
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 打开蓝牙
	 * @param moduleContext
	 * 
	 */
	public void jsmethod_openBt(UZModuleContext moduleContext) {
		try {
			// 蓝牙适配器未开启
			if (!mBluetoothAdapter.isEnabled()) {
				boolean ret = mBluetoothAdapter.enable();
				mIsBleServiceAlive = ret;
				retCallback(moduleContext,ret);
			}else{
				retCallback(moduleContext,true);
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 关闭蓝牙
	 * @param moduleContext
	 */
	public void jsmethod_closeBt(UZModuleContext moduleContext) {
		try {
			if (mBluetoothAdapter.isEnabled()) {
				boolean ret = mBluetoothAdapter.disable();
				mIsBleServiceAlive = ret;
				retCallback(moduleContext,ret);
			}else{
				retCallback(moduleContext,true);
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}
	
	/**
	 * 开始搜索蓝牙4.0设备
	 * 模块内部会不断的扫描更新附近的蓝牙4.0设备信息
	 * @param moduleContext
	 */
	public void jsmethod_scan(UZModuleContext moduleContext) {
		try {
			if (mIsBleServiceAlive) {
				initIBle(moduleContext);
				mIBle.scan();
			}
			retCallback(moduleContext, mIsBleServiceAlive);
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 获取当前扫描到的所有外围设备信息
	 * @param moduleContext
	 */
	public void jsmethod_getDevices(UZModuleContext moduleContext) {
		try {
			if (mIBle != null) {
				Map<String, BleDeviceInfo> deviceMap = mIBle.getPeripherals();
				getDevicesCallBack(moduleContext, deviceMap);
			} else {
				getDevicesCallBack(moduleContext, null);
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 判断是否正在扫描
	 * @param moduleContext
	 */
	public void jsmethod_isScanning(UZModuleContext moduleContext) {
		try {
			if (mIBle != null) {
				retCallback(moduleContext, mIBle.isScanning());
			} else {
				retCallback(moduleContext, false);
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 停止搜索附近的蓝牙设备，并清空已搜索到的记录在本地的外围设备信息
	 * @param moduleContext
	 */
	public void jsmethod_stopScan(UZModuleContext moduleContext) {
		try {
			if (mIBle != null) {
				mIBle.stopScan();
				retCallback(moduleContext, true);
			}else{
				retCallback(moduleContext, false);
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}
	
	/**
	 * 配对历史
	 * @param moduleContext
	 */
	public void jsmethod_getBondedDevices(UZModuleContext moduleContext) {
		try {
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
				try {
					JSONArray devices = new JSONArray();
					for (BluetoothDevice device : pairedDevices) {
						JSONObject pairedDevice = new JSONObject();
						for(PrinterModelEnum printerModelEnum : PrinterModelEnum.values()) {
					        if(device.getName().contains(printerModelEnum.getName())) {
					        	pairedDevice.put("name", device.getName());
					        	pairedDevice.put("uuid", device.getAddress());
								devices.put(pairedDevice);
					        }
					    }
					}
					JSONObject ret = new JSONObject();
					ret.put("devices", devices);
					ret.put("status", true);
					moduleContext.success(ret, false);
				} catch (Exception e) {
					retCallback(moduleContext,false);
				}
			} else {
				Toast.makeText(mContext, "没有已匹配的设备",Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}


	/**
	 * 连接指定外围设备
	 * @param moduleContext
	 */
	public void jsmethod_connect(final UZModuleContext moduleContext) {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					boolean ret = false;
					try {
						String address = moduleContext.optString("uuid");
						// 取消扫描
						if(mBluetoothAdapter.isDiscovering()){
							mBluetoothAdapter.cancelDiscovery();
						}
						BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
						String deviceName = device.getName();
						if(!StringUtil.isEmpty(deviceName)){
							mIPrinter = PrinterFactory.getInstance().createPrinter(deviceName);
							ret = mIPrinter.connect(moduleContext, address,deviceName);
							if(ret && !mIsRegistered){
								// 注册广播
								mContext.registerReceiver(mBleReceiver,UzBle.getIntentFilter());
								mIsRegistered = true;
							}
							retCallback(moduleContext,ret);
						}else{
							retCallback(moduleContext,ret);
						}
					} catch (BleException e) {
						errcodeCallback(moduleContext,e.getCode(),e);
					}
					Looper.loop();
				}
			}).start();
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}


	/**
	 * 断开与指定外围设备的连接
	 * @param moduleContext
	 */
	public void jsmethod_disconnect(UZModuleContext moduleContext) {
		try {
			if (mIPrinter != null) {
				boolean ret = mIPrinter.disconnect();
				retCallback(moduleContext,ret);
			}else{
				retCallback(moduleContext,false);
			}
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}

	/**
	 * 判断与指定外围设备是否为连接状态
	 * @param moduleContext
	 */
	public void jsmethod_isConnected(UZModuleContext moduleContext) {
		try {
			String address = moduleContext.optString("uuid");
			if (mIPrinter != null) {
				boolean ret = mIPrinter.isconnected(address);
				retCallback(moduleContext,ret);
			}else{
				retCallback(moduleContext,false);
			}
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}

	
	/**
	 * 图片打印
	 * @param moduleContext
	 * 
	 */
	public void jsmethod_printByImg(final UZModuleContext moduleContext) {
		try {
			// 打印图片base64数据
			String base64Img = moduleContext.optString("base64Img");
			// 打印模板的宽、高
			String templateWidth = moduleContext.optString("templateWidth");
			String templateHeight = moduleContext.optString("templateHeight");
			// 打印方式 0：正打 1：反打(旋转180度)
			String printType = moduleContext.optString("printType");
			// 转成RGB565彩色模式的Bitmap
			byte[] bitmapArray = Base64.decode(base64Img,Base64.DEFAULT);
			BitmapFactory.Options options =new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			InputStream	input = new ByteArrayInputStream(bitmapArray);
			Bitmap bitmap = BitmapFactory.decodeStream(input,null,options);
			// 打印图片
			mIPrinter.printByImg(moduleContext,templateWidth,templateHeight, bitmap,printType);
			// 回收Bitmap
			if(bitmap != null && !bitmap.isRecycled()){
			    // 回收并且置为null
			    bitmap.recycle();
			    bitmap = null;
			}
			System.gc();
			retCallback(moduleContext,true);
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}
	
	/**
	 * 指令打印
	 * @param moduleContext
	 * 
	 */
	public void jsmethod_printByCommand(final UZModuleContext moduleContext) {
		try {
			// 电子面单模板数据
			String templateStr = moduleContext.optString("template");
			// 订单数据
			String tradeStr = moduleContext.optString("trade");
			Trade trade = com.alibaba.fastjson.JSONObject.parseObject(tradeStr,Trade.class);
			// 打印方式 0：正打 1：反打(旋转180度)
			String printType = moduleContext.optString("printType");
			Template template = com.alibaba.fastjson.JSONObject.parseObject(templateStr,Template.class);
			mIPrinter.printByCommand(moduleContext,template,trade,printType);
			retCallback(moduleContext,true);
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}
	
	/**
	 * 打印条码
	 * @param moduleContext
	 * 
	 */
	public void jsmethod_printBarcode(UZModuleContext moduleContext) {
		try {
			String templateStr = moduleContext.optString("template");
			String tradeStr = moduleContext.optString("trade");
			String type = moduleContext.optString("type");
			final Trade trade = com.alibaba.fastjson.JSONObject.parseObject(tradeStr,Trade.class);
			final Template template = com.alibaba.fastjson.JSONObject.parseObject(templateStr,Template.class);
			mIPrinter.printBarcode(moduleContext,template,trade,type);
			retCallback(moduleContext,true);
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}
	
	/**
	 * 打印二维码
	 * @param moduleContext
	 * 
	 */
	public void jsmethod_printQrcode(UZModuleContext moduleContext) {
		try {
			String templateStr = moduleContext.optString("template");
			String tradeStr = moduleContext.optString("trade");
			String type = moduleContext.optString("type");
			final Trade trade = com.alibaba.fastjson.JSONObject.parseObject(tradeStr,Trade.class);
			final Template template = com.alibaba.fastjson.JSONObject.parseObject(templateStr,Template.class);
			mIPrinter.printQrcode(moduleContext, template, trade, type);
			retCallback(moduleContext,true);
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}
	
	/**
	 * 打印指令集
	 * @param moduleContext
	 * 
	 */
	public void jsmethod_printCmdSet(UZModuleContext moduleContext) {
		try {
			String cmdSet = moduleContext.optString("cmdSet");
			// 打印方式 0：正打 1：反打(旋转180度)
			String printType = moduleContext.optString("printType");
			mIPrinter.sendCmd(cmdSet,printType);
			retCallback(moduleContext,true);
		} catch (BleException e) {
			errcodeCallback(moduleContext,e.getCode(),e);
		}
	}
	
	/**
	 * 意图过滤器
	 * @return
	 */
	public static IntentFilter getIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		return intentFilter;
	}
	
	/**
	 * 监听周围蓝牙设备
	 */
	private final BroadcastReceiver mBleReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				String action = intent.getAction();
				//主要与蓝牙设备有关系
//		        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		        if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
					try {
						if(mIsRegistered){
//							Toast.makeText(context , "打印机:" + device.getName() + "已断开链接" , Toast.LENGTH_SHORT).show();
							if(mIPrinter != null){
			            		mIPrinter.disconnect();
			            	}
							//反注册广播
							mContext.unregisterReceiver(mBleReceiver);
							mIsRegistered = false;
			        	}
					} catch (Exception e) {
						//重复反注册时会抛异常 不做处理
					}
		        }
			} catch (Exception e) {
				Toast.makeText(mContext, "广播监听异常:"+e.getMessage(),Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	/**
	 * 初始化Ble接口
	 * @param moduleContext
	 */
	private void initIBle(UZModuleContext moduleContext) {
		BLESDK sdk = BleUtil.getBleSDK(mContext);
		if (mIBle == null) {
			if (sdk == BLESDK.ANDROID) {
				mIBle = new AndroidBle(mContext);
			} else if (sdk == BLESDK.SAMSUNG) {
				mIBle = new SamsungBle(mContext);
			} else if (sdk == BLESDK.BROADCOM) {
				mIBle = new BroadcomBle(mContext);
			} else {
				Toast.makeText(mContext, "您的设备不支持蓝牙4.0",Toast.LENGTH_LONG).show();
				return;
			}
		}
	}
	
	/**
	 * 初始化回调函数
	 * @param moduleContext
	 * @param state
	 */
	private void initCallBack(UZModuleContext moduleContext, String state) {
		JSONObject ret = new JSONObject();
		try {
			ret.put("state", state);
			mIsBleServiceAlive = BleCodeEnum.POWEREDON.getCode().equals(state);
			moduleContext.success(ret, false);
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 扫描获取蓝牙打印机
	 * @param moduleContext
	 * @param deviceMap
	 */
	private void getDevicesCallBack(UZModuleContext moduleContext,Map<String, BleDeviceInfo> deviceMap) {
		JSONObject ret = new JSONObject();
		JSONArray devices = new JSONArray();
		try {
			if (deviceMap != null) {
				for (Map.Entry<String, BleDeviceInfo> entry : deviceMap.entrySet()) {
					JSONObject device = new JSONObject();
					BleDeviceInfo bleDeviceInfo = entry.getValue();
					device.put("uuid", bleDeviceInfo.getBluetoothDevice().getAddress());
					device.put("name", bleDeviceInfo.getBluetoothDevice().getName());
					device.put("rssi", bleDeviceInfo.getRssi());
					devices.put(device);
				}
				ret.put("status", true);
			}else{
				ret.put("status", false);
			}
			ret.put("devices", devices);
			moduleContext.success(ret, false);
		} catch (Exception e) {
			errcodeCallback(moduleContext,BleException.SYS_EXCEPTION.getCode(),e);
		}
	}

	/**
	 * 返回结果回调函数
	 * @param moduleContext
	 * @param status
	 */
	private void retCallback(UZModuleContext moduleContext,boolean status) {
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			moduleContext.success(ret, false);
		} catch (JSONException e) {
			//do nothing;
		}
	}
	
	/**
	 * 错误码回调函数
	 * @param moduleContext
	 * @param code
	 * @param exception
	 */
	private void errcodeCallback(UZModuleContext moduleContext, int code,Exception exception) {
		JSONObject ret = new JSONObject();
		JSONObject err = new JSONObject();
		try {
			ret.put("status", false);
			err.put("code", code);
			if(exception != null){
				String msg = ErrorUtil.getStackMsg(exception);
				err.put("msg", msg);
			}
			moduleContext.error(ret, err, false);
		} catch (JSONException e) {
			// do nothing;
		}
	}
}
