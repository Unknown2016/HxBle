package com.hexin.apicloud.ble.printer.hprt;
import com.alibaba.fastjson.JSONObject;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.bean.Trade2Template;
import com.hexin.apicloud.ble.util.BeanUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.hexin.apicloud.ble.util.StringUtil;

import HPRTAndroidSDK.HPRTPrinterHelper;

/**
 * 打印条形码
 * 实现类
 * @author jundao
 */
public class PrintBarcodeItem implements IPrintTemplateItem {

	/**
	 * Command:条码方向: HPRTPrinterHelper.BARCODE:水平方向 HPRTPrinterHelper.VBARCODE:垂直方向
	 * Type:条码类型:
	 * HPRTPrinterHelper.UPCA,HPRTPrinterHelper.UPCA2, HPRTPrinterHelper.UPCA5,HPRTPrinterHelper.UPCE, HPRTPrinterHelper.UPCE2,HPRTPrinterHelper.UPCE5 , HPRTPrinterHelper.EAN13,HPRTPrinterHelper.EAN132, HPRTPrinterHelper.EAN135,HPRTPrinterHelper.EAN8, HPRTPrinterHelper.EAN82,HPRTPrinterHelper.EAN85, HPRTPrinterHelper.code39, HPRTPrinterHelper.code39C, HPRTPrinterHelper.F39,HPRTPrinterHelper.F39C, HPRTPrinterHelper.code93,HPRTPrinterHelper.I2OF5, HPRTPrinterHelper.I2OF5C,HPRTPrinterHelper.I2OF5G, HPRTPrinterHelper.code128,HPRTPrinterHelper.UCCEAN128, HPRTPrinterHelper.CODABAR,HPRTPrinterHelper.CODABAR16, HPRTPrinterHelper.MSI, HPRTPrinterHelper.MSI10, HPRTPrinterHelper.MSI1010,HPRTPrinterHelper.MSI1110, HPRTPrinterHelper.POSTNET,HPRTPrinterHelper.FIM
	 * Width:窄条的单位宽度。
	 * Ratio:宽条窄条的比例
	 * Height:条码高度。
  	 * X:条码的起始横坐标。
	 * Y:条码的起始纵坐标。 
	 * Undertext:条码下方的数据是否可见。ture:可见,false:不可见。 
	 * Number:字体的类型。
	 * Size:字体的大小。 
	 * Offset:条码与文字间的距离。 
	 * Data:条码数据。
	 * 
	 */
	@Override
	public void printItem(Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		Trade2Template trade2Template = new Trade2Template();
		BeanUtil.copyProperty(trade, trade2Template);
		JSONObject tradeJson = (JSONObject) JSONObject.toJSON(trade2Template);
		//HPRTPrinterHelper .Barcode(HPRTPrinterHelper.BARCODE,HPRTPrinterHelper.128,”1”,”1”,”50”,”0”,”0”,true,”7 ”,”0”, ”5”,”123456789”)
		int barWidth = pagedetails.getWidth().intValue();
		// 获取汉印条码窄条的单位宽度
		String width =  BarWidthEnum.valueOf(barWidth).getIndex();
		int height = NumberUtil.mm2Dot(pagedetails.getHeight());
		// Offset 设置为10
		if(pagedetails.isShowText()){
			height = height - 10;
		}
		if(!StringUtil.isEmpty(tradeJson.getString(pagedetails.getItemValue()))){
			HPRTPrinterHelper.Barcode(HPRTPrinterHelper.BARCODE,HPRTPrinterHelper.code128,width,"1",""+height, ""+NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),""+NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),pagedetails.isShowText(), "7","0","5",tradeJson.getString(pagedetails.getItemValue()));
		}
	}

	/**
	 * 条码宽度枚举
	 * 模板条码宽度和汉印条码宽度 转换
	 *
	 */
	public enum BarWidthEnum {
		
		WIDTH1("1",47),WIDTH2("2",62),WIDTH3("3",76);
	    
		//汉印 fontsize
	    private String index;
	   
	    //模板数据中width
	    private int width;
	   
	    private BarWidthEnum(String index,int width) {
	    	 this.index = index;
	    	 this.width = width;
	    }
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public String getIndex() {
		    return index;
		}
		public void setIndex(String index) {
		    this.index = index;
		}
	 
	    public static BarWidthEnum valueOf(int width) {
	    	if(width <= BarWidthEnum.WIDTH1.getWidth()){
			    return BarWidthEnum.WIDTH1;
			}else if(width <= BarWidthEnum.WIDTH2.getWidth()){
		    	return BarWidthEnum.WIDTH2;
		    }else{
		    	return BarWidthEnum.WIDTH3;
		    }
	    }
	}
}
