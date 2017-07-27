package com.kxwp.admin.actions.masterStation;

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

import com.kxwp.admin.entity.masterStation.MasterStationAccount;
import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.masterStation.MsRole;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.service.masterStation.MsAccountService;
import com.kxwp.admin.service.masterStation.ZZLoginService;
import com.kxwp.admin.utils.session.ZZUserSessionUtils;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

@Controller
@RequestMapping(value="/zz/msAccount")
public class MsAccountAction {
  @Autowired
  private MsAccountService msAccountService;
  
  @Autowired
  private ZZLoginService msLoginService;
  
  @RequestMapping(value="/addInit.do", method=RequestMethod.GET)
  public ModelAndView addInit(){
    ModelAndView mav = new ModelAndView();
    
    try {
      List<MsRole> msRoles = msAccountService.findMsRole();
      
      mav.setViewName("zz/add_number");
      mav.addObject("msRoles", msRoles);
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
  public ModelAndView add(MasterStationAccount masterStationAccount, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = ZZUserSessionUtils.getUserSession(request);
    
    masterStationAccount.setMasterStationId(userModel.getId());
    masterStationAccount.setCreateUserId(userModel.getId());
    masterStationAccount.setParentId(userModel.getId());
    
    try {
      msAccountService.addComposite(masterStationAccount);
      
      mav.setViewName("zz/add_number");
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
  public ExchangeData<Object> modifyStatusBatch(@RequestBody List<MasterStationAccount> mStationAccounts, HttpServletRequest request, HttpServletResponse response){
    KXWPLogUtils.logInfo(MsAccountAction.class, "总站账号批量修改---入参"+JacksonUtils.writeValue(mStationAccounts));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    
    try {
      UserModel userModel = ZZUserSessionUtils.getUserSession(request);
      
      msAccountService.modifyStatusBatch(mStationAccounts);
      
      if(userModel.getId() ==  mStationAccounts.get(0).getId()){
        msLoginService.logout(request, response);
      }
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    
    KXWPLogUtils.logInfo(MsAccountAction.class, "总站账号批量修改---出参"+JacksonUtils.writeValue(mStationAccounts));
    
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
  public ExchangeData<Object> resetPassword(MasterStationAccount mStationAccounts){
    KXWPLogUtils.logInfo(MsAccountAction.class, "总站账号管理重置密码--入参"+JacksonUtils.writeValue(mStationAccounts));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    
    try {
      msAccountService.resetPassword(mStationAccounts);
      exchangeData.setMessage("重置密码成功,新密码将会通过短信发送到手机");
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    
    KXWPLogUtils.logInfo(MsAccountAction.class, "总站账号管理重置密码--出参"+JacksonUtils.writeValue(exchangeData));
    
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
  public ModelAndView get(Long id){
    ModelAndView mav = new ModelAndView();
    MasterStationAccount masterStationAccount = null;
    try {
      masterStationAccount = msAccountService.get(id);
      List<MsRole> msRoles = msAccountService.findMsRole();
      
      mav.setViewName("zz/edit_number");
      mav.addObject("masterStationAccount", masterStationAccount);
      mav.addObject("msRoles", msRoles);
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
  public ModelAndView modify(MasterStationAccount masterStationAccount, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = ZZUserSessionUtils.getUserSession(request);
    masterStationAccount.setCreateUserId(userModel.getId());
    
    try {
      msAccountService.modifyComposite(masterStationAccount);
      
      mav.setViewName("zz/edit_number");
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
  public ModelAndView list(AccountQuery accountQuery){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    List<MasterStationAccount> masterStationAccounts;
    List<MsRole> msroles;
    try {
      String flag="alive";//只查有效的和无效的
      exchangeData = msAccountService.exchangeData(accountQuery,flag);
      masterStationAccounts = msAccountService.getFinalResult(accountQuery,flag);
      msroles = msAccountService.getAllRole();
      
      mav.setViewName("zz/number");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("masterStationAccounts",masterStationAccounts);
      mav.addObject("accountQuery", accountQuery);
      mav.addObject("msroles", msroles);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("msRoles", null);
      mav.addObject("accountQuery", accountQuery);
    }
    
    return  mav;
  }
  
  /**
   * list:(根据查询条件查询资源).
   * 2016年7月28日 下午2:50:54
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/dimission.do")
  public ModelAndView dimission(AccountQuery accountQuery){
    accountQuery.setAccountStatus(AccountStatusEnum.DIMISSION);
    
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    List<MasterStationAccount> masterStationAccounts;
    try {
      String flag="dimission";
      exchangeData = msAccountService.exchangeData(accountQuery,flag);
      masterStationAccounts = msAccountService.getFinalResult(accountQuery,flag);
      
      mav.setViewName("zz/number_dimission");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("masterStationAccounts",masterStationAccounts);
      mav.addObject("accountQuery", accountQuery);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("msRoles", null);
      mav.addObject("accountQuery", accountQuery);
    }
    
    return  mav;
  }
  
  /**
   * 
   * checkRoleIsUsed:(检查角色是否有账户在使用).
   *
   * 2016年8月20日 下午3:58:40
   * @author wangjun
   * @param id
   * @return
   */
  @RequestMapping(value="/checkRoleIsUsed.do")
  @ResponseBody
  public Boolean checkRoleIsUsed(Long id){
    return msAccountService.checkRoleIsUsed(id);
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
    return msAccountService.checkMobileIsUsed(mobile);
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
  public Set<MsResource> previewResourceInfo(@RequestBody List<MsRole> msRoles){
    return msAccountService.previewResourceInfo(msRoles);
  }
}
