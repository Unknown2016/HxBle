package com.hexin.apicloud.ble.printer.snbc4bplc;
import com.alibaba.fastjson.JSONObject;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.bean.Trade2Template;
import com.hexin.apicloud.ble.util.BeanUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.hexin.apicloud.ble.util.StringUtil;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import com.snbc.sdk.barcode.enumeration.BarCodeType;
import com.snbc.sdk.barcode.enumeration.HRIPosition;
import com.snbc.sdk.barcode.enumeration.Rotation;

/**
 * 打印条形码
 * 实现类
 * @author jundao
 */
public class PrintBarcodeItem implements IPrintTemplateItem {

	/**
	 * x	横坐标，单位点。
	 * y	纵坐标，单位点。
	 * barcodeType	条码类型。 
	 * rotate	打印旋转角度。
	 * data	条码内容。
	 * height	条码高度，单位：点。
	 * hriPositon	HRI字符打印模式。
	 * narrowbarWidth	窄条宽度，单位：点。
	 * wideBarwidth	宽条宽度，单位：点。
	 */
	@Override
	public void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		Trade2Template trade2Template = new Trade2Template();
		BeanUtil.copyProperty(trade, trade2Template);
		JSONObject tradeJson = (JSONObject) JSONObject.toJSON(trade2Template);
		int barWidth = pagedetails.getWidth().intValue();
		// 获取条码的单位宽度
		int width =  BarWidthEnum.valueOf(barWidth).getIndex();
		int height = NumberUtil.mm2Dot(pagedetails.getHeight());
		HRIPosition hriPositon = HRIPosition.None;
		if(pagedetails.isShowText()){
			hriPositon = HRIPosition.AlignCenter;
			height -= 10;
		}
		Rotation rotation = Rotation.Rotation0;
		if(pagedetails.getRotateRate() == 0){
			rotation = Rotation.Rotation0;
		}else if(pagedetails.getRotateRate() == 90){
			rotation = Rotation.Rotation90;
		}else if(pagedetails.getRotateRate() == -90){
			rotation = Rotation.Rotation270;
		}
		if(!StringUtil.isEmpty(tradeJson.getString(pagedetails.getItemValue()))){
			labelEdit.printBarcode1D(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())) , NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), BarCodeType.Code128, rotation, tradeJson.getString(pagedetails.getItemValue()).getBytes("GB18030"),height,hriPositon,width,width*2);
		}
	}
	
	/**
	 * 条码宽度枚举
	 * 模板条码宽度和汉印条码宽度 转换
	 *
	 */
	public enum BarWidthEnum {
		
		WIDTH1(1,25),WIDTH2(2,45),WIDTH3(3,65),WIDTH4(4,76);
	    
		//汉印 fontsize
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
		    }else{
		    	return BarWidthEnum.WIDTH4;
		    }
	    }
	}

}
