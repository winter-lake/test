package com.kxwp.h5.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kxwp.common.constants.CommonRequestAttr;
import com.kxwp.common.constants.HomePage;
import com.kxwp.common.constants.HostNameConst;
import com.kxwp.common.constants.ShareRequestAttr;
import com.kxwp.common.constants.config.ProjectConfig;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.utils.H5UserSessionUtils;

@Component("H5UserSessionInterceptor")
public class H5UserSessionInterceptor extends HandlerInterceptorAdapter {


  @SuppressWarnings("rawtypes")
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String host = (String) request.getAttribute(CommonRequestAttr.ATTR_ACCESS_HOST);
    String requestURL = (String) request.getAttribute(CommonRequestAttr.ATTR_REQUEST_URL);

    // 正式环境必须制定域名访问
    if (!StringUtils.contains(host, HostNameConst.H5_HOST) && ProjectConfig.isProd()) {
      KXWPLogUtils.logInfo(H5UserSessionUtils.class, "非正常域名访问,跳转至首页 " + host);
      response.sendRedirect(HomePage.H5_HOME_PAGE);
      return false;
    }
    APPUserModel um = (APPUserModel) request.getAttribute(ShareRequestAttr.ATTR_LOGIN_USER);
    if (um == null) {

      // ajax请求,url /**/ajax/**
      if (StringUtils.contains(requestURL, "/ajax/")) {
        ExchangeData deb = new ExchangeData();
        deb.markUnLogin();
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(JacksonUtils.writeValue(deb));
        return false;
      }
      response.sendRedirect(HomePage.H5_HOME_PAGE);
      return false;
    }
    return true;
  }



  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    super.postHandle(request, response, handler, modelAndView);

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    super.afterCompletion(request, response, handler, ex);

  }

  @Override
  public void afterConcurrentHandlingStarted(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception {
    super.afterConcurrentHandlingStarted(request, response, handler);

  }

}
