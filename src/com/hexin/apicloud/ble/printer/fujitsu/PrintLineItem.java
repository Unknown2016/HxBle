package com.hexin.apicloud.ble.printer.fujitsu;
import java.math.BigDecimal;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.enums.LineTypeEnum;
import com.hexin.apicloud.ble.util.NumberUtil;
/**
 * 打印线条
 * 实现类
 * @author jundao
 */
public class PrintLineItem implements IPrintTemplateItem {

	@Override
	public void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		/**
		 * 页模式下绘制宽度为2个打印点的线段
		 * 参数:
		 * sx - 起始点横坐标
		 * sy - 起始点纵坐标
		 * ex - 结束点横坐标
		 * ey - 结束点纵坐标
		 */
		//打印线条
		if(LineTypeEnum.HORIZONTAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.HORIZONTAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			//lpk130.NFCP_Page_drawLine(sx, sy, ex, ey)
			// 水平实线、水平虚线
			if(pagedetails.getX().add(template.getCalibrationX()).compareTo(BigDecimal.valueOf(0L))<=0){
				 lpk130.NFCP_Page_drawLine(0,NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getWidth()),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())));
			}else{
				 lpk130.NFCP_Page_drawLine(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX()).add(pagedetails.getWidth())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())));
			}
		}else if(LineTypeEnum.VERTICAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.VERTICAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			// 垂直实线、垂直虚线
			if(pagedetails.getY().add(template.getCalibrationY()).compareTo(BigDecimal.valueOf(0L))<=0){
				 lpk130.NFCP_Page_drawLine(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),0, NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getHeight()));
			}else{
				 lpk130.NFCP_Page_drawLine(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY()).add(pagedetails.getHeight())));
			}
		}
	}
	
}
