package com.kxwp.admin.service.supermarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.entity.supermarket.SupermarketAccount;
import com.kxwp.admin.entity.supermarket.SupermarketAccountExample;
import com.kxwp.admin.mapper.serviceStation.ServiceStationMapper;
import com.kxwp.admin.mapper.supermarket.SupermarketAccountMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.service.serviceStation.FWZCommonService;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.NotificationConsumerEnum;
import com.kxwp.common.constants.ReviewTypeEnum;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.constants.SupermarketStausEnum;
import com.kxwp.common.constants.SystemTypeEnum;
import com.kxwp.common.entity.SYSNotification;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.SysReview;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.form.supermarket.admin.SupermarketCreateForm;
import com.kxwp.common.form.supermarket.admin.SupermarketUpdateStatusInfo;
import com.kxwp.common.form.supermarket.admin.UpdateSupermarketForm;
import com.kxwp.common.mapper.supermarket.SupermarketMapper;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.supermarket.SupermarketQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.query.sys.RegionQuery;
import com.kxwp.common.query.sys.ReviewQuery;
import com.kxwp.common.service.core.NotificationService;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.core.ReviewService;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.PhoneNumberFormat;
import com.kxwp.common.utils.SMSTemplateUtil;


/**
 * date: 2016年8月13日 下午3:31:32
 *
 * @author zhaojn
 */
@Service("SupermarketService")
public class SupermarketService {
  static final String SEND_PASSWORD_YES = "1";
  static final String MATCH_SUCCESS_DESC = "服务站匹配成功";
  static final String REVIEW_SUCCESS_DESC = "审核资料成功";
  @Autowired
  private OddNumberService oddNumberService;
  
  @Autowired
  private SupermarketMapper supermarketMapper;

  @Autowired
  private ServiceStationMapper serviceStationMapper;

  @Autowired
  private RegionService regionService;

  @Autowired
  private FWZCommonService fwzInfoService;


  @Autowired
  private SupermarketAccountMapper supermarketAccountMapper;

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private NotificationService notificationService;

  /**
   * @throws searchByContion:(按条件查询).
   *
   * 2016年8月13日 下午3:31:40 @author zhaojn @param supermarketQuery @return @throws
   * KXWPNotFoundException @throws
   * 
   */
  public List<Supermarket> searchByQuery( SupermarketQuery supermarketQuery)
      throws KXWPNotFoundException {
    List<Supermarket> supermarket_list = new ArrayList<>();
    if(supermarketQuery.getId() != null){
      //根据id查询
      supermarket_list.add(supermarketMapper.selectByPrimaryKey(supermarketQuery.getId()));
    }else{
      supermarket_list = supermarketMapper.selectByQuery(supermarketQuery);
    }
        
       
    for (Supermarket supermarket : supermarket_list) {
      // 添加完整地址
      SupermarketAccount account =
          supermarketAccountMapper.selectBySupermarketId(supermarket.getId());
      if (account == null) {
        throw new KXWPNotFoundException("超市[" + supermarket.getId() + "]未绑定账户");
      }
      supermarket = searchFullAddress(supermarket);
      supermarket.setAccountMobile(account.getMobile());
      if (supermarket.getServiceStationId() != null) {
        ServiceStation fwzInfo =
            fwzInfoService.getServiceStationByID(supermarket.getServiceStationId());
        supermarket.setServiceStationName(fwzInfo.getServiceStationName());
      }
    }
    return supermarket_list;
  }

  
  /**
   * getSupreMarket:(根据ID获取超市信息).
   *
   * 2016年8月9日 下午4:21:12
   * 
   * @author zhaojn
   * @param id
   * @return
   * @throws KXWPNotFoundException
   * @throws LoginException
   * 
   */
  public Supermarket getSupreMarket(Long id) throws KXWPNotFoundException, LoginException {
    if (id == null) {
      throw new LoginException("supermarket：id不允许为空");
    }

    Supermarket superMarketInfo = supermarketMapper.selectByPrimaryKey(id);
    if (superMarketInfo == null) {
      throw new KXWPNotFoundException("超市不存在[" + id + "]");
    }
    SupermarketAccount account =
        supermarketAccountMapper.selectBySupermarketId(superMarketInfo.getId());
    if (account == null) {
      throw new KXWPNotFoundException("超市未绑定账号[" + id + "]");
    }
    addMappingName(superMarketInfo, account);
    return superMarketInfo;
  }

