package com.kxwp.admin.service.masterStation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.masterStation.MasterStationAccount;
import com.kxwp.admin.entity.masterStation.MsRole;
import com.kxwp.admin.form.user.LoginForm;
import com.kxwp.admin.mapper.masterStation.MasterStationAccountMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CachekeyPrefix;
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

@Service("ZZLoginService")
public class ZZLoginService {


  @Autowired
  private AbstractCacheService cacheService;

  @Autowired
  private MasterStationAccountMapper msAccountMapper;
  
  @Autowired
  private MsAccountService msAccountService;

  public UserModel msLogin(LoginForm loginForm, HttpServletRequest request,
      HttpServletResponse response)
      throws LoginException, IllegalAccessException, InvocationTargetException, SYSException {
    // 返回加密密码
    loginForm.setLogin_password(KXWPEncryptUtils.encryptPassword(loginForm.getLogin_password()));
    // TODO 验证码校验
    MasterStationAccount msAccount = msAccountMapper.msLogin(loginForm);
    
    if (msAccount == null) {
      throw new LoginException("手机号或者密码不正确");
    }
    
   if(msAccount.getAccountStatus() != AccountStatusEnum.VALID && msAccount.getAccountStatus() != AccountStatusEnum.NEEDRESET){
       throw new LoginException("账户 " + loginForm.getLogin_mobile() + "不可用,请联系管理员");
    }
    
    Cookie loginCookie = WebCookieComponent.createCookie(WebCookieComponent.ZZ_LGOIN_COOKIE_NAME,
        WebCookieComponent.createRandomCookieValue());
    response.addCookie(loginCookie);
    UserModel um = new UserModel();
    BeanUtilsBean2.getInstance().copyProperties(um, msAccount);
    um.setOrganizationId(msAccount.getMasterStationId());
    MasterStationAccount masterStationAccount = msAccountService.getAccountInfo(um.getId());
    List<MsRole> role_name_list = masterStationAccount.getMsRoles();
    StringBuilder role_name = new StringBuilder();
    for(int i=0;i<role_name_list.size();i++){
      MsRole _role = role_name_list.get(i);
      role_name.append(_role.getName());
      if(i != role_name_list.size()-1){
        role_name.append(",");
      }
    }
    um.setRole_name(role_name.toString());
    cacheService.putKey(CachekeyPrefix.ADMIN_ZZ_LOGIN + loginCookie.getValue(), um, AbstractCacheService.HALFDAY);
    return um;
  }

  public UserModel autoLogin(HttpServletRequest request)
      throws LoginException, IllegalAccessException, InvocationTargetException {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.ZZ_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      UserModel um = cacheService.getValue(CachekeyPrefix.ADMIN_ZZ_LOGIN +loginCookie.getValue());
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
  public UserModel getZZLoginUser(HttpServletRequest request) {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.ZZ_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      UserModel um = cacheService.getValue(CachekeyPrefix.ADMIN_ZZ_LOGIN + loginCookie.getValue());
      if (um != null) {
        return um;
      }
    }
    return null;
  }


  public void logout(HttpServletRequest request, HttpServletResponse response)
      throws LoginException, IllegalAccessException, InvocationTargetException {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.ZZ_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      cacheService.removeCache(CachekeyPrefix.ADMIN_ZZ_LOGIN + loginCookie.getValue());
      // 清除cookie
      response.addCookie(WebCookieComponent.removeCookie(WebCookieComponent.ZZ_LGOIN_COOKIE_NAME));
    }
  }
}

