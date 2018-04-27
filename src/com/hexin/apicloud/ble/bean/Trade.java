package com.hexin.apicloud.ble.bean;
import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单
 * 实体类
 * @author 军刀
 */
public class Trade {
	
	/**
	 * 订单编号，主键
	 */
	private String tid;

	/**
	 * 快递网点ID 备注:分区字段，即organization表中is_store=1时的组织机构ID
	 */
	private Long organizationId;

	/**
	 * 快递网点名字
	 */
	private String organizationName;

	/**
	 * 快递员id
	 */
	private Long courierId;

	/**
	 * 快递员名字
	 */
	private String courierName;

	/**
	 * 快递公司ID 备注:来自于模板
	 */
	private Long expressId;

	/**
	 * 快递公司 备注:来自于模板
	 */
	private String expressName;

	/**
	 * 快递单号
	 */
	private String outSid;

	/**
	 * 收件人姓名
	 */
	private String reName;

	/**
	 * 收货人手机
	 */
	private String reMobile;

	/**
	 * 收件人电话
	 */
	private String rePhone;

	/**
	 * 收件人省份
	 */
	private String reProvince;

	/**
	 * 收件人城市
	 */
	private String reCity;

	/**
	 * 收件人区域
	 */
	private String reArea;

	/**
	 * 收件人详细地址
	 */
	private String reAddress;

	/**
	 * 收件人地址信息
	 */
	private String reCityInfo;

	/**
	 * 寄件人ID
	 */
	private Long senderId;

	/**
	 * 寄件人姓名
	 */
	private String seName;

	/**
	 * 寄件人手机
	 */
	private String seMobile;

	/**
	 * 寄件人电话
	 */
	private String sePhone;

	/**
	 * 寄件人省份
	 */
	private String seProvince;

	/**
	 * 寄件人城市
	 */
	private String seCity;

	/**
	 * 寄件人区域
	 */
	private String seArea;

	/**
	 * 寄件人详细地址
	 */
	private String seAddress;

	/**
	 * 寄件人地址信息
	 */
	private String seCityInfo;

	/**
	 * 品类
	 */
	private String category;
	
	/**
	 * 宝贝数量
	 */
	private String num;

	/**
	 * 车牌号码
	 */
	private String carNo;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 距离 备注:接单时，快递员当前位置与寄件人之间的距离
	 */
	private Integer distance;

	/**
	 * 重量 备注:单位：克
	 */
	private BigDecimal weight;

	/**
	 * 支付方式 备注:0：现付 1：到付
	 */
	private Integer paymentMode;

	/**
	 * 运费
	 */
	private BigDecimal postFee;

	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;

	/**
	 * 订单状态 备注:CREATED:订单已创建; WAIT_ACCEPT:等待接单; WAIT_PRINT:已接单等待打单; PRINTED:已打单;
	 * SEND_SUCCESS:发货成功; REJECTED:已拒接（接单前快递员拒绝接单）;
	 * CLOSED_BEFORE_ACCEPT:已取消（接单前客户自己取消）; CANCELLING:撤单中; CANCELED:已撤单;
	 */
	private String tradeStatus;

	/**
	 * 撤单状态 备注:NO_CANCEL:非撤单状态; BUYER_CREATED:下单人请求撤单; COURIER_CREATED:快递员请求撤单;
	 * BUYER_ACCEPTED:下单人同意撤单; COURIER_ACCEPTED:快递员同意撤单; BUYER_REJECTED:下单人拒绝撤单;
	 * COURIER_REJECTED:快递员拒绝撤单;
	 */
	private String cancelStatus;

	/**
	 * 期望拿件时间项
	 */
	private String expectTimeItem;

	/**
	 * 预计拿件时间项
	 */
	private String estimateTimeItem;

	/**
	 * 留言
	 */
	private String buyerMessage;

	/**
	 * 快递员备注
	 */
	private String courierMemo;
	
	/**
	 * 卖家备注
	 */
	private String sellerMemo;
	
	/**
	 * 我打备注
	 */
	private String wdMemo;

	/**
	 * 请求撤单原因
	 */
	private String cancelReason;

	/**
	 * 拒接或拒绝撤单原因
	 */
	private String refuseReason;

	/**
	 * 下单人ID（即微信里的unionid）
	 */
	private String buyerId;

	/**
	 * 下单人昵称
	 */
	private String buyerNick;

	/**
	 * 快递员在LBS里的ID
	 */
	private Long hxUserId;