  /**
   * searchFullAddress:(封装好的查询全部supermarket完整地址的方法).
   *
   * 2016年8月9日 下午4:51:59
   * 
   * @author zhaojn
   * @param supermarket
   * @return
   * 
   */
  public Supermarket searchFullAddress(Supermarket supermarket) {
    StringBuilder full_address = new StringBuilder();
    SYSRegion ssx = selectRegionNameByCurrentRegionId(supermarket.getProvince().longValue());
    full_address.append(ssx.getName());
    ssx = selectRegionNameByCurrentRegionId(supermarket.getCity().longValue());
    full_address.append(ssx.getName());
    ssx = selectRegionNameByCurrentRegionId(supermarket.getCounty().longValue());
    if (ssx != null) {
      full_address.append(ssx.getName());
    }
    ssx = selectRegionNameByCurrentRegionId(supermarket.getTown().longValue());
    if (ssx != null) {
      full_address.append(ssx.getName());
    }
    if (StringUtils.isNotBlank(supermarket.getAdress())) {
      full_address.append(supermarket.getAdress());
    }
    supermarket.setFull_address(full_address.toString());

    return supermarket;
  }

  /**
   * selectRegionNameByCurrentRegionId:(根据当前ID查询region实体数据).
   *
   * 2016年8月9日 下午6:19:35
   * 
   * @author zhaojn
   * @param CurrentRegionId
   * @return
   * 
   */
  public SYSRegion selectRegionNameByCurrentRegionId(Long CurrentRegionId) {
    RegionQuery regionQuery = new RegionQuery();
    regionQuery.setCurrentRegionID(CurrentRegionId);
    SYSRegion ssx = regionService.queryCurrentRegion(regionQuery);
    return ssx;
  }

  /**
   * RegionMappingAddressName:(根据对应级联ID查询地址名称).
   *
   * 2016年8月10日 上午9:29:23
   * 
   * @author zhaojn
   * @param supermarket
   * @return
   * 
   */
  public Supermarket RegionMappingAddressName(Supermarket supermarket) {
    SYSRegion ssx = selectRegionNameByCurrentRegionId(supermarket.getProvince().longValue());
    supermarket.setProvinceName(ssx.getName());
    ssx = selectRegionNameByCurrentRegionId(supermarket.getCity().longValue());
    supermarket.setCityName(ssx.getName());

    ssx = selectRegionNameByCurrentRegionId(supermarket.getCounty().longValue());
    if (ssx != null) {
      supermarket.setCountyName(ssx.getName());
    }

    ssx = selectRegionNameByCurrentRegionId(supermarket.getTown().longValue());

    if (ssx != null) {
      supermarket.setTownName(ssx.getName());
    }
    return supermarket;
  }

  /**
   * selectServiceStationNameByServiceId:(通过服务站id查询服务站名称).
   *
   * 2016年8月10日 上午9:30:09
   * 
   * @author zhaojn
   * @param supermarket
   * @return
   * @throws KXWPNotFoundException
   * 
   */
  public Supermarket selectServiceStationNameByServiceId(Supermarket supermarket)
      throws KXWPNotFoundException {
    ServiceStation se =
        serviceStationMapper.selectByPrimaryKey(supermarket.getServiceStationId().longValue());
    if (se == null) {
      throw new KXWPNotFoundException("没有找到id为" + supermarket.getServiceStationId() + "的服务站");
    }
    supermarket.setServiceStationName(se.getServiceStationName());
    supermarket.setServiceStationPhone(PhoneNumberFormat.formatPhone(se.getPhone()));
    return supermarket;
  }

  /**
   * countByQuery:(查询符合条件的数据的数量).
   *
   * 2016年8月10日 上午9:31:31
   * 
   * @author zhaojn
   * @param supermarketQuery
   * @return
   * 
   */
  public int countByQuery(SupermarketQuery supermarketQuery) {
    //根据超市id查询
    if(supermarketQuery.getId() != null){
      return 1;
    }
    int total = supermarketMapper.countByQuery(supermarketQuery);
    return total;
  }

