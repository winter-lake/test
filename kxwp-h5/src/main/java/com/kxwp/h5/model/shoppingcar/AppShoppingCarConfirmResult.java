package com.kxwp.h5.model.shoppingcar;

import java.math.BigDecimal;
import java.util.List;


public class AppShoppingCarConfirmResult {
  
  private String orderSeq;
  
  private String receptionName;
  // 超市名称
  private String supermarketName;
  // 超市详细地址
  private String full_address;
  private Long phone;
  // 供应商中商品的详细信息
  private List<AppSupplierShoppingCarResult> supplierResultList;
  // 超市总共商品的价格
  private BigDecimal totalAmount;
  // 超市总共商品的种类
  private Long totalGoodsCount;
  // 超市总共商品的数量
  private Long totalGoodsQit;

  public String getSupermarketName() {
    return supermarketName;
  }

  public void setSupermarketName(String supermarketName) {
    this.supermarketName = supermarketName;
  }

  public String getFull_address() {
    return full_address;
  }

  public void setFull_address(String full_address) {
    this.full_address = full_address;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getTotalGoodsCount() {
    return totalGoodsCount;
  }

  public void setTotalGoodsCount(Long totalGoodsCount) {
    this.totalGoodsCount = totalGoodsCount;
  }

  public Long getTotalGoodsQit() {
    return totalGoodsQit;
  }

  public void setTotalGoodsQit(Long totalGoodsQit) {
    this.totalGoodsQit = totalGoodsQit;
  }

  public List<AppSupplierShoppingCarResult> getSupplierResultList() {
    return supplierResultList;
  }

  public void setSupplierResultList(List<AppSupplierShoppingCarResult> supplierResultList) {
    this.supplierResultList = supplierResultList;
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public String getOrderSeq() {
    return orderSeq;
  }

  public void setOrderSeq(String orderSeq) {
    this.orderSeq = orderSeq;
  }

  public String getReceptionName() {
    return receptionName;
  }

  public void setReceptionName(String receptionName) {
    this.receptionName = receptionName;
  }




}
