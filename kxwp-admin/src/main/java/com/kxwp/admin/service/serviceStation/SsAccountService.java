package com.kxwp.admin.service.serviceStation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccountExample;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.entity.serviceStation.SsRoleRelation;
import com.kxwp.admin.entity.serviceStation.SsRoleResourceRelation;
import com.kxwp.admin.mapper.serviceStation.ServiceStationAccountMapper;
import com.kxwp.admin.mapper.serviceStation.ServiceStationMapper;
import com.kxwp.admin.mapper.serviceStation.SsRoleRelationMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationRoleQuery;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.NotificationConsumerEnum;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.SYSNotification;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.page.BasePager;
import com.kxwp.common.model.page.MySQLPager;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.core.NotificationService;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.common.utils.SMSTemplateUtil;

/**
 * 服务站账号service date: 2016年8月3日 下午2:52:00
 *
 * @author wangjun
 */
@Service
public class SsAccountService {
  @Autowired
  private ServiceStationAccountMapper serviceStationAccountMapper;
  @Autowired
  private SsRoleRelationService ssRoleRelationService;
  @Autowired
  private SsRoleManageService ssRoleManageService;
  @Autowired
  private OddNumberService oddNumberService;
  @Autowired
  private SsResourceManageService ssResourceManageService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private SsRoleRelationMapper ssRoleRelationMapper;
  @Autowired
  private ServiceStationMapper serviceStationMapper;
  
  public void modifyComposite(ServiceStationAccount serviceStationAccount) throws ConfigurationException {
    this.modify(serviceStationAccount);

    List<SsRoleRelation> ssRoleRelations = new ArrayList<SsRoleRelation>();

    SsRoleRelation ssRoleRelation = null;

    for (SsRole ssRole : serviceStationAccount.getSsRoles()) {
      ssRoleRelation = new SsRoleRelation();
      if (ssRole.getId() == null)
        continue;
      ssRoleRelation.setRoleId(ssRole.getId());
      ssRoleRelation.setAccountId(serviceStationAccount.getId());
      ssRoleRelation.setRrStatus(RoleStatusEnum.VALID);

      ssRoleRelations.add(ssRoleRelation);
    }

    // 存储账号和账号角色关系
    ssRoleRelationService.remove(serviceStationAccount);
    ssRoleRelationService.addBatch(ssRoleRelations);

    sendNotification(serviceStationAccount);
  }

  public void resetPassword(ServiceStationAccount serviceStationAccount) throws SYSException, ConfigurationException {
    String encryptPassword = "";
    String random = "";

      random = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(3);
      encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    serviceStationAccount.setRandom(random);
    serviceStationAccount.setUserPassword(encryptPassword);
    serviceStationAccount.setAccountStatus(AccountStatusEnum.NEEDRESET);

    this.modify(serviceStationAccount);

    sendNotification(serviceStationAccount);

  }

  /**
   * 
   * modifyBatch:(批量修改账号).
   *
   * 2016年8月3日 下午8:05:05
   * 
   * @author wangjun
   */
  public void modifyStatusBatch(List<ServiceStationAccount> serviceStationAccounts) {
    this.modifyBatch(serviceStationAccounts);
  }

  /**
   * 
   * addComposite:(添加账号和账号角色关系).
   *
   * 2016年8月3日 下午4:27:45
   * 
   * @author wangjun
   * @param masterStationAccount
   * @return
   * @throws SYSException 
   * @throws ConfigurationException 
   */
  public void addComposite(ServiceStationAccount serviceStationAccount) throws SYSException, ConfigurationException {
    this.add(serviceStationAccount);

    List<SsRoleRelation> ssRoleRelations = new ArrayList<SsRoleRelation>();

    SsRoleRelation ssRoleRelation = null;

    for (SsRole msRole : serviceStationAccount.getSsRoles()) {
      ssRoleRelation = new SsRoleRelation();

      if (msRole.getId() == null)
        continue;
      ssRoleRelation.setRoleId(msRole.getId());
      ssRoleRelation.setAccountId(serviceStationAccount.getId());
      ssRoleRelation.setRrStatus(RoleStatusEnum.VALID);

      ssRoleRelations.add(ssRoleRelation);
    }

    // 存储账号和账号角色关系
    ssRoleRelationService.addBatch(ssRoleRelations);

    sendNotification(serviceStationAccount);
  }

