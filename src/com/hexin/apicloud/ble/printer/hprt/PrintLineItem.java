package com.hexin.apicloud.ble.printer.hprt;
import java.math.BigDecimal;

import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.enums.LineTypeEnum;
import com.hexin.apicloud.ble.util.NumberUtil;
import HPRTAndroidSDK.HPRTPrinterHelper;
/**
 * 打印线条
 * 实现类
 * @author jundao
 */
public class PrintLineItem implements IPrintTemplateItem {

	@Override
	public void printItem(Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		
		/**
		 *
		 * X0:起始的 X 坐标。
		 * Y0:起始的 Y 坐标。 
		 * X1:结尾的 X 坐标。 
		 * Y1:结尾的 Y 坐标。 
		 * Width:线条的单位宽度。 
		 * 
		 */
		if(LineTypeEnum.HORIZONTAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.HORIZONTAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			// 水平实线、水平虚线
			if(pagedetails.getX().add(template.getCalibrationX()).compareTo(BigDecimal.valueOf(0L))<=0){
				HPRTPrinterHelper.Line("0","" + NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), "" + NumberUtil.mm2Dot(pagedetails.getWidth()),"" +  NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),"2");
			}else{
				HPRTPrinterHelper.Line("" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"" + NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), "" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX()).add(pagedetails.getWidth())),"" +  NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),"2");
			}
		}else if(LineTypeEnum.VERTICAL_LINE_1.getValue().equalsIgnoreCase(pagedetails.getItemCode()) || LineTypeEnum.VERTICAL_LINE_2.getValue().equalsIgnoreCase(pagedetails.getItemCode())){
			// 垂直实线、垂直虚线
			if(pagedetails.getY().add(template.getCalibrationY()).compareTo(BigDecimal.valueOf(0L))<=0){
				HPRTPrinterHelper.Line("" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"0","" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"" + NumberUtil.mm2Dot(pagedetails.getHeight()),"2");
			}else{
				HPRTPrinterHelper.Line("" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"" + NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),"" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"" + NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY()).add(pagedetails.getHeight())),"2");
			}
		}
	}
	
}
