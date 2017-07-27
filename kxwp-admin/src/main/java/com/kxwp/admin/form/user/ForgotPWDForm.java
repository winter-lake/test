package com.kxwp.admin.form.user;

public class ForgotPWDForm {
    
  private Long forgot_mobile;
  
  private String new_password;
  
  private String vercode;

  /**
   * @return  forgot_mobile
  
   */
  public Long getForgot_mobile() {
    return forgot_mobile;
  }

  public void setForgot_mobile(Long forgot_mobile) {
    this.forgot_mobile = forgot_mobile;
  }

  /**
   * @return  new_password
  
   */
  public String getNew_password() {
    return new_password;
  }

  public void setNew_password(String new_password) {
    this.new_password = new_password;
  }

  /**
   * @return  vercode
   */
  public String getVercode() {
    return vercode;
  }

  public void setVercode(String vercode) {
    this.vercode = vercode;
  }
  
  
}
