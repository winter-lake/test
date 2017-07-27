package com.kxwp.h5.service.user;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.constants.OperationTypeEnum;
import com.kxwp.common.constants.SupermarketStausEnum;
import com.kxwp.common.constants.goods.OrderSourceEnum;
import com.kxwp.common.entity.SYSOperationLog;
import com.kxwp.common.entity.supermarket.Account;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.form.supermarket.SupermarketLoginForm;
import com.kxwp.common.mapper.supermarket.AccountMapper;
import com.kxwp.common.mapper.supermarket.SupermarketMapper;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.LoginException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.service.core.LogServive;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.http.WebCookieComponent;

/**
 * Date: 2016年8月24日 下午3:08:31
 * 
 * @author lou jian wen
 */
@Service("H5UserService")
public class H5UserService {

  @Autowired
  private AccountMapper accountMapper;

  @Autowired
  private SupermarketMapper supermarketMapper;

  @Autowired
  private AbstractCacheService cacheService;
  
  @Autowired
  private LogServive logServive;

  public APPUserModel login(SupermarketLoginForm loginForm, HttpServletRequest request,
      HttpServletResponse response)
      throws IllegalAccessException, InvocationTargetException, SYSException, LoginException {
    // 返回加密密码
    loginForm.setLogin_password(KXWPEncryptUtils.encryptPassword(loginForm.getLogin_password()));
    Account supermaketAccount = accountMapper.appLogin(loginForm);
    // TODO 验证码校验

    if (supermaketAccount == null) {
      throw new LoginException("手机号或者密码不正确");
    }

    if (supermaketAccount.getAccountStatus() == AccountStatusEnum.INVALID) {
      throw new LoginException("账户 " + loginForm.getLogin_mobile() + "不可用,请联系服务站");
    }

    Supermarket supermarket =
        supermarketMapper.selectByPrimaryKey(supermaketAccount.getSupmarketId());

    APPUserModel um = new APPUserModel();
    um.setAccountStatus(supermaketAccount.getAccountStatus());
    um.setSupermarketStatus(supermarket.getSupermarketStatus());
    if (supermaketAccount.getAccountStatus() != AccountStatusEnum.VALID
        || supermarket.getSupermarketStatus() != SupermarketStausEnum.ON) {
      throw new LoginException("您的账号 " + loginForm.getLogin_mobile() + "正在审核中,请耐心等待");
    }
    BeanUtilsBean2.getInstance().copyProperties(um, supermaketAccount);
    Cookie loginCookie =
        WebCookieComponent.createCookie(WebCookieComponent.SUPERMARKET_LGOIN_COOKIE_NAME,
            WebCookieComponent.createRandomCookieValue());
    response.addCookie(loginCookie);
    um.setUser_id(supermaketAccount.getId());
    um.setSupermarket_id(supermaketAccount.getSupmarketId());
    um.setSupermarket_name(supermarket.getSupermarketName());
    if (supermarket.getServiceStationId() != null) {
      um.setService_station_id(supermarket.getServiceStationId());
    }
    cacheService.putKey(CachekeyPrefix.SUPERMARKET_LOGIN + loginCookie.getValue(), um,
        AbstractCacheService.ONEDAY);
    //记录登录日志
    SYSOperationLog log = new SYSOperationLog();
    log.setOperationObject(supermaketAccount.getUserNo());
    log.setOperationType(OperationTypeEnum.SUPER_MARKET_LOGIN);
    log.setOperationUserId(supermaketAccount.getId());
    log.setRemarks(request.getRemoteHost() + ",登录平台:" + OrderSourceEnum.WX);
    logServive.addLog(log);
    return um;
  }

  
  /**
   * 
   * getH5LoginUser:(获取已登录用户信息).
   *
   * 2016年8月24日 下午4:02:31
   * @author lou jian wen
   * @param request
   * @return
   */
  public APPUserModel getH5LoginUser(HttpServletRequest request) {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.SUPERMARKET_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      APPUserModel um =
          cacheService.getValue(CachekeyPrefix.SUPERMARKET_LOGIN + loginCookie.getValue());
      if (um != null) {
        return um;
      }
    }
    return null;
  }
  
  

  public APPUserModel autoLogin(HttpServletRequest request)
      throws LoginException, IllegalAccessException, InvocationTargetException {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.SUPERMARKET_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      APPUserModel um = cacheService.getValue(CachekeyPrefix.SUPERMARKET_LOGIN +loginCookie.getValue());
      if (um != null) {
        return um;
      }
    }
    return null;
  }
  
  
  public void logout(HttpServletRequest request, HttpServletResponse response)
      throws LoginException, IllegalAccessException, InvocationTargetException {
    Cookie loginCookie =
        WebCookieComponent.getCookie(request, WebCookieComponent.SUPERMARKET_LGOIN_COOKIE_NAME);
    if (loginCookie != null) {
      cacheService.removeCache(CachekeyPrefix.SUPERMARKET_LOGIN + loginCookie.getValue());
      // 清除cookie
      response.addCookie(WebCookieComponent.removeCookie(WebCookieComponent.SUPERMARKET_LGOIN_COOKIE_NAME));
    }
  }
}

