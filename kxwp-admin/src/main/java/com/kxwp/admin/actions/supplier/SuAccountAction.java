package com.kxwp.admin.actions.supplier;

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

import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.entity.supplier.SuRole;
import com.kxwp.admin.entity.supplier.SupplierAccount;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.service.supplier.GYSLoginService;
import com.kxwp.admin.service.supplier.SuAccountService;
import com.kxwp.admin.utils.session.GYSUserSessionUtils;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;

@Controller
@RequestMapping(value="/gys/suAccount")
public class SuAccountAction {
  @Autowired
  private SuAccountService suAccountService;
  
  @Autowired
  private GYSLoginService gysLoginService;
  
  @RequestMapping(value="/addInit.do", method=RequestMethod.GET)
  public ModelAndView addInit(HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    
    try {
      UserModel userModel = GYSUserSessionUtils.getUserSession(request);
      List<SuRole> suRoles = suAccountService.findSuRole(userModel);
      
      mav.setViewName("gys/add_number");
      mav.addObject("suRoles", suRoles);
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
  public ModelAndView add(SupplierAccount supplierAccount, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = GYSUserSessionUtils.getUserSession(request);
    supplierAccount.setCreateUserId(userModel.getId());
    supplierAccount.setParentId(userModel.getId());
    supplierAccount.setSupplierId(userModel.getOrganizationId());
    
    try {
      suAccountService.addComposite(supplierAccount);
      
      mav.setViewName("gys/add_number");
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
  public ExchangeData<Object> modifyStatusBatch(@RequestBody List<SupplierAccount> supplierAccounts, HttpServletRequest request, HttpServletResponse response){
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    
    try {
      suAccountService.modifyStatusBatch(supplierAccounts);
      
      UserModel userModel = GYSUserSessionUtils.getUserSession(request);
      
      if(userModel.getId() == supplierAccounts.get(0).getId()){
        gysLoginService.logout(request, response);
      }
    } catch (Exception e) {
      
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    
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
  public ExchangeData<Object> resetPassword(SupplierAccount supplierAccount){
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    
    try {
      suAccountService.resetPassword(supplierAccount);
      exchangeData.setMessage("重置密码成功,新密码将会通过短信发送到手机");
    } catch (Exception e) {
      
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    
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
    SupplierAccount supplierAccount = null;
    try {
      UserModel userModel = GYSUserSessionUtils.getUserSession(request);
      supplierAccount = suAccountService.getMixture(id);
      List<SuRole> suRoles = suAccountService.findSuRole(userModel);
      
      mav.setViewName("gys/edit_number");
      mav.addObject("supplierAccount", supplierAccount);
      mav.addObject("suRoles", suRoles);
    } catch (Exception e) {
      
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.setViewName("500");
    }
    
    return  mav;
  }
  
  /**
   * 
   * modify:(账号修改).
   *
   * 2016年8月3日 下午4:46:40
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/modify.do")
  public ModelAndView modify(SupplierAccount supplierAccount, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = GYSUserSessionUtils.getUserSession(request);
    supplierAccount.setCreateUserId(userModel.getId());
    
    try {
      suAccountService.modifyComposite(supplierAccount);
      
      mav.setViewName("gys/edit_number");
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
    List<SupplierAccount> supplierAccounts;
    List<SuRole> suroles;
    try {
      UserModel userModel = GYSUserSessionUtils.getUserSession(request);
      accountQuery.setSupplier_id(userModel.getOrganizationId());
      
      String flag="alive";//只查有效的和无效的
      exchangeData = suAccountService.exchangeData(accountQuery,flag);
      supplierAccounts = suAccountService.getFinalResult(accountQuery,flag);
      suroles = suAccountService.getAllRole(accountQuery);
      
      mav.setViewName("gys/number");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("supplierAccounts",supplierAccounts);
      mav.addObject("accountQuery", accountQuery);
      mav.addObject("suroles", suroles);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("SuRole", null);
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
    List<SupplierAccount> supplierAccounts;
    try {
      String flag="dimission";
      exchangeData = suAccountService.exchangeData(accountQuery,flag);
      supplierAccounts = suAccountService.getFinalResult(accountQuery,flag);
      
      mav.setViewName("gys/number_dimission");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("supplierAccounts",supplierAccounts);
      mav.addObject("accountQuery", accountQuery);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("SuRole", null);
      mav.addObject("accountQuery", accountQuery);
    }
    
    return  mav;
  }
  
  @RequestMapping(value="/checkRoleIsUsed.do")
  @ResponseBody
  public Boolean checkRoleIsUsed(Long id){
    return suAccountService.checkRoleIsUsed(id);
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
    return suAccountService.checkMobileIsUsed(mobile);
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
  public Set<SuResource> previewResourceInfo(@RequestBody List<SuRole> suRoles){
    return suAccountService.previewResourceInfo(suRoles);
  }
}
