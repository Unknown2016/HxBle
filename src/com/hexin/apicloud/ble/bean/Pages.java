package com.hexin.apicloud.ble.bean;
import java.util.List;
/**
 * 页面
 * 实体类
 * @author 军刀
 */
public class Pages {
	
	/**
	 * 扩展数据
	 */
	private List<Extend> extend;
	
	/**
	 * 页面明细
	 */
	private List<Pagedetails> pageDetails;
	public List<Extend> getExtend() {
		return extend;
	}
	public void setExtend(List<Extend> extend) {
		this.extend = extend;
	}
	public List<Pagedetails> getPageDetails() {
		return pageDetails;
	}
	public void setPageDetails(List<Pagedetails> pageDetails) {
		this.pageDetails = pageDetails;
	}
}
