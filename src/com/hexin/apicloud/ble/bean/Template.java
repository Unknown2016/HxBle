package com.hexin.apicloud.ble.bean;
import java.math.BigDecimal;
import java.util.List;
/**
 * 快递单模板
 * 实体类
 * @author 军刀
 */
public class Template {
	
	/**
	 * 默认字体
	 */
	private String fontName;
	
	/**
	 * 页面数组
	 */
	private List<Pages> pages;
	
	/**
	 * 默认位置
	 */
	private String textAlign;
	
	/**
	 * 默认字号
	 */
	private int fontSize;
	
	/**
	 * 模板宽度
	 */
	private int templateWidth;
	
	/**
	 * 模板高度
	 */
	private int templateHeight;
	
	/**
	 * 校准X坐标
	 */
	private BigDecimal calibrationX;
	
	/**
	 * 校准Y坐标
	 */
	private BigDecimal calibrationY;
	
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public List<Pages> getPages() {
		return pages;
	}
	public void setPages(List<Pages> pages) {
		this.pages = pages;
	}
	public String getTextAlign() {
		return textAlign;
	}
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public int getTemplateWidth() {
		return templateWidth;
	}
	public int getTemplateHeight() {
		return templateHeight;
	}
	public void setTemplateHeight(int templateHeight) {
		this.templateHeight = templateHeight;
	}
	public void setTemplateWidth(int templateWidth) {
		this.templateWidth = templateWidth;
	}
	public BigDecimal getCalibrationX() {
		return calibrationX;
	}
	public void setCalibrationX(BigDecimal calibrationX) {
		this.calibrationX = calibrationX;
	}
	public BigDecimal getCalibrationY() {
		return calibrationY;
	}
	public void setCalibrationY(BigDecimal calibrationY) {
		this.calibrationY = calibrationY;
	}
}
