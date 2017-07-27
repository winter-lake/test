package com.kxwp.admin.form.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Date:     2016年8月4日 上午9:57:27 
 * @author   lou jian wen 
 */
public class ResetPWDForm {

  
  private Long reset_mobile;
  
  @NotBlank(message="密码不允许设置为空")
//@Length(min=8,max=16,message="密码长度不能少于8位")
//@Pattern(regexp="/^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,10}$/",message="密码必须包含字符和数字")
  private String new_password;

  /**
   * @return  reset_mobile
   */
  public Long getReset_mobile() {
    return reset_mobile;
  }

  public void setReset_mobile(Long reset_mobile) {
    this.reset_mobile = reset_mobile;
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
}

