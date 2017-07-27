package com.kxwp.admin.actions.serviceStation;

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

import com.kxwp.admin.form.fwz.CreateGysForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.serviceStation.FWZCommonService;
import com.kxwp.admin.service.supplier.GYSManagerService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.MSClassificaion;
import com.kxwp.common.entity.supplier.GoodsShippingArea;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.supplier.ShippingAreaData;
import com.kxwp.common.query.supplier.SupplierManagerQuery;
import com.kxwp.common.query.supplier.SupplierShippingAreaQuery;
import com.kxwp.common.query.sys.ClassificaionQuery;
import com.kxwp.common.service.core.ClassificaionService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * Date: 2016年8月8日 下午8:29:26
 * 
 * @author zhaojn 服务站对供应商的管理
 */
@Controller
@RequestMapping("/fwz/manager/gys")
public class FWZManageGYSAction {

  @Autowired
  private GYSManagerService gysManagerService;

  @Autowired
  private FWZCommonService fwzCommonService;


  @Autowired
  private SupplierRepoService supplierRepoService;

  @Autowired
  private ClassificaionService classificaionService;
  

  /**
   * querygys:(根据条件查询服务站列表).
   *
   * 2016年8月13日 下午3:57:22
   * 
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/ajax/querygys.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<Supplier>> querygys(
      @RequestBody SupplierManagerQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<Supplier>> edb = new ExchangeData<>();
    query.setServiceStationId(
        FWZUserSessionUtils.getUserSession(request).getServiceStation().getId());
    List<Supplier> dataList = gysManagerService.selectSupplierByQuery(query);
    int total = gysManagerService.countByQuery(query);
    edb.getPager().setCurrentPage(query.getPager().getCurrentPage());
    edb.getPager().setPageSize(query.getPager().getPageSize());
    edb.getPager().setTotoalResults(total);
    edb.setData(dataList);
    return edb;
  }

  /**
   * queryList:(跳转到服务站查询供应商列表页).
   *
   * 2016年8月15日 上午9:57:37
   * 
   * @author Jiangsl
   * @param request
   * @param response
   * @return
   * 
   */
  @RequestMapping(value = "/gysList.do", method = RequestMethod.GET)
  public String queryList(HttpServletRequest request, HttpServletResponse response) {
    return "fwz/fwz.gys-list";
  }

