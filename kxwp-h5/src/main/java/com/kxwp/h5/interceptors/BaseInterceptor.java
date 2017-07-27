package com.kxwp.h5.interceptors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kxwp.common.constants.APPBuildVersion;
import com.kxwp.common.constants.CommonRequestAttr;
import com.kxwp.common.constants.ShareRequestAttr;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.utils.ConfigFileUtil;
import com.kxwp.common.utils.KXWPDatetimeUtils;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.common.utils.http.WebHttpUtils;
import com.kxwp.h5.service.user.H5UserService;

@Component("BaseInterceptor")
public class BaseInterceptor extends HandlerInterceptorAdapter {


  protected String host;

  protected String requestURL;
  
  //回退路径
  protected String refer_url;

  //H5端页面返回出现死循环的都回到首页
  static Map<String, String> BACK_URL_MAP = new HashMap<>();
  static{
    BACK_URL_MAP.put(KXWPEncryptUtils.getMD5String("/h5/supplier/supplierDetail.do"), "供应商店铺首页");
  }

  @Autowired
  private H5UserService userService;



  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    readBuildtimeStamp(request);
    WebHttpUtils.logHttpRequest(request);
    host = request.getHeader("host");
    requestURL = request.getRequestURI();
    request.setAttribute(CommonRequestAttr.ATTR_REQUEST_URL, requestURL);
    request.setAttribute(CommonRequestAttr.ATTR_ACCESS_HOST, host);
    refer_url = request.getHeader("Referer");
    if(BACK_URL_MAP.containsKey(KXWPEncryptUtils.getMD5String(requestURL))){
      refer_url = "/h5/user/home.do";
    }
    request.setAttribute(CommonRequestAttr.ATTR_REFER_URL, refer_url);
    KXWPLogUtils.logInfo(this.getClass(), "requestURL=" + requestURL + ",refer_url=" + refer_url); 
    APPUserModel um = userService.getH5LoginUser(request);
    if (um != null) {
      request.setAttribute(ShareRequestAttr.ATTR_LOGIN_USER, um);
    }
    return true;
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
