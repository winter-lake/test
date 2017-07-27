package com.kxwp.h5.actions.user;


import java.io.IOException;
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

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.form.supermarket.SupermarketLoginForm;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.LoginException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.common.ErrorPage;
import com.kxwp.h5.model.home.HomePageContent;
import com.kxwp.h5.service.content.ContentService;
import com.kxwp.h5.service.user.H5UserService;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * Date: 2016年8月24日 下午1:23:08
 * 
 * @author lou jian wen
 */

@RequestMapping("/h5/user")
@Controller
public class SupermarketLoginAction {


  @Autowired
  private H5UserService h5UserService;



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
    return "user/login";
  }
  
  
  @Autowired
  private ContentService contentService;
  /**
   * 
   * home:(登录后跳转的首页).
   *
   * 2016年8月27日 下午3:31:54
   * @author lou jian wen
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/home.do")
  public String home(HttpServletRequest request, HttpServletResponse response) {
    HomePageContent homePageContent;
    try {
      homePageContent = contentService.getHomeContent(H5UserSessionUtils.getUserSession(request));
      request.setAttribute("homeContent", homePageContent);
    } catch (KXWPException | IOException e) {
      KXWPLogUtils.logException(this.getClass(),"home page content exception", e);
      return ErrorPage.PAGE_500;
    }
    
    return "user/index";
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
  public @ResponseBody ExchangeData login(@Valid @RequestBody SupermarketLoginForm loginForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData exchangeData = new ExchangeData<>();
    try {
      h5UserService.login(loginForm, request, response);
    } catch (IllegalAccessException | InvocationTargetException | SYSException | LoginException e) {
      exchangeData.markException(e.getMessage(), e);
    }
    return exchangeData;
  }


  @RequestMapping(value = "/logout.do")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      h5UserService.logout(request, response);
    } catch (LoginException | IllegalAccessException | InvocationTargetException e) {
        KXWPLogUtils.logException(this.getClass(),"h5 logout exception", e);
    }
    return "redirect:/h5/user/index.do";
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
     APPUserModel user = h5UserService.autoLogin(request);
     if(user == null){
       exchangeData.markFail("用户未登录");
     }
    } catch (LoginException | IllegalAccessException | InvocationTargetException e) {
      exchangeData.markException("h5 autologin exception", e);
    }
    return exchangeData;
  }
  
  

}

