package com.kxwp.admin.entity.serviceStation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kxwp.common.constants.FWZTypeEnum;
import com.kxwp.common.constants.OrganizationStatusEnum;
import com.kxwp.common.utils.KXWPDatetimeUtils;

public class ServiceStation {

  @Override
  public String toString() {
    return "ServiceStation [id=" + id + ", masterStationId=" + masterStationId
        + ", serviceStationName=" + serviceStationName + ", serviceStationAdmin="
        + serviceStationAdmin + ", feServiceStationId=" + feServiceStationId
        + ", phone=" + phone + ", province="
        + province + ", platformFee=" + platformFee
        + ", platformBzj=" + platformBzj + ", city="
        + city + ", county=" + county
        + ", town=" + town + ", adress="
        + adress + ", licenseNum=" + licenseNum
        + ", identityCardNum=" + identityCardNum + ",longitude ="
        + longitude + ", lantitude=" + lantitude
        + ", stationStatus=" + stationStatus + ", createUserId="
        + createUserId + ", serviceStaionType=" + serviceStaionType
        + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
  }

  private Long id;

  private Long masterStationId;

  private String serviceStationName;

  private String serviceStationAdmin;

  private String feServiceStationId;

  private Long phone;

  private Integer province;

  private BigDecimal platformFee;

  private BigDecimal platformBzj;


  // 对应地址信息的中文
  private String province_desc;
  
  private ServiceStationAccount serviceStationAccount;


  private Integer city;

  private String city_desc;

  private Integer county;

  private String county_desc;

  private Integer town;

  private String town_desc;

  private String adress;

  private String licenseNum;

  private String identityCardNum;

  private String longitude;

  private String lantitude;

  private OrganizationStatusEnum stationStatus;

  private Long createUserId;

  private FWZTypeEnum serviceStaionType;
  //服务站所有服务区域[省市县,省市县]
  private List<String> service_region_list = new ArrayList<>();

 

  // 时间范围统一用startTime,endTime
  @DateTimeFormat(iso = ISO.DATE_TIME, pattern = KXWPDatetimeUtils.YYYY_MM_DD_HH_MM_SS_DASH)
  @JsonFormat(pattern = KXWPDatetimeUtils.YYYY_MM_DD_HH_MM_SS_DASH, timezone = "Asia/Shanghai")
  private Date createTime;

  private Date updateTime;
  
  public List<String> getService_region_list() {
    return service_region_list;
  }

  public void setService_region_list(List<String> service_region_list) {
    this.service_region_list = service_region_list;
  }
  
  
  /**
   * @return province_desc
   * 
   */
  public String getProvince_desc() {
    return province_desc;
  }

  public void setProvince_desc(String province_desc) {
    this.province_desc = province_desc;
  }

  /**
   * @return city_desc
   * 
   */
  public String getCity_desc() {
    return city_desc;
  }

  public void setCity_desc(String city_desc) {
    this.city_desc = city_desc;
  }

  /**
   * @return county_desc
   * 
   */
  public String getCounty_desc() {
    return county_desc;
  }

  public void setCounty_desc(String county_desc) {
    this.county_desc = county_desc;
  }



  public BigDecimal getPlatformFee() {
    return platformFee;
  }

  public void setPlatformFee(BigDecimal platformFee) {
    this.platformFee = platformFee;
  }

  public BigDecimal getPlatformBzj() {
    return platformBzj;
  }

  public void setPlatformBzj(BigDecimal platformBzj) {
    this.platformBzj = platformBzj;
  }

  /**
   * @return town_desc
   * 
   */
  public String getTown_desc() {
    return town_desc;
  }

  public void setTown_desc(String town_desc) {
    this.town_desc = town_desc;
  }


  public String getFeServiceStationId() {
    return feServiceStationId;
  }

