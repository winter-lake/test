package com.kxwp.h5.actions.channel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.channel.ChannelStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.page.MySQLPager;
import com.kxwp.common.query.channel.ChannelQuery;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.model.channel.ChannelResponse;
import com.kxwp.h5.service.channel.ChannelH5Service;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * 频道API Action
 * date: 2016年9月22日 下午4:56:54 
 *
 * @author wangjun
 */
@Controller
@RequestMapping(value="/h5/channel")
public class ChannelH5Action {
  @Autowired
  private ChannelH5Service channelH5Service;
  
  @RequestMapping(value="/listChannelInit.do")
  public ModelAndView listChannelInit(HttpServletRequest request){
    ModelAndView modelAndView = new ModelAndView();
    
    modelAndView.setViewName("/activity/daily_specials");
    
    return modelAndView;
  }
  /**
   * 
   * listChannel:(查询频道信息).
   *
   * 2016年9月22日 下午5:58:58
   * @author wangjun
   * @param channelQuery
   * @param request
   * @return
   */
  @RequestMapping(value="/ajax/listChannel.do", produces="application/json")
  @ResponseBody
  public ExchangeData<List<ChannelResponse>> listChannel(@RequestBody ChannelQuery channelQuery, 
      HttpServletRequest request){
    KXWPLogUtils.logInfo(ChannelH5Action.class, "：入参------" + JacksonUtils.writeValue(channelQuery));
    
    ExchangeData<List<ChannelResponse>> exchangeData = new ExchangeData<List<ChannelResponse>>();
    
    try {
      APPUserModel appUserModel = H5UserSessionUtils.getUserSession(request);
      
      channelQuery.setServiceStationId(appUserModel.getService_station_id());
//      channelQuery.setServiceStationId(1L);
      channelQuery.setChannelStatus(ChannelStatusEnum.VALID);
      
      List<ChannelResponse> channelResponses = channelH5Service.listChannelMixture(channelQuery);
      
      int totalResult = channelH5Service.countChannelItemTotalResults(channelQuery);
      
      MySQLPager pager = channelQuery.getPager();
      
      pager.setTotoalResults(totalResult);
      
      exchangeData.setPager(pager);
      
      exchangeData.setData(channelResponses);
    } catch (KXWPNotFoundException e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("异常，请联系管理员");
      
      KXWPLogUtils.logException(ChannelH5Action.class, "：出参------" + JacksonUtils.writeValue(exchangeData), e);
    }
    
    KXWPLogUtils.logInfo(ChannelH5Action.class, ":出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
}
