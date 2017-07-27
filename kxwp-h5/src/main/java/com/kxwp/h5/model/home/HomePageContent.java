package com.kxwp.h5.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Date:     2016年8月27日 下午1:52:33 
 * @author   lou jian wen
 * 整个首页的内容 
 */
public class HomePageContent implements Serializable {

  /**
   * serialVersionUID:TODO(用一句话描述这个变量表示什么).
   */
  private static final long serialVersionUID = -3036626706774484740L;

  //banner
  private List<HomeItem> bannerList;
  
  //分类
  private List<HomeItem> categoryList;
  
  //推荐品牌
  private List<HomeItem> brandList;
  
  //推荐供应商
  private List<HomeSupplier> supplierList;

  public List<HomeItem> getBannerList() {
    return bannerList;
  }

  public void setBannerList(List<HomeItem> bannerList) {
    this.bannerList = bannerList;
  }

  public List<HomeItem> getCategoryList() {
    return categoryList;
  }

  public void setCategoryList(List<HomeItem> categoryList) {
    this.categoryList = categoryList;
  }

  public List<HomeItem> getBrandList() {
    return brandList;
  }

  public void setBrandList(List<HomeItem> brandList) {
    this.brandList = brandList;
  }

  public List<HomeSupplier> getSupplierList() {
    return supplierList;
  }

  public void setSupplierList(List<HomeSupplier> supplierList) {
    this.supplierList = supplierList;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }
  
  
  
}

