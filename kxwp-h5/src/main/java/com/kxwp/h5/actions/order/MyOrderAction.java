package com.kxwp.h5.actions.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.constants.goods.OrderStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.form.salesorder.BuyAgainSalesOrderForm;
import com.kxwp.common.form.salesorder.CreateSalesOrderForm;
import com.kxwp.common.form.salesorder.SalesOrderForm;
import com.kxwp.common.form.salesorder.UpdateSalesOrderForm;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.order.AppOrderStatusCount;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.common.ErrorPage;
import com.kxwp.h5.model.salesorder.APPSalesOrderResult;
import com.kxwp.h5.service.salesorder.H5SalesOrderService;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * Date: 2016年9月2日 上午9:29:49
 * 
 * @author lou jian wen
 */

@Controller
@RequestMapping("/h5/order")
public class MyOrderAction {
  @Autowired
  private SalesOrderService salesOrderService;

  @Autowired
  private H5SalesOrderService appSalesOrderService;


  @RequestMapping(value = "/gotoMyOrder.do")
  public String gotoOrders(HttpServletRequest request, HttpServletResponse response) {
    return "order/my_order";
  }
  
  @RequestMapping(value = "/gotoPlaceOrderResult.do")
  public String gotoPlaceOrderResult(@RequestParam String result,HttpServletRequest request, HttpServletResponse response) {
    return "order/place_order_" + result;
  }


  /**
   * addSalesOrder:(app生成订单接口).
   *
   * 2016年8月24日 下午3:31:26
   * 
   * @author zhaojn
   * @param createSalesOrderForm
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/addSalesOrder.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData addSalesOrder(
      @Valid @RequestBody CreateSalesOrderForm createSalesOrderForm, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      createSalesOrderForm.setAppUserModel(um);
      salesOrderService.addSalesOrder(createSalesOrderForm);
    } catch (KXWPNotFoundException | SYSException e) {
      edb.markException(e.getMessage(), e);
      KXWPLogUtils.logException(this.getClass(), e);
    } catch (Exception e) {
      edb.markException("/ajax/addSalesOrder.do exception", e);
    }
    return edb;
  }

  /**
   * getSalesOrderList:(获取超市下面的订单列表).
   *
   * 2016年8月25日 上午10:53:37
   * 
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/ajax/getSalesOrderList.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<APPSalesOrderResult>> getSalesOrderList(
      @RequestBody SelectSalesOrderQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<APPSalesOrderResult>> edb = new ExchangeData<List<APPSalesOrderResult>>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      List<APPSalesOrderResult> data = appSalesOrderService.getSalesOrderList(um, query);
      query.setSupermarketId(um.getSupermarket_id());
      edb.setData(data);
      int total = salesOrderService.countBySearchSalesOrder(query);
      edb.getPager().setTotoalResults(total);
      edb.getPager().setCurrentPage(query.getPager().getCurrentPage());

    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }



  /**
   * getSalesOrderDetail:(获取订单详情).
   *
   * 2016年8月25日 下午3:31:37
   * 
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/getOrderDetail.do")
  public String getOrderDetail(@RequestParam Long orderID, HttpServletRequest request,
      HttpServletResponse response) {
    try {
      SalesOrderForm form = new SalesOrderForm();
      form.setId(orderID);
      SalesOrder data = appSalesOrderService.getSalesOrderDetail(form);
      if (data.getSupermarketId().longValue() != H5UserSessionUtils.getUserSession(request)
          .getSupermarket_id()) {
        return ErrorPage.PAGE_403;
      }
      request.setAttribute("orderDetail", data);
    } catch (KXWPNotFoundException e) {
      return ErrorPage.PAGE_404;
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "getOrderDetail exception", e);
      return ErrorPage.PAGE_500;
    }
    return "order/order_detail";
  }


  /**
   * buyGoodsAgain:(再次购买功能).
   *
   * 2016年8月19日 下午6:27:07
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/buyGoodsAgain.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData buyGoodsAgain(@Valid @RequestBody BuyAgainSalesOrderForm form,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      salesOrderService.addSalesOrderAgain(form);
    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }

  /**
   * updateOrderStauts:(修改订单状态为确认收货(已完成)的状态).
   *
   * 2016年8月27日 上午10:12:34
   * 
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/completeOrder.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData completeOrder(@Valid @RequestBody UpdateSalesOrderForm form,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      form.setNew_orderStatus(OrderStatusEnum.Completed);
      salesOrderService.updateSalesOrderStatus(form);
    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }


  /**
   * salesOrderStatusCount:(获取订单状态数量).
   *
   * 2016年8月27日 下午5:30:07
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/ajax/salesOrderStatusCount.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<AppOrderStatusCount> salesOrderStatusCount(
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<AppOrderStatusCount> edb = new ExchangeData<>();
    try {
      APPUserModel model = H5UserSessionUtils.getUserSession(request);
      AppOrderStatusCount result = salesOrderService.getOrderStatusCount(model);
      edb.setData(result);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }

}

