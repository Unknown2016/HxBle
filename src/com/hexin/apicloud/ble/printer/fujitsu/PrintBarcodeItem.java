package com.hexin.apicloud.ble.printer.fujitsu;
import com.alibaba.fastjson.JSONObject;
import com.fujitsu.sdk.LPK130;
import com.hexin.apicloud.ble.bean.Pagedetails;
import com.hexin.apicloud.ble.bean.Template;
import com.hexin.apicloud.ble.bean.Trade;
import com.hexin.apicloud.ble.bean.Trade2Template;
import com.hexin.apicloud.ble.util.BeanUtil;
import com.hexin.apicloud.ble.util.NumberUtil;
import com.hexin.apicloud.ble.util.StringUtil;

/**
 * 打印条形码
 * 实现类
 * @author jundao
 */
public class PrintBarcodeItem implements IPrintTemplateItem {

	/**
	 * 参数:
	 * x - 打印的起始横坐标
	 * y - 打印的起始纵坐标
	 * str - 字符串
	 * barcodetype - 条码类型 0：CODE39；1：CODE128；2：CODE93；3：CODEBAR；4：EAN8；5：EAN13；6：UPCA ;7:UPC-E;8:ITF
	 * rotate - 旋转角度 0：不旋转；1：90度；2：180°；3:270°
	 * barWidth - 条码宽度
	 * barHeight - 条码高度
	 */
	@Override
	public void printItem(LPK130 lpk130,Template template,Pagedetails pagedetails,Trade trade) throws Exception{
		//lpk130.NFCP_drawBarCode
		Trade2Template trade2Template = new Trade2Template();
		BeanUtil.copyProperty(trade, trade2Template);
		JSONObject tradeJson = (JSONObject) JSONObject.toJSON(trade2Template);
		//HPRTPrinterHelper .Barcode(HPRTPrinterHelper.BARCODE,HPRTPrinterHelper.128,”1”,”1”,”50”,”0”,”0”,true,”7 ”,”0”, ”5”,”123456789”)
		int barWidth = pagedetails.getWidth().intValue();
		// 获取富士通条码窄条的单位宽度
		int lineWidth =  BarWidthEnum.valueOf(barWidth).getIndex();
		if(!StringUtil.isEmpty(tradeJson.getString(pagedetails.getItemValue()))){
			//打印条码
			lpk130.NFCP_Page_drawBar(NumberUtil.mm2Dot(pagedetails.getX().add(template.getCalibrationX())), NumberUtil.mm2Dot(pagedetails.getY().add(template.getCalibrationY())),tradeJson.getString(pagedetails.getItemValue()), 1,0,lineWidth,NumberUtil.mm2Dot(pagedetails.getHeight()));
		}
	}

	/**
	 * 条码宽度枚举
	 * 模板条码宽度和富士通条码宽度 转换
	 *
	 */
	public enum BarWidthEnum {
		
		WIDTH1(1,15),WIDTH2(2,30),WIDTH3(3,47),WIDTH4(4,62),WIDTH5(5,76);
	    
		//富士通 fontsize
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
		    }else if(width <= BarWidthEnum.WIDTH4.getWidth()){
		    	return BarWidthEnum.WIDTH4;
		    }else{
		    	return BarWidthEnum.WIDTH5;
		    }
	    }
	}
}
