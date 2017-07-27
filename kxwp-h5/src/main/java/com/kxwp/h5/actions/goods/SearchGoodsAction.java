package com.kxwp.h5.actions.goods;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.constants.AdLocationEnum;
import com.kxwp.common.constants.OSSImgStyleEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.model.api.GoodsDetail;
import com.kxwp.common.model.api.SearchFilter;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.query.ad.ADQuery;
import com.kxwp.common.query.goods.SearchGoodsQuery;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.goods.SearchGoodsService;
import com.kxwp.h5.model.home.HomeItem;
import com.kxwp.h5.service.content.ContentService;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * Date: 2016年8月27日 下午5:49:10
 * 
 * @author lou jian wen
 */

@Controller
@RequestMapping("/h5/goods")
public class SearchGoodsAction {

  @Autowired
  private ContentService contentService;


  @RequestMapping(value = "/gotoSearchInput.do")
  public String gotoSearchInput(SearchGoodsQuery searchGoodsQuery, HttpServletRequest request,
      HttpServletResponse response) {
    return "goods/search_input";
  }

  /**
   * 
   * getHomeGoods:(首页底部推荐商品).
   *
   * 2016年8月27日 下午6:18:42
   * 
   * @author lou jian wen
   * @param adQuery
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/ajax/homeGoods.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      method = RequestMethod.POST)
  public @ResponseBody ExchangeData<List<HomeItem>> getHomeGoods(@RequestBody ADQuery adQuery,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<HomeItem>> edb = new ExchangeData<List<HomeItem>>();
    try {
      adQuery.setAdLocation(AdLocationEnum.E);
      adQuery
          .setServiceStationId(H5UserSessionUtils.getUserSession(request).getService_station_id());
      List<HomeItem> goods_list = contentService.getHomeGoods(adQuery);
      edb.setData(goods_list);
    } catch (KXWPException e) {
      edb.markFail(e.getMessage());
    } catch (Exception e) {
      edb.markException(e);
    }

    return edb;
  }



  @Autowired
  private SearchGoodsService searchGoodsService;

  @Autowired
  private GoodsRepoService goodsRepoService;



  /**
   * 
   * searchGoods:(商品搜索页面中转链接).
   *
   * 2016年8月29日 上午10:36:05
   * 
   * @author lou jian wen
   * @param searchGoodsQuery
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/searchGoodsProxy.do")
  public String searchGoodsProxy(SearchGoodsQuery searchGoodsQuery, HttpServletRequest request,
      HttpServletResponse response) {
    return "goods/search_goods_list";
  }


  @RequestMapping(value = "/ajax/searchGoods.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<Goods>> searchGoods(
      @RequestBody SearchGoodsQuery searchGoodsQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<Goods>> data = new ExchangeData<>();
    try {
      // 排序字段特殊处理
      searchGoodsQuery.setOrderBy(searchGoodsQuery.getRank_code().getOrder_by());
      searchGoodsQuery.setService_station_id(
          H5UserSessionUtils.getUserSession(request).getService_station_id());
      searchGoodsQuery.setOssImgStyleEnum(OSSImgStyleEnum.goods_300_300);
      searchGoodsQuery
          .setSupermarket_id(H5UserSessionUtils.getUserSession(request).getSupermarket_id());
      List<Goods> searchedGoods = goodsRepoService.searchGoods(searchGoodsQuery);
      data.setData(searchedGoods);
    } catch (KXWPNotFoundException e) {
      data.markException(e.getMessage(), e);
    } catch (Exception e) {
      data.markException(e);
    }
    return data;
  }

  @RequestMapping(value = "/goodsDetail.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public String getGoodsDetail(@RequestParam Long id, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<GoodsDetail> data = new ExchangeData<>();
    try {
      SearchGoodsQuery searchGoodsQuery = new SearchGoodsQuery();
      searchGoodsQuery.setOssImgStyleEnum(OSSImgStyleEnum.goods_detail_750_750);;
      searchGoodsQuery.setId(id);
      searchGoodsQuery.setSupermarket_id(H5UserSessionUtils.getUserSession(request).getSupermarket_id());
      GoodsDetail goods_detail = goodsRepoService.getGoodsDetail(searchGoodsQuery);
      // 判断商品是否属于当前超市的服务站
      if (goods_detail.getSupplier().getServiceStationId().longValue() != H5UserSessionUtils
          .getUserSession(request).getService_station_id().longValue()) {
        data.mark403();
      }
      request.setAttribute("goods_detail", goods_detail);
      data.setData(goods_detail);
    } catch (KXWPNotFoundException e) {
      data.markException(e.getMessage(), e);
    } catch (Exception e) {
      data.markException(e);
    }
    return "goods/goods_detail";
  }

  /**
   * 
   * getSearchFilter:(app搜索的过滤条件).
   *
   * 2016年8月22日 下午6:29:02
   * 
   * @author lou jian wen
   * @param searchGoodsQuery
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/ajax/getSearchFilter.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<SearchFilter> getSearchFilter(
      @RequestBody SearchGoodsQuery searchGoodsQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<SearchFilter> data = new ExchangeData<>();
    try {
      searchGoodsQuery.setService_station_id(
          H5UserSessionUtils.getUserSession(request).getService_station_id());
      SearchFilter goods_detail = searchGoodsService.getSearchFilter(searchGoodsQuery);
      data.setData(goods_detail);
    } catch (KXWPNotFoundException e) {
      data.markException(e.getMessage(), e);
    } catch (Exception e) {
      data.markException(e);
    }
    return data;
  }



}

