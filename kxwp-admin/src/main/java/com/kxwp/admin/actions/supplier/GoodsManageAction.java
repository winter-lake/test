package com.kxwp.admin.actions.supplier;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.entity.supplier.SupplierAccount;
import com.kxwp.admin.form.gys.CreateGoodsForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.supplier.SupplierManageGoodsService;
import com.kxwp.admin.utils.session.GYSUserSessionUtils;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.ReviewTypeEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.model.exception.InvalidQueryException;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.supplier.EditGoodsDetail;
import com.kxwp.common.model.supplier.ShippingAreaData;
import com.kxwp.common.model.uc.LoginUserInfo;
import com.kxwp.common.query.goods.SearchGoodsRepoQuery;
import com.kxwp.common.query.goods.UpdateGoodsStatusQuery;
import com.kxwp.common.query.supplier.SupplierShippingAreaQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.query.sys.ReviewQuery;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.service.core.ReviewService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * Date: 2016年8月12日 下午6:31:50
 * 
 * @author lou jian wen
 */
@Controller
@RequestMapping("/gys/goods")
public class GoodsManageAction {

  @Autowired
  private OddNumberService oddNumberService;

  @Autowired
  private GoodsRepoService goodsRepoService;
  
  @Autowired
  private SupplierRepoService supplierRepoService;

  @RequestMapping(value = "/gotoAddGoods.do")
  public String gotoAddGoods(HttpServletRequest request, HttpServletResponse response) {
    UserModel userModel = GYSUserSessionUtils.getUserSession(request);
    request.setAttribute("user", userModel);
    OddNumberQuery query = new OddNumberQuery();
    query.setNumber_type(KXWPNumberRuleEnum.GOODS_NO);
    try {
      SupplierShippingAreaQuery shippingAreaQuery = new SupplierShippingAreaQuery();
      shippingAreaQuery.setSupplier_id(userModel.getOrganizationId());
      shippingAreaQuery.setService_station_id(userModel.getServiceStation().getId());
      ShippingAreaData shippingAreaData = supplierRepoService.getSupplierShippingAreaData(shippingAreaQuery);
      request.setAttribute("shipping_area_data", shippingAreaData);
      request.setAttribute("goodsNo", oddNumberService.newOddNumberViaProcedure(query));
    } catch (SYSException | InvalidQueryException e) {
      return ErrorPage.PAGE_500;
    }
    return "gys/gys.add_goods";
  }


