package com.kxwp.admin.actions.masterStation;

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

import com.kxwp.admin.service.supplier.GYSManagerService;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supplier.GoodsShippingArea;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.query.supplier.SupplierManagerQuery;
import com.kxwp.common.query.supplier.SupplierShippingAreaQuery;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * Date: 2016年8月8日 下午8:29:26
 * 
 * @author 总站对供应商的管理
 */
@Controller
@RequestMapping("/zz/manager/gys")
public class ZZManageGYSAction {

  @Autowired
  private GYSManagerService gysManagerService;



  @Autowired
  private SupplierRepoService supplierRepoService;

  

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
    List<Supplier> dataList = gysManagerService.selectSupplierByQuery(query);
    int total = gysManagerService.countByQuery(query);
    edb.getPager().setCurrentPage(query.getPager().getCurrentPage());
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
    return "zz/zz.gys_list";
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
    return "zz/zz.gys_detail";
  }


}

