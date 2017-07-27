package com.kxwp.admin.actions.serviceStation;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.AnnouncementStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.fwz.SsAnnouncement;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.announcement.AnnouncementQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.announcement.AnnouncementService;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * 公告action
 * date: 2016年9月13日 上午11:44:50 
 *
 * @author wangjun
 */
@Controller
@RequestMapping(value="/fwz/SsAnnouncement")
public class SsAnnouncementAction {
  @Autowired
  private AnnouncementService announcementService;
  
  @Autowired
  private OddNumberService oddNumberService;
  
  /**
   * 
   * addInit:(添加初始化).
   *
   * 2016年9月13日 上午11:49:22
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/addInit.do")
  public ModelAndView addInit(){
    ModelAndView mav = new ModelAndView();
    
    OddNumberQuery query = new OddNumberQuery();
    query.setNumber_type(KXWPNumberRuleEnum.ANNOUNCEMENT_NO);
    
    
    mav.setViewName("/fwz/announcement_add");
    try {
      mav.addObject("announcementNo", oddNumberService.newOddNumberViaProcedure(query));
    } catch (SYSException e) {
      KXWPLogUtils.logException(SsAnnouncement.class, "添加初始化异常", e);
    }
    
    return mav;
  }
  
  /**
   * 
   * add:(添加公告).
   *
   * 2016年9月13日 上午11:56:38
   * @author wangjun
   * @param ssAnnouncement
   * @return
   */
  @RequestMapping(value="/ajax/add.do")
  @ResponseBody
  public ExchangeData<Object> add(@RequestBody SsAnnouncement ssAnnouncement, HttpServletRequest request){
    KXWPLogUtils.logInfo(SsAnnouncement.class, "添加公告:入参------" + JacksonUtils.writeValue(ssAnnouncement));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      
      ssAnnouncement.setCreateUserId(userModel.getId());
      ssAnnouncement.setServiceStationId(userModel.getOrganizationId());
      ssAnnouncement.setAnnouncementStatus(AnnouncementStatusEnum.UNSENT.toString());
      ssAnnouncement.setCreateTime(new Date());
      ssAnnouncement.setUpdateTime(new Date());
      
      announcementService.add(ssAnnouncement);
    } catch (Exception e) {
      KXWPLogUtils.logException(SsAnnouncement.class, "", e);
      
      exchangeData.markFail();
    }
    
