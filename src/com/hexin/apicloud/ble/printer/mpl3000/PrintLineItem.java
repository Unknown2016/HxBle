package com.hexin.apicloud.ble.printer.mpl3000;
import java.math.BigDecimal;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.enums.LineTypeEnum;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.shenyuan.fujitsu.mylibrary.lib.bt.Printer;
/**
 * 打印线条
 * 实现类
 * @author jundao
 */
public class PrintLineItem implements IPrintTemplateItem {

	@Override
	public void printItem(Printer printer,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		/**
		 * x0 直线起点的横坐标,单位为 mm
		 * y0 直线起点的纵坐标,单位为 mm 
		 * x1 直线终点的横坐标,单位为 mm 
		 * y1 直线终点的纵坐标,单位为 mm 
		 * width 直线的宽度,单位为像素
		 */
		//打印线条
		if(LineTypeEnum.HORIZONTAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.HORIZONTAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			//lpk130.NFCP_Page_drawLine(sx, sy, ex, ey)
			// 水平实线、水平虚线
			if(pagedetails.getX().add(template.getCalibrationX()).compareTo(BigDecimal.valueOf(0L))<=0){
				 printer.draw_line(0,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()));
			}else{
				 printer.draw_line(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX()).add(pagedetails.getWidth())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()));
			}
		}else if(LineTypeEnum.VERTICAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.VERTICAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			// 垂直实线、垂直虚线
			if(pagedetails.getY().add(template.getCalibrationY()).compareTo(BigDecimal.valueOf(0L))<=0){
				 printer.draw_line(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),0, NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getHeight()),NumberUtil.mm2Dot(pagedetails.getHeight()));
			}else{
				 printer.draw_line(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY()).add(pagedetails.getHeight())),NumberUtil.mm2Dot(pagedetails.getHeight()));
			}
		}
	}
	
}
