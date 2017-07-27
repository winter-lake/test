package com.kxwp.admin.service.masterStation;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.mapper.masterStation.MasterStationAccountMapper;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.form.core.SMSCodeForm;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.service.core.SMSCodeService;

@Service("msUserManageService")
public class MsUserManageService {

  @Autowired
  private MasterStationAccountMapper accountMapper;

  @Autowired
  private SMSCodeService smsCodeService;

  /**
   * resetPWD:(重置密码). 2016年8月4日 上午10:50:29
   * 
   * @author Liuzibing
   * @param resetPWDForm
   * @return
   * @throws Exception
   */
  public int resetPWD(ResetPWDForm resetPWDForm) throws Exception {
    int key = accountMapper.resetPWD(resetPWDForm);
    if (key < 1) {
      throw new Exception("系统错误，重置密码失败");
    }
    return key;
  }

  public int mobileAlive(Long mobile) throws Exception {
     int key=accountMapper.selectAlivePhone(mobile);
     if(key==0)
     {
       throw new Exception("该账号不存在");
     }
     else
     {
       return key;
     }
  }

  /**
   * forSMSCode:(发送给手机短信验证码). 2016年8月6日 下午3:23:19
   * 
   * @author Liuzibing
   * @param forgotPWDForm
   * @throws SYSException
   * @throws ConfigurationException
   */
  public void forSMSCode(ForgotPWDForm forgotPWDForm) throws Exception {
    String mobile = forgotPWDForm.getForgot_mobile().toString();
    SMSCodeForm smsCodeForm = new SMSCodeForm();
    smsCodeForm.setMobile(mobile);
    smsCodeForm.setTemplate(SMSTemplateEnum.SMS_CODE);
    smsCodeService.sendSMSCode(smsCodeForm);
  }


  /**
   * forgotPWD:(验证短信验证码). 2016年8月6日 下午3:47:28
   * 
   * @author Liuzibing
   * @param forgotPWDForm
   * @throws Exception
   */
  public void checkSMSCode(ForgotPWDForm forgotPWDForm) throws SYSException {
    String mobile = forgotPWDForm.getForgot_mobile().toString();
    String smscode = forgotPWDForm.getVercode();
    SMSCodeForm smsCodeForm = new SMSCodeForm();
    smsCodeForm.setMobile(mobile);
    smsCodeForm.setSmsCode(smscode);
    smsCodeForm.setTemplate(SMSTemplateEnum.SMS_CODE);
    smsCodeService.verifySMSCode(smsCodeForm);
  }

  /**
   * forgotPWD:(忘记密码/找回密码 设置新密码).
   *
   * 2016年8月6日 下午3:50:59
   * 
   * @author Liuzibing
   * @param forgotPWDForm
   * @return
   * @throws Exception
   * 
   */
  public int forgotPWD(ForgotPWDForm forgotPWDForm) throws Exception {
    int key = accountMapper.forgotPWD(forgotPWDForm);
    if (key < 1) {
      throw new Exception("系统错误，设置新密码失败");
    }
    return key;
  }
}
