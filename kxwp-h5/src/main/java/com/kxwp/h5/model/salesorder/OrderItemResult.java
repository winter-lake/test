package com.kxwp.h5.model.salesorder;

import java.util.ArrayList;
import java.util.List;

import com.kxwp.common.entity.supplier.OrderItem;

/**
 * 微信端商品清单返回结果
 * date: 2016年9月21日 下午2:24:36 
 *
 * @author zhaojn
 */
public class OrderItemResult {
  //商品总件数
  private Long sumGoodsQit; 
  
  private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

  public Long getSumGoodsQit() {
    return sumGoodsQit;
  }

  public void setSumGoodsQit(Long sumGoodsQit) {
    this.sumGoodsQit = sumGoodsQit;
  }

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }
  
  
}
