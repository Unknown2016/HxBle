package com.hexin.apicloud.ble.enums;
/**
 * 打印项目类型
 * 枚举
 * @author jundao
 */
public enum PrintItemEnum {
	
	TEXT(0,"文本"),IMAGE(1,"图片"),LINE(2,"线条"),QRCODE(3,"二维码"),RECTANGLE(4,"矩形"),BARCODE(5,"条码"),
    
    GRID(10,"表格"),SEQNUMBER(11,"序号"),BLANK(12,"空白"),WATERMARK(13,"水印");
	
    private int index;
    private String name;
    private PrintItemEnum(int index,String name) {
    	this.index = index;
   	 	this.name = name;
   }
    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