  private void sendNotification(ServiceStationAccount serviceStationAccount) throws ConfigurationException {
    KXWPLogUtils.logInfo(this.getClass(), "重置密码" + serviceStationAccount.toString());

    SYSNotification notification = new SYSNotification();
    notification.setConsumer(NotificationConsumerEnum.SMS);
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("password", serviceStationAccount.getRandom());
      notification
          .setMsgContent(SMSTemplateUtil.getTemplate(SMSTemplateEnum.INIT_PASSWORD, valuesMap));
      notification.setReception(serviceStationAccount.getMobile().toString());
      notificationService.addNotification(notification);
  }

  /**
   * 
   * findMsRole:(获取角色信息).
   *
   * 2016年8月3日 下午6:57:09
   * 
   * @author wangjun
   * @return
   */
  public List<SsRole> findSsRole(UserModel userModel) {
    // 如果是服务站
    ServiceStationRoleQuery serviceStationRoleQuery = new ServiceStationRoleQuery();
    serviceStationRoleQuery.setRoleStatus(RoleStatusEnum.VALID);
    serviceStationRoleQuery.setPageSize(0);
    serviceStationRoleQuery.setServiceStationId(userModel.getOrganizationId());

    List<SsRole> ssRoles = ssRoleManageService.list(serviceStationRoleQuery);

    return ssRoles;
  }

  public List<ServiceStationAccount> getFinalResult(AccountQuery accountQuery, String flag) {
    accountQuery.setOffset(accountQuery.getPageSize() * (accountQuery.getCurrentPage() - 1));

    List<ServiceStationAccount> serviceStationAccounts;
    switch (flag) {
      case "alive":
        serviceStationAccounts = this.selectByAlive(accountQuery);
        break;
      case "dimission":
        serviceStationAccounts = this.selectByCondition(accountQuery);
        break;
      default:
        serviceStationAccounts = this.selectByCondition(accountQuery);
        break;
    }

    SsRoleRelation ssRoleRelation = null;

    Iterator<ServiceStationAccount> it = serviceStationAccounts.iterator();
    ServiceStationAccount serviceStationAccount;
    while (it.hasNext()) {
      serviceStationAccount = it.next();
      ssRoleRelation = new SsRoleRelation();
      ssRoleRelation.setRoleId(accountQuery.getRoleId());
      ssRoleRelation.setAccountId(serviceStationAccount.getId());

      List<SsRoleRelation> ssRoleRelations = null;

      ssRoleRelations = ssRoleRelationService.list(ssRoleRelation);

      // 筛选角色
      if (ssRoleRelations.size() == 0) {
        it.remove();
        continue;
      }

      ssRoleRelation.setRoleId(null);

      ssRoleRelations = ssRoleRelationService.list(ssRoleRelation);

      List<SsRole> ssRoles = new ArrayList<SsRole>();
      SsRole ssRole = null;
      for (SsRoleRelation ssRoleRelation2 : ssRoleRelations) {
        ssRole = new SsRole();

        ssRole = ssRoleManageService.get(ssRoleRelation2.getRoleId());

        ssRoles.add(ssRole);
      }

      serviceStationAccount.setSsRoles(ssRoles);
    }

    return serviceStationAccounts;
  }
  
  public List<ServiceStationAccount> getFinalResultImportData(AccountQuery accountQuery, String flag) {
    accountQuery.setOffset(accountQuery.getPageSize() * (accountQuery.getCurrentPage() - 1));
    
    List<ServiceStationAccount> serviceStationAccounts = serviceStationAccountMapper.selectImportData(accountQuery);
    
    return serviceStationAccounts;
  }

  public ExchangeData<Object> exchangeData(AccountQuery accountQuery, String flag) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    accountQuery.setOffset(null);

    List<ServiceStationAccount> serviceStationAccounts;
    switch (flag) {
      case "alive":
        serviceStationAccounts = this.selectByAlive(accountQuery);
        break;
      case "dimission":
        serviceStationAccounts = this.selectByCondition(accountQuery);
        break;
      default:
        serviceStationAccounts = this.selectByCondition(accountQuery);
        break;
    }

    SsRoleRelation ssRoleRelation = null;

