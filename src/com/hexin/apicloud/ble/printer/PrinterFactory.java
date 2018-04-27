package com.hexin.apicloud.ble.printer;
import java.util.HashMap;
import java.util.Map;
import com.hexin.apicloud.ble.enums.PrinterModelEnum;
import com.hexin.apicloud.ble.printer.fujitsu.FujitsuPrinter;
import com.hexin.apicloud.ble.printer.hprt.HprtPrinter;
import com.hexin.apicloud.ble.printer.mpl3000.Mpl3000Printer;
import com.hexin.apicloud.ble.printer.qr380.Qr380Printer;
import com.hexin.apicloud.ble.printer.qr386a.Qr386aPrinter;
import com.hexin.apicloud.ble.printer.snbc4bplc.Snbc4BplcPrinter;
import com.hexin.apicloud.ble.printer.snbc4bplz.Snbc4BplzPrinter;

/**
 * 打印机工厂
 * @author 军刀
 */
public class PrinterFactory {
	
	public PrinterFactory() {
		super();
	}
	
	private static class PrinterFactoryHolder {
        private final static PrinterFactory INSTANCE = new PrinterFactory();
    }

	/**
     * 工厂实例
     * @return
     */
    public static PrinterFactory getInstance() {
        return PrinterFactoryHolder.INSTANCE;
    }
	
	/**
	 * 打印机型号Map
	 * key:打印机名称
	 * value:具体型号打印机
	 */
	private static Map<String,IPrinter> printerMap = new HashMap<String,IPrinter>();
	
	/**
	 * 静态初始化
	 */
	static{
		printerMap.put(PrinterModelEnum.P32.getName(), new Snbc4BplcPrinter());
		printerMap.put(PrinterModelEnum.P33.getName(), new Snbc4BplcPrinter());
		printerMap.put(PrinterModelEnum.P36.getName(), new Snbc4BplzPrinter());
		printerMap.put(PrinterModelEnum.L640H.getName(), new Snbc4BplzPrinter());
		printerMap.put(PrinterModelEnum.L690H.getName(), new Snbc4BplzPrinter());
		printerMap.put(PrinterModelEnum.V540L.getName(), new Snbc4BplzPrinter());
		printerMap.put(PrinterModelEnum.HMZ3.getName(), new HprtPrinter());
		printerMap.put(PrinterModelEnum.BMAU32.getName(), new HprtPrinter());
		printerMap.put(PrinterModelEnum.A318.getName(), new HprtPrinter());
		printerMap.put(PrinterModelEnum.M35.getName(), new HprtPrinter());
		printerMap.put(PrinterModelEnum.HMA300.getName(), new HprtPrinter());
		printerMap.put(PrinterModelEnum.QR386A.getName(), new Qr386aPrinter());
		printerMap.put(PrinterModelEnum.QR380A.getName(), new Qr380Printer());
		printerMap.put(PrinterModelEnum.LPK130.getName(), new FujitsuPrinter());
		printerMap.put(PrinterModelEnum.MPL3000.getName(), new Mpl3000Printer());
    }
	
	/**
	 * 生成具体型号打印机
	 * @param name 打印机名称
	 * @return
	 */
    public IPrinter createPrinter(String name){
    	for(PrinterModelEnum printerTypeEnum : PrinterModelEnum.values()) {
	        if(name.contains(printerTypeEnum.getName())) {
	        	return printerMap.get(printerTypeEnum.getName());
	        }
	    }
    	return null;
    }
    
}
