package com.kxwp.admin.constants;

/**
 * 《定义品牌管理枚举》
 * date: 2016年8月18日 下午3:52:16 
 * @author Liuzibing
 */
public enum BrandStatusEnum {
   ALL(1,"全部"),ONSALE(2, "上线"),OFFSALE(3,"下线");
  private int code;

  private String desc;

  private BrandStatusEnum(int code, String desc) {
    this.code = code;
    this.desc = desc;
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
