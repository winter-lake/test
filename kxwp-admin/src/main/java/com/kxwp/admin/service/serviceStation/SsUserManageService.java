package com.kxwp.admin.service.serviceStation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.mapper.serviceStation.ServiceStationAccountMapper;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.form.core.SMSCodeForm;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.service.core.SMSCodeService;

@Service("ssUserManageService")
public class SsUserManageService {
    @Autowired
    private ServiceStationAccountMapper accountMapper;
    
    @Autowired
    private SMSCodeService smsCodeService;
    
    /**
     * resetPWD:(服务站重置密码).
     * 2016年8月11日 上午9:58:23
     * @author Liuzibing
     * @param resetPWDForm
     * @return
     * @throws Exception
     */
    public int resetPWD(ResetPWDForm resetPWDForm) throws Exception
    {
      int key =accountMapper.resetPWD(resetPWDForm);
      if(key<1){
        throw new Exception("系统错误，重置密码失败");
      }
      return key;
    }
    
    /**
     * mobileAlive:(查看手机账号是否存在).
     * 2016年8月11日 上午9:59:10
     * @author Liuzibing
     * @param mobile
     * @return
     * @throws Exception
     */
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
     * forSMSCode:(这里用一句话描述这个方法的作用).
     * 2016年8月11日 上午10:00:27
     * @author Liuzibing
     * @param forgotPWDForm
     * @throws Exception
     */
    public void forSMSCode(ForgotPWDForm forgotPWDForm)throws Exception{
      String mobile = forgotPWDForm.getForgot_mobile().toString();
      SMSCodeForm smsCodeForm = new SMSCodeForm();
      smsCodeForm.setMobile(mobile);
      smsCodeForm.setTemplate(SMSTemplateEnum.SMS_CODE);
      smsCodeService.sendSMSCode(smsCodeForm);
    }
    
    /**
     * checkSMSCode:(验证短信验证码是否正确).
     * 2016年8月11日 上午10:02:47
     * @author Liuzibing
     * @param forgotPWDForm
     * @throws Exception
     */
    public void checkSMSCode(ForgotPWDForm forgotPWDForm)throws SYSException{
      String mobile = forgotPWDForm.getForgot_mobile().toString();
      String smscode = forgotPWDForm.getVercode();
      SMSCodeForm smsCodeForm = new SMSCodeForm();
      smsCodeForm.setMobile(mobile);
      smsCodeForm.setSmsCode(smscode);
      smsCodeForm.setTemplate(SMSTemplateEnum.SMS_CODE);
      smsCodeService.verifySMSCode(smsCodeForm);
    }
    
    /**
     * forgotPWD:(忘记密码/找回密码/设置新密码).
     * 2016年8月11日 上午10:04:08
     * @author Liuzibing
     * @param forgotPWDForm
     * @return
     * @throws Exception
     */
    public int forgotPWD(ForgotPWDForm forgotPWDForm)throws Exception{
      int key = accountMapper.forgotPWD(forgotPWDForm);
      if(key<1){
        throw new Exception("系统错误，设置新密码失败");
      }
      return key;
    }
}
