package com.kxwp.admin.interceptor.base;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.masterStation.ZZLoginService;
import com.kxwp.admin.service.serviceStation.FWZLoginService;
import com.kxwp.admin.service.supplier.GYSLoginService;
import com.kxwp.common.constants.APPBuildVersion;
import com.kxwp.common.constants.CommonRequestAttr;
import com.kxwp.common.constants.HostNameConst;
import com.kxwp.common.constants.ShareRequestAttr;
import com.kxwp.common.utils.ConfigFileUtil;
import com.kxwp.common.utils.KXWPDatetimeUtils;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.common.utils.http.WebHttpUtils;

@Component("BaseInterceptor")
public class BaseInterceptor extends HandlerInterceptorAdapter {


  protected String host;

  protected String requestURL;



  protected String SYS_URL_PREFIX = "zz";

  @Autowired
  private ZZLoginService msLoginService;


  @Autowired
  private FWZLoginService fwzLoginService;


  @Autowired
  private GYSLoginService gysLoginService;

 

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    readBuildtimeStamp(request);
    WebHttpUtils.logHttpRequest(request);
    host = request.getHeader("host");
    requestURL = request.getRequestURI();
    request.setAttribute(CommonRequestAttr.ATTR_ACCESS_HOST, host);
    request.setAttribute(CommonRequestAttr.ATTR_REQUEST_URL, requestURL);
    formatSYSURLPREFIX();
    request.setAttribute(CommonRequestAttr.ATTR_SYS_URL_PREFIX, SYS_URL_PREFIX);

    if (StringUtils.containsIgnoreCase(host, HostNameConst.ZZ_HOST)) {
      UserModel um = msLoginService.getZZLoginUser(request);
      if (um != null) {
        request.setAttribute(ShareRequestAttr.ATTR_LOGIN_USER, um);
      }
    }

    if (StringUtils.containsIgnoreCase(host, HostNameConst.FWZ_HOST)) {
      UserModel um = fwzLoginService.getFWZLoginUser(request);
      if (um != null) {
        request.setAttribute(ShareRequestAttr.ATTR_LOGIN_USER, um);
      }
    }

    if (StringUtils.containsIgnoreCase(host, HostNameConst.GYS_HOST)) {
      UserModel um = gysLoginService.getGYSLoginUser(request);
      if (um != null) {
        request.setAttribute(ShareRequestAttr.ATTR_LOGIN_USER, um);
      }
    }

    return true;
  }



  /**
   * 
   * formateSYSURLPREFIX:(根据host判断系统url前缀).
   *
   * 2016年8月6日 下午2:07:32
   * 
   * @author lou jian wen
   */
  void formatSYSURLPREFIX() {
    int first_dot_index = StringUtils.indexOf(host, ".");
    this.SYS_URL_PREFIX = StringUtils.substring(host, 0, first_dot_index);
  }

  public static void readBuildtimeStamp(HttpServletRequest request) {
    if (StringUtils.isBlank(APPBuildVersion.APP_BUILD_TIMESTAMP)
        || StringUtils.equalsIgnoreCase(APPBuildVersion.APP_BUILD_TIMESTAMP, "0000")) {
      try {
        String path = request.getSession().getServletContext().getRealPath("/");
        path = path + "META-INF/MANIFEST.MF";

        String appBuildTime = ConfigFileUtil.getConfigValue(path, "app-build-time");

        Date _appBuildTime = KXWPDatetimeUtils.parseStr2Date(appBuildTime, "yyyyMMddHHmmss");
        DateTime dt = new DateTime(_appBuildTime);
        dt = dt.plusHours(8);
        appBuildTime = dt.toString("yyyyMMddHHmmss");
        if (StringUtils.isNotBlank(appBuildTime)) {
          APPBuildVersion.APP_BUILD_TIMESTAMP = appBuildTime;
        } else {
          KXWPLogUtils.logInfo(BaseInterceptor.class, "read build time fail");
        }
      } catch (Exception e) {
        KXWPLogUtils.logException(BaseInterceptor.class, "read build time exception", e);
      }
    }
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