  /**
   * updateReviewingSupermarketStatus:(审核中超市信息状态变更).
   *
   * 2016年8月10日 上午9:54:49
   * 
   * @author zhaojn
   * @param supermarket
   * @return
   * @throws LoginException
   * 
   */
  public UpdateSupermarketForm updateReviewingSupermarketStatus(UpdateSupermarketForm form,
      UserModel user) throws LoginException {
    form.setCreateUserId(user.getId());
    Supermarket supermarket = supermarketMapper.selectByPrimaryKey(form.getId());
    
    int count = supermarketMapper.updateSupermarketSelective(form);
    if (count == 0) {
      throw new LoginException("id为" + form.getId() + "的超市信息状态变更失败");
    }
    
    //更新账号状态
    SupermarketAccount supermarketAccount = new SupermarketAccount();
    
    supermarketAccount.setSupmarketId(form.getId());
    
    if(form.getSupermarketStatus() == SupermarketStausEnum.ON){
      supermarketAccount.setAccountStatus(AccountStatusEnum.VALID);
    }else if(form.getSupermarketStatus() == SupermarketStausEnum.OFF){
      supermarketAccount.setAccountStatus(AccountStatusEnum.INVALID);
    }
    
    if(form.getSupermarketStatus() == SupermarketStausEnum.REJECT){
      supermarketAccount.setAccountStatus(AccountStatusEnum.INVALID);
    }
    
    supermarketAccountMapper.updateByCondition(supermarketAccount);
    
    SysReview review = new SysReview();
    review.setReviewSys(SystemTypeEnum.ServiceStation);
    review.setReviewType(ReviewTypeEnum.Supermarket);
    review.setReviewId(form.getId());
    review.setReviewUserId(user.getId());
    review.setBeforeReviewStatus(supermarket.getSupermarketStatus().getDesc());
    review.setAfterReviewStatus(form.getSupermarketStatus().getDesc());
    if (form.getSupermarketStatus() == SupermarketStausEnum.ON) {
      review.setDescribtion(REVIEW_SUCCESS_DESC);
    }else {
      review.setDescribtion(form.getStatusUpdateDescription());
    }

    review.setCreateTime(new Date());
    review.setUpdateTime(new Date());
    reviewService.addReview(review);
    return form;
  }


  /**
   * updateUnmatchSupermarketStatus:(更新超市状态).
   *
   * 2016年8月10日 下午9:10:55
   * 
   * @author zhaojn
   * @param supermarketUpdateStatusInfo
   * @param user
   * @return
   * @throws LoginException
   * 
   */
  public Supermarket updateUnmatchSupermarketStatus(
      SupermarketUpdateStatusInfo supermarketUpdateStatusInfo) throws LoginException {
    Supermarket supermarket =
        supermarketMapper.selectByPrimaryKey(supermarketUpdateStatusInfo.getId());
    if(supermarketUpdateStatusInfo.getServiceStationId() != null){
      supermarket.setServiceStationId(supermarketUpdateStatusInfo.getServiceStationId());
    }
    supermarket.setSupermarketStatus(supermarketUpdateStatusInfo.getNew_status());
    supermarket.setUpdateTime(new Date());
    int count = supermarketMapper.updateByPrimaryKeySelective(supermarket);
    if (count == 0) {
      throw new LoginException("更新超市id为" + supermarket.getId() + "数据失败");
    }
    return supermarket;
  }

  /**
   * addMappingName:(本类使用的私有方法，用来匹配表ID对应的name值放入到supermarket 实体类中).
   *
   * 2016年8月10日 下午1:20:23
   * 
   * @author zhaojn
   * @param supermarket
   * @param account
   * @return
   * @throws KXWPNotFoundException
   * 
   */
  private Supermarket addMappingName(Supermarket supermarket, SupermarketAccount account)
      throws KXWPNotFoundException {
    if (supermarket.getAccountMobile() == null) {
      supermarket.setAccountMobile(account.getMobile());
    }
    if (supermarket.getFull_address() == null) {
      supermarket = searchFullAddress(supermarket);
    }
    if (StringUtils.isEmpty(supermarket.getProvinceName())) {
      supermarket = RegionMappingAddressName(supermarket);
    }
    if (supermarket.getServiceStationId() != null) {
      selectServiceStationNameByServiceId(supermarket);
    }
    return supermarket;
  }

