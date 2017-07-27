package com.kxwp.admin.actions.masterStation;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.supplier.OrderSumAmountDetail;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.utils.KXWPLogUtils;

@Controller
@RequestMapping("/zz/manager/salesOrder")
public class ZZSalesOrderManagerAction {
  
  @Autowired
  private SalesOrderService salesOrderService;
  
  
  /**********************************生成大订单编号后删除***************************************************/
  /**
   * gotoAddBigOrderNo:(跳转到生成大订单编号页面   修复数据后删除此方法).
   *
   * 2016年8月25日 下午7:16:25
   * @author zhaojn
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/gotoAddBigOrderNo.do")
  public String gotoAddBigOrderNo(HttpServletRequest request, HttpServletResponse response) {
    return "zz/zz.addbigOrderNo_salesOrder";
  }
  
  
  /**
   * listSalesOrder:(addBigOrderNo).
   *
   * 2016年8月27日 下午3:17:11
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
  
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/addBigOrderNo.do",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData addBigOrderNo(
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      salesOrderService.addBigOrderNo();
    }catch (SYSException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException("系统出错", e);
      KXWPLogUtils.logException(ZZSalesOrderManagerAction.class, "/addBigOrderNo.do :",
          e);
    }
    return edb;
  } 
  
  /**********************************************************************************************/
  
  
  
  /**
   * listFWZ:(跳转到订单列表).
   *
   * 2016年8月25日 下午7:16:25
   * @author zhaojn
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/gotolistSalesOrder.do")
  public String gotolistSalesOrder(HttpServletRequest request, HttpServletResponse response) {
    return "zz/zz.list_salesOrder";
  }
  
  /**
   * listSalesOrder:(条件查询).
   *
   * 2016年8月27日 下午3:17:11
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/listSalesOrder.do")
  public @ResponseBody ExchangeData<OrderSumAmountDetail> listSalesOrder(@RequestBody SelectSalesOrderQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<OrderSumAmountDetail> edb = new ExchangeData<>();
    try {
      OrderSumAmountDetail results = salesOrderService.selectSalesOrderBySys(query);
      int total = salesOrderService.countBySearchSalesOrder(query);
      edb.getPager().setTotoalResults(total);
      edb.getPager().setCurrentPage(query.getPager().getCurrentPage());
      edb.setData(results);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }
  
  @RequestMapping(value = "/gotoSalesOrderDetail.do")
  public String gotoSalesOrderDetail(@RequestParam Long orderID, HttpServletRequest request,
      HttpServletResponse response) {
    SalesOrder salesOrder_detail;
    try {
      salesOrder_detail = salesOrderService.getSalesOrderById(orderID);
      request.setAttribute("salesOrder_detail", salesOrder_detail);
    } catch (KXWPNotFoundException | KXWPValidException e) {
      KXWPLogUtils.logException(this.getClass(), e);
      return ErrorPage.PAGE_500;
    }
    return "zz/zz.salesOrder_detail";
  }
}
