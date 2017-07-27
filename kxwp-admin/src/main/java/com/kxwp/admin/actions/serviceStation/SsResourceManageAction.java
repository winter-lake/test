package com.kxwp.admin.actions.serviceStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.serviceStation.SsResourceManageService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;


/**
 * 资源管理controller
 * date: 2016年7月27日 下午3:19:35 
 *
 * @author wangjun
 */
@Controller
@RequestMapping(value="/zz/ssResourceManage")
public class SsResourceManageAction {

  @Autowired
  private SsResourceManageService resourceManageService;
  
  @RequestMapping(value="/add.do", method=RequestMethod.POST)
  public ModelAndView add(SsResource resource, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = FWZUserSessionUtils.getUserSession(request);
    resource.setCreateUserId(userModel.getId());
    
    try {
      resourceManageService.add(resource);
      
      mav.setViewName("zz/add_menu");
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
   * modify:(修改资源).
   *
   * 2016年7月28日 上午11:25:54
   * @author wangjun
   * @param msResources
   * @return
   */
  @RequestMapping(value="/modify.do", method=RequestMethod.POST)
  @ResponseBody
  public ExchangeData<Object> modify(@RequestBody List<MsResource>  ssResources){
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    try {
      resourceManageService.modify(ssResources);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员！");
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
  public ModelAndView get(Long id){
    ModelAndView mav = new ModelAndView();
    MsResource msResource = null;
    
    try {
      msResource = resourceManageService.get(id);
      
      mav.setViewName("zz/edit_menu");
      
      mav.addObject("msResource", msResource);
    } catch (Exception e) {
      
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.setViewName("500");
    }
    
    return  mav;
  }
}
