package com.kxwp.admin.form.zz;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Date:     2016年8月8日 上午11:54:52 
 * @author   lou jian wen 
 */
public class FWZCreateForm {

  
  @NotBlank(message="管理员姓名不能为空")
  private String fwz_admin_name;
  
  @Min(value=1,message="手机号码不能为空")
  private Long fwz_admin_mobile;
  
  
  private FWZCreateInfo fwzCreateInfo;
  
  
  private Long service_station_id;
  
  
  /**
   * @return  service_station_id
  
   */
  public Long getService_station_id() {
    return service_station_id;
  }


  public void setService_station_id(Long service_station_id) {
    this.service_station_id = service_station_id;
  }


  public boolean isValid(){
    if(fwzCreateInfo.getCountyList() == null || fwzCreateInfo.getCountyList().size() == 0){
      return false;
    }
    
    return true;
  }
  

  /**
   * @return  fwz_admin_name
  
   */
  public String getFwz_admin_name() {
    return fwz_admin_name;
  }

  public void setFwz_admin_name(String fwz_admin_name) {
    this.fwz_admin_name = fwz_admin_name;
  }

  /**
   * @return  fwz_admin_mobile
  
   */
  public Long getFwz_admin_mobile() {
    return fwz_admin_mobile;
  }

  public void setFwz_admin_mobile(Long fwz_admin_mobile) {
    this.fwz_admin_mobile = fwz_admin_mobile;
  }

  public FWZCreateInfo getFwzCreateInfo() {
    return fwzCreateInfo;
  }

  public void setFwzCreateInfo(FWZCreateInfo fwzCreateInfo) {
    this.fwzCreateInfo = fwzCreateInfo;
  }

  
  
}

