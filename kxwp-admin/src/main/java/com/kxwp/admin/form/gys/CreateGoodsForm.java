package com.kxwp.admin.form.gys;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.GoodsCategory;
import com.kxwp.common.entity.supplier.GoodsLotPrice;

public class CreateGoodsForm {

	
	//商品基本信息
	private Goods goodsInfo;
	
	//商品分类信息
	@NotNull(message="请选择商品分类")
	private List<GoodsCategory> goodsCategoryList;
	
	
	//阶梯价格
	private List<GoodsLotPrice> goodsLotPriceList;
	
	//配送范围
	private List<Long> goodsShippingAreaIDList;

	public Goods getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(Goods goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public List<GoodsCategory> getGoodsCategoryList() {
		return goodsCategoryList;
	}

	public void setGoodsCategoryList(List<GoodsCategory> goodsCategoryList) {
		this.goodsCategoryList = goodsCategoryList;
	}


  public List<GoodsLotPrice> getGoodsLotPriceList() {
    return goodsLotPriceList;
  }

  public void setGoodsLotPriceList(List<GoodsLotPrice> goodsLotPriceList) {
    this.goodsLotPriceList = goodsLotPriceList;
  }

  public List<Long> getGoodsShippingAreaIDList() {
    return goodsShippingAreaIDList;
  }

  public void setGoodsShippingAreaIDList(List<Long> goodsShippingAreaIDList) {
    this.goodsShippingAreaIDList = goodsShippingAreaIDList;
  }
	
	
}
