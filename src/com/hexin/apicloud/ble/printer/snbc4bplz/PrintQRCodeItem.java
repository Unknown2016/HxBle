package com.hexin.apicloud.ble.printer.snbc4bplz;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import com.snbc.sdk.barcode.enumeration.QRLevel;
import com.snbc.sdk.barcode.enumeration.QRMode;
import com.snbc.sdk.barcode.enumeration.Rotation;
/**
 * 打印二维码
 * 实现类
 * @author jundao
 */
public class PrintQRCodeItem implements IPrintTemplateItem {
	
	/**
	 * 	x	横坐标，单位点。
	 * 	y	纵坐标，单位点。
	 * 	rotate	打印旋转角度。
	 * 	content	条码内容。
	 * 	ECCLever	安全级别。'H'表示极高可靠性级别, 'Q'表示高可靠性级别, 'M'表示标准级别, 'L'表示高密度级别
	 *	cellWidth	模块宽度。
	 * 	model	条码模式, 1为原始模式，2为增强模式
	 */
	@Override
	public void printItem(ILabelEdit labelEdit,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		int qrWidth = pagedetails.getWidth().intValue();
		// 获取二维码单位宽度
		int cellWidth =  QrWidthEnum.valueOf(qrWidth).getIndex();
		labelEdit.printBarcodeQR(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),Rotation.Rotation0,pagedetails.getItemValue(), QRLevel.QR_LEVEL_H.getLevel(),cellWidth, QRMode.QR_MODE_ENHANCED.getMode());
	}
	
	/**
	 * 二维码宽度枚举
	 * 模板二维码宽度和新北洋二维码宽度 转换
	 *
	 */
	public enum QrWidthEnum {
		// 目前只取 3-10
		WIDTH3(3,9),WIDTH4(4,12),WIDTH5(5,15),WIDTH6(6,18),WIDTH7(7,21),WIDTH8(8,24),WIDTH9(9,27),WIDTH10(10,30);
	    
		// 二维码宽度
	    private int index;
	   
	    //模板数据中width
	    private int width;
	   
	    private QrWidthEnum(int index,int width) {
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
	 
	    public static QrWidthEnum valueOf(int width) {
	    	if(width <= QrWidthEnum.WIDTH3.getWidth()){
			    return QrWidthEnum.WIDTH3;
			}else if(width <= QrWidthEnum.WIDTH4.getWidth()){
		    	return QrWidthEnum.WIDTH4;
		    }else if(width <= QrWidthEnum.WIDTH5.getWidth()){
		    	return QrWidthEnum.WIDTH5;
		    }else if(width <= QrWidthEnum.WIDTH6.getWidth()){
		    	return QrWidthEnum.WIDTH6;
		    }else if(width <= QrWidthEnum.WIDTH7.getWidth()){
		    	return QrWidthEnum.WIDTH7;
		    }else if(width <= QrWidthEnum.WIDTH8.getWidth()){
		    	return QrWidthEnum.WIDTH8;
		    }else if(width <= QrWidthEnum.WIDTH9.getWidth()){
		    	return QrWidthEnum.WIDTH9;
		    }else if(width <= QrWidthEnum.WIDTH10.getWidth()){
		    	return QrWidthEnum.WIDTH10;
		    }else{
		    	return QrWidthEnum.WIDTH6;
		    }
	    }
	}
}
