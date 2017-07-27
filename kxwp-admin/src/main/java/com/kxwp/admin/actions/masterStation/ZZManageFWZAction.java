package com.kxwp.admin.actions.masterStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.form.zz.FWZCreateForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.serviceStation.ServiceStationManagerQuery;
import com.kxwp.admin.service.serviceStation.FWZManagerService;
import com.kxwp.admin.utils.session.ZZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.fwz.SSServiceRegion;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.query.fwz.FWZRegionQuery;
import com.kxwp.common.service.fwz.FWZRegionService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * Date: 2016年8月8日 下午8:29:26
 * 
 * @author lou jian wen 总站对服务站的相关管理
 */
@Controller
@RequestMapping("/zz/manager/fwz")
public class ZZManageFWZAction {

  @Autowired
  private FWZManagerService fwzManagerService;
  
  @Autowired
  private FWZRegionService fwzRegionService;



  @RequestMapping(value = "/queryFWZ.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<ServiceStation>> queryFWZ(
      @RequestBody ServiceStationManagerQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<ServiceStation>> edb = new ExchangeData<>();
    List<ServiceStation> dataList;
    try {
      dataList = fwzManagerService.queryServiceStation(query);
      int total = fwzManagerService.countByQuery(query);
      edb.getPager().setTotoalResults(total);
      edb.getPager().setCurrentPage(query.getPager().getCurrentPage());
      edb.setData(dataList);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }


  /**
   * 
   * getUnOpendCounty:(获取未开通服务站的区域).
   *
   * 2016年8月20日 上午11:01:49
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/getUnOpendCounty.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<SYSRegion>> getUnOpendCounty(
      @RequestBody FWZRegionQuery fwzRegionQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<SYSRegion>> edb = new ExchangeData<>();
    List<SYSRegion> dataList = fwzManagerService.getUnCheckedCounty(fwzRegionQuery);
    edb.setData(dataList);
    return edb;
  }



  /**
   * 
   * queryFWZDetail:(查询服务站相信信息).
   *
   * 2016年8月9日 下午3:58:46
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/queryFWZDetail.do")
  public String queryFWZDetail(@RequestParam Long id, HttpServletRequest request,
      HttpServletResponse response) {
    ServiceStation data;
    try {
      ServiceStationManagerQuery query = new ServiceStationManagerQuery();
      query.setId(id);
      data = fwzManagerService.getFWZDetail(query);
      FWZRegionQuery fwzRegionQuery = new FWZRegionQuery();
      fwzRegionQuery.setService_station_id(id);
      List<SSServiceRegion> serviceRegionList = fwzRegionService.queryServiceRegion(fwzRegionQuery);
      request.setAttribute("regionList", serviceRegionList);
      request.setAttribute("zzDetail", data);
    } catch (KXWPNotFoundException e) {
      KXWPLogUtils.logException(this.getClass(), "queryFWZDetail exception", e);
    }

    return "zz/fwz-view";
  }


  @RequestMapping(value = "/createFWZ.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<ServiceStation>> createFWZ(
      @Valid @RequestBody FWZCreateForm form, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<ServiceStation>> edb = new ExchangeData<>();
    UserModel zz_login_user = ZZUserSessionUtils.getUserSession(request);
    try {
      fwzManagerService.addNewFWZ(form, zz_login_user);
    } catch (ConfigurationException | KXWPException e) {
      edb.markException(e);
      KXWPLogUtils.logException(this.getClass(), "createFWZ exception", e);
    }
    return edb;
  }



  @RequestMapping(value = "/listFWZ.do")
  public String listFWZ(HttpServletRequest request, HttpServletResponse response) {
    return "zz/list_fwz";
  }

  @RequestMapping(value = "/gotoAddFWZ.do")
  public String gotoAddFWZ(HttpServletRequest request, HttpServletResponse response) {
    return "zz/fwz-add";
  }

  /**
   * 
   * resetPassword:(重置密码).
   *
   * 2016年8月3日 下午8:18:01
   * 
   * @author wangjun
   * @return
   */
  @RequestMapping(value = "/resetPassword.do")
  @ResponseBody
  public ExchangeData<Object> resetPassword(
      @RequestBody ServiceStationAccount serviceStationAccount) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    try {
      fwzManagerService.resetPassword(serviceStationAccount);
      exchangeData.setMessage("重置密码成功,新密码将会通过短信发送到手机");
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }

    return exchangeData;
  }

}

