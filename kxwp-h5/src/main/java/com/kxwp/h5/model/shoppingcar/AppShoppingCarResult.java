package com.kxwp.h5.model.shoppingcar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kxwp.common.constants.CheckEnum;
import com.kxwp.common.query.shoppingcar.SupplierShoppingCarResult;

public class AppShoppingCarResult {
  
  private String platFormMessage;
  //返回不满足的信息
  private String message;
  
  // 购物车中超市商品是否全被选中 0未选中 1全选
  private Long allCheck;
  // 超市名称
  private String supermarketName;
  // 超市详细地址
  private String full_address;
  
  private List<SupplierShoppingCarResult> shoppingcarResult = new ArrayList<SupplierShoppingCarResult>();
  // 超市总共商品的价格
  private BigDecimal totalAmount;
  // 大订单商品的种类
  private Long totalGoodsCount;
  
  //默认总数给0   便于清空进货单时  返回null  前端好解决
  //大订单商品的总数量
  private Long totalGoodsQit = 0L;
  // 是否可以结算信息
  private CheckEnum platFormstandard;
  
  private BigDecimal minPlatformPrice;

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getTotalGoodsCount() {
    return totalGoodsCount;
  }

  public void setTotalGoodsCount(Long totalGoodsCount) {
    this.totalGoodsCount = totalGoodsCount;
  }

  public String getFull_address() {
    return full_address;
  }

  public void setFull_address(String full_address) {
    this.full_address = full_address;
  }

  public String getSupermarketName() {
    return supermarketName;
  }

  public void setSupermarketName(String supermarketName) {
    this.supermarketName = supermarketName;
  }

  public List<SupplierShoppingCarResult> getShoppingcarResult() {
    return shoppingcarResult;
  }

  public void setShoppingcarResult(List<SupplierShoppingCarResult> shoppingcarResult) {
    this.shoppingcarResult = shoppingcarResult;
  }

  public CheckEnum getPlatFormstandard() {
    return platFormstandard;
  }

  public void setPlatFormstandard(CheckEnum platFormstandard) {
    this.platFormstandard = platFormstandard;
  }

  public Long getTotalGoodsQit() {
    return totalGoodsQit;
  }

  public void setTotalGoodsQit(Long totalGoodsQit) {
    this.totalGoodsQit = totalGoodsQit;
  }

  public Long getAllCheck() {
    return allCheck;
  }

  public void setAllCheck(Long allCheck) {
    this.allCheck = allCheck;
  }

  public BigDecimal getMinPlatformPrice() {
    return minPlatformPrice;
  }

  public void setMinPlatformPrice(BigDecimal minPlatformPrice) {
    this.minPlatformPrice = minPlatformPrice;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPlatFormMessage() {
    return platFormMessage;
  }

  public void setPlatFormMessage(String platFormMessage) {
    this.platFormMessage = platFormMessage;
  }





}
