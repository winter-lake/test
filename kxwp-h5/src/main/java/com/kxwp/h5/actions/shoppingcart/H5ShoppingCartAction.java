package com.kxwp.h5.actions.shoppingcart;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.form.shoppingCar.UpdateShoppingCarForm;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.shopcart.UpdateShopcartData;
import com.kxwp.common.model.shopcart.UpdateShopcartResult;
import com.kxwp.common.query.shoppingcar.ShoppingCarCountQuery;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.common.ErrorPage;
import com.kxwp.h5.model.shoppingcar.AppShoppingCarConfirmResult;
import com.kxwp.h5.model.shoppingcar.AppShoppingCarResult;
import com.kxwp.h5.service.shoppingcar.AppShoppingCarService;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * date: 2016年8月16日 下午9:38:35
 *
 * @author zhaojn
 */
@Controller
@RequestMapping("/h5/shoppingCart")
public class H5ShoppingCartAction {

  @Autowired
  private AppShoppingCarService appShoppingCarService;



  @RequestMapping(value = "/gotoShoppingCart.do")
  public String gotoShoppingCart(HttpServletRequest request, HttpServletResponse response) {
    try {
      AppShoppingCarResult shopcart = appShoppingCarService
          .selectShoppingCarGroupBySupplied(H5UserSessionUtils.getUserSession(request));

      if (shopcart == null) {
        return "shopcart/shopcart_empty";
      }
      request.setAttribute("shopcartData", shopcart);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e.getMessage(), e);
      return ErrorPage.PAGE_500;
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "/gotoShoppingCart.do exception", e);
      return ErrorPage.PAGE_500;
    }
    return "shopcart/shopcart_list";
  }

  @RequestMapping(value = "/ajax/getShoppingCartData.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<AppShoppingCarResult> getShoppingCartData(
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<AppShoppingCarResult> data = new ExchangeData<>();
    try {
      AppShoppingCarResult shopcart = appShoppingCarService
          .selectShoppingCarGroupBySupplied(H5UserSessionUtils.getUserSession(request));
      data.setData(shopcart);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e.getMessage(), e);
      data.markException(e.getMessage(), e);
    } catch (Exception e) {
      data.markException(e);
    }
    return data;
  }



  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/addShoppingCart.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData addShoppingCarItem(@Valid @RequestBody UpdateShopcartData data,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      UpdateShopcartResult updateShopcartResult = appShoppingCarService.addShoppingCar(data, um);
      edb.setData(updateShopcartResult);
    } catch (SYSException | KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }



  /**
   * getCount:(获取超市购物车清单中总数量接口).
   *
   * 2016年8月18日 上午11:42:18
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/getCount.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData getCount(HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      ShoppingCarCountQuery countQuery = appShoppingCarService.shoppingCarCountBySupermarket(um);
      edb.setData(countQuery);
    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }

  /**
   * getShoppingCarDetail:(获取购物车详情接口).
   *
   * 2016年8月19日 下午5:42:07
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/ajax/getShoppingCartDetail.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<AppShoppingCarResult> getShoppingCarDetail(
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<AppShoppingCarResult> edb = new ExchangeData<AppShoppingCarResult>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      AppShoppingCarResult data = appShoppingCarService.selectShoppingCarGroupBySupplied(um);
      edb.setData(data);
    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }


  /**
   * getShoppingCarDetail:(更新购物车接口).
   *
   * 2016年8月19日 下午5:42:07
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/updateShoppingCart.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData updateShoppingCart(
      @Valid @RequestBody UpdateShoppingCarForm form, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      AppShoppingCarResult data = appShoppingCarService.updateShoppingCarByOperate(form, um);
      edb.setData(data);
    } catch (KXWPNotFoundException e) {
      edb.markException(e.getMessage(), e);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }


  /**
   * getShoppingCarDetail:(购物车商品确认提交订单页面).
   *
   * 2016年8月19日 下午5:42:07
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/confirmOrder.do")
  public String confirmOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      APPUserModel um = H5UserSessionUtils.getUserSession(request);
      AppShoppingCarConfirmResult data = appShoppingCarService.confirmShoppingCar(um);
      request.setAttribute("orderData", data);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e.getMessage(), e);
      return ErrorPage.PAGE_500;
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "confirmOrder.do exception", e);
      return ErrorPage.PAGE_500;
    }
    return "shopcart/confirm_order";
  }

}
