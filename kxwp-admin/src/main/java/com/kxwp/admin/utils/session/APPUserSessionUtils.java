package com.kxwp.admin.utils.session;

import javax.servlet.http.HttpServletRequest;

import com.kxwp.admin.constants.ZZRequestAttr;
import com.kxwp.admin.model.ms.UserModel;

/**
 * 用户登录信息 date: 2016年8月3日 下午3:24:56
 *
 * @author lou jian wen
 */
public class APPUserSessionUtils {

  public static UserModel getUserSession(HttpServletRequest request) {
    if (request.getAttribute(ZZRequestAttr.ATTR_LOGIN_USER) != null) {
      return (UserModel) request.getAttribute(ZZRequestAttr.ATTR_LOGIN_USER);
    }
    return null;
  }


}
