package com.kxwp.admin.actions.supplier;

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
import com.kxwp.admin.service.supplier.SuUserManageService;
import com.kxwp.admin.utils.session.GYSUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.utils.KXWPEncryptUtils;

@Controller
@RequestMapping(value="gys/userManage")
public class SuUserManageAction {
    @Autowired
    private SuUserManageService suUserManageService;
    
    /**
     * goGysResetPWD:(跳转至重置密码界面).
     * 2016年8月11日 下午4:45:47
     * @author Liuzibing
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/goReset.do" ,method = RequestMethod.GET)
    public ModelAndView goGysResetPWD(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav=new ModelAndView();
        try{
          UserModel usermodel=GYSUserSessionUtils.getUserSession(request);
          mav.setViewName("gys/gysczmm");
          mav.addObject("userModel",usermodel);
        }catch(Exception e){
          mav.setViewName(ErrorPage.PAGE_500);
          mav.addObject("message", "系统错误，请联系管理员！");
        }
        return mav;
    }
    
    /**
     * gysResetPWD:(服务站重置密码).
     * 2016年8月11日 下午4:16:55
     * @author Liuzibing
     * @param resetPWDForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetGysPassword.do",method =RequestMethod.POST)
    public ModelAndView gysResetPWD(ResetPWDForm resetPWDForm,HttpServletRequest request){
      ExchangeData<Object> exchangeData=new ExchangeData<Object>();
      ModelAndView mav=new ModelAndView();
      try{
        resetPWDForm.setNew_password(KXWPEncryptUtils.encryptPassword(resetPWDForm.getNew_password()));
        this.suUserManageService.resetPWD(resetPWDForm);
        mav.addObject("exchangeData",exchangeData);
        mav.setViewName("redirect:/gys/user/logout.do");
      }catch(Exception e){
        e.getMessage();
        exchangeData.markException(e);
        mav.addObject("exchangeData", exchangeData);
        mav.setViewName("500");
      }
      return mav;
    }
    
    /**
     * gotoGysforgotPWD:(跳转至供应商忘记密码界面).
     * 2016年8月11日 下午4:17:53
     * @author Liuzibing
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/gogysForgot.do")
    public String gotoGysforgotPWD(HttpServletRequest request,HttpServletResponse response){
      return "gys/gyswjmm";
    }
    
    
    /**
     * selectGysMobileAlive:(查询该手机账号是否存在).
     * 2016年8月11日 下午4:44:58
     * @author Liuzibing
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/selectGysMobileAlive.do", method = RequestMethod.GET)
    public @ResponseBody ExchangeData<Object> selectGysMobileAlive(Long mobile){
      ExchangeData<Object> exchangeData=new ExchangeData<Object>();
      try{
        this.suUserManageService.mobileAlive(mobile);
        exchangeData.setMessage("该账号存在");
      }catch(Exception ex){
        exchangeData.markException(ex);
      }
      return exchangeData;
    }
    
    /**
     * gysgetsmscode1:(根据手机获取验证码).
     * 2016年8月11日 下午4:51:39
     * @author Liuzibing
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/gysgetSMSCode.do", method = RequestMethod.GET)
    public @ResponseBody ExchangeData<Object> gysgetsmscode1(Long mobile){
      ExchangeData<Object> exchangeData=new ExchangeData<Object>();
      
      ForgotPWDForm forgotPWDForm=new ForgotPWDForm();
      forgotPWDForm.setForgot_mobile(mobile);
      try{
        this.suUserManageService.forSMSCode(forgotPWDForm);
      }catch(Exception e){
          e.getMessage();
          exchangeData.markException(e);
      }
      return exchangeData;
    }
    
    /**
     * gyscheckSMSCode1:(校验手机验证码).
     * 2016年8月11日 下午4:59:07
     * @author Liuzibing
     * @param mobile
     * @param SMScode
     * @return
     */
    @RequestMapping(value = "/gyscheckSMSCode.do",method = RequestMethod.GET)
    public @ResponseBody ExchangeData<Object> gyscheckSMSCode1(Long mobile,String SMScode){
      ExchangeData<Object> exchangeData = new ExchangeData<Object>();
      ForgotPWDForm forgotPWDForm = new ForgotPWDForm();
      forgotPWDForm.setForgot_mobile(mobile);
      forgotPWDForm.setVercode(SMScode);
      try {
        this.suUserManageService.checkSMSCode(forgotPWDForm);
        exchangeData.setCallStatus(CallStatusEnum.SUCCESS);
      } catch (SYSException e) {
        e.getMessage();
        exchangeData.markException(e);
      }
      return exchangeData;
    }
    
    /**
     * gysforgotPWD:(这里用一句话描述这个方法的作用).
     *
     * 2016年8月11日 下午5:06:29
     * @author Liuzibing
     * @param forgotPWDForm
     * @param request
     * @return
    
     */
    @RequestMapping(value = "/gysforgotPassword.do" , method = RequestMethod.POST)
    public ModelAndView gysforgotPWD(ForgotPWDForm forgotPWDForm, HttpServletRequest request){
      ExchangeData<Object> exchangeData=new ExchangeData<Object>();
      ModelAndView mav=new ModelAndView();
      try{
        forgotPWDForm.setNew_password(KXWPEncryptUtils.encryptPassword(forgotPWDForm.getNew_password()));
        this.suUserManageService.forgotPWD(forgotPWDForm);
        mav.addObject("exchangeData",exchangeData);
        mav.setViewName("gys/gys_login");
      }catch(Exception e){
        e.getMessage();
        exchangeData.markException(e);
        mav.addObject("exchangeData",exchangeData);
        mav.setViewName("gys/gyswjmm");
      }
      return mav;
    }
}
