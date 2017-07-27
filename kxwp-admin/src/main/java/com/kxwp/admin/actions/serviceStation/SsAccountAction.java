package com.kxwp.admin.actions.serviceStation;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.service.serviceStation.FWZLoginService;
import com.kxwp.admin.service.serviceStation.SsAccountService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.admin.utils.session.GYSUserSessionUtils;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

@Controller
@RequestMapping(value="/fwz/ssAccount")
public class SsAccountAction {
  @Autowired
  private SsAccountService ssAccountService;
  
  @Autowired
  private FWZLoginService fwzLoginService;
  
  @RequestMapping(value="/addInit.do", method=RequestMethod.GET)
  public ModelAndView addInit(HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    
    try {
      UserModel userModel = GYSUserSessionUtils.getUserSession(request);
      List<SsRole> ssRoles = ssAccountService.findSsRole(userModel);
      
      mav.setViewName("fwz/add_number");
      mav.addObject("ssRoles", ssRoles);
    } catch (Exception e) {
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");
    }
    
    
    return mav;
  }
  
  /**
   * 
   * add:(账号添加).
   *
   * 2016年8月3日 下午4:46:40
   * @author wangjun
   * @param masterStationAccount
   * @return
   */
  @RequestMapping(value="/add.do")
  public ModelAndView add(ServiceStationAccount serviceStationAccount, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = FWZUserSessionUtils.getUserSession(request);
    serviceStationAccount.setCreateUserId(userModel.getId());
    serviceStationAccount.setParentId(userModel.getId());
    serviceStationAccount.setServiceStationId(userModel.getOrganizationId());
    
    try {
      ssAccountService.addComposite(serviceStationAccount);
      
      mav.setViewName("fwz/add_number");
      mav.addObject("exchangeData", exchangeData);
    } catch (Exception e) {
      
      mav.setViewName("500");
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员！");
    }
    
    return mav;
  }
  
