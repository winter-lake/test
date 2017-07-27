package com.kxwp.admin.service.serviceStation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.masterStation.MasterStation;
import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccountExample;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.entity.serviceStation.SsRoleRelation;
import com.kxwp.admin.form.zz.FWZCreateForm;
import com.kxwp.admin.mapper.serviceStation.ServiceStationAccountMapper;
import com.kxwp.admin.mapper.serviceStation.ServiceStationMapper;
import com.kxwp.admin.mapper.serviceStation.SsRoleRelationMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationManagerQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationRoleQuery;
import com.kxwp.admin.service.masterStation.MasterStationService;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.FWZTypeEnum;
import com.kxwp.common.constants.NotificationConsumerEnum;
import com.kxwp.common.constants.OrganizationStatusEnum;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.entity.SYSNotification;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.fwz.SSServiceRegion;
import com.kxwp.common.mapper.fwz.FWZRegionMapper;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.fwz.FWZRegionQuery;
import com.kxwp.common.query.sys.RegionQuery;
import com.kxwp.common.service.core.NotificationService;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.fwz.FWZRegionService;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.SMSTemplateUtil;

/**
 * Date: 2016年8月8日 下午6:53:12
 * 
 * @author lou jian wen
 */
@Service("FWZManagerService")
public class FWZManagerService {


  @Autowired
  private FWZRegionService fwzRegionService;

  @Autowired
  private RegionService regionService;

  @Autowired
  private ServiceStationAccountMapper serviceStationAccountMapper;

  @Autowired
  private MasterStationService masterStationService;


  @Autowired
  private FWZRegionMapper fwzRegionMapper;


  @Autowired
  private NotificationService notificationService;

  @Autowired
  private ServiceStationMapper serviceStationMapper;


  @Autowired
  private FWZCommonService fwzInfoService;

  @Autowired
  private SsAccountService ssAccountService;

  @Autowired
  private SsRoleManageService ssRoleManageService;

  @Autowired
  private SsResourceManageService resourceManageService;

  @Autowired
  private SsRoleRelationMapper ssRoleRelationMapper;

  /**
   * 
   * getUnCheckedCounty:(查询当前未开通服务站的区县).
   *
   * 2016年8月20日 上午10:44:36
   * 
   * @author lou jian wen
   * @param regionQuery 传当前省市id
   * @return
   */
  public List<SYSRegion> getUnCheckedCounty(FWZRegionQuery fwzRegionQuery) {
    RegionQuery regionQuery = new RegionQuery();
    // 查询当前市以下区县
    regionQuery.setParentRegionID(fwzRegionQuery.getCity().longValue());;
    List<SYSRegion> results = regionService.queryCascadeRegion(regionQuery);
    for (SYSRegion region : results) {
      fwzRegionQuery.setCounty(region.getId());
      int opendCount = fwzRegionService.countByQuery(fwzRegionQuery);
      if (opendCount > 0) {
        region.setIsOpendFWZ(YNEnum.Y);
      }
    }
    return results;
  }



  /**
   * 
   * getFWZDetail:(获取服务站详情).
   *
   * 2016年8月9日 下午4:13:16
   * 
   * @author lou jian wen
   * @param query
   * @return
   * @throws KXWPNotFoundException
   */
  public ServiceStation getFWZDetail(ServiceStationManagerQuery query)
      throws KXWPNotFoundException {
    ServiceStation fwzInfo = fwzInfoService.getServiceStationByID(query.getId());

    // 查询账号信息
    AccountQuery accountQuery = new AccountQuery();
    accountQuery.setServiceStationId(query.getId());

    ServiceStationAccount serviceStationAccount =
        serviceStationAccountMapper.selectByCondition(accountQuery).get(0);

    fwzInfo.setServiceStationAccount(serviceStationAccount);

    RegionQuery regionQuery = new RegionQuery();

    // 获取省描述信息
    regionQuery.setCurrentRegionID(Long.parseLong(fwzInfo.getProvince().toString()));

    SYSRegion sysRegion = regionService.queryCurrentRegion(regionQuery);

    if (sysRegion != null) {
      fwzInfo.setProvince_desc(sysRegion.getName());
    }
    // 获取市描述信息
    regionQuery.setCurrentRegionID(Long.parseLong(fwzInfo.getCity().toString()));

    sysRegion = regionService.queryCurrentRegion(regionQuery);

    if (sysRegion != null) {
      fwzInfo.setCity_desc(sysRegion.getName());
    }
    // 获取县描述信息
    regionQuery.setCurrentRegionID(Long.parseLong(fwzInfo.getCounty().toString()));

    sysRegion = regionService.queryCurrentRegion(regionQuery);

    if (sysRegion != null) {
      fwzInfo.setCounty_desc(sysRegion.getName());
    }
    
    // 获取镇描述信息
    if(fwzInfo.getTown() != null){
      regionQuery.setCurrentRegionID(Long.parseLong(fwzInfo.getTown().toString()));
      sysRegion = regionService.queryCurrentRegion(regionQuery);
      if (sysRegion != null) {
        fwzInfo.setTown_desc(sysRegion.getName());
      }
    }
  

    return fwzInfo;
  }

