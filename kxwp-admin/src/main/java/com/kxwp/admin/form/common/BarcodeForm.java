package com.kxwp.admin.form.common;

import com.kxwp.admin.constants.BarcodeType;

/**
 * janwen 2015年9月10日 上午11:18:37
 *
 **/

public class BarcodeForm {


  private String code;
  
  
  //发货单类型
  private BarcodeType barcodeType = BarcodeType.FHD;


  public BarcodeType getBarcodeType() {
    return barcodeType;
  }


  public void setBarcodeType(BarcodeType barcodeType) {
    this.barcodeType = barcodeType;
  }



  public String getCode() {
    return code;
  }


  public void setCode(String code) {
    this.code = code;
  }

}
