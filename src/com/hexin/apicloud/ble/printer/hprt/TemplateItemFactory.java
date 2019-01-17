package com.hexin.apicloud.ble.printer.hprt;
import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;
import com.hexin.apicloud.ble.enums.PrintItemEnum;

/**
 * 打印项目模板工厂
 * @author 军刀
 */
@SuppressLint("UseSparseArrays")
public class TemplateItemFactory {
	
	public TemplateItemFactory() {
		super();
	}
	
	private static class TemplateItemFactoryHolder {
        private final static TemplateItemFactory INSTANCE = new TemplateItemFactory();
    }

	/**
     * 工厂实例
     * @return
     */
    public static TemplateItemFactory getInstance() {
        return TemplateItemFactoryHolder.INSTANCE;
    }
	
	/**
	 * 电子面单模板实现类Map
	 * key:快递模板类型
	 * value:电子面单模板实现类
	 */
	private static Map<Integer,IPrintTemplateItem> templateItemMap = new HashMap<Integer,IPrintTemplateItem>();
	
	/**
	 * TEXT(0, "文本"),IMAGE(1, "图片"),LINE(2,"线条"),QRCODE(3,"二维码"),RECTANGLE(4,"矩形"),BARCODE(5,"条码"),
     * GRID(10,"表格"),SEQNUMBER(11,"序号"),BLANK(12,"空白"),WATERMARK(13,"水印");
	 */
	static{
		templateItemMap.put(PrintItemEnum.TEXT.ordinal(), new PrintTextItem());
		templateItemMap.put(PrintItemEnum.IMAGE.ordinal(), new PrintImageItem());
		templateItemMap.put(PrintItemEnum.LINE.ordinal(), new PrintLineItem());
		templateItemMap.put(PrintItemEnum.QRCODE.ordinal(), new PrintQRCodeItem());
		templateItemMap.put(PrintItemEnum.RECTANGLE.ordinal(), new PrintRectangleItem());
		templateItemMap.put(PrintItemEnum.BARCODE.ordinal(), new PrintBarcodeItem());
		templateItemMap.put(PrintItemEnum.GRID.getIndex(), new PrintGridItem());
		templateItemMap.put(PrintItemEnum.SEQNUMBER.getIndex(), new PrintSeqNumberItem());
		templateItemMap.put(PrintItemEnum.BLANK.getIndex(), new PrintBlankItem());
		templateItemMap.put(PrintItemEnum.WATERMARK.getIndex(), new PrintWaterMarkItem());
    }
	
	/**
	 * 生成快递电子面单模板
	 * @param type 快递模板类型
	 * @return
	 */
    public IPrintTemplateItem createTemplateItem(Integer type){
       return templateItemMap.get(type);
    }
    
}
