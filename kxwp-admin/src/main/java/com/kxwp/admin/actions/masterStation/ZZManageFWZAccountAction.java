package com.kxwp.admin.actions.masterStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.service.serviceStation.SsAccountService;
import com.kxwp.admin.utils.session.ZZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;

@Controller
@RequestMapping(value = "/zz/fwzAccount")
public class ZZManageFWZAccountAction {
  @Autowired
  private SsAccountService ssAccountService;

  /**
   * 
   * resetPassword:(重置密码).
   *
   * 2016年8月3日 下午8:18:01
   * 
   * @author wangjun
   * @return
   */
  @RequestMapping(value = "/initRole.do")
  @ResponseBody
  public ExchangeData<Object> initRole(ServiceStationAccount serviceStationAccount, HttpServletRequest request) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel zz_login_user = ZZUserSessionUtils.getUserSession(request);
    
    try {
      ssAccountService.initRole(serviceStationAccount, zz_login_user);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }

    return exchangeData;
  }

  /**
   * 
   * list:(根据查询条件查询资源).
   *
   * 2016年7月28日 下午2:50:54
   * 
   * @author wangjun
   * @return
   */
  @RequestMapping(value = "/list.do")
  public ModelAndView list(AccountQuery accountQuery) {
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    List<ServiceStationAccount> serviceStationAccounts;
    try {
      String flag = "alive";// 只查有效的和无效的
      exchangeData = ssAccountService.exchangeDataImportData(accountQuery, flag);
      serviceStationAccounts = ssAccountService.getFinalResultImportData(accountQuery, flag);

      mav.setViewName("zz/zzManageFWZ_number");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("serviceStationAccounts", serviceStationAccounts);
      mav.addObject("accountQuery", accountQuery);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("ssRoles", null);
      mav.addObject("accountQuery", accountQuery);
    }
    return mav;
  }
}
