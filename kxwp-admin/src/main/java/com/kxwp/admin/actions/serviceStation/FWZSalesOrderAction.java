package com.kxwp.admin.actions.serviceStation;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.model.fwz.FWZSalesOrderResult;
import com.kxwp.admin.service.salesOrder.PrintSalesOrderService;
import com.kxwp.admin.service.serviceStation.FWZSalesOrderService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.form.salesorder.UpdateSalesOrderForm;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.order.PrintSalesOrder;
import com.kxwp.common.model.uc.LoginUserInfo;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.utils.KXWPLogUtils;

@Controller
@RequestMapping("/fwz/manager/salesOrder")
public class FWZSalesOrderAction {
  @Autowired
  private SalesOrderService salesOrderService;
  
  @Autowired
  private PrintSalesOrderService printSalesOrderService;
  
  @Autowired
  private FWZSalesOrderService fwzSalesOrderService;

  /**
   * listFWZ:(跳转到订单列表).
   *
   * 2016年8月25日 下午7:16:25
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/gotolistSalesOrder.do")
  public String gotolistSalesOrder(HttpServletRequest request, HttpServletResponse response) {
    return "fwz/fwz.list_salesOrder";
  }

  /**
   * listSalesOrder:(条件查询).
   *
   * 2016年8月27日 下午3:17:11
   * 
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/listSalesOrder.do")
  public @ResponseBody ExchangeData<FWZSalesOrderResult> listSalesOrder(
      @RequestBody SelectSalesOrderQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<FWZSalesOrderResult> edb = new ExchangeData<>();
    try {
      Long serviceStationId = FWZUserSessionUtils.getUserSession(request)
      .getOrganizationId();
      query.setServiceStationId(serviceStationId);
      int total = fwzSalesOrderService.fwzCountByQuery(query);
      FWZSalesOrderResult data = fwzSalesOrderService.queryOrders(query);
      edb.getPager().setTotoalResults(total);
      edb.getPager().setCurrentPage(query.getPager().getCurrentPage());
      edb.setData(data);
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "/listSalesOrder.do",e);
      edb.markException(e);
    }
    return edb;
  }

  /**
   * gotoSalesOrderDetail:(查看订单详情).
   *
   * 2016年8月29日 下午3:31:18
   * 
   * @author zhaojn
   * @param orderID
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/gotoSalesOrderDetail.do")
  public String gotoSalesOrderDetail(@RequestParam Long orderID, HttpServletRequest request,
      HttpServletResponse response) {
    SalesOrder salesOrder_detail;
    try {
      salesOrder_detail = salesOrderService.getSalesOrderById(orderID);
      if (salesOrder_detail.getServiceStationId() != FWZUserSessionUtils.getUserSession(request)
          .getOrganizationId()) {
        return ErrorPage.PAGE_403;
      } else {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtilsBean2.getInstance().copyProperties(loginUserInfo,
            FWZUserSessionUtils.getUserSession(request));
        request.setAttribute("salesOrder_detail", salesOrder_detail);
      }
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e);
      return ErrorPage.PAGE_500;
    }catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "/gotoSalesOrderDetail.do",e);
      return ErrorPage.PAGE_500;
    }
    return "fwz/fwz.salesOrder_detail";
  }

  /**
   * updateSalesOrderStatus:(更新订单状态).
   *
   * 2016年8月29日 下午3:32:10
   * 
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @return
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * 
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/updateSalesOrderStatus.do")
  public @ResponseBody ExchangeData updateSalesOrderStatus(@RequestBody UpdateSalesOrderForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IllegalAccessException, InvocationTargetException {
    ExchangeData edb = new ExchangeData<>();
    try {
      SalesOrder salesOrder_detail = salesOrderService.getSalesOrderById(form.getOrderId());
      if (salesOrder_detail.getServiceStationId() != FWZUserSessionUtils.getUserSession(request)
          .getOrganizationId()) {
        edb.mark403();
      } else {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtilsBean2.getInstance().copyProperties(loginUserInfo,
            FWZUserSessionUtils.getUserSession(request));
        salesOrderService.updateSalesOrderStatus(form);
      }
    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
      KXWPLogUtils.logException(this.getClass(), "/updateSalesOrderStatus.do",e);
    }
    return edb;
  }
  
  /**
   * gotoPrintSalesOrderDetail:(打印订单详情).
   *
   * 2016年9月12日 下午4:16:51
   * @author zhaojn
   * @param orderID
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/gotoPrintSalesOrder.do")
  public String gotoPrintSalesOrderDetail(@RequestParam Long orderID, HttpServletRequest request,
      HttpServletResponse response) {
    SalesOrder salesOrder_detail;
    PrintSalesOrder printSalesOrder_detail;
    try {
      salesOrder_detail = salesOrderService.getSalesOrderById(orderID);
      if (salesOrder_detail.getServiceStationId() != FWZUserSessionUtils.getUserSession(request)
          .getOrganizationId()) {
        return ErrorPage.PAGE_403;
      } else {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtilsBean2.getInstance().copyProperties(loginUserInfo,
            FWZUserSessionUtils.getUserSession(request));
        printSalesOrder_detail = printSalesOrderService.getPrintSalesOrder(orderID);
        request.setAttribute("printSalesOrder_detail", printSalesOrder_detail);
      }
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e);
      return ErrorPage.PAGE_500;
    } catch(Exception e){
      KXWPLogUtils.logException(this.getClass(), "/gotoPrintSalesOrder.do",e);
      return ErrorPage.PAGE_500;
    }
    return "fwz/fwz.printSalesOrder_detail";
  }
}
