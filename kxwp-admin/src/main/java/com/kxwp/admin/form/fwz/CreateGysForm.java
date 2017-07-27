package com.kxwp.admin.form.fwz;

import java.util.List;

import com.kxwp.common.entity.supplier.Supplier;

public class CreateGysForm {
  
  //经营范围Id
  private List<Long> classificaionIdList;
  
  //代理品牌
  private List<Long> brandIdList;
  
  //配送范围
  private List<Long> shippingAreaIdList; 
  
  public List<Long> getShippingAreaIdList() {
    return shippingAreaIdList;
  }

  public void setShippingAreaIdList(List<Long> shippingAreaIdList) {
    this.shippingAreaIdList = shippingAreaIdList;
  }

  private Supplier supplier;
  
  private String sendPassword;
  

  private String remark;
  
  public List<Long> getClassificaionIdList() {
    return classificaionIdList;
  }

  public void setClassificaionIdList(List<Long> classificaionIdList) {
    this.classificaionIdList = classificaionIdList;
  }

  public List<Long> getBrandIdList() {
    return brandIdList;
  }

  public void setBrandIdList(List<Long> brandIdList) {
    this.brandIdList = brandIdList;
  }

  public String getSendPassword() {
    return sendPassword;
  }

  public void setSendPassword(String sendPassword) {
    this.sendPassword = sendPassword;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }



}   