    KXWPLogUtils.logInfo(SsAnnouncement.class, "添加公告:出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  /**
   * 
   * modifyInit:(修改公告初始化).
   *
   * 2016年9月13日 下午12:02:48
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/modifyInit.do")
  public String modifyInit(){
    return "";
  }
  
  /**
   * 
   * modify:(修改公告).
   *
   * 2016年9月13日 下午12:14:27
   * @author wangjun
   * @param ssAnnouncement
   * @return
   */
  @RequestMapping(value="/ajax/modify.do")
  @ResponseBody
  public ExchangeData<Object> modify(@RequestBody SsAnnouncement ssAnnouncement){
    KXWPLogUtils.logInfo(SsAnnouncement.class, "修改公告" + JacksonUtils.writeValue(ssAnnouncement));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    try {
      announcementService.modify(ssAnnouncement);
    } catch (Exception e) {
      KXWPLogUtils.logInfo(SsAnnouncement.class, "修改公告" + JacksonUtils.writeValue(ssAnnouncement));
      
      exchangeData.markFail();
    }
    
    KXWPLogUtils.logInfo(SsAnnouncement.class, "修改公告" + JacksonUtils.writeValue(ssAnnouncement));
    
    return exchangeData;
  }
  
  /**
   * 
   * getInit:(获取公告初始化).
   *
   * 2016年9月13日 下午12:22:25
   * @author wangjun
   * @param id
   * @return
   */
  @RequestMapping(value="/getInit.do")
  public ModelAndView getInit(Long id){
    KXWPLogUtils.logInfo(SsAnnouncement.class, "" + id);
    
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("");
    mav.addObject("id", id);
    
    return mav;
  }
  
  /**
   * 
   * get:(根据id获取公告信息).
   *
   * 2016年9月13日 下午12:28:23
   * @author wangjun
   * @param id
   * @return
   */
  @RequestMapping(value="/ajax/get.do", produces="application/json")
  @ResponseBody
  public ExchangeData<SsAnnouncement> get(Long id){
    KXWPLogUtils.logInfo(SsAnnouncement.class, "根据id获取公告信息：入参------" + id);
    
    ExchangeData<SsAnnouncement> exchangeData = new ExchangeData<SsAnnouncement>();
    
    SsAnnouncement ssAnnouncement = null;
    try {
      ssAnnouncement = announcementService.get(id);
    } catch (Exception e) {
      exchangeData.markFail();
      
      KXWPLogUtils.logException(SsAnnouncement.class, "根据id获取公告信息异常", e);
    }
    
    exchangeData.setData(ssAnnouncement);
    
    KXWPLogUtils.logInfo(SsAnnouncement.class, "根据id获取公告信息：出差------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  /**
   * 
   * preview:(预览公告).
   *
   * 2016年9月19日 下午4:42:14
   * @author wangjun
   * @param id
   * @return
   */
  @RequestMapping(value="/preview.do")
  public ModelAndView preview(Long id){
    KXWPLogUtils.logInfo(SsAnnouncementAction.class, "预览公告：入参------" + JacksonUtils.writeValue(id));
    
    ModelAndView mav = new ModelAndView();
    
    try {
      SsAnnouncement ssAnnouncement = announcementService.previewAnnouncement(id);
      
      mav.setViewName("/fwz/announcement_detail");
      
      mav.addObject("ssAnnouncement", ssAnnouncement);
      
      KXWPLogUtils.logInfo(SsAnnouncementAction.class, "预览公告：出参------" + JacksonUtils.writeValue(ssAnnouncement));
    } catch (Exception e) {
      mav.setViewName("500");
      
      KXWPLogUtils.logException(SsAnnouncementAction.class, "预览公告：异常------", e);
    }
    
    return mav;
  }

  /**
   * 
   * listInit:(查询初始化).
   *
   * 2016年9月13日 下午2:36:15
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/listInit.do")
  public String listInit(){
    return "/fwz/announcement_list";
  }
  
  /**
   * 
   * list:(查询公告).
   *
   * 2016年9月13日 下午2:30:25
   * @author wangjun
   * @param ssAnnouncement
   * @return
   */
  @RequestMapping(value="/ajax/list.do")
  @ResponseBody
  public ExchangeData<List<SsAnnouncement>> list(@RequestBody AnnouncementQuery ssAnnouncement){
    KXWPLogUtils.logInfo(SsAnnouncement.class, "查询公告：入参------" + JacksonUtils.writeValue(ssAnnouncement));
    
    ExchangeData<List<SsAnnouncement>> exchangeData = new ExchangeData<List<SsAnnouncement>>();
    
    List<SsAnnouncement> ssAnnouncements = null;
    
    try {
      if(ssAnnouncement != null && 
          StringUtils.equals(ssAnnouncement.getAnnouncementStatus(), AnnouncementStatusEnum.ALL.toString())){
        ssAnnouncement.setAnnouncementStatus(null);
      }
      
      ssAnnouncements = announcementService.list(ssAnnouncement);
      
      exchangeData.setData(ssAnnouncements);
    } catch (Exception e) {
      exchangeData.markFail();
      
      KXWPLogUtils.logException(SsAnnouncement.class, "查询公告异常" + JacksonUtils.writeValue(exchangeData), e);
    }
    
    KXWPLogUtils.logInfo(SsAnnouncement.class, "查询公告：出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
}
