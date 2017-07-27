package com.kxwp.admin.actions.masterStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.AdLocationEnum;
import com.kxwp.common.constants.AdStatusEnum;
import com.kxwp.common.constants.AdTypeEnum;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.ErrorPage;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.ADConfig;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.ad.ADQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.ad.ADConfigService;
import com.kxwp.common.service.core.OddNumberService;

/**
 * 服务站广告管理
 * date: 2016年8月17日 上午11:28:47 
 *
 * @author wangjun
 */
@Controller
@RequestMapping(value="/zz/msAd")
public class MsADAction {
  @Autowired
  private ADConfigService ADConfigService;
  
  @Autowired
  private OddNumberService oddNumberService;
 /**
  *   
  * findInit:(查询列表初始化).
  *
  * 2016年8月17日 上午11:34:31
  * @author wangjun
  * @return
  */
 @RequestMapping(value="/findInit.do")
 public String findInit(){
   return "zz/ad_list";
 }
 
 /**
  * 
  * find:(查询).
  *
  * 2016年8月17日 上午11:41:13
  * @author wangjun
  * @param adQuery
  * @return
  */
 @RequestMapping(value="/find.do")
 @ResponseBody
 public ExchangeData<List<ADConfig>> find(@RequestBody ADQuery adQuery, HttpServletRequest request){
   ExchangeData<List<ADConfig>> exchangeData = new ExchangeData<List<ADConfig>>();
  
   
   if(adQuery.getAdStatus().equals(AdStatusEnum.ALL)){
     adQuery.setAdStatus(null);
   }
   if(adQuery.getAdType().equals(AdTypeEnum.ALL)){
     adQuery.setAdType(null);
   }
     
   if(adQuery.getAdLocation().equals(AdLocationEnum.ALL)){
     adQuery.setAdLocation(null);
   }
   
   adQuery.setStatusFlag(true);
   
   try {
     adQuery.setOrderBy("create_time desc,sort asc");
     List<ADConfig> adConfigs = ADConfigService.find(adQuery);
    
     exchangeData.setPager(ADConfigService.getPager(adQuery));
    exchangeData.setData(adConfigs);
  } catch (Exception e) {
    exchangeData.setCallStatus(CallStatusEnum.FAIL);
    exchangeData.setMessage("广告查询异常，请联系管理员！");
  }
   
   return exchangeData;
 }
 
 /**
  * 
  * addInit:(添加初始化).
  *
  * 2016年8月17日 上午11:40:54
  * @author wangjun
  * @return
  */
 @RequestMapping(value="/addInit.do")
 public String addInit(HttpServletRequest request){
   OddNumberQuery query = new OddNumberQuery();
   query.setNumber_type(KXWPNumberRuleEnum.AD_NO);
   
   try {
     request.setAttribute("adNo", oddNumberService.newOddNumberViaProcedure(query));
   } catch (SYSException e) {
     return ErrorPage.PAGE_500;
   }
   return "zz/ad_add";
 }
 
 /**
  * 
  * add:(广告添加).
  *
  * 2016年8月17日 上午11:53:06
  * @author wangjun
  * @param adConfig
  * @return
  */
 @RequestMapping(value="/add.do")
 @ResponseBody
 public ExchangeData<Object> add(@RequestBody ADConfig adConfig, HttpServletRequest request){
   ExchangeData<Object> exchangeData = new ExchangeData<Object>();
   
   UserModel userModel = FWZUserSessionUtils.getUserSession(request);
   
   adConfig.setCreateUserId(userModel.getId());
   adConfig.setServiceStationId(userModel.getOrganizationId());
   
   try {
    ADConfigService.add(adConfig);
  } catch (Exception e) {
    exchangeData.setCallStatus(CallStatusEnum.FAIL);
    exchangeData.setMessage("广告添加异常，请联系管理员！");
  }
   
   return exchangeData;
 }
 
