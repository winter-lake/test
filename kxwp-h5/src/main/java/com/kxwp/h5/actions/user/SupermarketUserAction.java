package com.kxwp.h5.actions.user;



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
import com.kxwp.common.entity.supermarket.Account;
import com.kxwp.common.form.app.supermarket.APPModifyPasswordForm;
import com.kxwp.common.form.app.supermarket.APPUserAccountForm;
import com.kxwp.common.form.core.SMSCodeForm;
import com.kxwp.common.model.app.supermarket.PassKeyModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.LoginException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.order.AppOrderStatusCount;
import com.kxwp.common.region.RegionModel;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.service.supermarket.SuperMarketUserService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.common.ErrorPage;
import com.kxwp.h5.utils.H5UserSessionUtils;



/**
 * date: 2016年8月26日 上午10:01:00
 *
 * @author zhaojn
 */
@RequestMapping("/h5/user")
@Controller
public class SupermarketUserAction {


  @Autowired
  private SuperMarketUserService superMarketUserService;

  @Autowired
  private RegionService regionService;
  
  @Autowired
  private SalesOrderService salesOrderService;


  /**
   * 
   * mypage:(我的个人中心).
   *
   * 2016年9月2日 下午2:35:32
   * @author lou jian wen
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/mypage.do")
  public String mypage(HttpServletRequest request,
      HttpServletResponse response) {
    AppOrderStatusCount appOrderStatusCount = salesOrderService.getOrderStatusCount(H5UserSessionUtils.getUserSession(request));
    request.setAttribute("orderCount", appOrderStatusCount);
    request.setAttribute("user", H5UserSessionUtils.getUserSession(request));
    return "user/mypage";
  }
  
  @RequestMapping(value = "/myAccountInfo.do")
  public String myAccountInfo(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      request.setAttribute("supermarket", superMarketUserService.getSuperMarketById(H5UserSessionUtils.getUserSession(request).getSupermarket_id()));
    } catch (KXWPNotFoundException e) {
      
     return ErrorPage.PAGE_404;
      
    }
    return "user/account_info";
  }
  
  @RequestMapping(value = "/gotoSecurityCenter.do")
  public String gotoSecurityCenter(HttpServletRequest request,
      HttpServletResponse response) {
    return "user/security_center";
  }
  
  @RequestMapping(value = "/setting.do")
  public String setting(HttpServletRequest request,
      HttpServletResponse response) {
    return "user/setting";
  }

  /**
   * registerSupermarket:(注册超市基本相关信息).
   *
   * 2016年8月2日 下午5:36:22
   * 
   * @author zhaojn
   * @param supermarket
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/registerSupermarketAccount.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData registerSupermarket(
      @Valid @RequestBody APPUserAccountForm accountForm, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();

    try {
      superMarketUserService.validateByTelUnique(accountForm.getAccountMobile());
      RegionModel model = new RegionModel();
      model.setProvince(accountForm.getProvince());
      model.setCountry(accountForm.getCountry());
      model.setCity(accountForm.getCity());
      model.setTown(accountForm.getTown());
      // 验证级联地址是否正确
      regionService.validateRegion(model);
      superMarketUserService.registerSupermarketAccountByWX(accountForm);
    } catch (SYSException | LoginException e) {
      edb.markException(e.getMessage(), e);
      KXWPLogUtils.logException(this.getClass(), "API.login exception", e);
      return edb;
    } catch (Exception e) {
      KXWPLogUtils.logException(SupermarketUserAction.class, "registerSupermarket throws :", e);
      edb.markException("系统出现异常", e);
    }
    return edb;
  }

  /**
   * validateAccount:(验证手机号码是否已存在).
   *
   * 2016年8月3日 上午11:25:28
   * 
   * @author zhaojn
   * @param account
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/validateAccount.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData validateAccount(@RequestBody Account account,
      HttpServletRequest request, HttpServletResponse response) {

    ExchangeData edb = new ExchangeData<>();
    try {
      superMarketUserService.validateByTelUnique(account.getMobile());
    } catch (LoginException e) {
      edb.markException(e.getMessage(), e);
      KXWPLogUtils.logException(this.getClass(), "API.login exception", e);
    } catch (Exception e) {
      edb.markException(e);
      KXWPLogUtils.logException(this.getClass(), "API.login exception", e);
    }

    return edb;
  }
  
  
  /**
   * modifyPassword:(修改密码).
   *
   * 2016年8月6日 下午10:31:44
   * 
   * @author zhaojn
   * @param modifyPasswordForm
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/modifyPassword.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData modifyPassword(
      @Valid @RequestBody APPModifyPasswordForm modifyPasswordForm, HttpServletRequest request,
      HttpServletResponse response) {

    ExchangeData edb = new ExchangeData<>();
    try {
      superMarketUserService.modifypassword(modifyPasswordForm);
    } catch (Exception e) {
      edb.markException(e);
      KXWPLogUtils.logException(this.getClass(), "API.modifyPassword exception", e);
    }
    return edb;
  }
  
  
  /**
   * forgetPasswordAndverifySMSCode:(忘记密码时校验成功后返回passKey).
   *
   * 2016年8月26日 下午6:17:54
   * @author zhaojn
   * @param smsCodeForm
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/ajax/forgetPasswordAndverifySMSCode.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<PassKeyModel> forgetPasswordAndverifySMSCode(@RequestBody SMSCodeForm smsCodeForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<PassKeyModel> edb =new ExchangeData<PassKeyModel>();
    try {
      PassKeyModel data = superMarketUserService.forgetAndVerifySMSCode(smsCodeForm);
      edb.setData(data);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

  
  /**
   * forgetAndModifyPassWord:(忘记密码后重置密码).
   *
   * 2016年8月6日 下午10:31:44
   * 
   * @author zhaojn
   * @param modifyPasswordForm
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/forgetAndModifyPassWord.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData forgetAndModifyPassWord(
      @Valid @RequestBody APPModifyPasswordForm modifyPasswordForm, HttpServletRequest request,
      HttpServletResponse response) {

    ExchangeData edb = new ExchangeData<>();
    try {
      superMarketUserService.forgetAndModifyPassWord(modifyPasswordForm);
    } catch (Exception e) {
      edb.markException(e);
      KXWPLogUtils.logException(this.getClass(), "API.modifyPassword exception", e);
    }
    return edb;
  }
 
  
  /**
   * 
   * validateIsExist:(忘记密码操作时验证手机号是否存在).
   *
   * 2016年8月2日 下午4:02:27
   * 
   * @author zhaojn
   * @param account
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/validateIsExist.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData validateIsExist(@RequestBody Account account,
      HttpServletRequest request, HttpServletResponse response) {

    ExchangeData edb = new ExchangeData<>();
    try {
      superMarketUserService.validateMobileIsExist(account.getMobile());
    } catch (LoginException e) {
      edb.markException(e.getMessage(), e);
      KXWPLogUtils.logException(this.getClass(), "API.login exception", e);
    } catch (Exception e) {
      edb.markException(e);
      KXWPLogUtils.logException(this.getClass(), "API.login exception", e);
    }

    return edb;
  }

 

}

