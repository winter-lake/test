package com.kxwp.h5.model.common;

import java.io.Serializable;


/**
 * 通用枚举类
 * date: 2016年7月29日 下午2:08:40 
 *
 * @author lou jian wen
 */
public class KXWPEnumProperty implements Serializable {
  
  
  private static final long serialVersionUID = -1919786341589579440L;

  private int code;
  
  private String name;
  
  private String desc;

  public KXWPEnumProperty() {
    
  }
  public KXWPEnumProperty(int code, String name, String desc) {
    
    this.code = code;
    this.name = name;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
