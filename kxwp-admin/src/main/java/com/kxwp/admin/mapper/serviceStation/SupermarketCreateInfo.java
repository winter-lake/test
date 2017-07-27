package com.kxwp.admin.mapper.serviceStation;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.kxwp.common.constants.FWZTypeEnum;

/**
 * Date:     2016年8月8日 上午11:55:45 
 * @author   lou jian wen 
 */
public class SupermarketCreateInfo {

  /**
   * TODO 简单描述该方法的实现功能（可选）.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "FWZCreateInfo [fwz_name=" + fwz_name + ", service_phone=" + service_phone + ", bzj="
        + bzj + ", fwzType=" + fwzType + ", city=" + city + ", county=" + county + ", platformFee="
        + platformFee + "]";
  }

  @NotBlank(message="服务站名称不能为空")
  private String fwz_name;
  
  //服务站客服电话
  @NotBlank(message="服务站客服电话不能为空")
  private Long service_phone;
  
  //保证金
  @Digits(integer=8,fraction=2,message="只能保留两位小数")
  private BigDecimal bzj;
  
  //服务站类型
  @NotNull(message="请选择服务站类型")
  private FWZTypeEnum fwzType;
  
  //服务站市
  private Long city;
  
  //区县
  private Long county;
  
 

  
  //平台使用金
  @Digits(integer=8,fraction=2,message="只能保留两位小数")
  private BigDecimal platformFee;
  
  
  /**
   * @return  city
  
   */
  public Long getCity() {
    return city;
  }

  public void setCity(Long city) {
    this.city = city;
  }

  /**
   * @return  county
  
   */
  public Long getCounty() {
    return county;
  }

  public void setCounty(Long county) {
    this.county = county;
  }

  
  /**
   * @return  service_phone
  
   */
  public Long getService_phone() {
    return service_phone;
  }

  public void setService_phone(Long service_phone) {
    this.service_phone = service_phone;
  }

 
  
  /**
   * @return  fwz_name
  
   */
  public String getFwz_name() {
    return fwz_name;
  }

  public void setFwz_name(String fwz_name) {
    this.fwz_name = fwz_name;
  }


  /**
   * @return  fwzType
  
   */
  public FWZTypeEnum getFwzType() {
    return fwzType;
  }

  public void setFwzType(FWZTypeEnum fwzType) {
    this.fwzType = fwzType;
  }

  /**
   * @return  platformFee
  
   */
  public BigDecimal getPlatformFee() {
    return platformFee;
  }

  public void setPlatformFee(BigDecimal platformFee) {
    this.platformFee = platformFee;
  }

  /**
   * @return  bzj
  
   */
  public BigDecimal getBzj() {
    return bzj;
  }

  public void setBzj(BigDecimal bzj) {
    this.bzj = bzj;
  }


  
}

