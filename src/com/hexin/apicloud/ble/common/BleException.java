package com.hexin.apicloud.ble.common;

/**
 * Ble 异常
 * @author 军刀
 * 
 */
public class BleException extends RuntimeException {

    /**
	 * UID
	 */
	private static final long serialVersionUID = -5328652254809718786L;

	/**
     * 系统异常
     */
    public static final BleException SYS_EXCEPTION = new BleException(1001, "系统异常");
    
    /**
     * 参数异常
     */
    public static final BleException PARAM_EXCEPTION = new BleException(1002, "参数异常");
    
    /**
     * 打印机缺纸异常
     */
    public static final BleException LACK_PAPER_EXCEPTION = new BleException(1003, "打印机缺纸");
    
    /**
     * 打印机开盖异常
     */
    public static final BleException OPEN_EXCEPTION = new BleException(1004, "打印机开盖");
    
    /**
     * 打印机其他异常
     */
    public static final BleException OTHER_EXCEPTION = new BleException(1005, "打印机出错");
    
    /**
     * 打印机状态获取异常
     */
    public static final BleException STATUS_EXCEPTION = new BleException(1006, "打印机状态获取异常");
    
    /**
     * 打印机暂停
     */
    public static final BleException PAUSE_EXCEPTION = new BleException(1007, "打印机暂停");

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public BleException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BleException() {
        super();
    }

    public BleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BleException(int code,Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BleException(String message) {
        super(message);
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 实例化异常
     * 
     * @param msgFormat
     * @param args
     * @return
     */
    public BleException newInstance(String msgFormat, Object... args) {
        return new BleException(this.code, msgFormat, args);
    }

}
