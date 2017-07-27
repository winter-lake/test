package com.kxwp.admin.constants;
/**
 * Date:     2016年7月20日 下午5:27:06 
 * @author   lou jian wen 
 */
public enum UserTypeEnum {

  A(1,"A"),
  B(2,"B");
  
  private UserTypeEnum(int code,String desc){
    this.code = code;
    this.desc = desc;
  }
  
  private int code;
  
  /**
   * @return  code
  
   */
  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  /**
   * @return  desc
  
   */
  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  private String desc;
  
 
}

