package com.kxwp.admin.actions.masterStation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.masterStation.MsUserManageService;
import com.kxwp.admin.utils.session.ZZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.utils.KXWPEncryptUtils;

@Controller
@RequestMapping(value = "zz/userManage")
public class MsUserManageAction {

  @Autowired
  private MsUserManageService msUserManageService;
  
  /**
   * goresetPWD:(获取当前账号信息跳转至重置密码界面).
   *
   * 2016年8月5日 上午10:28:31
   * 
   * @author Liuzibing
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/goReset.do", method = RequestMethod.GET)
  public ModelAndView goresetPWD(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView();
    try {
      UserModel userModel = ZZUserSessionUtils.getUserSession(request);
      mav.setViewName("zz/zzczmm");
      mav.addObject("userModel", userModel);
    } catch (Exception e) {
      mav.setViewName(ErrorPage.PAGE_500);
      mav.addObject("message", "系统错误，请联系管理员！");
    }
    return mav;
  }


  /**
   * resetPWD:(重置密码).
   *
   * 2016年8月4日 下午1:44:36
   * @author Liuzibing
   * @param resetPWDForm
   * @param request 
   * @param response
   * @return
   * @tag category name = "New Category" color = "Yellow"
   */
  @RequestMapping(value = "/resetPassword.do", method = RequestMethod.POST)
  public ModelAndView resetPWD(ResetPWDForm resetPWDForm, HttpServletRequest request) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    ModelAndView mav = new ModelAndView();
    try {
      resetPWDForm
          .setNew_password(KXWPEncryptUtils.encryptPassword(resetPWDForm.getNew_password()));
      this.msUserManageService.resetPWD(resetPWDForm);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("redirect:/zz/user/logout.do");
    } catch (Exception e){
      e.getMessage();
      exchangeData.markException(e);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("500");
    }
    return mav;
  }

  /**
   * gotoforgotPWD:(跳转至忘记密码页面). 2016年8月6日 下午2:15:26
   * 
   * @author Liuzibing
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/goForgot.do")
  public String gotoforgotPWD(HttpServletRequest request, HttpServletResponse response) {
    return "zz/zzwjmm";
  }

  /**
   * selectMobileAlive:(查找该手机号码的账号是否 存在). 2016年8月8日 下午4:31:23
   * 
   * @author Liuzibing
   * @param mobile
   * @return
   */
  @RequestMapping(value = "/selectMobileAlive.do", method = RequestMethod.GET)
  public @ResponseBody ExchangeData<Object> selectMobileAlive(Long mobile) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    try {
      this.msUserManageService.mobileAlive(mobile);
      exchangeData.setMessage("该账号存在");
    } catch (Exception e) {
      exchangeData.markException(e);
    }
    return exchangeData;
  }

  /**
   * dsada:(根据手机获取验证码). 2016年8月6日 下午4:13:37
   * 
   * @author Liuzibing
   * @param mobile
   * @throws SYSException
   * @throws ConfigurationException
   */
  @RequestMapping(value = "/getSMSCode.do", method = RequestMethod.GET)
  public @ResponseBody ExchangeData<Object> getsmscode1(Long mobile) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    ForgotPWDForm forgotPWDForm = new ForgotPWDForm();
    forgotPWDForm.setForgot_mobile(mobile);
    try {
      this.msUserManageService.forSMSCode(forgotPWDForm);
    } catch (Exception e) {
      e.getMessage();
      exchangeData.markException(e);
    }
    return exchangeData;
  }

  /**
   * checkSMSCode1:(校验验证码). 2016年8月8日 下午4:53:48
   * 
   * @author Liuzibing
   * @param mobile
   * @param SMScode
   * @return
   */
  @RequestMapping(value = "/checkSMScode.do", method = RequestMethod.GET)
  public @ResponseBody ExchangeData<Object> checkSMSCode1(Long mobile, String SMScode) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    ForgotPWDForm forgotPWDForm = new ForgotPWDForm();
    forgotPWDForm.setForgot_mobile(mobile);
    forgotPWDForm.setVercode(SMScode);
    try {
      this.msUserManageService.checkSMSCode(forgotPWDForm);
      exchangeData.setCallStatus(CallStatusEnum.SUCCESS);
    } catch (SYSException e) {
      e.getMessage();
      exchangeData.markException(e);
    }
    return exchangeData;
  }

  /**
   * forgotPWD:(忘记密码/找回密码).
   *
   * 2016年8月4日 下午4:41:33
   * 
   * @author Liuzibing
   * @param resetPWDForm
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/forgotPassword.do", method = RequestMethod.POST)
  public ModelAndView forgotPWD(ForgotPWDForm forgotPWDForm, HttpServletRequest request) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    ModelAndView mav = new ModelAndView();
    try {
      forgotPWDForm
          .setNew_password(KXWPEncryptUtils.encryptPassword(forgotPWDForm.getNew_password()));
      this.msUserManageService.forgotPWD(forgotPWDForm);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("zz/zz_login");
    } catch (Exception ex) {
      ex.getMessage();
      exchangeData.markException(ex);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("zz/zzwjmm");
    }
    return mav;
  }
}
