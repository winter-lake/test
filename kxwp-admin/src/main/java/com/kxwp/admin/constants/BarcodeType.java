package com.kxwp.admin.constants;

/**
 * Date: 2016年9月9日 下午3:23:53
 * 
 * @author lou jian wen
 */
public enum BarcodeType {


  FHD(105.82, 20.29, "发货单");

  private BarcodeType(double width, double height, String desc) {
    this.width = width;
    this.height = height;
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }



  private String desc;

  //mm
  private double width;

  //mm
  private double height;
}

