package com.kxwp.h5.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Date:     2016年8月27日 下午1:57:30 
 * @author   lou jian wen 
 */
public class HomeSupplier implements Serializable {

  
  /**
   * serialVersionUID:TODO(用一句话描述这个变量表示什么).
   */
  private static final long serialVersionUID = -1524406183170361532L;

  private List<HomeItem> recommendGoodsList;
  
  private HomeItem supplier_detail;

  public List<HomeItem> getRecommendGoodsList() {
    return recommendGoodsList;
  }

  public void setRecommendGoodsList(List<HomeItem> recommendGoodsList) {
    this.recommendGoodsList = recommendGoodsList;
  }

  public HomeItem getSupplier_detail() {
    return supplier_detail;
  }

  public void setSupplier_detail(HomeItem supplier_detail) {
    this.supplier_detail = supplier_detail;
  }
}

