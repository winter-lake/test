package com.kxwp.admin.utils.session;

import javax.servlet.http.HttpServletRequest;

import com.kxwp.admin.constants.FWZRequestAttr;
import com.kxwp.admin.model.ms.UserModel;

/**
 * 服务站用户登录信息 date: 2016年8月3日 下午3:24:56
 *
 * @author lou jian wen
 */
public class FWZUserSessionUtils {

  public static UserModel getUserSession(HttpServletRequest request) {
    if (request.getAttribute(FWZRequestAttr.ATTR_LOGIN_USER) != null) {
      return (UserModel) request.getAttribute(FWZRequestAttr.ATTR_LOGIN_USER);
    }
    return null;
  }


}
