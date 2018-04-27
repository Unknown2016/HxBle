package com.hexin.apicloud.ble.printer.qr386a;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.qr.printer.Printer;
/**
 * 打印二维码
 * 实现类
 * @author jundao
 */
public class PrintQRCodeItem implements IPrintTemplateItem {
	/**
	 * start_x - 二维码起始横坐标
	 * start_y - 二维码起始纵坐标
	 * text - 二维码内容
	 * rotate - 旋转角度 0:不旋转; 1:90 度; 2:180°; 3:270° 
	 * ver - QrCode 宽度(2-6)
	 * lel - QrCode 纠错等级(0-20)
	 */
	@Override
	public void printItem(Printer iPrinter,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		int qrWidth = pagedetails.getWidth().intValue();
		// 获取二维码宽度
		int ver =  QrWidthEnum.valueOf(qrWidth).getIndex();
		iPrinter.drawQrCode(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),pagedetails.getItemValue(), 0, ver, 5);
	}

	/**
	 * 二维码宽度枚举
	 * 模板二维码宽度和启锐386二维码宽度 转换
	 *
	 */
	public enum QrWidthEnum {
		
		WIDTH2(2,12),WIDTH3(3,16),WIDTH4(4,19),WIDTH5(5,22),WIDTH6(6,76);
	    
		//启锐386 二维码宽度
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
	    	if(width <= QrWidthEnum.WIDTH2.getWidth()){
			    return QrWidthEnum.WIDTH3;
			}else if(width <= QrWidthEnum.WIDTH3.getWidth()){
			    return QrWidthEnum.WIDTH3;
			}else if(width <= QrWidthEnum.WIDTH4.getWidth()){
		    	return QrWidthEnum.WIDTH4;
		    }else if(width <= QrWidthEnum.WIDTH5.getWidth()){
		    	return QrWidthEnum.WIDTH5;
		    }else if(width <= QrWidthEnum.WIDTH6.getWidth()){
		    	return QrWidthEnum.WIDTH6;
		    }else{
		    	return QrWidthEnum.WIDTH6;
		    }
	    }
	}
}
