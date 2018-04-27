package com.hexin.apicloud.ble.bean;
/**
 * 扩展信息
 * 实体类
 * @author 军刀
 */
public class ThermalExdata {
    /**
     * 大头笔/一级流向信息
     */
    private String markDestination;
    /**
     * 集包地代码
     */
    private String packageCenterCode;
    /**
     * 集包地名称
     */
    private String packageCenterName;
    /**
     * 菜鸟旧打印数据
     */
    private String printConfig;
    /**
     * 菜鸟云打印数据
     */
    private String printData;

    /**
     * 揽货商（分拨中心）编码
     */
    private String allocatorCode;
    /**
     * 揽货商（分拨中心）名称
     */
    private String allocatorName;
    /**
     * 二级配送公司编码
     */
    private String tmsCode;
    /**
     * 二级配送公司名称
     */
    private String tmsName;
    /**
     * 二级流向信息
     */
    private String secDistribution;
    /**
     * 物流商公司编码 （ERP在调用菜鸟发货接口时此字段赋值到tms_code, 调用淘宝“自己联系物流（线下物流）发货”时，做为company_code传入）
     */
    private String logisticsCode;
    /**
     * 物流商名称
     */
    private String logisticsName;
    /**
     * 目的地网点
     */
    private String destSiteName;
    /**
     * 安能转单
     */
    private String aneNewNeed;
    // 顺丰
    /**
     * 主单号
     */
    private String masterSid;
    /**
     * 包裹数量
     */
    private Integer size;
    /**
     * 当前序号
     */
    private Integer num;
    // 韵达信息
    private String senderAreaNames;
    private String senderBranch;
    private String senderBranchJc;
    private String receiverAreaNames;
    // 京东信息
    private String agingName;
    private Integer targetSortCenterId;
    private String sourcetSortCenterName;
    private Integer sourcetSortCenterId;
    private String road;
    private String originalTabletrolleyCode;
    private String destinationCrossCode;
    private Integer siteId;
    private String originalCrossCode;
    private String destinationTabletrolleyCode;
    private String targetSortCenterName;
    private String siteName;
    private Integer aging;

    public String getMasterSid() {
        return masterSid;
    }

    public void setMasterSid(String masterSid) {
        this.masterSid = masterSid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getMarkDestination() {
        return markDestination;
    }

    public void setMarkDestination(String markDestination) {
        this.markDestination = markDestination;
    }

    public String getPackageCenterCode() {
        return packageCenterCode;
    }

    public void setPackageCenterCode(String packageCenterCode) {
        this.packageCenterCode = packageCenterCode;
    }

    public String getPackageCenterName() {
        return packageCenterName;
    }

    public void setPackageCenterName(String packageCenterName) {
        this.packageCenterName = packageCenterName;
    }

    public String getPrintConfig() {
        return printConfig;
    }

    public void setPrintConfig(String printConfig) {
        this.printConfig = printConfig;
    }

    public String getPrintData() {
        return printData;
    }

    public void setPrintData(String printData) {
        this.printData = printData;
    }

    public String getAllocatorCode() {
        return allocatorCode;
    }

    public void setAllocatorCode(String allocatorCode) {
        this.allocatorCode = allocatorCode;
    }

    public String getAllocatorName() {
        return allocatorName;
    }

    public void setAllocatorName(String allocatorName) {
        this.allocatorName = allocatorName;
    }

    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    public String getTmsName() {
        return tmsName;
    }

    public void setTmsName(String tmsName) {
        this.tmsName = tmsName;
    }

    public String getSecDistribution() {
        return secDistribution;
    }

    public void setSecDistribution(String secDistribution) {
        this.secDistribution = secDistribution;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getDestSiteName() {
        return destSiteName;
    }

    public void setDestSiteName(String destSiteName) {
        this.destSiteName = destSiteName;
    }

    public String getAneNewNeed() {
        return aneNewNeed;
    }

    public void setAneNewNeed(String aneNewNeed) {
        this.aneNewNeed = aneNewNeed;
    }

    public String getSenderAreaNames() {
        return senderAreaNames;
    }

    public void setSenderAreaNames(String senderAreaNames) {
        this.senderAreaNames = senderAreaNames;
    }

    public String getSenderBranch() {
        return senderBranch;
    }

    public void setSenderBranch(String senderBranch) {
        this.senderBranch = senderBranch;
    }

    public String getSenderBranchJc() {
        return senderBranchJc;
    }

    public void setSenderBranchJc(String senderBranchJc) {
        this.senderBranchJc = senderBranchJc;
    }

    public String getReceiverAreaNames() {
        return receiverAreaNames;
    }

    public void setReceiverAreaNames(String receiverAreaNames) {
        this.receiverAreaNames = receiverAreaNames;
    }

    public String getAgingName() {
        return agingName;
    }

    public void setAgingName(String agingName) {
        this.agingName = agingName;
    }

    public Integer getTargetSortCenterId() {
        return targetSortCenterId;
    }

    public void setTargetSortCenterId(Integer targetSortCenterId) {
        this.targetSortCenterId = targetSortCenterId;
    }

    public String getSourcetSortCenterName() {
        return sourcetSortCenterName;
    }

    public void setSourcetSortCenterName(String sourcetSortCenterName) {
        this.sourcetSortCenterName = sourcetSortCenterName;
    }

    public Integer getSourcetSortCenterId() {
        return sourcetSortCenterId;
    }

    public void setSourcetSortCenterId(Integer sourcetSortCenterId) {
        this.sourcetSortCenterId = sourcetSortCenterId;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getOriginalTabletrolleyCode() {
        return originalTabletrolleyCode;
    }

    public void setOriginalTabletrolleyCode(String originalTabletrolleyCode) {
        this.originalTabletrolleyCode = originalTabletrolleyCode;
    }

    public String getDestinationCrossCode() {
        return destinationCrossCode;
    }

    public void setDestinationCrossCode(String destinationCrossCode) {
        this.destinationCrossCode = destinationCrossCode;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getOriginalCrossCode() {
        return originalCrossCode;
    }

    public void setOriginalCrossCode(String originalCrossCode) {
        this.originalCrossCode = originalCrossCode;
    }

    public String getDestinationTabletrolleyCode() {
        return destinationTabletrolleyCode;
    }

    public void setDestinationTabletrolleyCode(String destinationTabletrolleyCode) {
        this.destinationTabletrolleyCode = destinationTabletrolleyCode;
    }

    public String getTargetSortCenterName() {
        return targetSortCenterName;
    }

    public void setTargetSortCenterName(String targetSortCenterName) {
        this.targetSortCenterName = targetSortCenterName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getAging() {
        return aging;
    }

    public void setAging(Integer aging) {
        this.aging = aging;
    }
}