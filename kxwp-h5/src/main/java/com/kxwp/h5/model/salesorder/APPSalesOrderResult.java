package com.kxwp.h5.model.salesorder;

import java.math.BigDecimal;
import java.util.List;

import com.kxwp.common.constants.goods.OrderStatusEnum;
import com.kxwp.common.constants.goods.PayMethodEnum;
import com.kxwp.common.entity.supplier.OrderItem;

public class APPSalesOrderResult {
  private Long id;
  private String receptionName;
  private String full_address;
  private int goodsCount;
  private List<OrderItem> orderItemList;
  private Long supplierId;
  private String supplierName;
  private BigDecimal orderAmount;
  private OrderStatusEnum orderStatus;
  private String orderStatus_desc;
  private Long mobile;
  private PayMethodEnum payMethod;
  private String payMethod_desc;
  
  public String getPayMethod_desc() {
    this.payMethod_desc = payMethod.getDesc();
    return payMethod_desc;
  }
  public void setPayMethod_desc(String payMethod_desc) {
    this.payMethod_desc = payMethod_desc;
  }
  public String getOrderStatus_desc() {
    this.orderStatus_desc = orderStatus.getDesc();
    return orderStatus_desc;
  }
  public void setOrderStatus_desc(String orderStatus_desc) {
    this.orderStatus_desc = orderStatus_desc;
  }
  public String getReceptionName() {
    return receptionName;
  }
  public void setReceptionName(String receptionName) {
    this.receptionName = receptionName;
  }
  public String getFull_address() {
    return full_address;
  }
  public void setFull_address(String full_address) {
    this.full_address = full_address;
  }

  public int getGoodsCount() {
    return goodsCount;
  }
  public void setGoodsCount(int goodsCount) {
    this.goodsCount = goodsCount;
  }
  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }
  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }
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
  public BigDecimal getOrderAmount() {
    return orderAmount;
  }
  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public OrderStatusEnum getOrderStatus() {
    return orderStatus;
  }
  public void setOrderStatus(OrderStatusEnum orderStatus) {
    this.orderStatus = orderStatus;
  }
  public Long getMobile() {
    return mobile;
  }
  public void setMobile(Long mobile) {
    this.mobile = mobile;
  }
  public PayMethodEnum getPayMethod() {
    return payMethod;
  }
  public void setPayMethod(PayMethodEnum payMethod) {
    this.payMethod = payMethod;
  }
  
  
 
}
