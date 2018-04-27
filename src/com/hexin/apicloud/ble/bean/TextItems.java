package com.hexin.apicloud.ble.bean;
/**
 * 文字项目
 * 实体类
 * @author 军刀
 */
public class TextItems {
	
	/**
	 * 自定义文字
	 */
	private String text;
	
	/**
	 * 打印代码
	 */
	private String code;
	
	/**
	 * 文字类型
	 */
    private int type;
    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
     
}
