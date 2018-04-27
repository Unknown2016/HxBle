package com.hexin.apicloud.ble.enums;
/**
 * 打印项目类型
 * 枚举
 * @author jundao
 */
public enum PrintItemEnum {
	
	TEXT("文本"),IMAGE("图片"),LINE("线条"),QRCODE("二维码"),RECTANGLE("矩形"),BARCODE("条码"),
    
    GRID("表格"),SEQNUMBER("序号"),BLANK("空白"),WATERMARK("水印");
    
    private String name;
   
    private PrintItemEnum(String name) {
    	 this.name = name;
    }
      
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

    public static PrintItemEnum valueOf(int index) {
	    for(PrintItemEnum printItemEnum : PrintItemEnum.values()) {
	        if(printItemEnum.ordinal() == index) {
	            return printItemEnum;
	        }
	    }
	    return null;
    }
}
