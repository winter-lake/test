package com.kxwp.admin.actions.serviceStation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.model.common.KXWPEnumProperty;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.supplier.SupplierManageGoodsService;
import com.kxwp.admin.utils.session.FWZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.GoodsStatusEnum;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.constants.channel.ChannelNameEnum;
import com.kxwp.common.constants.channel.ChannelStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.channel.Channel;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.query.channel.ChannelQuery;
import com.kxwp.common.query.goods.SearchGoodsRepoQuery;
import com.kxwp.common.service.channel.ChannelService;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * 频道 aciton
 * date: 2016年9月20日 下午5:53:11 
 *
 * @author wangjun
 */
@Controller
@RequestMapping(value="/fwz/channel")
public class ChannelAction {
  @Autowired
  private ChannelService channelService;
  
  @Autowired
  private SupplierManageGoodsService supplierManageGoodsServivce;
  
  /**
   * 
   * addChannelInit:(添加频道初始化).
   *
   * 2016年9月20日 下午5:56:26
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/addChannelInit.do")
  public String addChannelInit(){
    return "/fwz/channel_add";
  }
  
  /**
   * 
   * getChannelNameData:(查询可用频道名称).
   *
   * 2016年9月20日 下午6:32:58
   * @author wangjun
   * @param request
   * @return
   */
  @RequestMapping(value="/ajax/getChannelNameData.do")
  @ResponseBody
  public ExchangeData<List<KXWPEnumProperty>> getChannelNameData(HttpServletRequest request){
    ExchangeData<List<KXWPEnumProperty>> exchangeData = new ExchangeData<List<KXWPEnumProperty>>();
    
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      
      ChannelQuery channelQuery = new ChannelQuery();
      
      channelQuery.setServiceStationId(userModel.getOrganizationId());
      channelQuery.setChannelStatus(ChannelStatusEnum.VALID);
      
      List<ChannelNameEnum> channelNameEnums = channelService.listChannelNameEnumByCondition(channelQuery);
      
      List<ChannelNameEnum> finalChannelNameEnums = new ArrayList<ChannelNameEnum>();
      
      for(ChannelNameEnum channelNameEnum : ChannelNameEnum.values()){
        if(!channelNameEnums.contains(channelNameEnum)){
          finalChannelNameEnums.add(channelNameEnum);
        }
      }
      
      //转化为KXWPEnumProperty
      List<KXWPEnumProperty> kxwpEnumProperties = new ArrayList<KXWPEnumProperty>();
      
      for(ChannelNameEnum channelNameEnum : finalChannelNameEnums){
        KXWPEnumProperty kxwpEnumProperty = new KXWPEnumProperty(channelNameEnum.getCode(), channelNameEnum.name(), channelNameEnum.getDesc());
        
        kxwpEnumProperties.add(kxwpEnumProperty);
      }
      //转化为KXWPEnumProperty
      
      exchangeData.setData(kxwpEnumProperties);
    } catch (Exception e) {
       exchangeData.setCallStatus(CallStatusEnum.FAIL);
       exchangeData.setMessage("查询可用频道名称异常，请联系管理员");
       
       KXWPLogUtils.logException(ChannelAction.class, "查询可用频道名称" + JacksonUtils.writeValue(exchangeData), e);
    }
    
