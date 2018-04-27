package com.hexin.apicloud.ble.bean;
/**
 * 扩展数据
 * 实体类
 * @author 军刀
 */
public class Extend {
	
	/**
	 * 打印代码
	 * 发送给菜鸟打印模块的打印代码
	 */
	private String printCode;
	
	/**
	 * 取值代码
	 * 根据source进行取值
     * 例如: tride.order.title或preference.xxxx
	 */
	private int valueCode;
	
	/**
	 * 数据来源
	 */
	private int source;
	
	/**
	 * 是否是状态信息
	 * 0: 式样, 1:扩传数据, 2: 增值服务, 3: 菜鸟打印加密串
	 */
	private int extendType;
	
	/**
	 * 打印参数
	 * 打印logo时可能需要的参数 PreviewOnly
	 */
	private String parameters;
	public String getPrintCode() {
		return printCode;
	}
	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}
	public int getValueCode() {
		return valueCode;
	}
	public void setValueCode(int valueCode) {
		this.valueCode = valueCode;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getExtendType() {
		return extendType;
	}
	public void setExtendType(int extendType) {
		this.extendType = extendType;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	
}
