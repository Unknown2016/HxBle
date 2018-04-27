package com.hexin.apicloud.ble.printer.qr386a;
import com.alibaba.fastjson.JSONObject;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.bean.Trade2Template;
import com.hexin.apicloud.ble.util.BeanUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.hexin.apicloud.ble.util.StringUtil;
import com.qr.printer.*;

/**
 * 打印条形码
 * 实现类
 * @author jundao
 */
public class PrintBarcodeItem implements IPrintTemplateItem {

	/**
	 * start_x - 一维码起始横坐标
	 * start_y - 一维码起始纵坐标
	 * text - 内容
	 * type - 条码类型 0:CODE39; 1:CODE128; 2:CODE93; 3:CODEBAR; 4:EAN8; 5:EAN13; 6:UPCA; 7:UPC-E; 8:ITF
	 * rotate - 旋转角度 0:不旋转; 1:90 度; 2:180°; 3:270°
	 * linewidth - 条码线宽度
	 * height - 条码高度
	 */
	@Override
	public void printItem(Printer iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		Trade2Template trade2Template = new Trade2Template();
		BeanUtil.copyProperty(trade, trade2Template);
		JSONObject tradeJson = (JSONObject) JSONObject.toJSON(trade2Template);
		int barWidth = pagedetails.getWidth().intValue();
		// 获取条码线宽度
		int lineWidth =  BarWidthEnum.valueOf(barWidth).getIndex();
		if(!StringUtil.isEmpty(tradeJson.getString(pagedetails.getItemValue()))){
			iPrinter.drawBarCode(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())) , NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),tradeJson.getString(pagedetails.getItemValue()), 1 , pagedetails.getRotateRate(),lineWidth,NumberUtil.mm2Dot(pagedetails.getHeight()));
		}
	}
	
	/**
	 * 条码宽度枚举
	 * 模板条码宽度和启锐380条码宽度 转换
	 *
	 */
	public enum BarWidthEnum {
		
		WIDTH1(1,33),WIDTH2(2,48),WIDTH3(3,64),WIDTH4(4,76);
	    
		//启锐386 fontsize
	    private int index;
	   
	    //模板数据中width
	    private int width;
	   
	    private BarWidthEnum(int index,int width) {
	    	 this.index = index;
	    	 this.width = width;
	    }
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getIndex() {
		    return index;
		}
		public void setIndex(int index) {
		    this.index = index;
		}
	 
	    public static BarWidthEnum valueOf(int width) {
	    	if(width <= BarWidthEnum.WIDTH1.getWidth()){
			    return BarWidthEnum.WIDTH1;
			}else if(width <= BarWidthEnum.WIDTH2.getWidth()){
		    	return BarWidthEnum.WIDTH2;
		    }else if(width <= BarWidthEnum.WIDTH3.getWidth()){
		    	return BarWidthEnum.WIDTH3;
		    }else if(width <= BarWidthEnum.WIDTH4.getWidth()){
		    	return BarWidthEnum.WIDTH4;
		    }else{
		    	return BarWidthEnum.WIDTH4;
		    }
	    }
	}

}
