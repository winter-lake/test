package com.kxwp.admin.constants;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kxwp.common.model.exception.EnumNotFoundException;


/**
 * date: 2016年8月1日 下午3:06:09 
 *
 * @author Liuzibing
 */
public enum DelAllowedStatusEnum {

  N(1, "禁止"), Y(1, "允许");

  private int code;

  private String desc;

  private DelAllowedStatusEnum(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final Map<Integer, DelAllowedStatusEnum> BY_VALUE_MAP = new LinkedHashMap<>();

  static {
    for (DelAllowedStatusEnum enumValue : DelAllowedStatusEnum.values()) {
      BY_VALUE_MAP.put(enumValue.getCode(), enumValue);
    }
  }

  public static DelAllowedStatusEnum convertCode2Enum(int code) throws EnumNotFoundException {
    if (!BY_VALUE_MAP.containsKey(code)) {
      throw new EnumNotFoundException("枚举对应的code" + code + "不存在");
    }
    return BY_VALUE_MAP.get(code);
  }

  public static DelAllowedStatusEnum convertStr2Enum(String str) throws EnumNotFoundException {
    try {
      return DelAllowedStatusEnum.valueOf(str);
    } catch (Exception e) {
      throw new EnumNotFoundException("枚举对应常量" + str + "不存在");
    }
  }

  public int getCode() {
    return code;
  }


  public void setCode(int code) {
    this.code = code;
  }


  public String getDesc() {
    return desc;
  }


  public void setDesc(String desc) {
    this.desc = desc;
  }



}

