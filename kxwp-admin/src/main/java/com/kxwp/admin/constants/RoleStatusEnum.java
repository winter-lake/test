package com.kxwp.admin.constants;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kxwp.common.model.exception.EnumNotFoundException;

/**
 * 
 * date: 2016年7月26日 下午7:16:15 
 *
 * @author wangjun
 */
public enum RoleStatusEnum {



  VALID(1, "有效"),INVALID(2,"无效");
  
  private int code;

  private String desc;

  private RoleStatusEnum(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final Map<Integer, RoleStatusEnum> BY_VALUE_MAP = new LinkedHashMap<>();

  static {
    for (RoleStatusEnum enumValue : RoleStatusEnum.values()) {
      BY_VALUE_MAP.put(enumValue.getCode(), enumValue);
    }
  }

  public static RoleStatusEnum convertCode2Enum(int code) throws EnumNotFoundException {
    if (!BY_VALUE_MAP.containsKey(code)) {
      throw new EnumNotFoundException("枚举对应的code" + code + "不存在");
    }
    return BY_VALUE_MAP.get(code);
  }

  public static RoleStatusEnum convertStr2Enum(String str) throws EnumNotFoundException {
    try {
      return RoleStatusEnum.valueOf(str);
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

