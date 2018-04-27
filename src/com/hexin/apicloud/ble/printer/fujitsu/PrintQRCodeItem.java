package com.hexin.apicloud.ble.printer.fujitsu;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.util.NumberUtil;
/**
 * 打印二维码
 * 实现类
 * @author jundao
 */
public class PrintQRCodeItem implements IPrintTemplateItem {
	
	/**
	 * x - 打印的起始横坐标
	 * y - 打印的起始纵坐标
	 * rotate - 旋转角度 0：不旋转；1：90度；2：180°；3:270°
	 * Ver - DQCODE版本号
	 * lel - 纠错等级 0：纠错等级为L 1：纠错等级为M 2：纠错等级为Q 3：纠错等级为H
	 * Text - 要打印的字符串
	 */
	@Override
	public void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		int qrWidth = pagedetails.getWidth().intValue();
		// 获取汉印二维码单位宽度
		int ver =  QrWidthEnum.valueOf(qrWidth).getIndex();
		lpk130.NFCP_Page_printQrCode(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())), 0, ver, 3, pagedetails.getItemValue());
	}

	/**
	 * 二维码宽度枚举
	 * 模板二维码宽度和汉印二维码宽度 转换
	 *
	 */
	public enum QrWidthEnum {
		// 富士通二维码 目前只取 1-20
		WIDTH1(1,13),WIDTH2(2,15),WIDTH3(3,17),WIDTH4(4,19),WIDTH5(5,21),WIDTH6(6,23),WIDTH7(7,25),WIDTH8(8,27),WIDTH9(9,29),WIDTH10(10,31),
		WIDTH11(11,33),WIDTH12(12,35),WIDTH13(13,37),WIDTH14(14,39),WIDTH15(15,41),WIDTH16(16,43),WIDTH17(17,45),WIDTH18(18,47),WIDTH19(19,50),WIDTH20(20,76);
	    
		//富士通 二维码宽度
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
	    	if(width <= QrWidthEnum.WIDTH1.getWidth()){
			    return QrWidthEnum.WIDTH1;
			}else if(width <= QrWidthEnum.WIDTH2.getWidth()){
			    return QrWidthEnum.WIDTH2;
			}else if(width <= QrWidthEnum.WIDTH3.getWidth()){
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
		    }else if(width <= QrWidthEnum.WIDTH11.getWidth()){
			    return QrWidthEnum.WIDTH11;
			}else if(width <= QrWidthEnum.WIDTH12.getWidth()){
			    return QrWidthEnum.WIDTH12;
			}else if(width <= QrWidthEnum.WIDTH13.getWidth()){
			    return QrWidthEnum.WIDTH13;
			}else if(width <= QrWidthEnum.WIDTH14.getWidth()){
		    	return QrWidthEnum.WIDTH14;
		    }else if(width <= QrWidthEnum.WIDTH15.getWidth()){
		    	return QrWidthEnum.WIDTH15;
		    }else if(width <= QrWidthEnum.WIDTH16.getWidth()){
			    return QrWidthEnum.WIDTH16;
			}else if(width <= QrWidthEnum.WIDTH17.getWidth()){
			    return QrWidthEnum.WIDTH17;
			}else if(width <= QrWidthEnum.WIDTH18.getWidth()){
		    	return QrWidthEnum.WIDTH18;
		    }else if(width <= QrWidthEnum.WIDTH19.getWidth()){
		    	return QrWidthEnum.WIDTH19;
		    }else{
		    	return QrWidthEnum.WIDTH20;
		    }
	    }
	}
}