	/**
	 * 市级管理员账号在LBS里的ID
	 */
	private Long masterHxUserId;

	/**
	 * 打印次数 备注:每成功打印一次加1
	 */
	private Integer printTimes;

	/**
	 * 最后一次打印时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date printTime;

	/**
	 * 纸张材质类型 备注:0:普通;1:热敏纸
	 */
	private Integer materialType;

	/**
	 * 快递单的模板ID
	 */
	private Long expressTemplateId;

	/**
	 * 快递单的模板名称
	 */
	private String expressTemplateName;

	/**
	 * 大头笔
	 */
	private String markDestination;

	/**
	 * 集包地
	 */
	private String packageCenterName;
	
	/**
	 * 集包地代码
	 */
	private String packageCenterCode;

	/**
	 * 是否是拷贝的包裹
	 */
	private Integer isCopy;

	/**
	 * 业务来源 备注:general:一般业务 car:车牌业务
	 */
	private String businessFrom;

	/**
	 * 终端来源 备注:kuaifa_pc:快发PC版 kuaifa_wechat:微信公众号 express_app:快递APP
	 */
	private String terminalFrom;

	/**
	 * 下单时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 接单时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date acceptTime;

	/**
	 * 拒接时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date rejectTime;

	/**
	 * 请求撤单时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date waitCancelTime;

	/**
	 * 拒绝撤单时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date rejectCancelTime;

	/**
	 * 成功撤单时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date cancelTime;

	/**
	 * 取消时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date closeTime;

	/**
	 * 发货时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;

	/**
	 * 快递员电话
	 */
	private String courierMobile;

	/**
	 * 网点电话
	 */
	private String organizationMobile;

	/**
	 * pc手动获取单号快递面单Id
	 */
	private String pcExpressTemplateId;

	/**
	 * 付款渠道
	 */
	private String paymentFrom;
	
	/**
	 * 扩展数据
	 */
	private ThermalExdata thermalExdata;

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTid() {
		return this.tid;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}

