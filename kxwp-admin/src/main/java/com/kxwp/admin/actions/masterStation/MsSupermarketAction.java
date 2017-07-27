package com.kxwp.admin.actions.masterStation;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.service.supermarket.SupermarketService;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.query.supermarket.SupermarketQuery;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * date: 2016年8月8日 上午11:53:35
 *
 * @author zhaojn
 */
@Controller
@RequestMapping(value = "/zz/supermarket")
public class MsSupermarketAction {
  
  @Autowired
  private SupermarketService supermarketservice;
  /**
   * addInit:(初始化超市管理界面(总站管理)).
   *
   * 2016年8月8日 上午11:58:36
   * 
   * @author zhaojn
   * @return
   * 
   */
  @RequestMapping(value = "/supermarketManager.do", method = RequestMethod.GET)
  public ModelAndView addInit() {
    ModelAndView mav = new ModelAndView();
    try {
      mav.setViewName("zz/zz.supermarket_manager");
    } catch (Exception e) {
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");// TODO
    }
    return mav;
  }
  
  

  
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/querySuperMarketMessage.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},method = {RequestMethod.POST})
  public @ResponseBody ExchangeData querySuperMarketMessage(
      @RequestBody SupermarketQuery supermarketQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<Supermarket>> edb = new ExchangeData<>();
    try {
      List<Supermarket> supermarket_list = supermarketservice.searchByQuery(supermarketQuery);
      int total = supermarketservice.countByQuery(supermarketQuery);
      edb.setData(supermarket_list);
      edb.getPager().setCurrentPage(supermarketQuery.getPager().getCurrentPage());
      edb.getPager().setPageSize(supermarketQuery.getPager().getPageSize());
      edb.getPager().setTotoalResults(total);
    } catch (Exception e) {
      KXWPLogUtils.logException(MsSupermarketAction.class, "serviceStationName throws :", e);
      edb.markException("系统出现异常", e);
    }
    return edb;
  }

  /**
   * getSupermarketId:(根据ID获取详细信息).
   *
   * 2016年8月9日 上午11:07:59
   * @author zhaojn
   * @param SupermarketQuery
   * @return
  
   */
  @RequestMapping(value = "/getSupermarketId.do", method = RequestMethod.GET)
  public ModelAndView getSupermarketId(SupermarketQuery supermarketQuery) {
    ModelAndView mav = new ModelAndView();
    try {
      Supermarket supermarket = supermarketservice.getSupreMarket(supermarketQuery.getId());
      mav.setViewName("zz/zz.supermarket_view");
      mav.addObject("supermarketMessage", supermarket);
    } catch (Exception e) {
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");// TODO
    }
    return mav;
  }
}