 /**
  * 
  * get:(获取广告信息初始化).
  *
  * 2016年8月17日 下午8:03:07
  * @author wangjun
  * @param id
  * @return
  */
 @RequestMapping(value="/viewInit.do")
 public ModelAndView getInit(Long id, HttpServletRequest request){
   ModelAndView mav = new ModelAndView();
   
   ADConfig adConfig;
  try {
    adConfig = ADConfigService.get(id);
    request.setAttribute("adNo", adConfig.getAdNo());
    if(adConfig.getPhotoList().size() != 0)
      request.setAttribute("photoList0", adConfig.getPhotoList().get(adConfig.getPhotoList().size()-1));
    mav.setViewName("zz/ad_view");
    mav.addObject("id", id);
  } catch (KXWPException e) {
    mav.setViewName("500");
  }
   
   return mav;
 }
 
 /**
  * 
  * get:(修改广告信息初始化).
  *
  * 2016年8月17日 下午8:03:07
  * @author wangjun
  * @param id
  * @return
  */
 @RequestMapping(value="/modifyInit.do")
 public ModelAndView modifyInit(Long id, HttpServletRequest request){
   ModelAndView mav = new ModelAndView();
   
  ADConfig adConfig;
  try {
    adConfig = ADConfigService.get(id);
    
    request.setAttribute("adNo", adConfig.getAdNo());
    if(adConfig.getPhotoList().size() != 0)
    request.setAttribute("photoList0", adConfig.getPhotoList().get(adConfig.getPhotoList().size()-1));
    
    mav.setViewName("zz/ad_edit");
    mav.addObject("id", id);
  } catch (KXWPException e) {
    mav.setViewName("500");
  }
   
   return mav;
 }
 
 /**
  * 
  * get:(获取广告信息).
  *
  * 2016年8月17日 下午8:03:07
  * @author wangjun
  * @param id
  * @return
  */
 @RequestMapping(value="/get.do",produces="application/json")
 @ResponseBody
 public ExchangeData<ADConfig> get(Long id){
   ExchangeData<ADConfig> exchangeData = new ExchangeData<ADConfig>();
   
   try {
     ADConfig adConfig = ADConfigService.get(id);
     
     exchangeData.setData(adConfig);
  } catch (Exception e) {
    exchangeData.setCallStatus(CallStatusEnum.FAIL);
    exchangeData.setMessage("获取广告信息失败，请联系管理员！");
  }
   return exchangeData;
 }
 
 /**
  * 
  * modify:(广告修改).
  *
  * 2016年8月17日 下午9:01:15
  * @author wangjun
  * @param adConfig
  * @return
  */
 @RequestMapping(value="/modify.do")
 @ResponseBody
 public ExchangeData<Object>  modify(@RequestBody ADConfig adConfig){
   ExchangeData<Object> exchangeData = new ExchangeData<Object>();
   
   try {
    ADConfigService.modify(adConfig);
  } catch (Exception e) {
    exchangeData.setCallStatus(CallStatusEnum.FAIL);
    exchangeData.setMessage("广告修改失败，请联系管理员！");
  }
   
   return exchangeData;
 }
 
 /**
  * 
  * modify:(广告修改).
  *
  * 2016年8月17日 下午9:01:15
  * @author wangjun
  * @param adConfig
  * @return
  */
 @RequestMapping(value="/audit.do")
 @ResponseBody
 public ExchangeData<Object>  audit(@RequestBody ADConfig adConfig, HttpServletRequest request){
   ExchangeData<Object> exchangeData = new ExchangeData<Object>();
   
   UserModel userModel = FWZUserSessionUtils.getUserSession(request);
   
   adConfig.setReviewUserId(userModel.getId());
   try {
     ADConfigService.audit(adConfig);
   } catch (Exception e) {
     exchangeData.markException(e.getMessage(), e);
   }
   
   return exchangeData;
 }
}