package com.kxwp.h5.service.salesorder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.common.entity.supplier.OrderItem;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.form.salesorder.SalesOrderForm;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.query.orderitem.SelectOrderItemQuery;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.service.orderitems.OrderItemsService;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.h5.model.salesorder.APPSalesOrderResult;
import com.kxwp.h5.model.salesorder.OrderItemResult;

@Service("H5SalesOrderService")
public class H5SalesOrderService {
  
  @Autowired
  private SalesOrderService salesOrderService;
  
  
  @Autowired
  private OrderItemsService orderItemsService;
  
  
  
  /**
   * getSalesOrderDetail:(获取超市下生成的订单列表).
   *
   * 2016年8月25日 上午10:46:58
   * @author zhaojn
   * @param model
   * @param query
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
  
   */
  public List<APPSalesOrderResult> getSalesOrderList(APPUserModel model,SelectSalesOrderQuery query) throws KXWPNotFoundException, KXWPValidException{
    
    if(model == null){
      throw new KXWPNotFoundException("未获取到APPUserModel");
    }
    
    query.setSupermarketId(model.getSupermarket_id());
    List<SalesOrder> salesOrderList= salesOrderService.selectSalesOrderAndOrderItem(query);
    
    //返回结果封装一下 传给app  不然多余字段太多
    List<APPSalesOrderResult> resultList = new ArrayList<APPSalesOrderResult>();
    for(SalesOrder salesOrder:salesOrderList){
      APPSalesOrderResult result = new APPSalesOrderResult();
      
      result.setId(salesOrder.getId());
      result.setReceptionName(salesOrder.getReceptionName());
      result.setFull_address(salesOrder.getFull_address());
      result.setGoodsCount(salesOrder.getGoodsCount());
      result.setOrderAmount(salesOrder.getOrderAmount());
      result.setOrderItemList(salesOrder.getOrderItemList());
      result.setSupplierId(salesOrder.getSupplierId());
      result.setSupplierName(salesOrder.getSupplierName());
      result.setOrderAmount(salesOrder.getOrderAmount());
      result.setOrderStatus(salesOrder.getOrderStatus());
      result.setPayMethod(salesOrder.getPayMethod());
      result.setMobile(salesOrder.getPhone());
      resultList.add(result);
    }
    return resultList;
  }
  
  
  /**
   * getSalesOrderDetail:(查看订单详情).
   *
   * 2016年8月25日 下午3:28:05
   * @author zhaojn
   * @param form
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
  
   */
  public  SalesOrder getSalesOrderDetail(SalesOrderForm form) throws KXWPNotFoundException, KXWPValidException{
    //FIXME 查询条件最好带上对应的超市,否则前端可以根据id查看到其它超市的订单 by jianwen
    SalesOrder salesOrder = salesOrderService.getSalesOrderById(form.getId());
    salesOrder.setSumGoodsQit(sumSalesOrderGoodsQit(form.getId()));
    return salesOrder;
  }
  
  
  /**
   * SumSalesOrderGoodsQit:(私有方法,判断订单下的商品总共数量).
   *
   * 2016年8月31日 下午5:22:23
   * @author zhaojn
   * @param orderId
   * @return
   * @throws KXWPNotFoundException 
   * @throws KXWPValidException 
  
   */
  private Long sumSalesOrderGoodsQit(Long orderId) throws KXWPNotFoundException, KXWPValidException{
    Long sumGoodsQit = 0L;
    List<OrderItem> orderItemList = orderItemsService.selectByOrderId(orderId);
    for(OrderItem item:orderItemList){
      sumGoodsQit = sumGoodsQit + item.getItemQit();
    }
    return sumGoodsQit;
  }
  
  /**
   * queryOrderItems:(查询商品清单).
   *
   * 2016年9月21日 下午2:31:19
   * @author zhaojn
   * @param query
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException
  
   */
  public OrderItemResult queryOrderItems(SelectOrderItemQuery query) throws KXWPNotFoundException, KXWPValidException{
    OrderItemResult result = new OrderItemResult();
    //查询大订单下商品总件数
    Long sumGoodsQit = orderItemsService.sumGoodsQit(query.getBigOrderNo());
    
    //查询大订单下的商品清单
    List<OrderItem> orderItemList = orderItemsService.queryOrderItemByBigOrderNo(query);
    
    result.setOrderItemList(orderItemList);
    
    result.setSumGoodsQit(sumGoodsQit);
    
    return result;
  }
  
}
