package com.kxwp.h5.v2.actions.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.app.salesOrder.APISalesOrderResult;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.query.orderitem.SelectOrderItemQuery;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.service.salesorder.APISalesOrderService;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.salesorder.OrderItemResult;
import com.kxwp.h5.service.salesorder.H5SalesOrderService;
import com.kxwp.h5.utils.H5UserSessionUtils;



/**
 * date: 2016年9月21日 上午10:48:26 
 *
 * @author zhaojn
 */
@Controller
@RequestMapping("/h5/v2/order")
public class MyOrderV2Action {
  
  @Autowired
  private APISalesOrderService apiSalesOrderService;
  
  @Autowired
  private SalesOrderService salesOrderService;
  
  @Autowired
  private H5SalesOrderService appSalesOrderService;
  
  /**
   * querySalesOrderByBigOrderNo:(订单列表(显示大订单编号)).
   *
   * 2016年9月21日 上午10:57:00
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/ajax/querySalesOrderList.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<APISalesOrderResult>> querySalesOrder(
      @RequestBody SelectSalesOrderQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<APISalesOrderResult>> edb = new ExchangeData<>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      query.setSupermarketId(um.getSupermarket_id());
      List<APISalesOrderResult> data = apiSalesOrderService.selectApiSalesOrderList(query);
      edb.setData(data);
     /* int total = salesOrderService.countBySearchSalesOrder(query);
      edb.getPager().setTotoalResults(total);
      edb.getPager().setCurrentPage(query.getPager().getCurrentPage());*/

    } catch (KXWPNotFoundException | KXWPValidException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException("系统出错", e);
      KXWPLogUtils.logException(MyOrderV2Action.class, "/querySalesOrderList.do :",
          e);
    }
    return edb;
  }
  
  
  /**
   * gotoOrders:(跳到商品清单页面).
   *
   * 2016年9月21日 上午11:06:30
   * @author zhaojn
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/gotoMyOrderItem.do")
  public String gotoOrders(HttpServletRequest request, HttpServletResponse response) {
    return "order/order_goods_list";
  }
  
  
  /**
   * querySalesOrderByBigOrderNo:(订单列表(显示大订单编号)).
   *
   * 2016年9月21日 上午10:57:00
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/ajax/queryOrderItemByBigOrderNo.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<OrderItemResult> queryOrderItemByBigOrderNo(
      @RequestBody SelectOrderItemQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<OrderItemResult> edb = new ExchangeData<>();
    try {
      OrderItemResult data = appSalesOrderService.queryOrderItems(query);
      edb.setData(data);
    } catch (KXWPNotFoundException | KXWPValidException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException("系统出错", e);
      KXWPLogUtils.logException(MyOrderV2Action.class, "/querySalesOrderList.do :",
          e);
    }
    return edb;
  }
}
