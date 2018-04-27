package com.hexin.apicloud.ble.enums;
/**
 * 文字打印项目类型
 * 枚举
 * @author jundao
 */
public enum TextItemTypeEnum {
	//2:例如：对号、回车
	PRINT(0, "打印项目"),CUSTOM(1, "自定义项目"),FIXED(2,"固定字符");
    
    private int index;
   
    private String name;
   
    private TextItemTypeEnum(int index, String name) {
    	 this.index = index;
    	 this.name = name;
    }
      
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public int getIndex() {
	    return index;
	}

	public void setIndex(int index) {
	    this.index = index;
	}
 
    public static TextItemTypeEnum valueOf(int index) {
	    for(TextItemTypeEnum textItemType : TextItemTypeEnum.values()) {
	        if(textItemType.getIndex() == index) {
	            return textItemType;
	        }
	    }
	    return null;
    }
}
