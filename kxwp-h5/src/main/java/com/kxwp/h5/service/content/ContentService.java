package com.kxwp.h5.service.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.common.constants.AdLocationEnum;
import com.kxwp.common.constants.AdTypeEnum;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.constants.OSSImgStyleEnum;
import com.kxwp.common.entity.ADConfigELocationResponse;
import com.kxwp.common.entity.ADConfigResponse;
import com.kxwp.common.entity.supplier.BusinessScope;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.query.ad.ADQuery;
import com.kxwp.common.service.ad.ADConfigService;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.h5.model.home.HomeItem;
import com.kxwp.h5.model.home.HomePageContent;
import com.kxwp.h5.model.home.HomeSupplier;

/**
 * Date: 2016年8月27日 下午1:59:52
 * 
 * @author lou jian wen
 */
@Service("ContentService")
public class ContentService {

  @Autowired
  private ADConfigService adConfigService;
  
  @Autowired
  private AbstractCacheService cacheService;


  @Autowired
  private GoodsRepoService goodsRepoService;

  @Autowired
  private SupplierRepoService supplierRepoService;
  


  String formatBusinessScope(List<BusinessScope> businessScopeList) {
    StringBuilder business = new StringBuilder();
    for (BusinessScope businessScope : businessScopeList) {
      business.append(businessScope.getCategory_name()).append(",");
    }
    return business.toString();
  }

  
  
  /**
   * 
   * getHomeGoods:(首页推荐商品).
   *
   * 2016年8月29日 上午10:43:12
   * @author lou jian wen
   * @param adQuery
   * @return
   * @throws KXWPException
   */
  public List<HomeItem> getHomeGoods(ADQuery adQuery) throws KXWPException{
    List<HomeItem> itemList = cacheService.getValue(CachekeyPrefix.H5_HOME_GOODS + adQuery);
    if(itemList != null && itemList.size() > 0){
      return itemList;
    }
    List<ADConfigELocationResponse> goodsList =   adConfigService.findELocationInfo(adQuery);
    itemList = new ArrayList<>();
    for(ADConfigELocationResponse adConfigELocationResponse:goodsList){
      HomeItem item = new HomeItem();
      item.setId(adConfigELocationResponse.getRecommendedId());
      item.setOpen_url("/h5/goods/goodsDetail.do?id=" + item.getId());
      Goods goods = goodsRepoService.getGoodsWithThumbnail(adConfigELocationResponse.getRecommendedId(),OSSImgStyleEnum.goods_216_216);
      if(goods != null && goods.getPhotoList() != null &&  goods.getPhotoList().size() > 0){
        item.setPhoto_url(goods.getPhotoList().get(0));
      }
      item.setTitle(adConfigELocationResponse.getGoodsName());
      item.setSub_title(adConfigELocationResponse.getSalePrice().toString());
      itemList.add(item);
    }
    if(itemList != null && itemList.size() > 0){
      cacheService.putKey(CachekeyPrefix.H5_HOME_GOODS + adQuery, itemList,AbstractCacheService.TENMINUTES);
    }
    return itemList;
  }


