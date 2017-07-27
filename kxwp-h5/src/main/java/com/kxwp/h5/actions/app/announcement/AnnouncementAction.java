package com.kxwp.h5.actions.app.announcement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.common.constants.AnnouncementStatusEnum;
import com.kxwp.common.constants.PlatformTypeEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.page.BasePager;
import com.kxwp.common.model.page.MySQLPager;
import com.kxwp.common.query.announcement.AnnouncementQuery;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.announcement.AnnouncementResponse;
import com.kxwp.h5.service.announcement.H5AnnouncementService;

/**
 * 公告action
 * date: 2016年9月14日 上午10:28:51 
 *
 * @author wangjun
 */
@Controller(value="appAnnouncementAction")
@RequestMapping(value="/h5/app/announcement")
public class AnnouncementAction {
  @Autowired
  private H5AnnouncementService h5AnnouncementService;
  
    /**
     * listAnnouncementInit:(公告列表初始化).
     *
     * 2016年9月18日 下午6:09:31
     * @author wangjun
     * @return
     */
    @RequestMapping(value="/listAnnouncementInit.do")
    public ModelAndView appListAnnouncementInit(HttpServletRequest request){
      ModelAndView modelAndView = new ModelAndView();
      
      //TODO 根据app请求信息判断app是否登录
      
      modelAndView.setViewName("/announcement/announcement_list");
      modelAndView.addObject("flag", "app");
      modelAndView.addObject("authStatus", "Y");
      
      return modelAndView;
    }
    
    /**
     * 
     * list:(查询当前超市可查看的公告).
     *
     * 2016年9月14日 上午10:45:13
     * @author wangjun
     * @param request
     * @return
     */
    @RequestMapping(value="/ajax/listAnnouncement.do", produces="application/json")
    @ResponseBody
    public ExchangeData<List<AnnouncementResponse>> appListAnnouncement(@RequestBody AnnouncementQuery announcementQuery, HttpServletRequest request){
      ExchangeData<List<AnnouncementResponse>> exchangeData 
      = new ExchangeData<List<AnnouncementResponse>>();
      
      try {
        //TODO 根据app请求信息判断app是否登录
        
        //定义查询条件
        announcementQuery.setServiceStationId(null);
        announcementQuery.setPlatformType(PlatformTypeEnum.MOBILE);
        announcementQuery.setAnnouncementStatus(AnnouncementStatusEnum.SENT.toString());
        
        List<AnnouncementResponse> announcementResponses =
            h5AnnouncementService.listAnnouncementResponse(announcementQuery);
        
        exchangeData.setData(announcementResponses);
        
        //设置分页
        BasePager pager = new MySQLPager();
        
        pager.setTotoalResults(h5AnnouncementService.getTotalResults(announcementQuery));
        
        exchangeData.setPager(pager);
      } catch (IllegalAccessException | InvocationTargetException | KXWPException e) {
        KXWPLogUtils.logException(AnnouncementAction.class, "查询当前超市可查看的公告：出参------" + JacksonUtils.writeValue(exchangeData), e);
      }
      
      KXWPLogUtils.logInfo(AnnouncementAction.class, "查询当前超市可查看的公告：出参------" + JacksonUtils.writeValue(exchangeData));
      
      return exchangeData;
    }

    /**
     * 
     * get:(根据公告id获取公告信息).
     *
     * 2016年9月18日 上午10:15:28
     * @author wangjun
     * @param id
     * @return
     */
    @RequestMapping(value="/getAnnouncement.do")
    public ModelAndView appGetAnnouncement(Long id){
      KXWPLogUtils.logInfo(AnnouncementAction.class, "根据公告id获取公告信息：入参------" + JacksonUtils.writeValue(id));
      
      ModelAndView mav = new ModelAndView();
      
      try {
        //TODO 根据app请求信息判断app是否登录
        
        AnnouncementResponse announcementResponse = h5AnnouncementService.getAnnouncementResponse(id);
        
        mav.setViewName("/announcement/announcement_detail");
        
        mav.addObject("announcementResponse", announcementResponse);
        
        KXWPLogUtils.logInfo(AnnouncementAction.class, "根据公告id获取公告信息：出参------" + JacksonUtils.writeValue(announcementResponse));
      } catch (IllegalAccessException | InvocationTargetException | KXWPValidException e) {
        mav.setViewName("500");
        
        KXWPLogUtils.logException(AnnouncementAction.class, "根据公告id获取公告信息：异常------", e);
      }
      
      return mav;
    }
}