  /**
   * 
   * updateGoodsStatus:(服务站更新商品状态,审核,下架).
   *
   * 2016年8月19日 上午10:11:58
   * 
   * @author lou jian wen
   * @param goodsID
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/updateGoodsStatus.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData updateGoodsStatus(
      @RequestBody UpdateGoodsStatusQuery updateGoodsStatusQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData ed = new ExchangeData<>();
    try {

      Goods goods_detail = goodsRepoService.getGoodsByID(updateGoodsStatusQuery.getId());
      if (goods_detail.getSupplierId().longValue() != GYSUserSessionUtils.getUserSession(request).getOrganizationId()) {
        ed.mark403();
      } else {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtilsBean2.getInstance().copyProperties(loginUserInfo, GYSUserSessionUtils.getUserSession(request));
        updateGoodsStatusQuery.setLoginUserInfo(loginUserInfo);
        goodsRepoService.updateGoodsStatus(updateGoodsStatusQuery);
      }
    } catch (KXWPNotFoundException | SYSException e) {
      ed.markException(e.getMessage(), e);
    } catch (Exception e) {
      ed.markException(e);
    }

    return ed;
  }


  @Autowired
  private ReviewService reviewService;
  /**
   * 
   * getGoodsDetail:(供应商显示商品详情).
   *
   * 2016年8月14日 下午5:54:49
   * 
   * @author janwen
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/gotoGoodsDetail.do")
  public String gotoGoodsDetail(@RequestParam Long goodsID, HttpServletRequest request,
      HttpServletResponse response) {
    Goods goods_detail;
    try {
      goods_detail = goodsRepoService.getGoodsByID(goodsID);
      request.setAttribute("goods_detail", goods_detail);
      ReviewQuery reviewQuery = new ReviewQuery();
      reviewQuery.setReviewId(goodsID);
      reviewQuery.setReviewType(ReviewTypeEnum.Goods);

      request.setAttribute("reviews", reviewService.queryReview(reviewQuery));
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e);
      return ErrorPage.PAGE_500;
    }
    return "gys/gys.goods_detail";
  }
  
  @RequestMapping(value = "/gotoEditGoods.do")
  public String gotoEditGoods(@RequestParam Long goodsID, HttpServletRequest request,
      HttpServletResponse response) {
    EditGoodsDetail goods_detail;
    try {
      goods_detail = goodsRepoService.getEditGoodsDetail(goodsID);
      request.setAttribute("goods_detail", goods_detail);
      SupplierShippingAreaQuery shippingAreaQuery = new SupplierShippingAreaQuery();
      shippingAreaQuery.setService_station_id(GYSUserSessionUtils.getUserSession(request).getServiceStation().getId());
      shippingAreaQuery.setGoods_id(goods_detail.getId());
      ShippingAreaData shippingAreaData = supplierRepoService.getSupplierShippingAreaData(shippingAreaQuery);
      request.setAttribute("shipping_area_data", shippingAreaData);
    } catch (KXWPNotFoundException | KXWPValidException | IllegalAccessException | InvocationTargetException | SYSException | IOException | InvalidQueryException e) {
      KXWPLogUtils.logException(this.getClass(), e);
      return ErrorPage.PAGE_500;
    }
    return "gys/gys.edit_goods";
  }

  /**
   * 
   * searchGoodsRepo:(商品库搜索).
   *
   * 2016年8月14日 上午10:58:03
   * 
   * @author janwen
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/searchGoodsRepo.do")
  public @ResponseBody ExchangeData<List<Goods>> searchGoodsRepo(SearchGoodsRepoQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Goods>> edb = new ExchangeData<>();
    query.setOrderBy("update_time desc");
    List<Goods> results = goodsRepoService.searchGoodsRepo(query);
    edb.setData(results);
    return edb;
  }

  @Autowired
  private SupplierManageGoodsService supplierManageGoodsServivce;

  /**
   * 
   * addGoods:(创建商品).
   *
   * 2016年8月14日 下午8:50:28
   * 
   * @author janwen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/addGoods.do")
  public @ResponseBody ExchangeData<List<Goods>> addGoods(@Valid @RequestBody CreateGoodsForm goodsForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Goods>> edb = new ExchangeData<>();
    UserModel user = GYSUserSessionUtils.getUserSession(request);
    try {
      supplierManageGoodsServivce.addGoods(goodsForm, user);
    } catch (Exception e) {
      edb.markException(e);

    }
    return edb;
  }
  
  
  @RequestMapping(value = "/editGoods.do")
  public @ResponseBody ExchangeData<List<Goods>> editGoods(@Valid @RequestBody CreateGoodsForm goodsForm,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Goods>> edb = new ExchangeData<>();
    UserModel user = GYSUserSessionUtils.getUserSession(request);
    try {
      supplierManageGoodsServivce.editGoods(goodsForm, user);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }


  @RequestMapping(value = "/listGoods.do")
  public @ResponseBody ExchangeData<List<Goods>> listGoods(@RequestBody SearchGoodsRepoQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Goods>> edb = new ExchangeData<>();
    try {
      UserModel user = GYSUserSessionUtils.getUserSession(request);
      query.setSupplierID(user.getOrganizationId());
      List<Goods> results = supplierManageGoodsServivce.listGoods(query);
      int total = supplierManageGoodsServivce.countSearchGoods(query);
      edb.getPager().setCurrentPage(query.getPager().getCurrentPage());
      edb.getPager().setTotoalResults(total);
      edb.setData(results);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }

  @RequestMapping(value = "/gotoListGoods.do")
  public String gotoListGoods(SupplierAccount supplierAccount) {

    return "gys/gys.list_goods";
  }
}
