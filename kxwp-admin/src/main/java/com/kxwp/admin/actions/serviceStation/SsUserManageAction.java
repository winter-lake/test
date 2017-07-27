package com.kxwp.admin.actions.serviceStation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.serviceStation.SsUserManageService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.utils.KXWPEncryptUtils;

@Controller
@RequestMapping(value = "fwz/userManage")
public class SsUserManageAction {
  @Autowired
  private SsUserManageService ssUserManageService;

  /**
   * goFwzResetPWD:(跳转至重置密码界面). 2016年8月11日 上午10:18:13
   * 
   * @author Liuzibing
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/goReset.do", method = RequestMethod.GET)
  public ModelAndView goFwzResetPWD(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView();
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      mav.setViewName("fwz/fwzczmm");
      mav.addObject("userModel", userModel);
    } catch (Exception e) {
      mav.setViewName(ErrorPage.PAGE_500);
      mav.addObject("message", "系统错误，请联系管理员！");
    }
    return mav;
  }

  /**
   * fwzResetPWD:(服务站重置密码). 2016年8月11日 上午10:27:24
   * 
   * @author Liuzibing
   * @param resetPWDForm
   * @param request
   * @return
   */
  @RequestMapping(value = "/resetFwzPassword.do", method = RequestMethod.POST)
  public ModelAndView fwzResetPWD(ResetPWDForm resetPWDForm, HttpServletRequest request) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    ModelAndView mav = new ModelAndView();
    try {
      //密码加密
      resetPWDForm
          .setNew_password(KXWPEncryptUtils.encryptPassword(resetPWDForm.getNew_password()));
      this.ssUserManageService.resetPWD(resetPWDForm);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("redirect:/fwz/user/logout.do");
    } catch (Exception e) {
      e.getMessage();
      exchangeData.markException(e);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("500");
    }
    return mav;
  }

  /**
   * gotoFwzforgotPWD:(跳转至服务站忘记密码界面). 2016年8月11日 上午10:29:19
   * 
   * @author Liuzibing
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/gofwzForgot.do")
  public String gotoFwzforgotPWD(HttpServletRequest request, HttpServletResponse response) {
    return "fwz/fwzwjmm";
  }

  /**
   * selectFwzMobileAlive:(查找该手机账号是否存在).
   * 2016年8月11日 下午8:04:46
   * @author Liuzibing
   * @param mobile
   * @return
   */
  @RequestMapping(value = "/selectFwzMobileAlive.do", method = RequestMethod.GET)
  public @ResponseBody ExchangeData<Object> selectFwzMobileAlive(Long mobile) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    try {
      this.ssUserManageService.mobileAlive(mobile);
      exchangeData.setMessage("该账号存在");
    } catch (Exception ex) {
      exchangeData.markException(ex);
    }
    return exchangeData;
  }

  /**
   * getsmscode1:(根据手机获取验证码). 2016年8月11日 上午10:44:40
   * 
   * @author Liuzibing
   * @param mobile
   * @return
   */
  @RequestMapping(value = "/fwzgetSMSCode.do", method = RequestMethod.GET)
  public @ResponseBody ExchangeData<Object> fwzgetsmscode1(Long mobile) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    ForgotPWDForm forgotPWDForm = new ForgotPWDForm();
    forgotPWDForm.setForgot_mobile(mobile);
    try {
      this.ssUserManageService.forSMSCode(forgotPWDForm);
    } catch (Exception e) {
      e.getMessage();
      exchangeData.markException(e);
    }
    return exchangeData;
  }

  /**
   * fwzcheckSMSCode1:(校验用户输入的验证码). 2016年8月11日 上午10:49:40
   * 
   * @author Liuzibing
   * @param mobile
   * @param SMScode
   * @return
   */
  @RequestMapping(value = "/fwzcheckSMSCode.do", method = RequestMethod.GET)
  public @ResponseBody ExchangeData<Object> fwzcheckSMSCode1(Long mobile, String SMScode) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    ForgotPWDForm forgotPWDForm = new ForgotPWDForm();
    forgotPWDForm.setForgot_mobile(mobile);
    forgotPWDForm.setVercode(SMScode);
    try {
      this.ssUserManageService.checkSMSCode(forgotPWDForm);
      exchangeData.setCallStatus(CallStatusEnum.SUCCESS);
    } catch (SYSException e) {
      e.getMessage();
      exchangeData.markException(e);
    }
    return exchangeData;
  }

  /**
   * fwzforgotPWD:(服务站忘记密码功能修改密码). 2016年8月11日 下午3:16:55
   * 
   * @author Liuzibing
   * @param forgotPWDForm
   * @param request
   * @return
   */
  @RequestMapping(value = "/fwzforgotPassword.do", method = RequestMethod.POST)
  public ModelAndView fwzforgotPWD(ForgotPWDForm forgotPWDForm, HttpServletRequest request) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    ModelAndView mav = new ModelAndView();
    try {
      forgotPWDForm
          .setNew_password(KXWPEncryptUtils.encryptPassword(forgotPWDForm.getNew_password()));
      this.ssUserManageService.forgotPWD(forgotPWDForm);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("fwz/fwz_login");
    } catch (Exception ex) {
      ex.getMessage();
      exchangeData.markException(ex);
      mav.addObject("exchangeData", exchangeData);
      mav.setViewName("fwz/fwzwjmm");
    }
    return mav;
  }
}
