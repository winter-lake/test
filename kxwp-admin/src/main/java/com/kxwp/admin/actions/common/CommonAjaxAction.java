package com.kxwp.admin.actions.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.serviceStation.ServiceStationNameQuery;
import com.kxwp.admin.service.serviceStation.FWZCommonService;
import com.kxwp.admin.service.serviceStation.FWZLoginService;
import com.kxwp.admin.service.supermarket.SupermarketService;
import com.kxwp.admin.service.supplier.SupplierManageGoodsService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.CommonRequestAttr;
import com.kxwp.common.constants.GoodsStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.MSBrand;
import com.kxwp.common.entity.MSClassificaion;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.query.base.BaseQuery;
import com.kxwp.common.query.brand.SearchBrandRepoQuery;
import com.kxwp.common.query.goods.SearchGoodsRepoQuery;
import com.kxwp.common.query.supermarket.SupermarketQuery;
import com.kxwp.common.query.supplier.SearchSupplierQuery;
import com.kxwp.common.query.sys.ClassificaionQuery;
import com.kxwp.common.query.sys.RegionQuery;
import com.kxwp.common.service.brand.BrandRepoService;
import com.kxwp.common.service.core.ClassificaionService;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * date: 2016年8月13日 上午10:55:50 前台常用的AJAX 调用 里面只有ajax方法
 * 
 * @author zhaojn
 */
@Controller
@RequestMapping("/common/ajax")
public class CommonAjaxAction {

  @Autowired
  private BrandRepoService brandRepoService;
  @Autowired
  private SupermarketService supermarketservice;

  @Autowired
  private ClassificaionService classificaionService;

  @Autowired
  private FWZCommonService fwzCommonService;

  @Autowired
  private RegionService regionService;

  @Autowired
  private GoodsRepoService goodsRepoService;

  @Autowired
  private SupplierRepoService supplierRepoService;

  @Autowired
  private FWZLoginService fwzLoginService;

  @Autowired
  private SupplierManageGoodsService supplierManageGoodsServivce;



  /**
   * queryServiceStationName:(模糊查询(服务站名称)).
   *
   * 2016年8月13日 上午10:57:15
   * 
   * @author zhaojn
   * @param serviceStationNameQuery
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/gys/querySupplier.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = {RequestMethod.POST})
  public @ResponseBody ExchangeData queryServiceStationName(@RequestBody SearchSupplierQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Supplier>> edb = new ExchangeData<>();
    try {
      String host = (String) request.getAttribute(CommonRequestAttr.ATTR_ACCESS_HOST);
      // 服务站只允许查看当前服务站对应供应商
      if (UserModel.isFWZ(host)) {
        UserModel um = fwzLoginService.getFWZLoginUser(request);
        query.setService_station_id(um.getOrganizationId());
      }

      List<Supplier> data = supplierRepoService.searchSupplier(query);
      edb.setData(data);
    } catch (Exception e) {
      KXWPLogUtils.logException(CommonAjaxAction.class, "/gys/querySupplier.do exception", e);
      edb.markException(e);
    }
    return edb;
  }



  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/fwz/queryServiceStationName.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = {RequestMethod.POST})
  public @ResponseBody ExchangeData queryServiceStationName(
      @RequestBody ServiceStationNameQuery serviceStationNameQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<ServiceStation>> edb = new ExchangeData<>();
    try {
      List<ServiceStation> service_station =
          fwzCommonService.searchLikeServiceStationName(serviceStationNameQuery.getStationName());
      edb.setData(service_station);
    } catch (Exception e) {
      KXWPLogUtils.logException(CommonAjaxAction.class, "serviceStationName throws :", e);
      edb.markException("系统出现异常", e);
    }
    return edb;
  }



  /**
   * 地址区域级联
   * 
   * @param regionQuery
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/region/queryRegion.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryRegion(@Valid @RequestBody RegionQuery regionQuery,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      List<SYSRegion> regionList = regionService.queryCascadeRegion(regionQuery);
      edb.setData(regionList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }



  /**
   * 查询商品分类
   * 
   * @param regionQuery
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/goods/queryGoodsCategory.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryGoodsCategory(@Valid @RequestBody ClassificaionQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      List<MSClassificaion> resultsList = classificaionService.queryCascadeClassificaion(query);
      edb.setData(resultsList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

  @SuppressWarnings({"rawtypes"})
  @RequestMapping(value = "/brand/queryBrand.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryBrand(@Valid @RequestBody BaseQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

  /**
   * queryClassificaion:(查询分类信息).
   *
   * 2016年8月15日 下午4:16:57
   * 
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/classificaion/queryClassificaion.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryClassificaion(@Valid @RequestBody ClassificaionQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    List<MSClassificaion> classficaionList = classificaionService.queryCascadeClassificaion(query);
    try {
      edb.setData(classficaionList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }



  /**
   * 
   * queryBrandRepo:(搜索品牌仓库).
   *
   * 2016年8月15日 下午6:18:52
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/brand/queryBrandRepo.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryBrandRepo(@RequestBody SearchBrandRepoQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      List<MSBrand> brandList = brandRepoService.searchBrandRepo(query);
      edb.setData(brandList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/goods/queryGoodsRepo.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryGoodsRepo(SearchGoodsRepoQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      List<Goods> goodsList = goodsRepoService.searchGoodsRepo(query);
      edb.setData(goodsList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }
  
  @RequestMapping(value = "/goods/queryGoodsRepoByCondition.do")
  public @ResponseBody ExchangeData<List<Goods>> queryGoodsRepoByCondition(SearchGoodsRepoQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Goods>> edb = new ExchangeData<>();
    try {
      UserModel user = FWZUserSessionUtils.getUserSession(request);
      query.setOrderBy("update_time desc");
      query.setServiceStationID(user.getOrganizationId());
      query.setGoods_statusEnum(GoodsStatusEnum.ONSALE);
      List<Goods> results = supplierManageGoodsServivce.listGoods(query);
      int total = supplierManageGoodsServivce.countSearchGoods(query);
      edb.getPager().setTotoalResults(total);
      edb.setData(results);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/supermarket/querySupermarketRepo.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData querySupermarketRepo(@RequestBody SupermarketQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      // 不允许查看其它服务站超市
      query.setServiceStationId(FWZUserSessionUtils.getUserSession(request).getOrganizationId());
      List<Supermarket> supermarkList = supermarketservice.searchByQuery(query);
      edb.setData(supermarkList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

}
