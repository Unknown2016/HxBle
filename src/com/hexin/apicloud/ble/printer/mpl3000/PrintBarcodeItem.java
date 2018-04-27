package com.hexin.apicloud.ble.printer.mpl3000;
import com.alibaba.fastjson.JSONObject;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.bean.Trade2Template;
import com.hexin.apicloud.ble.util.BeanUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.hexin.apicloud.ble.util.StringUtil;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;

/**
 * 打印条形码
 * 实现类
 * @author jundao
 */
public class PrintBarcodeItem implements IPrintTemplateItem {

	/**
	 * x 偏移 X 坐标的量,单位为 mm
	 * y 偏移 Y 坐标的量,单位为 mm
	 * type 条码类型 width 宽度(mm) height 高度(mm) text 文本 rotate 旋转
	 */
	@Override
	public void printItem(Printer printer,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		//lpk130.NFCP_drawBarCode
		Trade2Template trade2Template = new Trade2Template();
		BeanUtil.copyProperty(trade, trade2Template);
		JSONObject tradeJson = (JSONObject) JSONObject.toJSON(trade2Template);
		//HPRTPrinterHelper .Barcode(HPRTPrinterHelper.BARCODE,HPRTPrinterHelper.128,”1”,”1”,”50”,”0”,”0”,true,”7 ”,”0”, ”5”,”123456789”)
		int barWidth = pagedetails.getWidth().intValue();
		// 获取富士通条码窄条的单位宽度
		int lineWidth =  BarWidthEnum.valueOf(barWidth).getIndex();
		if(!StringUtil.isEmpty(tradeJson.getString(pagedetails.getItemValue()))){
			//打印条码
			printer.draw_barcode1d(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),Printer.BarcodeCODE128, lineWidth, NumberUtil.mm2Dot(pagedetails.getHeight()),tradeJson.getString(pagedetails.getItemValue()),false);
		}
	}

	/**
	 * 条码宽度枚举
	 * 模板条码宽度和富士通条码宽度 转换
	 *
	 */
	public enum BarWidthEnum {
		
		WIDTH1(1,15),WIDTH2(2,30),WIDTH3(3,40),WIDTH4(4,60),WIDTH5(5,76);
	    
		//富士通 fontsize
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
		    	return BarWidthEnum.WIDTH5;
		    }
	    }
	}
}
