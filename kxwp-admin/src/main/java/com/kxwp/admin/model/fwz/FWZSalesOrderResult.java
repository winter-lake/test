package com.kxwp.admin.model.fwz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务站查询 返回结果 date: 2016年9月23日 上午11:02:01
 *
 * @author zhaojn
 */
public class FWZSalesOrderResult {

  // 订单列表
  private List<FWZSalesOrder> fwz_salesOrderList = new ArrayList<FWZSalesOrder>();

  // 当前页 未取消订单的总价格
  private BigDecimal totalSumAmount;


  public List<FWZSalesOrder> getFwz_salesOrderList() {
    return fwz_salesOrderList;
  }

  public void setFwz_salesOrderList(List<FWZSalesOrder> fwz_salesOrderList) {
    this.fwz_salesOrderList = fwz_salesOrderList;
  }

  public BigDecimal getTotalSumAmount() {
    return totalSumAmount;
  }

  public void setTotalSumAmount(BigDecimal totalSumAmount) {
    this.totalSumAmount = totalSumAmount;
  }



}