    KXWPLogUtils.logInfo(ChannelAction.class, "查询可用频道名称" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  @RequestMapping(value="/addGoodsInit.do")
  public String addGoodsInit(HttpServletRequest request){
    request.setAttribute("supplierID", request.getParameter("supplierID"));
    return "/fwz/channel_addGoodsInit";
  }
  
  /**
   * 
   * queryGoodsRepoByCondition:(根据添加查询商品).
   *
   * 2016年9月22日 下午12:00:12
   * @author wangjun
   * @param query
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/ajax/goods/queryGoodsRepoByCondition.do")
  public @ResponseBody ExchangeData<List<Goods>> queryGoodsRepoByCondition(SearchGoodsRepoQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData<List<Goods>> edb = new ExchangeData<>();
    try {
      //TODO 修改判断
      query.setOrderBy("update_time desc");
      query.setGoods_statusEnum(GoodsStatusEnum.ONSALE);
      query.setLotsPrice(YNEnum.N);
      List<Goods> results = supplierManageGoodsServivce.listGoods(query);
      int total = supplierManageGoodsServivce.countSearchGoods(query);
      edb.getPager().setTotoalResults(total);
      edb.setData(results);
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }
  
  /**
   * 
   * addChannel:(添加频道).
   *
   * 2016年9月21日 上午9:47:30
   * @author wangjun
   * @param channel
   * @param request
   * @return
   */
  @RequestMapping(value="/ajax/addChannel.do")
  @ResponseBody
  public ExchangeData<Object> addChannel(@RequestBody Channel channel, HttpServletRequest request){
    KXWPLogUtils.logInfo(ChannelAction.class, "添加频道:入参------" + JacksonUtils.writeValue(channel));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      
      channel.setServiceStationId(userModel.getOrganizationId());
      channel.setChannelStatus(ChannelStatusEnum.VALID);
      channel.setCreateUserId(userModel.getId());
      Date date = new Date();
      channel.setCreateTime(date);
      channel.setUpdateTime(date);
      
      channelService.addMixture(channel);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("添加频道异常，请联系管理员");
      
      KXWPLogUtils.logException(ChannelAction.class, "添加频道：出参------" + JacksonUtils.writeValue(exchangeData), e);
    }
    
    KXWPLogUtils.logInfo(ChannelAction.class, "添加频道：出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  /**
   * 
   * listChannelInit:(查询频道列表初始化).
   *
   * 2016年9月21日 上午9:48:34
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/listChannelInit.do")
  public String listChannelInit(){
    return "/fwz/channel_list";
  }
  
  /**
   * 
   * listChannel:(查询频道列表).
   *
   * 2016年9月21日 上午10:16:51
   * @author wangjun
   * @param channelQuery
   * @param request
   * @return
   */
  @RequestMapping(value="/ajax/listChannel.do")
  @ResponseBody
  public ExchangeData<List<Channel>> listChannel(@RequestBody ChannelQuery channelQuery, HttpServletRequest request){
    KXWPLogUtils.logInfo(ChannelAction.class, "查询频道列表:入参------" + JacksonUtils.writeValue(channelQuery));
    
    ExchangeData<List<Channel>> exchangeData = new ExchangeData<List<Channel>>();
    
    try {
      UserModel userModel = FWZUserSessionUtils.getUserSession(request);
      
      channelQuery.setServiceStationId(userModel.getOrganizationId());
      
      List<Channel> channels = channelService.listByConditionPaging(channelQuery);
      
      int totalResults = channelService.countTotalResultsByExample(channelQuery);
      
      exchangeData.getPager().setTotoalResults(totalResults);
      exchangeData.setData(channels);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("查询频道列表异常，请联系管理员");
      
      KXWPLogUtils.logException(ChannelAction.class, "查询频道列表:出参------" + JacksonUtils.writeValue(exchangeData), e);
    }
    
    KXWPLogUtils.logInfo(ChannelAction.class, "查询频道列表：出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  /**
   * 
   * getChannelInit:(查询单个频道初始化).
   *
   * 2016年9月21日 上午10:46:54
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/getChannelInit.do")
  public String getChannelInit(HttpServletRequest request){
    request.setAttribute("channelId", request.getParameter("channelId"));
    
    return "/fwz/channel_edit";
  }
  
  /**
   * 
   * getChannel:(查询频道).
   *
   * 2016年9月21日 上午11:02:54
   * @author wangjun
   * @param channelId
   * @return
   */
  @RequestMapping(value="/ajax/getChannel.do")
  @ResponseBody
  public ExchangeData<Channel> getChannel(Long channelId){
    KXWPLogUtils.logInfo(ChannelAction.class, "查询频道:入参------" + JacksonUtils.writeValue(channelId));
    
    ExchangeData<Channel> exchangeData = new ExchangeData<Channel>();
    
    try {
      Channel channel = channelService.getMixture(channelId);
      
      exchangeData.setData(channel);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("查询频道异常，请联系管理员");
      
      KXWPLogUtils.logException(ChannelAction.class, "查询频道:出参------" + JacksonUtils.writeValue(exchangeData), e);
    }
    
    KXWPLogUtils.logInfo(ChannelAction.class, "查询频道：出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  /**
   * 
   * modifyChannel:(修改频道).
   *
   * 2016年9月21日 上午11:33:20
   * @author wangjun
   * @param channel
   * @return
   */
  @RequestMapping(value="/ajax/modifyChannel.do")
  @ResponseBody
  public ExchangeData<Object> modifyChannel(@RequestBody Channel channel){
    KXWPLogUtils.logInfo(ChannelAction.class, "修改频道：入参------" + JacksonUtils.writeValue(channel));
    
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    try {
      channel.setUpdateTime(new Date());
      channelService.modifyMixture(channel);
    } catch (Exception e) {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("修改频道异常，请联系管理员");
      
      KXWPLogUtils.logException(ChannelAction.class, "修改频道:出参------" + JacksonUtils.writeValue(channel), e);
    }
    
    KXWPLogUtils.logInfo(ChannelAction.class, "修改频道：出参------" + JacksonUtils.writeValue(exchangeData));
    
    return exchangeData;
  }
  
  /**
   * 
   * getChannelInit:(查看单个频道初始化).
   *
   * 2016年9月21日 上午10:46:54
   * @author wangjun
   * @return
   */
  @RequestMapping(value="/viewChannelInit.do")
  public String viewChannelInit(HttpServletRequest request){
    request.setAttribute("channelId", request.getParameter("channelId"));
    
    return "/fwz/channel_view";
  }
}