  public void setFeServiceStationId(String feServiceStationId) {
    this.feServiceStationId = feServiceStationId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMasterStationId() {
    return masterStationId;
  }

  public void setMasterStationId(Long masterStationId) {
    this.masterStationId = masterStationId;
  }

  public String getServiceStationName() {
    return serviceStationName;
  }

  public void setServiceStationName(String serviceStationName) {
    this.serviceStationName = serviceStationName == null ? null : serviceStationName.trim();
  }

  public String getServiceStationAdmin() {
    return serviceStationAdmin;
  }

  public void setServiceStationAdmin(String serviceStationAdmin) {
    this.serviceStationAdmin = serviceStationAdmin == null ? null : serviceStationAdmin.trim();
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public Integer getProvince() {
    return province;
  }

  public void setProvince(Integer province) {
    this.province = province;
  }

  public Integer getCity() {
    return city;
  }

  public void setCity(Integer city) {
    this.city = city;
  }

  public Integer getCounty() {
    return county;
  }

  public void setCounty(Integer county) {
    this.county = county;
  }

  public Integer getTown() {
    return town;
  }

  public void setTown(Integer town) {
    this.town = town;
  }

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress == null ? null : adress.trim();
  }

  public String getLicenseNum() {
    return licenseNum;
  }

  public void setLicenseNum(String licenseNum) {
    this.licenseNum = licenseNum == null ? null : licenseNum.trim();
  }

  public String getIdentityCardNum() {
    return identityCardNum;
  }

  public void setIdentityCardNum(String identityCardNum) {
    this.identityCardNum = identityCardNum == null ? null : identityCardNum.trim();
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude == null ? null : longitude.trim();
  }

  public String getLantitude() {
    return lantitude;
  }

  public void setLantitude(String lantitude) {
    this.lantitude = lantitude == null ? null : lantitude.trim();
  }

  public OrganizationStatusEnum getStationStatus() {
    return stationStatus;
  }

  public void setStationStatus(OrganizationStatusEnum stationStatus) {
    this.stationStatus = stationStatus;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public FWZTypeEnum getServiceStaionType() {
    return serviceStaionType;
  }

  public void setServiceStaionType(FWZTypeEnum serviceStaionType) {
    this.serviceStaionType = serviceStaionType;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }



  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    ServiceStation other = (ServiceStation) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getMasterStationId() == null ? other.getMasterStationId() == null
            : this.getMasterStationId().equals(other.getMasterStationId()))
        && (this.getServiceStationName() == null ? other.getServiceStationName() == null
            : this.getServiceStationName().equals(other.getServiceStationName()))
        && (this.getServiceStationAdmin() == null ? other.getServiceStationAdmin() == null
            : this.getServiceStationAdmin().equals(other.getServiceStationAdmin()))
        && (this.getPlatformFee() == null ? other.getPlatformFee() == null
            : this.getPlatformFee().equals(other.getPlatformFee()))
        && (this.getPlatformBzj() == null ? other.getPlatformBzj() == null
            : this.getPlatformBzj().equals(other.getPlatformBzj()))
        && (this.getPhone() == null ? other.getPhone() == null
            : this.getPhone().equals(other.getPhone()))
        && (this.getProvince() == null ? other.getProvince() == null
            : this.getProvince().equals(other.getProvince()))
        && (this.getCity() == null ? other.getCity() == null
            : this.getCity().equals(other.getCity()))
        && (this.getCounty() == null ? other.getCounty() == null
            : this.getCounty().equals(other.getCounty()))
        && (this.getTown() == null ? other.getTown() == null
            : this.getTown().equals(other.getTown()))
        && (this.getAdress() == null ? other.getAdress() == null
            : this.getAdress().equals(other.getAdress()))
        && (this.getLicenseNum() == null ? other.getLicenseNum() == null
            : this.getLicenseNum().equals(other.getLicenseNum()))
        && (this.getIdentityCardNum() == null ? other.getIdentityCardNum() == null
            : this.getIdentityCardNum().equals(other.getIdentityCardNum()))
        && (this.getLongitude() == null ? other.getLongitude() == null
            : this.getLongitude().equals(other.getLongitude()))
        && (this.getLantitude() == null ? other.getLantitude() == null
            : this.getLantitude().equals(other.getLantitude()))
        && (this.getStationStatus() == null ? other.getStationStatus() == null
            : this.getStationStatus().equals(other.getStationStatus()))
        && (this.getCreateUserId() == null ? other.getCreateUserId() == null
            : this.getCreateUserId().equals(other.getCreateUserId()))
        && (this.getServiceStaionType() == null ? other.getServiceStaionType() == null
            : this.getServiceStaionType().equals(other.getServiceStaionType()))
        && (this.getCreateTime() == null ? other.getCreateTime() == null
            : this.getCreateTime().equals(other.getCreateTime()))
        && (this.getUpdateTime() == null ? other.getUpdateTime() == null
            : this.getUpdateTime().equals(other.getUpdateTime()))
        && (this.getFeServiceStationId() == null ? other.getFeServiceStationId() == null
            : this.getFeServiceStationId().equals(other.getFeServiceStationId()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result =
        prime * result + ((getMasterStationId() == null) ? 0 : getMasterStationId().hashCode());
    result = prime * result
        + ((getServiceStationName() == null) ? 0 : getServiceStationName().hashCode());
    result = prime * result
        + ((getServiceStationAdmin() == null) ? 0 : getServiceStationAdmin().hashCode());
    result = prime * result + ((getPlatformFee() == null) ? 0 : getPlatformFee().hashCode());
    result = prime * result + ((getPlatformBzj() == null) ? 0 : getPlatformBzj().hashCode());
    result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
    result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
    result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
    result = prime * result + ((getCounty() == null) ? 0 : getCounty().hashCode());
    result = prime * result + ((getTown() == null) ? 0 : getTown().hashCode());
    result = prime * result + ((getAdress() == null) ? 0 : getAdress().hashCode());
    result = prime * result + ((getLicenseNum() == null) ? 0 : getLicenseNum().hashCode());
    result =
        prime * result + ((getIdentityCardNum() == null) ? 0 : getIdentityCardNum().hashCode());
    result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
    result = prime * result + ((getLantitude() == null) ? 0 : getLantitude().hashCode());
    result = prime * result + ((getStationStatus() == null) ? 0 : getStationStatus().hashCode());
    result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
    result =
        prime * result + ((getServiceStaionType() == null) ? 0 : getServiceStaionType().hashCode());
    result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = prime * result
        + ((getFeServiceStationId() == null) ? 0 : getFeServiceStationId().hashCode());
    result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
    return result;
  }

  /**
   * @return  serviceStationAccount
  
   */
  public ServiceStationAccount getServiceStationAccount() {
    return serviceStationAccount;
  }

  public void setServiceStationAccount(ServiceStationAccount serviceStationAccount) {
    this.serviceStationAccount = serviceStationAccount;
  }
}
