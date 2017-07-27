package com.kxwp.h5.model.shoppingcar;

import java.math.BigDecimal;
import java.util.List;

import com.kxwp.common.entity.shoppingcar.AppShoppingCar;

public class AppSupplierShoppingCarResult {
  private Long supplierId;
  private String supplierName;
  private List<AppShoppingCar> shoppingCarList;
  private int goodsCount;
  private Long supplierGoodsQit;
  private BigDecimal supplierAmount;
  
  public Long getSupplierId() {
    return supplierId;
  }
  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }
  public String getSupplierName() {
    return supplierName;
  }
  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public List<AppShoppingCar> getShoppingCarList() {
    return shoppingCarList;
  }
  public void setShoppingCarList(List<AppShoppingCar> shoppingCarList) {
    this.shoppingCarList = shoppingCarList;
  }
  public int getGoodsCount() {
    return goodsCount;
  }
  public void setGoodsCount(int goodsCount) {
    this.goodsCount = goodsCount;
  }
  public Long getSupplierGoodsQit() {
    return supplierGoodsQit;
  }
  public void setSupplierGoodsQit(Long supplierGoodsQit) {
    this.supplierGoodsQit = supplierGoodsQit;
  }
  public BigDecimal getSupplierAmount() {
    return supplierAmount;
  }
  public void setSupplierAmount(BigDecimal supplierAmount) {
    this.supplierAmount = supplierAmount;
  }
  
  
}