	public Long getCourierId() {
		return this.courierId;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierName() {
		return this.courierName;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	public Long getExpressId() {
		return this.expressId;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressName() {
		return this.expressName;
	}

	public void setOutSid(String outSid) {
		this.outSid = outSid;
	}

	public String getOutSid() {
		return this.outSid;
	}

	public void setReName(String reName) {
		this.reName = reName;
	}

	public String getReName() {
		return this.reName;
	}

	public void setReMobile(String reMobile) {
		this.reMobile = reMobile;
	}

	public String getReMobile() {
		return this.reMobile;
	}

	public void setRePhone(String rePhone) {
		this.rePhone = rePhone;
	}

	public String getRePhone() {
		return this.rePhone;
	}

	public void setReProvince(String reProvince) {
		this.reProvince = reProvince;
	}

	public String getReProvince() {
		return this.reProvince;
	}

	public void setReCity(String reCity) {
		this.reCity = reCity;
	}

	public String getReCity() {
		return this.reCity;
	}

	public void setReArea(String reArea) {
		this.reArea = reArea;
	}

	public String getReArea() {
		return this.reArea;
	}

	public void setReAddress(String reAddress) {
		this.reAddress = reAddress;
	}

	public String getReAddress() {
		return this.reAddress;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getSenderId() {
		return this.senderId;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public String getSeName() {
		return this.seName;
	}

	public void setSeMobile(String seMobile) {
		this.seMobile = seMobile;
	}

	public String getSeMobile() {
		return this.seMobile;
	}

	public void setSePhone(String sePhone) {
		this.sePhone = sePhone;
	}

	public String getSePhone() {
		return this.sePhone;
	}

	public void setSeProvince(String seProvince) {
		this.seProvince = seProvince;
	}

	public String getSeProvince() {
		return this.seProvince;
	}

	public void setSeCity(String seCity) {
		this.seCity = seCity;
	}

	public String getSeCity() {
		return this.seCity;
	}

	public void setSeArea(String seArea) {
		this.seArea = seArea;
	}

	public String getSeArea() {
		return this.seArea;
	}

	public void setSeAddress(String seAddress) {
		this.seAddress = seAddress;
	}

	public String getSeAddress() {
		return this.seAddress;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getDistance() {
		return this.distance;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public Integer getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}

	public BigDecimal getPostFee() {
		return this.postFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getInsuranceFee() {
		return this.insuranceFee;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeStatus() {
		return this.tradeStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getCancelStatus() {
		return this.cancelStatus;
	}

	public void setExpectTimeItem(String expectTimeItem) {
		this.expectTimeItem = expectTimeItem;
	}

	public String getExpectTimeItem() {
		return this.expectTimeItem;
	}

	public void setEstimateTimeItem(String estimateTimeItem) {
		this.estimateTimeItem = estimateTimeItem;
	}

	public String getEstimateTimeItem() {
		return this.estimateTimeItem;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getBuyerMessage() {
		return this.buyerMessage;
	}

	public void setCourierMemo(String courierMemo) {
		this.courierMemo = courierMemo;
	}

	public String getCourierMemo() {
		return this.courierMemo;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getBuyerNick() {
		return this.buyerNick;
	}

	public void setHxUserId(Long hxUserId) {
		this.hxUserId = hxUserId;
	}

	public Long getHxUserId() {
		return this.hxUserId;
	}

	public void setMasterHxUserId(Long masterHxUserId) {
		this.masterHxUserId = masterHxUserId;
	}

	public Long getMasterHxUserId() {
		return this.masterHxUserId;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	public Integer getPrintTimes() {
		return this.printTimes;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public Date getPrintTime() {
		return this.printTime;
	}

	public void setExpressTemplateId(Long expressTemplateId) {
		this.expressTemplateId = expressTemplateId;
	}

	public Long getExpressTemplateId() {
		return this.expressTemplateId;
	}

	public void setExpressTemplateName(String expressTemplateName) {
		this.expressTemplateName = expressTemplateName;
	}

	public String getExpressTemplateName() {
		return this.expressTemplateName;
	}

	public void setMarkDestination(String markDestination) {
		this.markDestination = markDestination;
	}

	public String getMarkDestination() {
		return this.markDestination;
	}

	public void setPackageCenterName(String packageCenterName) {
		this.packageCenterName = packageCenterName;
	}

	public String getPackageCenterName() {
		return this.packageCenterName;
	}

	public Integer getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Integer isCopy) {
		this.isCopy = isCopy;
	}

	public void setBusinessFrom(String businessFrom) {
		this.businessFrom = businessFrom;
	}

	public String getBusinessFrom() {
		return this.businessFrom;
	}

	public void setTerminalFrom(String terminalFrom) {
		this.terminalFrom = terminalFrom;
	}

	public String getTerminalFrom() {
		return this.terminalFrom;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Date getAcceptTime() {
		return this.acceptTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	public Date getRejectTime() {
		return this.rejectTime;
	}

	public void setWaitCancelTime(Date waitCancelTime) {
		this.waitCancelTime = waitCancelTime;
	}

	public Date getWaitCancelTime() {
		return this.waitCancelTime;
	}

	public void setRejectCancelTime(Date rejectCancelTime) {
		this.rejectCancelTime = rejectCancelTime;
	}

	public Date getRejectCancelTime() {
		return this.rejectCancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getCancelTime() {
		return this.cancelTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public String getReCityInfo() {
		return reCityInfo;
	}

	public void setReCityInfo(String reCityInfo) {
		this.reCityInfo = reCityInfo;
	}

	public String getSeCityInfo() {
		return seCityInfo;
	}

	public void setSeCityInfo(String seCityInfo) {
		this.seCityInfo = seCityInfo;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCourierMobile() {
		return courierMobile;
	}

	public void setCourierMobile(String courierMobile) {
		this.courierMobile = courierMobile;
	}

	public String getOrganizationMobile() {
		return organizationMobile;
	}

	public void setOrganizationMobile(String organizationMobile) {
		this.organizationMobile = organizationMobile;
	}

	public String getPcExpressTemplateId() {
		return pcExpressTemplateId;
	}

	public void setPcExpressTemplateId(String pcExpressTemplateId) {
		this.pcExpressTemplateId = pcExpressTemplateId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getPaymentFrom() {
		return paymentFrom;
	}

	public void setPaymentFrom(String paymentFrom) {
		this.paymentFrom = paymentFrom;
	}

	public ThermalExdata getThermalExdata() {
		return thermalExdata;
	}

	public void setThermalExdata(ThermalExdata thermalExdata) {
		this.thermalExdata = thermalExdata;
	}
	
	public String getPackageCenterCode() {
		return packageCenterCode;
	}

	public void setPackageCenterCode(String packageCenterCode) {
		this.packageCenterCode = packageCenterCode;
	}

	public String getWdMemo() {
		return wdMemo;
	}

	public void setWdMemo(String wdMemo) {
		this.wdMemo = wdMemo;
	}
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}
	
}