    Iterator<ServiceStationAccount> it = serviceStationAccounts.iterator();
    ServiceStationAccount serviceStationAccount = null;
    while (it.hasNext()) {
      serviceStationAccount = it.next();
      ssRoleRelation = new SsRoleRelation();
      ssRoleRelation.setRoleId(accountQuery.getRoleId());
      ssRoleRelation.setAccountId(serviceStationAccount.getId());

      List<SsRoleRelation> ssRoleRelations = null;

      ssRoleRelations = ssRoleRelationService.list(ssRoleRelation);

      // 筛选角色
      if (ssRoleRelations.size() == 0) {
        it.remove();
      }
    }
    BasePager pager = new MySQLPager();
    pager.setCurrentPage(accountQuery.getCurrentPage());
    pager.setPageSize(accountQuery.getPageSize());
    pager.setTotoalResults(serviceStationAccounts.size());
    pager
        .setTotalPages((int) Math.ceil(serviceStationAccounts.size() / accountQuery.getPageSize()));

    exchangeData.setPager(pager);

    return exchangeData;
  }
  public ExchangeData<Object> exchangeDataImportData(AccountQuery accountQuery, String flag) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    accountQuery.setOffset(null);
    
    List<ServiceStationAccount> serviceStationAccounts = serviceStationAccountMapper.selectImportData(accountQuery);
    
    BasePager pager = new MySQLPager();
    pager.setCurrentPage(accountQuery.getCurrentPage());
    pager.setPageSize(accountQuery.getPageSize());
    pager.setTotoalResults(serviceStationAccounts.size());
    pager
    .setTotalPages((int) Math.ceil(serviceStationAccounts.size() / accountQuery.getPageSize()));
    
    exchangeData.setPager(pager);
    
    return exchangeData;
  }

  /**
   * 
   * add:(添加账号).
   *
   * 2016年8月3日 下午2:52:28
   * 
   * @author wangjun
   * @return
   * @throws SYSException 
   */
  public void add(ServiceStationAccount serviceStationAccount) throws SYSException {
    OddNumberQuery query = new OddNumberQuery();
    query.setNumber_type(KXWPNumberRuleEnum.USER_NO);
      serviceStationAccount.setUserNo(oddNumberService.newOddNumberViaProcedure(query));

    String encryptPassword = "";
    String random = "";

      random = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(3);
      encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    serviceStationAccount.setRandom(random);
    serviceStationAccount.setUserPassword(encryptPassword);
    serviceStationAccount.setAccountStatus(AccountStatusEnum.NEEDRESET);
    serviceStationAccount.setGrade("2");

    serviceStationAccountMapper.insertSelective(serviceStationAccount);
  }

  public ServiceStationAccount get(Long id) {
    ServiceStationAccount serviceStationAccount =
        serviceStationAccountMapper.selectByPrimaryKey(id);
    // 查询账号对应角色
    SsRoleRelation ssRoleRelation = new SsRoleRelation();

    ssRoleRelation.setAccountId(id);
    ssRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<SsRoleRelation> ssRoleRelations = ssRoleRelationService.list(ssRoleRelation);

    // 查询角色
    List<SsRole> ssRoles = new ArrayList<SsRole>();

    for (SsRoleRelation ssRoleRelation2 : ssRoleRelations) {
      SsRole ssRole = ssRoleManageService.get(ssRoleRelation2.getRoleId());

      ssRoles.add(ssRole);
    }

    serviceStationAccount.setSsRoles(ssRoles);

    return serviceStationAccount;
  }

  public void modifyBatch(List<ServiceStationAccount> records) {
    serviceStationAccountMapper.updateByPrimaryKeySelectiveBatch(records);
  }

  public void modify(ServiceStationAccount record) {
    serviceStationAccountMapper.updateByPrimaryKeySelective(record);
  }

  public List<ServiceStationAccount> selectByCondition(AccountQuery accountQuery) {
    return serviceStationAccountMapper.selectByCondition(accountQuery);
  }

  public List<ServiceStationAccount> selectByAlive(AccountQuery accountQuery) {
    return serviceStationAccountMapper.selectByAlive(accountQuery);
  }
  
  /**
   * 
   * getAccountInfo:(根据账号id查询账号信息 & 根据账号信息查询对应角色信息 & 根据角色信息查询对应资源信息).
   *
   * 2016年8月9日 下午3:29:10
   * 
   * @author wangjun
   * @param id
   * @return
   */
  public ServiceStationAccount getAccountInfo(Long id) {
    // 查询账号
    ServiceStationAccount serviceStationAccount = serviceStationAccountMapper.selectByPrimaryKey(id);

    // 查询账号对应角色
    SsRoleRelation ssRoleRelation = new SsRoleRelation();

    ssRoleRelation.setAccountId(id);
    ssRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<SsRoleRelation> ssRoleRelations = ssRoleRelationService.list(ssRoleRelation);

    // 查询角色
    List<SsRole> ssRoles = new ArrayList<SsRole>();
    SsRole ssRole = null;
    
    //存放所有角色对应资源合并在一起后的集合
    Set<SsResource> ssResourcesFinal1 = new TreeSet<SsResource>();
    Set<SsResource> ssResourcesFinal2 = new TreeSet<SsResource>();
    
    for (SsRoleRelation ssRoleRelation2 : ssRoleRelations) {
      // 获取角色信息
      ssRole = ssRoleManageService.getMixture(ssRoleRelation2.getRoleId());


      // 查询角色对应资源
      List<SsResource> ssResources = new ArrayList<SsResource>();
      SsResource ssResource = null;

      for (SsRoleResourceRelation ssRoleResourceRelation : ssRole.getSsRoleResourceRelations()) {
        ssResource = ssResourceManageService.get(ssRoleResourceRelation.getResourceId());
        
        if(StringUtils.equals(ssResource.getResourceStatus().name(), ResourceStatusEnum.OFFLINE.name())){
          continue;
        }
        
        ssResources.add(ssResource);
        
        //判断是第几层级的资源
        if(ssResource.getParentId() == 0){
          //如果msResourcesFinal1中没有数据
          if(ssResourcesFinal1.size() == 0){
            ssResourcesFinal1.add(ssResource);
          }
          //如果msResourcesFinal1中有数据
          else{
            //使用flag标记一级资源是否存在
            boolean flag = false;
            
            //循环判断资源是否存在
            for(SsResource ssResource0 : ssResourcesFinal1){
              if(ssResource0.getId().longValue() == ssResource.getId().longValue()){
                flag = true;
              }
            }
            
            //如果资源不存在
            if(!flag){
              ssResourcesFinal1.add(ssResource);
            }
          }
          
        }else if(ssResource.getParentId() != 0){
          ssResourcesFinal2.add(ssResource);
        }
      }

      ssRole.setSsResources(ssResources);


      // 将角色信息放入集合
      ssRoles.add(ssRole);
    }

    // 将角色信息放入账号信息
    serviceStationAccount.setSsRoles(ssRoles);
    
    for(SsResource ssResource : ssResourcesFinal1){
      for(SsResource ssResource0 : ssResourcesFinal2){
        if(ssResource0.getParentId() == ssResource.getId().longValue()){
          if(ssResource.getSsResources() == null){
            List<SsResource> ssResources = new ArrayList<SsResource>();
            
            ssResource.setSsResources(ssResources);
          }
          ssResource.getSsResources().add(ssResource0);
        }
      }
    }
     //将存放所有角色对应资源合并在一起后的集合放入账号信息
     serviceStationAccount.setSsResourcesFinal(ssResourcesFinal1);

    return serviceStationAccount;
  }

  /**
   * 
   * getAccountInfo:(根据账号id,url查询是否有权限访问).
   *
   * 2016年8月9日 下午3:29:10
   * 
   * @author wangjun
   * @param id url
   * @return
   */
  public Boolean isContainsURL(Long id, String url) {
    Boolean isContains = false;

    // 查询账号对应角色
    SsRoleRelation ssRoleRelation = new SsRoleRelation();

    ssRoleRelation.setAccountId(id);
    ssRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<SsRoleRelation> ssRoleRelations = ssRoleRelationService.list(ssRoleRelation);

    // 查询角色
    SsRole ssRole = null;

    for (SsRoleRelation ssRoleRelation2 : ssRoleRelations) {
      // 获取角色信息
      ssRole = ssRoleManageService.getMixture(ssRoleRelation2.getRoleId());


      // 查询角色对应资源
      SsResource ssResource = null;

      for (SsRoleResourceRelation ssRoleResourceRelation : ssRole.getSsRoleResourceRelations()) {
        ssResource = ssResourceManageService.get(ssRoleResourceRelation.getResourceId());
        
        if(StringUtils.equals(ssResource.getResourceStatus().name(), ResourceStatusEnum.OFFLINE.name())){
          continue;
        }
        
        if (StringUtils.equals(url, ssResource.getUrl())) {
          isContains = true;
          
          return isContains;
        }
      }
    }

    return isContains;
  }

  /**
   * 
   * checkRoleIsUsed:(检查角色是否有账户在使用).
   *
   * 2016年8月11日 上午11:33:27
   * 
   * @author wangjun
   * @param id
   * @return
   */
  public Boolean checkRoleIsUsed(Long id) {
    SsRoleRelation ssRoleRelation = new SsRoleRelation();
    ssRoleRelation.setRoleId(id);
    ssRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    if (ssRoleRelationService.list(ssRoleRelation).size() == 0) {
      return false;
    }

    return true;
  }

  public Boolean checkMobileIsUsed(Long mobile) {
    ServiceStationAccountExample example = new ServiceStationAccountExample();
    
    example.createCriteria().andMobileEqualTo(mobile);
    
    if (serviceStationAccountMapper.countByExample(example) == 0) {
      return false;
    }

    return true;
  }
  
  public List<SsRole> getAllRole(AccountQuery accountQuery) {
    ServiceStationRoleQuery serviceStationRoleQuery = new ServiceStationRoleQuery();
    serviceStationRoleQuery.setRoleStatus(RoleStatusEnum.VALID);
    serviceStationRoleQuery.setServiceStationId(accountQuery.getServiceStationId());
    
    return ssRoleManageService.getAllRole(serviceStationRoleQuery);
  }

  public void initRole(ServiceStationAccount serviceStationAccount, UserModel user) throws KXWPException {
    ServiceStationAccount serviceStationAccountTemp = serviceStationAccountMapper.selectByPrimaryKey(serviceStationAccount.getId());
    
    ServiceStation serviceStation = serviceStationMapper.selectByPrimaryKey(serviceStationAccountTemp.getServiceStationId());
    //给账号添加角色---开始
      //查询该服务站的角色是否存在
    ServiceStationRoleQuery serviceStationRoleQuery = new ServiceStationRoleQuery();
    
    serviceStationRoleQuery.setPageSize(0);
    serviceStationRoleQuery.setServiceStationId(serviceStation.getId());
    
    List<SsRole> ssRoles = ssRoleManageService.list(serviceStationRoleQuery);
    
    SsRole role = null;
    
    if(ssRoles.size() <= 0){
      //初始化角色信息
      role = new SsRole();
      
      role.setName("admin");
      role.setRoleDescription("管理员（具有最高权限）");
      role.setServiceStationId(serviceStation.getId());
      role.setCreateUserId(user.getId());
      
        //初始化资源信息
      ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
      
      serviceStationQuery.setPageSize(0);
      
      List<SsResource> ssResources = ssResourceManageService.list(serviceStationQuery);
      
      role.setSsResources(ssResources);
      
        //插入角色及相关资源信息
      ssRoleManageService.addMixture(role);
    }
    else{
      role = ssRoles.get(0);
    }
    
      //插入账号角色关系信息
    SsRoleRelation record = new SsRoleRelation();
    
    record.setRoleId(role.getId());
    record.setAccountId(serviceStationAccount.getId());
    
    List<SsRoleRelation> ssRoleRelations = ssRoleRelationMapper.selectByCondition(record);
    
    if(ssRoleRelations.size() == 0){
      record.setCreateUserId(user.getId());
      record.setRrStatus(RoleStatusEnum.VALID);
      record.setCreateTime(new Date());
      record.setUpdateTime(new Date());
      
      ssRoleRelationMapper.insertSelective(record);
    }
    //给账号添加角色---结束
  }
  
  /**
   * 
   * previewResourceInfo:(预览资源信息).
   *
   * 2016年9月7日 下午2:39:06
   * @author wangjun
   * @param msRoles
   */
  public Set<SsResource> previewResourceInfo(List<SsRole> ssRoles){
    List<SsRoleResourceRelation> ssRoleResourceRelations = null;
    
    Set<SsResource> mSet = new TreeSet<SsResource>();
    Set<SsResource> mSet0 = new TreeSet<SsResource>();
    
    for(SsRole ssRole : ssRoles){
      ssRoleResourceRelations = ssRoleManageService.getMixture(ssRole.getId()).getSsRoleResourceRelations();
      
      SsResource ssResource = null;
      for(SsRoleResourceRelation ssRoleResourceRelation : ssRoleResourceRelations){
        ssResource = ssResourceManageService.get(ssRoleResourceRelation.getResourceId());
        
        if(ssResource.getParentId() == 0){
          mSet.add(ssResource);
        }else{
          mSet0.add(ssResource);
        }
      }
    }
    
    List<SsResource> ssResources = null;
    for(SsResource ssResource : mSet){
      ssResources = new ArrayList<SsResource>();
      
      for(SsResource ssResource0 : mSet0){
        if(ssResource0.getParentId() == ssResource.getId()){
          ssResources.add(ssResource0);
        }
      }
      
      ssResource.setSsResources(ssResources);
    }
    
    return mSet;
  }
}
