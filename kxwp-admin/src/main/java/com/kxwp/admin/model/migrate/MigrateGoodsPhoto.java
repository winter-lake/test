package com.kxwp.admin.model.migrate;

import java.util.List;

/**
 * Date:     2016年8月31日 上午9:07:40 
 * @author   lou jian wen 
 */
public class MigrateGoodsPhoto {

  @Override
  public String toString() {
    return "MigrateGoodsPhoto [goods_no=" + goods_no + ", mainPhotoURL=" + mainPhotoURL
        + ", detailPhotoList=" + detailPhotoList + "]";
  }
  
  //商品编号
  private String goods_no;
 


  //主图
  private String mainPhotoURL;
  
  //详细图
  private List<String> detailPhotoList;
  
  public String getGoods_no() {
    return goods_no;
  }

  public void setGoods_no(String goods_no) {
    this.goods_no = goods_no;
  }
  
  public String getMainPhotoURL() {
    return mainPhotoURL;
  }

  public void setMainPhotoURL(String mainPhotoURL) {
    this.mainPhotoURL = mainPhotoURL;
  }

  public List<String> getDetailPhotoList() {
    return detailPhotoList;
  }

  public void setDetailPhotoList(List<String> detailPhotoList) {
    this.detailPhotoList = detailPhotoList;
  }

 
}

