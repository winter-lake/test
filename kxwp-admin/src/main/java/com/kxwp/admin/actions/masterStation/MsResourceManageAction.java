package com.kxwp.admin.actions.masterStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.masterStation.MasterStationQuery;
import com.kxwp.admin.service.masterStation.MsResourceManageService;
import com.kxwp.admin.utils.session.ZZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;

/**
 * 资源管理controller
 * date: 2016年7月27日 下午3:19:35 
 *
 * @author wangjun
 */
@Controller
@RequestMapping(value="/zz/msResourceManage")
public class MsResourceManageAction {

  @Autowired
  private MsResourceManageService msResourceManageService;
  
  /**
   * add:(添加资源初始化).
   * 2016年7月28日 上午11:26:04
   * @author wangjun
   * @param resource
   * @return
   */
  @RequestMapping(value="/addInit.do", method=RequestMethod.GET)
  public ModelAndView addInit(MsResource resource){
    ModelAndView mav = new ModelAndView();
    try {
      mav.setViewName("zz/add_menu");
    } catch (Exception e) {
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");
    }
    return mav;
  }
  
  /**
   * 
   * add:(添加资源).
   *
   * 2016年7月28日 上午11:26:04
   * @author wangjun
   * @param resource
   * @return
   */
  @RequestMapping(value="/add.do", method=RequestMethod.POST)
  public ModelAndView add(MsResource resource, HttpServletRequest request){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    UserModel userModel = ZZUserSessionUtils.getUserSession(request);
    resource.setCreateUserId(userModel.getId());
    
    try {
      msResourceManageService.add(resource);
      
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
  public ExchangeData<Object> modify(@RequestBody List<MsResource>  msResources){
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    try {
      msResourceManageService.modify(msResources);
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
  public ModelAndView get(Long id, String flag){
    ModelAndView mav = new ModelAndView();
    MsResource msResource = null;
    try {
      msResource = msResourceManageService.get(id);
      if(StringUtils.equals(flag, "view")){
        mav.setViewName("zz/view_menu");
      }else{
        mav.setViewName("zz/edit_menu");
      }
      mav.addObject("msResource", msResource);
      mav.addObject("flag", flag);
    } catch (Exception e) {
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.setViewName("500");
    }
    
    return  mav;
  } 
 
  @RequestMapping(value="/checkURL.do", method=RequestMethod.GET, produces="text/html")
  @ResponseBody
  public String checkURL(HttpServletRequest request){
    String fieldValue = request.getParameter("fieldValue");
      if(msResourceManageService.checkURLMixture(fieldValue)){
        return  "[\"url\",true]";
      }else{
        return  "[\"url\",false]";
      }
  }
  
  @RequestMapping(value="/checkModelURL.do", method=RequestMethod.GET, produces="text/html")
  @ResponseBody
  public String checkModelURL(HttpServletRequest request){
    String fieldId = request.getParameter("fieldId");
    String fieldValue = request.getParameter("fieldValue");
    if(msResourceManageService.checkURLMixture(fieldValue)){
      return  "[\""+fieldId+"\",true]";
    }else{
      return  "[\""+fieldId+"\",false]";
    }
  }
  
  /**
   * 
   * list:(根据查询条件查询资源).
   *
   * 2016年7月28日 下午2:50:54
   * @author wangjun
   * @param masterStationQuery
   * @return
   */
  @RequestMapping(value="/list.do")
  public ModelAndView list(MasterStationQuery masterStationQuery){
    ModelAndView mav = new ModelAndView();
    ExchangeData<Object> exchangeData = new ExchangeData<Object>() ;
    List<MsResource> msResources;
    try {
      exchangeData = msResourceManageService.exchangeData(masterStationQuery);
      msResources = msResourceManageService.getFinalResult(masterStationQuery);
      
      mav.setViewName("zz/menu");
      mav.addObject("exchangeData",exchangeData);
      mav.addObject("msResources",msResources);
      mav.addObject("masterStationQuery", masterStationQuery);
    } catch (Exception e) {
      
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("操作失败，请联系管理员");
      mav.setViewName("500");
      mav.addObject("exchangeData", exchangeData);
      mav.addObject("msResources", null);
      mav.addObject("masterStationQuery", masterStationQuery);
    }
    
    return  mav;
  }
  
  /**
   * 
   * listResource:(根据查询条件查询资源).
   *
   * 2016年7月28日 下午2:50:54
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/listResource.do")
  @ResponseBody
  public ExchangeData<List<MsResource>> listResource(String ownSystem, Integer grade){
    ExchangeData<List<MsResource>> exchangeData = new ExchangeData<List<MsResource>>() ;
    
    List<MsResource> msResources;
    try {
      msResources = msResourceManageService.listResource(ownSystem, grade);
      exchangeData.setData(msResources);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("系统错误，请联系管理员");
    }
    
    return exchangeData;
  }
}