  public void addServiceRegion(FWZCreateForm form) throws SYSException {

    SSServiceRegion ssServiceRegion = new SSServiceRegion();
    // 校验当前区域是否已经存在服务站
    FWZRegionQuery serviceRegionQuery = new FWZRegionQuery();

    for (Long county_id : form.getFwzCreateInfo().getCountyList()) {


      RegionQuery regionQuery = new RegionQuery();
      regionQuery.setCurrentRegionID(county_id);
      // 当前区县信息
      SYSRegion county = regionService.queryCurrentRegion(regionQuery);
      ssServiceRegion.setCounty(county.getId());
      serviceRegionQuery.setCounty(county.getId());
      // 市信息
      regionQuery.setCurrentRegionID(county.getParentId());
      SYSRegion city = regionService.queryCurrentRegion(regionQuery);
      ssServiceRegion.setCity(city.getId());
      serviceRegionQuery.setCity(city.getId());
      // 省信息
      regionQuery.setCurrentRegionID(city.getParentId());
      SYSRegion province = regionService.queryCurrentRegion(regionQuery);
      ssServiceRegion.setProvince(province.getId());
      serviceRegionQuery.setProvince(province.getId());
      // 判断当前区域是否已经存在服务站
      /*
       * List<SSServiceRegion> results = queryServiceRegion(serviceRegionQuery); if(results != null
       * && results.size() > 0){ throw new SYSException("当前区域已经存在服务站,不允许重复创建服务站"); }
       */
    }

    Date now = new Date();
    ssServiceRegion.setServiceStaionType(FWZTypeEnum.QXJI.name());
    ssServiceRegion.setCreateTime(now);
    ssServiceRegion.setUpdateTime(now);
    ssServiceRegion.setServiceStationId(form.getService_station_id());
    fwzRegionMapper.insertSelective(ssServiceRegion);
  }



