package com.hexin.apicloud.ble.printer.snbc4bplz;
import java.math.BigDecimal;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.enums.LineTypeEnum;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
/**
 * 打印线条
 * 实现类
 * @author jundao
 */
public class PrintLineItem implements IPrintTemplateItem {

	@Override
	public void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		/**
		 *	startX	起点横坐标，单位：点。 
		 *	startY	起点纵坐标，单位：点。 
	     *	endX	终点横坐标，单位：点。 
		 *	endY	终点纵坐标，单位：点。 
		 *	thickness	线条宽度，单位：点。 
		 */
		//水平实线、水平虚线、垂直实线、垂直虚线
		if(LineTypeEnum.HORIZONTAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.HORIZONTAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			// 横线X坐标是负数时打不出来
			if(pagedetails.getX().add(template.getCalibrationX()).compareTo(BigDecimal.valueOf(0L))<=0){
				labelEdit.printLine(0,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()) ,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),2);
			}else{
				labelEdit.printLine(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX()).add(pagedetails.getWidth())) ,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),2);
			}
		}else if(LineTypeEnum.VERTICAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.VERTICAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			if(pagedetails.getY().add(template.getCalibrationY()).compareTo(BigDecimal.valueOf(0L))<=0){
				labelEdit.printLine(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),0,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getHeight()),1);
			}else{
				labelEdit.printLine(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY()).add(pagedetails.getHeight())),2);
			}
		}
	}
	
}
