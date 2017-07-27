package com.kxwp.admin.service.serviceStation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.form.user.LoginForm;
import com.kxwp.admin.mapper.serviceStation.ServiceStationAccountMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.LoginException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.http.WebCookieComponent;

/**
 * Date: 2016年7月29日 下午4:20:42
 * 
 * @author lou jian wen
 */

@Service("FWZLoginService")
public class FWZLoginService {


  @Autowired
  private AbstractCacheService cacheService;

  @Autowired
  private ServiceStationAccountMapper serviceStationAccountMapper;
  
  @Autowired
  private SsAccountService ssAccountService;
  
  @Autowired
  private FWZCommonService fwzCommonService;

  public UserModel fwzLogin(LoginForm loginForm, HttpServletRequest request,
      HttpServletResponse response)
      throws LoginException, IllegalAccessException, InvocationTargetException, SYSException, KXWPNotFoundException {
    // 返回加密密码
    loginForm.setLogin_password(KXWPEncryptUtils.encryptPassword(loginForm.getLogin_password()));
    // TODO 验证码校验
    ServiceStationAccount loginAccount = serviceStationAccountMapper.fwzLogin(loginForm);

    if (loginAccount == null) {
      throw new LoginException("手机号或者密码不正确");
    }

    if (loginAccount.getAccountStatus() != AccountStatusEnum.VALID && loginAccount.getAccountStatus() != AccountStatusEnum.NEEDRESET) {
      throw new LoginException("账户 " + loginForm.getLogin_mobile() + "不可用,请联系管理员");
    }

    Cookie loginCookie = WebCookieComponent.createCookie(WebCookieComponent.FWZ_LGOIN_COOKIE_NAME,
        WebCookieComponent.createRandomCookieValue());
    response.addCookie(loginCookie);
    UserModel um = new UserModel();
    BeanUtilsBean2.getInstance().copyProperties(um, loginAccount);
    ServiceStation serviceStation = fwzCommonService.getServiceStationByID(loginAccount.getServiceStationId());
    ServiceStationAccount account = ssAccountService.getAccountInfo(um.getId());
    List<SsRole> role_name_list = account.getSsRoles();
    StringBuilder role_name = new StringBuilder();
    for(int i=0;i<role_name_list.size();i++){
      SsRole _role = role_name_list.get(i);
      role_name.append(_role.getName());
      if(i != role_name_list.size()-1){
        role_name.append(",");
      }
    }
    um.setRole_name(role_name.toString());
    um.setServiceStation(serviceStation);
    um.setOrganizationId(loginAccount.getServiceStationId());
    um.setOrganizationName(serviceStation.getServiceStationName());
    cacheService.putKey(CachekeyPrefix.ADMIN_FWZ_LOGIN + loginCookie.getValue(), um, AbstractCacheService.HALFDAY);
    return um;
  }

  public UserModel autoLogin(HttpServletRequest request)
      throws LoginException, IllegalAccessException, InvocationTargetException {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.FWZ_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      UserModel um = cacheService.getValue(CachekeyPrefix.ADMIN_FWZ_LOGIN +loginCookie.getValue());
      if (um != null) {
        return um;
      }
    }
    return null;
  }

  /**
   * 
   * getZZLoginUser:(获取总站登录用户session).
   *
   * 2016年8月3日 上午10:12:58
   * 
   * @author lou jian wen
   * @param request
   * @return
   * @throws SYSException
   */
  public UserModel getFWZLoginUser(HttpServletRequest request) {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.FWZ_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      UserModel um = cacheService.getValue(CachekeyPrefix.ADMIN_FWZ_LOGIN +loginCookie.getValue());
      if (um != null) {
        return um;
      }
    }
    return null;
  }


  public void logout(HttpServletRequest request, HttpServletResponse response)
      throws LoginException, IllegalAccessException, InvocationTargetException {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.FWZ_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      cacheService.removeCache(CachekeyPrefix.ADMIN_FWZ_LOGIN + loginCookie.getValue());
      // 清除cookie
      response.addCookie(WebCookieComponent.removeCookie(WebCookieComponent.FWZ_LGOIN_COOKIE_NAME));
    }
  }
}

