package com.kxwp.admin.interceptor.fwz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.serviceStation.FWZRBACItemService;
import com.kxwp.common.constants.CommonRequestAttr;
import com.kxwp.common.constants.HomePage;
import com.kxwp.common.constants.HostNameConst;
import com.kxwp.common.constants.ShareRequestAttr;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.rbac.MenuItem;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

@Component("FWZUserSessionInterceptor")
public class FWZUserSessionInterceptor extends HandlerInterceptorAdapter {


  @Autowired
  private FWZRBACItemService rbacItemService;

  @SuppressWarnings("rawtypes")
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String host = (String) request.getAttribute(CommonRequestAttr.ATTR_ACCESS_HOST);
    String requestURL = (String) request.getAttribute(CommonRequestAttr.ATTR_REQUEST_URL);
    // 必须制定域名访问
    if (!StringUtils.contains(host, HostNameConst.FWZ_HOST)) {
      KXWPLogUtils.logInfo(FWZUserSessionInterceptor.class, "非正常域名访问,跳转至首页 " + host);
      response.sendRedirect(HomePage.FWZ_HOME_PAGE);
      return false;
    }
    UserModel um = (UserModel) request.getAttribute(ShareRequestAttr.ATTR_LOGIN_USER);
    if (um == null) {
      // ajax请求,url /**/ajax/**
      if (StringUtils.contains(requestURL, "/ajax/")) {
        ExchangeData deb = new ExchangeData();
        deb.markUnLogin();
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(JacksonUtils.writeValue(deb));
        return false;
      }
      response.sendRedirect(HomePage.FWZ_HOME_PAGE);
      return false;
    }
    List<MenuItem> menuItemList = rbacItemService.getAssignedItem(um);
    request.setAttribute(CommonRequestAttr.ATTR_MENU_LIST, menuItemList);
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
