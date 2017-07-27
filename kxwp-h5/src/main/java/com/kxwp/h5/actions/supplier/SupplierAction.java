package com.kxwp.h5.actions.supplier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.constants.OrganizationStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.query.supplier.SearchSupplierQuery;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.common.ErrorPage;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * 供应商相关接口 Date: 2016年8月17日 上午10:00:42
 * 
 * @author lou jian wen
 */
@Controller
@RequestMapping("/h5/supplier")
public class SupplierAction {



  @Autowired
  private SupplierRepoService supplierRepoService;

  @Autowired
  private GoodsRepoService goodsRepoService;
  
  
  @RequestMapping(value = "/searchSupplierProxy.do")
  public String searchSupplierProxy(HttpServletRequest request,
      HttpServletResponse response) {
    return "gys/gys_search";
  }


  /**
   * 
   * searchSupplier:(供应商搜索).
   *
   * 2016年8月17日 下午1:48:08
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/searchSupplier.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<Supplier>> searchSupplier(
      @RequestBody SearchSupplierQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<Supplier>> data = new ExchangeData<>();
    try {
      query.setService_station_id(
          H5UserSessionUtils.getUserSession(request).getService_station_id());
      //只搜索未下架的供应商
      query.setOrganizationStatus(OrganizationStatusEnum.VALID);
      List<Supplier> searchedResults = supplierRepoService.searchSupplier(query);
      data.setData(searchedResults);
    } catch (Exception e) {
      data.markException(e);
    }
    return data;
  }


  /**
   * 
   * getSupplierRecommendGoods:(供应商推荐商品).
   *
   * 2016年8月23日 下午3:24:55
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/getRecommendGoods.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<Goods>> getRecommendGoods(
      @RequestBody SearchSupplierQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<Goods>> data = new ExchangeData<>();
    try {
      List<Goods> searchedResults = goodsRepoService.getRecommendGoods(query.getId());
      data.setData(searchedResults);
    } catch (Exception e) {
      data.markException(e);
    }
    return data;
  }


  /**
   * 
   * getSupplierDetail:(获取供应商明细).
   *
   * 2016年8月17日 下午1:48:03
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/supplierDetail.do")
  public String getSupplierDetail(@RequestParam Long supplierID, HttpServletRequest request,
      HttpServletResponse response) {
    try {
      Supplier supplier_detail = supplierRepoService.getSupplierByID(supplierID);
      if (supplier_detail.getServiceStationId().longValue() != H5UserSessionUtils
          .getUserSession(request).getService_station_id()) {
        return ErrorPage.PAGE_403;
      }
      request.setAttribute("supplierDetail",supplier_detail);
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(),"getSupplierDetail exception", e);
      return ErrorPage.PAGE_500;
    }
    return "gys/gys_detail";
  }


}

