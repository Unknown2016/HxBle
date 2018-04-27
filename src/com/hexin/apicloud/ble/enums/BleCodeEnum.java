package com.hexin.apicloud.ble.enums;
/**
 * Ble返回码
 * 枚举
 * @author 军刀
 */
public enum BleCodeEnum {
	
	// 未经授权
	UNAUTHORIZED("unauthorized"),
	// 不支持
	UNSUPPORTED("unsupported"),
	// 关闭
	POWEREDOFF("poweredOff"),
	// 开启
	POWEREDON("poweredOn"),
	// 重置
	RESETTING("resetting"),
	// 未知
	UNKNOWN("unknown");
    
    private String code;
   
    private BleCodeEnum(String code) {
    	 this.code = code;
    }
      
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static BleCodeEnum valueOf(int index) {
	    for(BleCodeEnum printerModelEnum : BleCodeEnum.values()) {
	        if(printerModelEnum.ordinal() == index) {
	            return printerModelEnum;
	        }
	    }
	    return null;
    }
	
}
