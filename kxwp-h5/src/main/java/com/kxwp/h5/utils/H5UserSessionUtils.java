package com.kxwp.h5.utils;

import javax.servlet.http.HttpServletRequest;

import com.kxwp.common.constants.ShareRequestAttr;
import com.kxwp.common.model.app.supermarket.APPUserModel;

/**
 * 用户登录信息 date: 2016年8月3日 下午3:24:56
 *
 * @author lou jian wen
 */
public class H5UserSessionUtils {

  public static APPUserModel getUserSession(HttpServletRequest request) {
    if (request.getAttribute(ShareRequestAttr.ATTR_LOGIN_USER) != null) {
      return (APPUserModel) request.getAttribute(ShareRequestAttr.ATTR_LOGIN_USER);
    }
    return null;
  }


}