  /**
   * 
   * addNewFWZ:(添加服务站).
   *
   * 2016年8月8日 下午1:39:16
   * 
   * @author lou jian wen
   * @param form
   * @throws ConfigurationException
   * @throws KXWPException
   */
  public void addNewFWZ(FWZCreateForm form, UserModel user)
      throws ConfigurationException, KXWPException {


    // 账号绑定的手机号不允许重复
    ServiceStationAccountExample example = new ServiceStationAccountExample();
    example.createCriteria().andMobileEqualTo(form.getFwz_admin_mobile());
    int count = serviceStationAccountMapper.countByExample(example);
    if (count > 0) {
      throw new SYSException("手机号 " + form.getFwz_admin_mobile() + "已绑定账号");
    }

    ServiceStation serviceStation = new ServiceStation();
    serviceStation.setServiceStationAdmin(form.getFwz_admin_name());
    serviceStation.setServiceStaionType(form.getFwzCreateInfo().getFwzType());
    // 先不设置服务站地址信息
    serviceStation.setCity(0);
    serviceStation.setCounty(0);
    serviceStation.setProvince(0);
    serviceStation.setTown(0);
    serviceStation.setAdress("");
    serviceStation.setStationStatus(OrganizationStatusEnum.VALID);
    Date now = new Date();
    serviceStation.setCreateTime(now);
    serviceStation.setUpdateTime(now);
    MasterStation masterStation = masterStationService.getUserMasterStation(user);
    serviceStation.setMasterStationId(masterStation.getId());
    serviceStation.setPhone(Long.parseLong(form.getFwzCreateInfo().getService_phone()));
    serviceStation.setCreateUserId(user.getId());
    serviceStation.setServiceStationName(form.getFwzCreateInfo().getFwz_name());
    serviceStation.setPlatformBzj(form.getFwzCreateInfo().getBzj());
    serviceStation.setPlatformFee(form.getFwzCreateInfo().getPlatformFee());
    serviceStationMapper.insertSelective(serviceStation);
    // 设置服务站管理员初始化账号
    ServiceStationAccount serviceStationAccount = new ServiceStationAccount();
    serviceStationAccount.setName(form.getFwz_admin_name());
    serviceStationAccount.setServiceStationId(serviceStation.getId());
    // 加密前密码
    String randomPasswd = RandomStringUtils.randomAlphanumeric(8);
    serviceStationAccount.setUserPassword(KXWPEncryptUtils.encryptPassword(randomPasswd));
    serviceStationAccount.setMobile(form.getFwz_admin_mobile());
    // 系统生成账号，需要重置密码
    serviceStationAccount.setAccountStatus(AccountStatusEnum.VALID);
    serviceStationAccount.setCreateUserId(user.getId());
    serviceStationAccount.setCreateTime(now);
    serviceStationAccount.setUpdateTime(now);
    serviceStationAccountMapper.insertSelective(serviceStationAccount);
    // 给账号添加角色---开始
    // 查询该服务站的角色是否存在
    ServiceStationRoleQuery serviceStationRoleQuery = new ServiceStationRoleQuery();

    serviceStationRoleQuery.setPageSize(0);
    serviceStationRoleQuery.setServiceStationId(serviceStation.getId());

    List<SsRole> ssRoles = ssRoleManageService.list(serviceStationRoleQuery);

    if (ssRoles.size() > 0) {
      throw new KXWPException("ServiceStationId为" + serviceStation.getId() + "的服务站角色已经存在！");
    }

    // 初始化角色信息
    SsRole role = new SsRole();

    role.setName("admin");
    role.setRoleDescription("管理员（具有最高权限）");
    role.setServiceStationId(serviceStation.getId());
    role.setCreateUserId(user.getId());

    // 初始化资源信息
    ServiceStationQuery serviceStationQuery = new ServiceStationQuery();

    serviceStationQuery.setPageSize(0);

    List<SsResource> ssResources = resourceManageService.list(serviceStationQuery);

    role.setSsResources(ssResources);

    // 插入角色及相关资源信息
    ssRoleManageService.addMixture(role);

    // 插入账号角色关系信息
    SsRoleRelation record = new SsRoleRelation();

    record.setRoleId(role.getId());
    record.setAccountId(serviceStationAccount.getId());
    record.setCreateUserId(user.getId());
    record.setRrStatus(RoleStatusEnum.VALID);
    record.setCreateTime(new Date());
    record.setUpdateTime(new Date());

    ssRoleRelationMapper.insertSelective(record);
    // 给账号添加角色---结束
    form.setService_station_id(serviceStation.getId());
    // 保存服务站的服务区域
    for (Long county : form.getFwzCreateInfo().getCountyList()) {
      SSServiceRegion fwzRegion = new SSServiceRegion();
      fwzRegion.setCity(form.getFwzCreateInfo().getCity());
      fwzRegion.setCounty(county);
      fwzRegion.setCreateTime(now);
      fwzRegion.setProvince(form.getFwzCreateInfo().getProvince());
      fwzRegion.setServiceStationId(form.getService_station_id());
      fwzRegion.setUpdateTime(now);
      fwzRegionService.addFWZRegion(fwzRegion);
    }


    // 密码通过短信发送到指定手机账号
    SYSNotification sysNotification = new SYSNotification();
    sysNotification.setConsumer(NotificationConsumerEnum.SMS);
    sysNotification.setCreateTime(now);
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("password", randomPasswd);
    String msgContent = SMSTemplateUtil.getTemplate(SMSTemplateEnum.INIT_PASSWORD, valuesMap);
    sysNotification.setMsgContent(msgContent);
    sysNotification.setReception(form.getFwz_admin_mobile() + "");
    notificationService.addNotification(sysNotification);
  }

