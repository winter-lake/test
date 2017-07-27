package com.kxwp.admin.query.serviceStation;

import com.kxwp.common.query.base.BaseQuery;
import com.kxwp.common.query.fwz.FWZRegionQuery;

/**
 * Date:     2016年8月8日 下午6:57:13 
 * @author   lou jian wen 
 */
public class ServiceStationManagerQuery extends BaseQuery {

  
  
  private Long province;
  
  private Long city;
  
  
  private Long county;

  //服务站id
  private Long serviceStationID;
  
  
  public void copyQuery(FWZRegionQuery fwzRegionQuery){
    if(province != null){
      fwzRegionQuery.setProvince(province);
      if(city != null){
        fwzRegionQuery.setCity(city);
      }
      
      if(county != null){
        fwzRegionQuery.setCounty(county);
      }
    }
    
    fwzRegionQuery.setPager(super.getPager());
    
  }
  
  
  //是否根据服务区域的省市县进行查询
  public boolean queryBySSX(){
    if(province != null){
      return true;
    }
    return false;
  }
  
  public Long getCounty() {
    return county;
  }

  public void setCounty(Long county) {
    this.county = county;
  }
  
 

  
  public Long getServiceStationID() {
    return serviceStationID;
  }

  public void setServiceStationID(Long serviceStationID) {
    this.serviceStationID = serviceStationID;
  }


  /**
   * @return  province
  
   */
  public Long getProvince() {
    return province;
  }

  public void setProvince(Long province) {
    this.province = province;
  }

  /**
   * @return  city
  
   */
  public Long getCity() {
    return city;
  }

  public void setCity(Long city) {
    this.city = city;
  }

}