  /**
   * 
   * modifyBatch:(批量修改).
   *
   * 2016年8月3日 下午8:18:01
   * @author wangjun
   * @param mStationAccounts
   * @return
   */
  @RequestMapping(value="/modifyStatusBatch.do")
  @ResponseBody
  public ExchangeData<Object> modifyStatusBatch(
      @RequestBody List<ServiceStationAccount> serviceStationAccounts, 
      HttpServletRequest request, 
      HttpServletResponse response){
    KXWPLogUtils.logInfo(SsAccountAction.class, "服务站批量修改---入参"+JacksonUtils.writeValue(serviceStationAccounts));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    
    try {
      ssAccountService.modifyStatusBatch(serviceStationAccounts);
      
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      
      if(userModel.getId() == serviceStationAccounts.get(0).getId()){
        fwzLoginService.logout(request, response);
      }
    } catch (Exception e) {
      
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    
    KXWPLogUtils.logInfo(SsAccountAction.class, "服务站批量修改---出参"+JacksonUtils.writeValue(exchangeData));
    return exchangeData;
  }
  
  /**
   * 
   * resetPassword:(重置密码).
   *
   * 2016年8月3日 下午8:18:01
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/resetPassword.do")
  @ResponseBody
  public ExchangeData<Object> resetPassword(ServiceStationAccount serviceStationAccount){
    KXWPLogUtils.logInfo(SsAccountAction.class, "服务站重置密码--入参"+JacksonUtils.writeValue(serviceStationAccount));
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    
    try {
      ssAccountService.resetPassword(serviceStationAccount);
      exchangeData.setMessage("重置密码成功,新密码将会通过短信发送到手机");
    } catch (Exception e) {
      
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    KXWPLogUtils.logInfo(SsAccountAction.class, "服务站重置密码--出参"+JacksonUtils.writeValue(serviceStationAccount));
    return exchangeData;
  }
  
  /**
   * 
   * get:(根据id获取资源).
   *
   * 2016年7月28日 下午1:58:50
   * @author wangjun
   * @param id
   * @return
   */
  @RequestMapping(value="/get.do", method=RequestMethod.GET)
  public ModelAndView get(Long id, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ServiceStationAccount serviceStationAccount = null;
    try {
      UserModel userModel = GYSUserSessionUtils.getUserSession(request);
      serviceStationAccount = ssAccountService.get(id);
      List<SsRole> ssRoles = ssAccountService.findSsRole(userModel);
      
      mav.setViewName("fwz/edit_number");
      mav.addObject("serviceStationAccount", serviceStationAccount);
      mav.addObject("ssRoles", ssRoles);
    } catch (Exception e) {
      
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.setViewName("500");
    }
    
    return  mav;
  }
  
  /**
   * 
   * add:(账号修改).
   *
   * 2016年8月3日 下午4:46:40
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/modify.do")
  public ModelAndView modify(ServiceStationAccount serviceStationAccount, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = FWZUserSessionUtils.getUserSession(request);
    serviceStationAccount.setCreateUserId(userModel.getId());
    
    try {
      ssAccountService.modifyComposite(serviceStationAccount);
      
      mav.setViewName("fwz/edit_number");
      mav.addObject("exchangeData", exchangeData);
    } catch (Exception e) {
      
      mav.setViewName("500");
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员！");
    }
    
    return mav;
  }
  
  /**
   * 
   * list:(根据查询条件查询资源).
   *
   * 2016年7月28日 下午2:50:54
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/list.do")
  public ModelAndView list(AccountQuery accountQuery, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    List<ServiceStationAccount> serviceStationAccounts;
    List<SsRole> ssroles;
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      accountQuery.setServiceStationId(userModel.getOrganizationId());
      
      String flag="alive";//只查有效的和无效的
      exchangeData = ssAccountService.exchangeData(accountQuery,flag);
      serviceStationAccounts = ssAccountService.getFinalResult(accountQuery,flag);
      ssroles = ssAccountService.getAllRole(accountQuery);
      
      mav.setViewName("fwz/number");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("serviceStationAccounts",serviceStationAccounts);
      mav.addObject("accountQuery", accountQuery);
      mav.addObject("ssroles", ssroles);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("ssRoles", null);
      mav.addObject("accountQuery", accountQuery);
    }
    return  mav;
  }
  
  /**
   * 
   * list:(根据查询条件查询资源).
   *
   * 2016年7月28日 下午2:50:54
   * @author wangjun
   * @return
   */  
  @RequestMapping(value="/dimission.do")
  public ModelAndView dimission(AccountQuery accountQuery){
    accountQuery.setAccountStatus(AccountStatusEnum.DIMISSION);
    
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    List<ServiceStationAccount> serviceStationAccounts;
    try {
      String flag="dimission";
      exchangeData = ssAccountService.exchangeData(accountQuery,flag);
      serviceStationAccounts = ssAccountService.getFinalResult(accountQuery,flag);
      
      mav.setViewName("fwz/number_dimission");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("serviceStationAccounts",serviceStationAccounts);
      mav.addObject("accountQuery", accountQuery);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
    }
    
    return  mav;
  }
  
  @RequestMapping(value="/checkRoleIsUsed.do")
  @ResponseBody
  public Boolean checkRoleIsUsed(Long id){
    return ssAccountService.checkRoleIsUsed(id);
  }
  
  /**
   * 
   * checkRoleIsUsed:(检查手机号是否有账户在使用).
   *
   * 2016年8月20日 下午3:58:40
   * @author wangjun
   * @param id
   * @return
   */
  @RequestMapping(value="/checkMobileIsUsed.do")
  @ResponseBody
  public Boolean checkMobileIsUsed(Long mobile){
    return ssAccountService.checkMobileIsUsed(mobile);
  }
  
  /**
   * 
   * previewResourceInfo:(预览资源信息).
   *
   * 2016年9月7日 下午2:39:06
   * @author wangjun
   * @param msRoles
   */
  @RequestMapping(value="/previewResourceInfo.do")
  @ResponseBody
  public Set<SsResource> previewResourceInfo(@RequestBody List<SsRole> ssRoles){
    return ssAccountService.previewResourceInfo(ssRoles);
  }
}