  /**
   * 
   * countByQuery:(统计查询总数).
   *
   * 2016年8月9日 下午1:48:48
   * 
   * @author lou jian wen
   * @param query
   * @return
   */
  public int countByQuery(ServiceStationManagerQuery query) {
    // 通过id查询直接返回1
    if (query.getServiceStationID() != null) {
      return 1;
    }
    FWZRegionQuery fwzRegionQuery = new FWZRegionQuery();
    if (query.queryBySSX()) {
      query.copyQuery(fwzRegionQuery);
    }
    return fwzRegionMapper.countByQuery(fwzRegionQuery);
  }


  String formatServiceRegion(SSServiceRegion ssServiceRegion) {
    StringBuffer service_region_name = new StringBuffer();
    Long province = ssServiceRegion.getProvince();
    RegionQuery regionQuery = new RegionQuery();
    regionQuery.setCurrentRegionID(province.longValue());
    SYSRegion _province = regionService.queryCurrentRegion(regionQuery);
    service_region_name.append(_province.getName());
    Long city = ssServiceRegion.getCity();
    regionQuery.setCurrentRegionID(city.longValue());
    SYSRegion _city = regionService.queryCurrentRegion(regionQuery);
    service_region_name.append("/").append(_city.getName());// 设置市描述
    Long county = ssServiceRegion.getCounty();
    regionQuery.setCurrentRegionID(county.longValue());
    SYSRegion _county = regionService.queryCurrentRegion(regionQuery);
    service_region_name.append("/").append(_county.getName());
    return service_region_name.toString();
  }

  /**
   * 
   * queryServiceStation:(服务站查询).
   *
   * 2016年8月8日 下午6:54:48
   * 
   * @author lou jian wen
   * @param query
   * @return
   * @throws KXWPNotFoundException
   */
  public List<ServiceStation> queryServiceStation(ServiceStationManagerQuery query)
      throws KXWPNotFoundException {

    List<ServiceStation> results = new ArrayList<>();
    FWZRegionQuery fwzRegionQuery = new FWZRegionQuery();
    fwzRegionQuery.setPager(query.getPager());
    // 如果按服务站查询，直接显示服务站信息
    if (query.getServiceStationID() != null) {
      ServiceStation serviceStation =
          fwzInfoService.getServiceStationByID(query.getServiceStationID());
      results.add(serviceStation);
      fwzRegionQuery.setService_station_id(query.getServiceStationID());
      List<SSServiceRegion> fwzRegionList = fwzRegionService.queryServiceRegion(fwzRegionQuery);
      SSServiceRegion ssServiceRegion = fwzRegionList.get(0);

      // 把服务区域信息拼接好返回
      serviceStation.getService_region_list().add(formatServiceRegion(ssServiceRegion));
      return results;
    }

    if (query.queryBySSX()) {
      query.copyQuery(fwzRegionQuery);
    }
    // 查出满足条件的服务站
    List<SSServiceRegion> service_region_list =
        fwzRegionService.queryServiceStation(fwzRegionQuery);
    Map<String, ServiceStation> service_station_map = new HashMap<>();
    for (SSServiceRegion service_region : service_region_list) {
      service_region = fwzRegionService.getServiceRegionByID(service_region.getId());
      ServiceStation serviceStation = null;
      if (!service_station_map.containsKey(service_region.getServiceStationId() + "")) {
        serviceStation =
            serviceStationMapper.selectByPrimaryKey(service_region.getServiceStationId());
        service_station_map.put(service_region.getServiceStationId() + "", serviceStation);
        results.add(serviceStation);
        // 把服务区域信息拼接好返回
        serviceStation.getService_region_list().add(formatServiceRegion(service_region));
      }
    }

    return results;
  }

  /**
   * 
   * resetPassword:(重置密码).
   *
   * 2016年8月15日 下午8:27:13
   * 
   * @author wangjun
   * @param serviceStationAccount
   * @throws SYSException
   */
  public void resetPassword(ServiceStationAccount serviceStationAccount) throws SYSException {
    String encryptPassword = "";
    String random = "";

    random = RandomStringUtils.randomAlphanumeric(8);
    encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    serviceStationAccount.setRandom(random);
    serviceStationAccount.setUserPassword(encryptPassword);
    serviceStationAccount.setAccountStatus(AccountStatusEnum.NEEDRESET);
    ssAccountService.modify(serviceStationAccount);
  }

}