  /**
   * addNewSupermarket:(只能用于服务站添加超市账号).
   *
   * 2016年8月11日 下午5:38:00
   * 
   * @author zhaojn
   * @param form
   * @param user
   * @throws SYSException
   * @throws ConfigurationException
   * 
   */
  public void addNewSupermarket(SupermarketCreateForm form, UserModel user)
      throws SYSException, ConfigurationException {

    if (!form.isValid()) {
      throw new SYSException("请选择具体级联地址");
    }

    // 账号绑定的手机号不允许重复
    SupermarketAccountExample example = new SupermarketAccountExample();
    // example.createCriteria().andMobileEqualTo(form.getSupermarketCreateInfo().getAccountMobile());
    example.createCriteria().andMobileEqualTo(form.getAccountMobile());
    int count = supermarketAccountMapper.countByExample(example);
    if (count > 0) {
      // throw new SYSException("手机号 " + form.getSupermarketCreateInfo().getAccountMobile() +
      // "已绑定账号");
      throw new SYSException("手机号 " + form.getAccountMobile() + "已绑定账号");
    }
    // 放入超市图片编号
    OddNumberQuery query = new OddNumberQuery();
    query.setNumber_type(KXWPNumberRuleEnum.SUPERMARKET_NO);
    String supermarketNo = oddNumberService.newOddNumberViaProcedure(query);
    
    Supermarket supermarket = new Supermarket();
    // 设置超市地址信息
    supermarket.setSupermarketName(form.getSupermarketName());
    supermarket.setProvince(form.getProvince());
    supermarket.setCity(form.getCity());
    supermarket.setCounty(form.getCounty());
    supermarket.setTown(form.getTown());
    supermarket.setAdress(form.getAdress());
    supermarket.setReceptionName(form.getReceptionName());
    supermarket.setPhone(form.getPhone());
    supermarket.setLicenseNum(form.getLicenseNum());
    supermarket.setIdentityCardNum(form.getIdentityCardNum());
    supermarket.setSupermarketNo(supermarketNo);
    supermarket.setServiceStationId(user.getServiceStation().getId());
    Date now = new Date();
    supermarket.setCreateTime(now);
    supermarket.setUpdateTime(now);
    //服务站添加,直接开通
    supermarket.setSupermarketStatus(SupermarketStausEnum.ON);
    int supermarketCount = supermarketMapper.insertSelective(supermarket);
    if (supermarketCount == 0) {
      throw new SYSException("注册超市具体信息时失败");
    }
    // 设置超市会员初始化账号
    SupermarketAccount supermarketAccount = new SupermarketAccount();
    supermarketAccount.setName(form.getName());
    supermarketAccount.setMobile(form.getAccountMobile());
    supermarketAccount.setUserNo(supermarketNo);
    // supermarketAccount.setName(form.getSupermarketCreateInfo().getName());
    // supermarketAccount.setMobile(form.getSupermarketCreateInfo().getAccountMobile());

    // 加密前密码
    String randomPasswd = RandomStringUtils.randomAlphanumeric(8);
    supermarketAccount.setPassword(KXWPEncryptUtils.encryptPassword(randomPasswd));
    supermarketAccount.setMobile(form.getAccountMobile());
    // supermarketAccount.setMobile(form.getSupermarketCreateInfo().getAccountMobile());
    // 系统生成账号，直接开通
    supermarketAccount.setAccountStatus(AccountStatusEnum.VALID);
    supermarketAccount.setSupmarketId(supermarket.getId());
    supermarketAccount.setCreateTime(now);
    supermarketAccount.setUpdateTime(now);
    supermarketAccount.setCreateUserId(user.getId());
    int accountCount = supermarketAccountMapper.insertSelective(supermarketAccount);
    if (accountCount == 0) {
      throw new SYSException("注册超市登录账号时失败");
    }

    // 判断是否需要将生成的密码发送的手机账号中
    if (form.getSendPassword().equals(SEND_PASSWORD_YES) && form.getSendPassword() != null) {
      // 密码通过短信发送到指定手机账号
      SYSNotification sysNotification = new SYSNotification();
      sysNotification.setConsumer(NotificationConsumerEnum.SMS);
      sysNotification.setCreateTime(now);
      Map<String, String> valuesMap = new HashMap<>();
      valuesMap.put("password", randomPasswd);
      String msgContent = SMSTemplateUtil.getTemplate(SMSTemplateEnum.INIT_PASSWORD, valuesMap);
      sysNotification.setMsgContent(msgContent);
      // sysNotification.setReception(form.getSupermarketCreateInfo().getAccountMobile() + "");
      sysNotification.setReception(form.getAccountMobile() + "");
      notificationService.addNotification(sysNotification);
    }

  }

  /**
   * 
   * getReviewInfo:(根据id获取审核信息).
   *
   * 2016年9月9日 下午3:39:33
   * @author wangjun
   * @param string
   * @param id
   * @return
   */
  public String getReviewInfo(Long id) {
    ReviewQuery query = new ReviewQuery();
    
    query.setReviewId(id);
    query.setReviewType(ReviewTypeEnum.Supermarket);
    
    List<SysReview> sysReviews = reviewService.queryReview(query);
    
    return sysReviews.get(0).getDescribtion();
  }

}
