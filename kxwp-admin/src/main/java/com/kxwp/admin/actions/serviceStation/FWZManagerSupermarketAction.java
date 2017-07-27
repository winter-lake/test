package com.kxwp.admin.actions.serviceStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.supermarket.SupermarketService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.SupermarketStausEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.form.supermarket.admin.SupermarketCreateForm;
import com.kxwp.common.form.supermarket.admin.UpdateSupermarketForm;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.uc.LoginUserInfo;
import com.kxwp.common.query.supermarket.SupermarketQuery;
import com.kxwp.common.utils.KXWPLogUtils;

@Controller
@RequestMapping(value = "/fwz/supermarket")
public class FWZManagerSupermarketAction {

  @Autowired
  private SupermarketService supermarketservice;

  @RequestMapping(value = "/FWZManagerSupermarket.do", method = RequestMethod.GET)
  public ModelAndView addInit() {
    ModelAndView mav = new ModelAndView();
    try {
      mav.setViewName("fwz/fwz.supermarket_list");
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "addInit throws :", e);
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");// TODO
    }
    return mav;
  }



  /**
   * querySuperMarketMessage:(根据条件查询超市列表).
   *
   * 2016年8月10日 下午5:25:23
   * 
   * @author zhaojn
   * @param supermarketQuery
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/querySuperMarketMessage.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData querySuperMarketMessage(
      @RequestBody SupermarketQuery supermarketQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<Supermarket>> edb = new ExchangeData<>();
    try {
      supermarketQuery.setOrderBy("create_time desc");
      supermarketQuery.setServiceStationId(
          FWZUserSessionUtils.getUserSession(request).getServiceStation().getId());
      List<Supermarket> supermarket_list = supermarketservice.searchByQuery(supermarketQuery);
      int total = supermarketservice.countByQuery(supermarketQuery);
      edb.setData(supermarket_list);
      edb.getPager().setCurrentPage(supermarketQuery.getPager().getCurrentPage());
      edb.getPager().setPageSize(supermarketQuery.getPager().getPageSize());
      edb.getPager().setTotoalResults(total);
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), e);
      edb.markException("系统出现异常", e);
    }
    return edb;
  }

  /**
   * getSupermarketId:(根据ID获取详细信息).
   *
   * 2016年8月9日 上午11:07:59
   * 
   * @author zhaojn
   * @param SupermarketQuery
   * @return
   * 
   */
  @RequestMapping(value = "/getSupermarketId.do", method = RequestMethod.GET)
  public ModelAndView getSupermarketId(SupermarketQuery supermarketQuery) {
    ModelAndView mav = new ModelAndView();
    try {
      Supermarket supermarket = supermarketservice.getSupreMarket(supermarketQuery.getId());
      if(supermarket.getSupermarketStatus() == SupermarketStausEnum.OFF){
        String reviewInfo = supermarketservice.getReviewInfo(supermarket.getId());
        
        mav.addObject("reviewDesc", reviewInfo);
      }
      if (supermarket.getSupermarketStatus() == SupermarketStausEnum.REVIEWING) {
        mav.setViewName("fwz/fwz.supermarket_review");
      } else {
        mav.setViewName("fwz/fwz.supermarket_message");
      }
      mav.addObject("supermarketMessage", supermarket);
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), e);
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");// TODO
    }
    return mav;
  }



  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/updateSupermarketStatus.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData updateSupermarketStatus(@RequestBody UpdateSupermarketForm form,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData ed = new ExchangeData<>();
    try {
      Supermarket supermarket = supermarketservice.getSupreMarket(form.getId());
      UserModel fwz_login_user = FWZUserSessionUtils.getUserSession(request);
      if (supermarket.getServiceStationId().longValue() != FWZUserSessionUtils
          .getUserSession(request).getServiceStation().getId()) {
        ed.mark403();
      } else {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtilsBean2.getInstance().copyProperties(loginUserInfo,
            FWZUserSessionUtils.getUserSession(request));
        supermarketservice.updateReviewingSupermarketStatus(form, fwz_login_user);
      }
    } catch (KXWPNotFoundException e) {
      ed.markException(e.getMessage(), e);
    } catch (Exception e) {
      ed.markException(e);
    }
    return ed;
  }

  /**
   * gotoAddFWZ:(跳转到添加超市会员页面).
   *
   * 2016年8月13日 下午1:25:09
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/gotoAddSupermarket.do")
  public String gotoAddFWZ(HttpServletRequest request, HttpServletResponse response) {
    return "fwz/fwz.supermarket_add";
  }

  /**
   * createSupermarket:(添加会员).
   *
   * 2016年8月13日 上午11:28:45
   * 
   * @author zhaojn
   * @param supermarketCreateForm
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/createSupermarket.do", method = RequestMethod.POST)
  public ModelAndView createSupermarket(SupermarketCreateForm supermarketCreateForm,
      HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView();
    try {
      UserModel fwz_login_user = FWZUserSessionUtils.getUserSession(request);
      supermarketservice.addNewSupermarket(supermarketCreateForm, fwz_login_user);
      mav.setViewName("success");
      mav.addObject("accountMobile", supermarketCreateForm.getAccountMobile());
      mav.addObject("message", "恭喜您 ， 操作成功");
    } catch (SYSException e) {
      mav.setViewName("fail");
      KXWPLogUtils.logException(this.getClass(), e);
      mav.addObject("error_message", e.getMessage());
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), e);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");
    }
    return mav;
  }



}
