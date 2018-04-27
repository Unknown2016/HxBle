package com.hexin.apicloud.ble.printer.qr380;
import java.math.BigDecimal;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.enums.LineTypeEnum;
import com.hexin.apicloud.ble.util.NumberUtil;
import printpp.printpp_yt.PrintPP_CPCL;
/**
 * 打印线条
 * 实现类
 * @author jundao
 */
public class PrintLineItem implements IPrintTemplateItem {

	/**
	 * lineWidth - 线条宽度
	 * start_x - 线条起始点 x 坐标 
	 * start_y - 线条起始点 y 坐标
	 * end_x - 线条结束点 x 坐标
	 * end_y - 线条结束点 y 坐标 
	 * fullline - true:实线 false: 虚线
	 * 
	 */
	@Override
	public void printItem(PrintPP_CPCL iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		//水平实线、水平虚线、垂直实线、垂直虚线
		if(LineTypeEnum.HORIZONTAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			if(pagedetails.getX().add(template.getCalibrationX()).compareTo(BigDecimal.valueOf(0L))<=0){
				iPrinter.drawLine(2,0,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()) ,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),true);
			}else{
				iPrinter.drawLine(2,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX()).add(pagedetails.getWidth())) ,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),true);
			}
		}else if(LineTypeEnum.HORIZONTAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			if(pagedetails.getX().add(template.getCalibrationX()).compareTo(BigDecimal.valueOf(0L))<=0){
				iPrinter.drawLine(2,0,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()) ,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),false);
			}else{
				iPrinter.drawLine(2,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX()).add(pagedetails.getWidth())) ,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),false);
			}
		}else if(LineTypeEnum.VERTICAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			if(pagedetails.getY().add(template.getCalibrationY()).compareTo(BigDecimal.valueOf(0L))<=0){
				iPrinter.drawLine(2,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),0,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getHeight()),true);
			}else{
				iPrinter.drawLine(2,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY()).add(pagedetails.getHeight())),true);
			}
		}else if(LineTypeEnum.VERTICAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			if(pagedetails.getY().add(template.getCalibrationY()).compareTo(BigDecimal.valueOf(0L))<=0){
				iPrinter.drawLine(2,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),0,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getHeight()),false);
			}else{
				iPrinter.drawLine(2,NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY()).add(pagedetails.getHeight())),false);
			}
		}
	}
	
}
