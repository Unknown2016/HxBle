package com.hexin.apicloud.ble.printer.hprt;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.NumberUtil;
import HPRTAndroidSDK.HPRTPrinterHelper;
/**
 * 打印二维码
 * 实现类
 * @author jundao
 */
public class PrintQRCodeItem implements IPrintTemplateItem {
	
	/**
	 * 参数: Command:打印方向: HPRTPrinterHelper.BARCODE:水平方向 HPRTPrinterHelper.VBARCODE:垂直方向
	 * X:二维码的起始横坐标。
	 * Y:二维码的起始纵坐标。
	 * M:QR 的类型:类型 1 和类型 2;类型 2 是增加了个别的符号,提供了而外的功能。
	 * U:单位宽度/模块的单元高度。范围是1到32默认为6。 
	 * Data:二维码的数据。
	 * 
	 */
	@Override
	public void printItem(Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		int qrWidth = pagedetails.getWidth().intValue();
		// 获取汉印二维码单位宽度
		String U =  QrWidthEnum.valueOf(qrWidth).getIndex();
		HPRTPrinterHelper.PrintQR(HPRTPrinterHelper.BARCODE,"" + NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())),"" + NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),"1" ,U,pagedetails.getItemValue());
	}

	/**
	 * 二维码宽度枚举
	 * 模板二维码宽度和汉印二维码宽度 转换
	 *
	 */
	public enum QrWidthEnum {
		// 汉印二维码 单位宽度1-32 目前只取 3-10
		WIDTH3("3",9),WIDTH4("4",12),WIDTH5("5",15),WIDTH6("6",18),WIDTH7("7",21),WIDTH8("8",24),WIDTH9("9",27),WIDTH10("10",30);
	    
		//汉印 二维码宽度
	    private String index;
	   
	    //模板数据中width
	    private int width;
	   
	    private QrWidthEnum(String index,int width) {
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
