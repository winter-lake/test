package com.kxwp.h5.actions.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.form.core.SMSCodeForm;
import com.kxwp.common.model.exception.LoginException;
import com.kxwp.common.query.sys.RegionQuery;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.core.SMSCodeService;
import com.kxwp.common.service.supermarket.SuperMarketUserService;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * date: 2016年8月25日 上午10:25:15 微信端公共接口
 * 
 * @author zhaojn
 */
@Controller
@RequestMapping("/h5/public")
public class H5PublicAction {
  @Autowired
  private RegionService regionService;

  @Autowired
  private SMSCodeService smsCodeService;

  @Autowired
  private SuperMarketUserService superMarketUserService;


  /**
   * queryRegion:(级联地址查询).
   *
   * 2016年8月25日 上午10:24:56
   * 
   * @author zhgaojn
   * @param regionQuery
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/queryRegion.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryRegion(@Valid @RequestBody RegionQuery regionQuery,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      List<SYSRegion> regionList = regionService.queryCascadeRegion(regionQuery);
      edb.setData(regionList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    KXWPLogUtils.logInfo(this.getClass(), JacksonUtils.writeValue(edb));
    return edb;
  }

  /**
   * sendSMSCode:(发送注册验证码).
   *
   * 2016年8月26日 下午3:38:15
   * 
   * @author zhaojn
   * @param smsCodeForm
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/sendRegisterSMSCode.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData sendRegisterSMSCode(@RequestBody SMSCodeForm smsCodeForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      superMarketUserService.validateByTelUnique(Long.parseLong(smsCodeForm.getMobile()));
      smsCodeService.sendSMSCode(smsCodeForm);
    } catch (LoginException loginException) {
      edb.markException(loginException.getMessage(),loginException);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }
  
  /**
   * 
   * sendForgetPasswdSMSCode:(发送忘记密码验证码).
   *
   * 2016年9月9日 下午4:49:10
   * @author lou jian wen
   * @param smsCodeForm
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/sendForgetPasswdSMSCode.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData sendForgetPasswdSMSCode(@RequestBody SMSCodeForm smsCodeForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      superMarketUserService.validateMobileIsExist(Long.parseLong(smsCodeForm.getMobile()));
      smsCodeService.sendSMSCode(smsCodeForm);
    } catch (LoginException loginException) {
      edb.markException(loginException.getMessage(),loginException);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }


  /**
   * 
   * verifySMSCode:(短信验证码验证).
   *
   * 2016年8月26日 下午3:44:27
   * 
   * @author zhaojn
   * @param smsCodeForm
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/ajax/verifySMSCode.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData verifySMSCode(@RequestBody SMSCodeForm smsCodeForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      KXWPLogUtils.logInfo(this.getClass(), "verifySMSCode.do" + smsCodeForm.toString());
      smsCodeService.verifySMSCode(smsCodeForm);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

}
