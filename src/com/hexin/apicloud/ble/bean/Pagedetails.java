package com.hexin.apicloud.ble.bean;
import java.math.BigDecimal;
import java.util.List;

import android.graphics.Bitmap;
/**
 * 页面明细
 * 实体类
 * @author 军刀
 */
public class Pagedetails {
	
	/**
	 * 打印项目类型
	 */
	private int itemType;
	
	/**
	 * 文字项目
	 */
	private List<TextItems> textItems;
	
	/**
	 * 默认字体
	 */
	private String fontName;
	
	/**
	 *  是否跟随表格改变位置 默认值 false
	 */
	private boolean isFollowByGrid;
	
	/**
	 *  是否显示文本
	 */
	private boolean isShowText;
	
	/**
	 * 默认位置
	 */
	private String textAlign;
	
	/**
	 * X坐标 毫米
	 */
	private BigDecimal x;
	
	/**
	 * 宽度 毫米
	 */
	private BigDecimal width;
	
	/**
	 * Y坐标 毫米
	 */
	private BigDecimal y;
	
	/**
	 * 默认字号
	 */
	private int fontSize;
	
	/**
	 * 高度 毫米
	 */
	private BigDecimal height;
	
	/**
	 * 图片地址
	 */
	private String imageUrl;
	
	/**
	 * 打印项目
	 */
	private String itemValue;
	
	/**
	 * 粗体
	 */
	private boolean fontBold;
	
	/**
	 * 下划线
	 */
	private boolean textUnderline;
	
	/**
	 * 斜体
	 */
	private boolean fontItalic;
	
	/**
	 * 旋转度数
	 */
	private int rotateRate;
	
	/**
	 * 线条类型
	 */
	private String itemCode;
	
	/**
	 * 位图
	 */
	private Bitmap bitmap;
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public boolean isFollowByGrid() {
		return isFollowByGrid;
	}
	public void setFollowByGrid(boolean isFollowByGrid) {
		this.isFollowByGrid = isFollowByGrid;
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
	public List<TextItems> getTextItems() {
		return textItems;
	}
	public void setTextItems(List<TextItems> textItems) {
		this.textItems = textItems;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public boolean isFontBold() {
		return fontBold;
	}
	public void setFontBold(boolean fontBold) {
		this.fontBold = fontBold;
	}
	public boolean isTextUnderline() {
		return textUnderline;
	}
	public void setTextUnderline(boolean textUnderline) {
		this.textUnderline = textUnderline;
	}
	public boolean isFontItalic() {
		return fontItalic;
	}
	public void setFontItalic(boolean fontItalic) {
		this.fontItalic = fontItalic;
	}
	public int getRotateRate() {
		return rotateRate;
	}
	public void setRotateRate(int rotateRate) {
		this.rotateRate = rotateRate;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isShowText() {
		return isShowText;
	}
	public void setShowText(boolean isShowText) {
		this.isShowText = isShowText;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public BigDecimal getX() {
		return x;
	}
	public void setX(BigDecimal x) {
		this.x = x;
	}
	public BigDecimal getWidth() {
		return width;
	}
	public void setWidth(BigDecimal width) {
		this.width = width;
	}
	public BigDecimal getY() {
		return y;
	}
	public void setY(BigDecimal y) {
		this.y = y;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

}