  /**
   * gotoAddGys:(跳转到添加供应商的页面并将服务站ID和服务站).
   *
   * 2016年8月15日 上午10:16:37
   * 
   * @author zhaojn
   * @param request
   * @param response
   * @param user
   * @return
   * 
   */
  @RequestMapping(value = "/gotoAddGys.do", method = RequestMethod.GET)
  public String gotoAddGys(HttpServletRequest request, HttpServletResponse response,
      UserModel user) {
    try {
      UserModel fwz_login_user = FWZUserSessionUtils.getUserSession(request);
      String serviceStationName = fwzCommonService
          .getServiceStationByID(fwz_login_user.getOrganizationId()).getServiceStationName();
      request.setAttribute("serviceStationName", serviceStationName);
      request.setAttribute("serviceStationId", fwz_login_user.getId());
      SupplierShippingAreaQuery shippingAreaQuery = new SupplierShippingAreaQuery();
      shippingAreaQuery.setService_station_id(fwz_login_user.getOrganizationId());
      ShippingAreaData shippingData =
          supplierRepoService.getSupplierShippingAreaData(shippingAreaQuery);
      request.setAttribute("shippingData", shippingData);
      ClassificaionQuery classificaionQuery = new ClassificaionQuery();
      classificaionQuery.setParentClassficationID(0L);
      List<MSClassificaion> fir_category_list =
          classificaionService.queryCascadeClassificaion(classificaionQuery);
      request.setAttribute("fir_category_list", fir_category_list);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), "gotoAddGys throws :", e);
      return ErrorPage.PAGE_500;
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "gotoAddGys throws :", e);
      return ErrorPage.PAGE_500;
    }
    return "fwz/fwz.gys-add";
  }
  
  @RequestMapping(value = "/gotoEditGys.do", method = RequestMethod.GET)
  public String gotoEditGys(HttpServletRequest request, HttpServletResponse response) {
    try {
      Supplier supplier = supplierRepoService.getSupplierByID(Long.parseLong(request.getParameter("id")));
      
      UserModel fwz_login_user = FWZUserSessionUtils.getUserSession(request);
      if(fwz_login_user.getServiceStation().getId().longValue() != supplier.getServiceStationId().longValue()){
        return ErrorPage.PAGE_403;
      }
      request.setAttribute("gys_detail", supplier);
      SupplierShippingAreaQuery supplierShippingAreaQuery = new SupplierShippingAreaQuery();
      supplierShippingAreaQuery.setSupplier_id(supplier.getId());
      List<GoodsShippingArea> shippingArea_list = supplierRepoService.getSupplierDefaultShippingArea(supplierShippingAreaQuery);
      request.setAttribute("shipping_area_list", shippingArea_list);
      
     
      String serviceStationName = fwzCommonService
          .getServiceStationByID(fwz_login_user.getOrganizationId()).getServiceStationName();
      request.setAttribute("serviceStationName", serviceStationName);
      request.setAttribute("serviceStationId", fwz_login_user.getId());
      SupplierShippingAreaQuery shippingAreaQuery = new SupplierShippingAreaQuery();
      shippingAreaQuery.setService_station_id(fwz_login_user.getOrganizationId());
      ShippingAreaData shippingData =
          supplierRepoService.getSupplierShippingAreaData(shippingAreaQuery);
      request.setAttribute("shippingData", shippingData);
      ClassificaionQuery classificaionQuery = new ClassificaionQuery();
      classificaionQuery.setParentClassficationID(0L);
      List<MSClassificaion> fir_category_list =
          classificaionService.queryCascadeClassificaion(classificaionQuery);
      request.setAttribute("fir_category_list", fir_category_list);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), "gotoAddGys throws :", e);
      return ErrorPage.PAGE_500;
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "gotoAddGys throws :", e);
      return ErrorPage.PAGE_500;
    }
    return "fwz/fwz.gys-edit";
  }



  /**
   * addGys:(服务站添加供应商).
   *
   * 2016年8月15日 上午11:07:46
   * 
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/addGys.do", method = RequestMethod.POST)
  public @ResponseBody ExchangeData addGys(@RequestBody CreateGysForm form,
      HttpServletRequest request, HttpServletResponse response) {
    UserModel fwz_login_user = FWZUserSessionUtils.getUserSession(request);
    ExchangeData ed = new ExchangeData<>();
    try {
      gysManagerService.addSupplier(form, fwz_login_user);

    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "添加供应商异常", e);
      ed.markException(e.getMessage(), e);
    }
    return ed;
  }

  
   
   

  /**
   * getGysById:(查看供应商信息).
   *
   * 2016年8月15日 下午10:25:52
   * 
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @param user
   * @return
   * 
   */
  @RequestMapping(value = "/gotoGYSDetail.do", method = RequestMethod.GET)
  public String gotoGYSDetail(@RequestParam Long supplier_id, HttpServletRequest request,
      HttpServletResponse response) {
    try {
      Supplier supplier = supplierRepoService.getSupplierByID(supplier_id);
     
      UserModel user = FWZUserSessionUtils.getUserSession(request);
      if(user.getServiceStation().getId().longValue() != supplier.getServiceStationId().longValue()){
        return ErrorPage.PAGE_403;
      }
      request.setAttribute("gys_detail", supplier);
      SupplierShippingAreaQuery supplierShippingAreaQuery = new SupplierShippingAreaQuery();
      supplierShippingAreaQuery.setSupplier_id(supplier.getId());
      List<GoodsShippingArea> shippingArea_list = supplierRepoService.getSupplierDefaultShippingArea(supplierShippingAreaQuery);
      request.setAttribute("shipping_area_list", shippingArea_list);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), e.getMessage(), e);
      return ErrorPage.PAGE_404;
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "gotoGYSDetail exception", e);
      return ErrorPage.PAGE_500;
    }
    return "fwz/gys_detail";
  }
  
  /**
   * 
   * updateMixture:(修改供应商状态).
   *
   * 2016年9月12日 下午2:47:23
   * @author wangjun
   * @param supplier
   */
  @RequestMapping(value="/updateMixture.do")
  @ResponseBody
  public ExchangeData<Object> updateMixture(@RequestBody Supplier supplier, HttpServletRequest request){
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      gysManagerService.updateMixture(supplier, userModel);
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "updateMixture exception", e);
      
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("修改供应商状态失败");
    }
    
    return exchangeData;
  }
  
  /**
   * 
   * editGys:(编辑供应商).
   *
   * 2016年9月12日 下午6:20:51
   * @author wangjun
   * @param form
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/editGys.do", method = RequestMethod.POST)
  public @ResponseBody ExchangeData editGys(@RequestBody CreateGysForm form,
      HttpServletRequest request, HttpServletResponse response) {
    UserModel fwz_login_user = FWZUserSessionUtils.getUserSession(request);
    ExchangeData ed = new ExchangeData<>();
    try {
      gysManagerService.editSupplier(form, fwz_login_user);
      
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(), "添加供应商异常", e);
      ed.markException(e.getMessage(), e);
    }
    return ed;
  }
}

