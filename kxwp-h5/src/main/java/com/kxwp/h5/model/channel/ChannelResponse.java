package com.kxwp.h5.model.channel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 频道 Response
 * date: 2016年9月22日 下午5:08:20 
 *
 * @author wangjun
 */
public class ChannelResponse {
  private BigDecimal promotionPrice;
  
  private List<String> photoList;
  
  private String goodsName;
  
  private String packageSpecific;
  
  private BigDecimal salePrice;
  
  private String supplierName;
  
  private Long goodsId;
  
  private Long supplierId;
  /**
   * @return  promotionPrice
  
   */
  public BigDecimal getPromotionPrice() {
    return promotionPrice;
  }

  public void setPromotionPrice(BigDecimal promotionPrice) {
    this.promotionPrice = promotionPrice;
  }

  /**
   * @return  photoList
  
   */
  public List<String> getPhotoList() {
    return photoList;
  }

  public void setPhotoList(List<String> photoList) {
    this.photoList = photoList;
  }

  /**
   * @return  goodsName
  
   */
  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  /**
   * @return  packageSpecific
  
   */
  public String getPackageSpecific() {
    return packageSpecific;
  }

  public void setPackageSpecific(String packageSpecific) {
    this.packageSpecific = packageSpecific;
  }

  /**
   * @return  salePrice
  
   */
  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  /**
   * @return  supplierName
  
   */
  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  /**
   * @return  goodsId
  
   */
  public Long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Long goodsId) {
    this.goodsId = goodsId;
  }

  /**
   * @return  supplierId
  
   */
  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }
}
