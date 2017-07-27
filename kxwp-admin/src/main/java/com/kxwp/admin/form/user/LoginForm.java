package com.kxwp.admin.form.user;

import java.math.BigInteger;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;

import com.kxwp.common.model.exception.SYSException;

/**
 * Date: 2016年7月29日 下午4:12:41
 * 
 * @author lou jian wen
 */
public class LoginForm {

  
  @Digits(integer=11,message="请输入正确的手机号",fraction=0)
  private BigInteger login_mobile;

  @NotBlank(message="登录密码不允许为空")
  private String login_password;

  // 验证码
  private String login_securityCode;



  /**
   * @return login_password
   * @throws SYSException 
   * 
   */
  public String getLogin_password() throws SYSException {
    
    return login_password;
  }

  public void setLogin_password(String login_password) {
    this.login_password = login_password;
  }


  public BigInteger getLogin_mobile() {
    return login_mobile;
  }

  public void setLogin_mobile(BigInteger login_mobile) {
    this.login_mobile = login_mobile;
  }

  public String getLogin_securityCode() {
    return login_securityCode;
  }

  public void setLogin_securityCode(String login_securityCode) {
    this.login_securityCode = login_securityCode;
  }


}