  public HomePageContent getHomeContent(APPUserModel appUserModel) throws KXWPException, IOException {
    HomePageContent pageContent = cacheService.getValue(CachekeyPrefix.H5_HOME_CONTENT + appUserModel.getService_station_id());
    if(pageContent != null){
      return pageContent;
    }
    pageContent = new HomePageContent();
    
    // banner
    ADQuery adQuery = new ADQuery();
    adQuery.setServiceStationId(appUserModel.getService_station_id());
    adQuery.setAdLocation(AdLocationEnum.A);
    List<ADConfigResponse> adConfigList = adConfigService.findMixture(adQuery);
    
    List<HomeItem> bannerList = new ArrayList<>();
    for (ADConfigResponse adConfigResponse : adConfigList) {
      HomeItem banner_item = new HomeItem();
      banner_item.setId(adConfigResponse.getRecommendedId());
      if (adConfigResponse.getAdType() == AdTypeEnum.GOODS) {
        banner_item.setOpen_url(
            "/h5/goods/goodsDetail.do?id=" + banner_item.getId());
        banner_item.setPhoto_url(adConfigResponse.getPhoto());
      }

      if (adConfigResponse.getAdType() == AdTypeEnum.BRAND) {
        // FIXME 搜索列表页面
        banner_item.setOpen_url("/h5/goods/searchGoodsProxy.do?brand_id_list=" + banner_item.getId());
        banner_item.setPhoto_url(adConfigResponse.getPhoto());
      }

      // 供应商
      if (adConfigResponse.getAdType() == AdTypeEnum.SUPPLIER) {
        banner_item.setOpen_url("/h5/supplier/supplierDetail.do?supplierID="
            + banner_item.getId());
        banner_item.setPhoto_url(adConfigResponse.getPhoto());
      }
      
      // URL
      if (adConfigResponse.getAdType() == AdTypeEnum.URL) {
        banner_item.setOpen_url(adConfigResponse.getUrl());
        banner_item.setPhoto_url(adConfigResponse.getPhoto());
      }
      
      // 图片
      if (adConfigResponse.getAdType() == AdTypeEnum.PICTURE) {
        banner_item.setOpen_url(adConfigResponse.getUrl());
        banner_item.setPhoto_url(adConfigResponse.getPhoto());
      }

      bannerList.add(banner_item);
    }
    pageContent.setBannerList(bannerList);
    // 分类
    adQuery.setAdLocation(AdLocationEnum.B);
    adConfigList = adConfigService.findMixture(adQuery);
    List<HomeItem> categoryList = new ArrayList<>();
    for (ADConfigResponse adConfigResponse : adConfigList) {
      HomeItem category_item = new HomeItem();
      category_item.setId(adConfigResponse.getRecommendedId());
      category_item.setOpen_url(
          "/h5/goods/searchGoodsProxy.do?category_id_list=" + category_item.getId());
      categoryList.add(category_item);
    }
    pageContent.setCategoryList(categoryList);

    // 品牌
    adQuery.setAdLocation(AdLocationEnum.C);
    adConfigList = adConfigService.findMixture(adQuery);
    List<HomeItem> brandList = new ArrayList<>();
    for (ADConfigResponse adConfigResponse : adConfigList) {
      HomeItem brand_item = new HomeItem();
      brand_item.setId(adConfigResponse.getRecommendedId());
      brand_item.setOpen_url(
          "/h5/goods/searchGoodsProxy.do?brand_id_list=" + brand_item.getId());
      brand_item.setPhoto_url(adConfigResponse.getPhoto());
      brandList.add(brand_item);
    }
    pageContent.setBrandList(brandList);

    // 供应商
    adQuery.setAdLocation(AdLocationEnum.D);
    adConfigList = adConfigService.findMixture(adQuery);
    List<HomeSupplier> homeSupplierList = new ArrayList<>();
    for (ADConfigResponse adConfigResponse : adConfigList) {
      HomeSupplier homeSupplier = new HomeSupplier();
      HomeItem supplier_detail = new HomeItem();
      Supplier supplier = supplierRepoService.getSupplierByID(adConfigResponse.getRecommendedId());
      supplier_detail.setId(adConfigResponse.getRecommendedId());
      supplier_detail.setTitle(supplier.getSupplierName());
      supplier_detail.setOpen_url(
          "/h5/supplier/supplierDetail.do?supplierID=" + supplier.getId());
      supplier_detail.setSub_title(formatBusinessScope(supplier.getBusinessScopeList()));
      homeSupplier.setSupplier_detail(supplier_detail);
      // 推荐商品
      List<Goods> goodsList = goodsRepoService.getRecommendGoods(supplier.getId());
      List<HomeItem> supplierRecommentGoods = new ArrayList<>();
      for (Goods goods : goodsList) {
        HomeItem recommend_goods = new HomeItem();
        recommend_goods.setTitle(goods.getSalePrice().toString());
        recommend_goods.setId(goods.getId());
        recommend_goods
            .setOpen_url("/h5/goods/goodsDetail.do?id=" + goods.getId());
        if(goods.getPhotoList() != null && goods.getPhotoList().size() > 0){
          recommend_goods.setPhoto_url(goods.getPhotoList().get(0));
        }
        supplierRecommentGoods.add(recommend_goods);
      }
      homeSupplier.setRecommendGoodsList(supplierRecommentGoods);
      homeSupplierList.add(homeSupplier);
    }
    pageContent.setSupplierList(homeSupplierList);
    cacheService.putKey(CachekeyPrefix.H5_HOME_CONTENT + appUserModel.getService_station_id(), pageContent,AbstractCacheService.TENMINUTES);
    return pageContent;
  }
}

