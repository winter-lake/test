package com.kxwp.admin.model.fwz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.utils.KXWPDatetimeUtils;

/**
 * 
 * date: 2016年9月22日 下午3:27:28
 *
 * @author zhaojn
 */
public class FWZSalesOrder {
  // 大订单编号
  private String bigOrderNo;
  
  //超市名称
  private String supermarketName;
  
  //订单 总共实付金额 (不包括已取消的订单)
  private BigDecimal sumAmount;
  
  
  //下单时间
  @DateTimeFormat(iso = ISO.DATE_TIME, pattern = KXWPDatetimeUtils.YYYY_MM_DD_HH_MM_SS_DASH)
  @JsonFormat(pattern = KXWPDatetimeUtils.YYYY_MM_DD_HH_MM_SS_DASH, timezone = "Asia/Shanghai")
  private Date orderTime;
  
  // 供应商维度订单list
  private List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();

  public String getBigOrderNo() {
    return bigOrderNo;
  }

  public void setBigOrderNo(String bigOrderNo) {
    this.bigOrderNo = bigOrderNo;
  }

  public List<SalesOrder> getSalesOrderList() {
    return salesOrderList;
  }

  public void setSalesOrderList(List<SalesOrder> salesOrderList) {
    this.salesOrderList = salesOrderList;
  }

  public Date getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(Date orderTime) {
    this.orderTime = orderTime;
  }

  public String getSupermarketName() {
    return supermarketName;
  }

  public void setSupermarketName(String supermarketName) {
    this.supermarketName = supermarketName;
  }

  public BigDecimal getSumAmount() {
    return sumAmount;
  }

  public void setSumAmount(BigDecimal sumAmount) {
    this.sumAmount = sumAmount;
  }




}
