package com.kxwp.admin.model.migrate;

/**
 * Date: 2016年8月31日 上午9:07:40
 * 证件相关图片迁移
 * @author lou jian wen
 */
public class MigrateZJPhoto {


  @Override
  public String toString() {
    return "MigrateZJPhoto [licensePicPath=" + licensePicPath + ", photo_belong=" + photo_belong
        + ", identityPicPath=" + identityPicPath + ", no=" + no + "]";
  }


  //营业执照
  private String licensePicPath;
  
  

  //图片所属对象
  //gys/cs
  private String photo_belong;
  
  public String getPhoto_belong() {
    return photo_belong;
  }

  public void setPhoto_belong(String photo_belong) {
    this.photo_belong = photo_belong;
  }


  //身份证
  private String identityPicPath;

   //供应商/超市编号
  private String no;

  public String getLicensePicPath() {
    return licensePicPath;
  }

  public void setLicensePicPath(String licensePicPath) {
    this.licensePicPath = licensePicPath;
  }

  public String getIdentityPicPath() {
    return identityPicPath;
  }

  public void setIdentityPicPath(String identityPicPath) {
    this.identityPicPath = identityPicPath;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }


}

