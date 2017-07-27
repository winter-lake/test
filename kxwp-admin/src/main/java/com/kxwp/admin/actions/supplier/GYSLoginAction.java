package com.kxwp.admin.actions.supplier;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.form.user.LoginForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.supplier.GYSLoginService;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.exception.LoginException;

/**
 * Date: 2016年7月29日 下午5:06:03
 * 
 * @author lou jian wen
 */
@RequestMapping("/gys/user")
@Controller
public class GYSLoginAction {

  @Autowired
  private GYSLoginService gysLoginService;


  /**
   * 
   * index:(系统首页).
   *
   * 2016年7月30日 上午9:36:28
   * 
   * @author lou jian wen
   * @param loginForm
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/index.do")
  public String index(HttpServletRequest request, HttpServletResponse response) {
    return "gys/gys_login";
  }


  @RequestMapping(value = "/gotoDashboard.do")
  public String gotoDashboard(HttpServletRequest request, HttpServletResponse response) {
    return "gys/dashboard";
  }

  /**
   * 
   * msLogin:(总站登录).
   *
   * 2016年7月29日 下午6:23:38
   * 
   * @author lou jian wen
   * @param loginForm
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/ajax/login.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      method = {RequestMethod.POST})
  public @ResponseBody ExchangeData msLogin(@Valid @RequestBody LoginForm loginForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData exchangeData = new ExchangeData<>();
    try {
      gysLoginService.gysLogin(loginForm, request, response);
    } catch (LoginException | IllegalAccessException | InvocationTargetException e) {
      exchangeData.markException(e.getMessage(), e);
    } catch (Exception e) {
      exchangeData.markException(e);;
    }
    exchangeData.setAuthStatus(YNEnum.N);
    return exchangeData;
  }


  @RequestMapping(value = "/logout.do")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      gysLoginService.logout(request, response);
    } catch (Exception e) {
      return ErrorPage.PAGE_500;
    }
    return "redirect:/gys/user/index.do";
  }

  /**
   * 
   * autologin:(尝试自动登录).
   *
   * 2016年7月29日 下午5:07:14
   * 
   * @author lou jian wen
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/ajax/autoLogin.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      method = {RequestMethod.POST})
  public @ResponseBody ExchangeData autologin(HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData exchangeData = new ExchangeData();
    try {
      UserModel um = gysLoginService.autoLogin(request);
      if (um == null) {
        exchangeData.markUnLogin();
      }
    } catch (Exception e) {
      exchangeData.markException(e.getMessage(), e);
    }
    return exchangeData;
  }
}

