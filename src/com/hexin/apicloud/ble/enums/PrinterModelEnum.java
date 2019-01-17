package com.hexin.apicloud.ble.enums;
/**
 * 打印机型号
 * 枚举
 * @author jundao
 */
public enum PrinterModelEnum {
	//新北洋
	P32("P32","SNBC"),P33("P33","SNBC"),P36("P36","SNBC"),L640H("L640H","SNBC"),V540L("V540L","SNBC"),L690H("L690H","SNBC"),UPN80I("UPN80I","SNBC"),
	//汉印
	HMZ3("HM-Z3","HPRT"),BMAU32("BMAU32","HPRT"),A318("A318","HPRT"),M35("M35","HPRT"),HMA300("HM-A300","HPRT"),
	//启锐
	QR386A("QR-386A","QRPRT"),QR380A("QR380A","QRPRT"),
	//富士通
	LPK130("LPK130","FUJITSU"),MPL3000("MPL3000","FUJITSU");
    
    private String name;
    
    private String code;
   
    private PrinterModelEnum(String name,String code) {
    	 this.name = name;
    	 this.code = code;
    }
      
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static PrinterModelEnum valueOf(int index) {
	    for(PrinterModelEnum printerModelEnum : PrinterModelEnum.values()) {
	        if(printerModelEnum.ordinal() == index) {
	            return printerModelEnum;
	        }
	    }
	    return null;
    }
	
	public static String getCodeByName(String name) {
	    for(PrinterModelEnum printerModelEnum : PrinterModelEnum.values()) {
	        if(name.contains(printerModelEnum.getName())) {
	            return printerModelEnum.getCode();
	        }
	    }
	    return null;
    }
}
