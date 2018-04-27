package com.hexin.apicloud.ble.enums;
/**
 * 线条类型
 * 枚举
 * @author jundao
 */
public enum LineTypeEnum {
	
	HORIZONTAL_LINE_1("HORIZONTAL_LINE_1", "水平实线"),HORIZONTAL_LINE_2("HORIZONTAL_LINE_2", "水平虚线"),VERTICAL_LINE_1("VERTICAL_LINE_1","垂直实线"),VERTICAL_LINE_2("VERTICAL_LINE_2","垂直虚线");
    
    private String value;
   
    private String name;
   
    private LineTypeEnum(String value, String name) {
    	 this.value = value;
    	 this.name = name;
    }
      
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
