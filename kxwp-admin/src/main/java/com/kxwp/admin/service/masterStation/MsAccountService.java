package com.kxwp.admin.service.masterStation;

import java.util.ArrayList;
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
import com.kxwp.admin.entity.masterStation.MasterStationAccount;
import com.kxwp.admin.entity.masterStation.MasterStationAccountExample;
import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.masterStation.MsRole;
import com.kxwp.admin.entity.masterStation.MsRoleRelation;
import com.kxwp.admin.entity.masterStation.MsRoleResourceRelation;
import com.kxwp.admin.mapper.masterStation.MasterStationAccountMapper;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.query.masterStation.MasterStationRoleQuery;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.NotificationConsumerEnum;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.SYSNotification;
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
 * 总站账号service date: 2016年8月3日 下午2:52:00
 *
 * @author wangjun
 */
@Service
public class MsAccountService {
  @Autowired
  private MasterStationAccountMapper masterStationAccountMapper;

  @Autowired
  private MsRoleRelationService msRoleRelationService;
  
  @Autowired
  private NotificationService notificationService;

  @Autowired
  private MsRoleManageService msRoleManageService;

  @Autowired
  private MsResourceManageService msResourceManageService;

  @Autowired
  private OddNumberService oddNumberService;

  /**
   * 
   * add:(添加账号).
   *
   * 2016年8月3日 下午2:52:28
   * 
   * @author wangjun
   * @param masterStationAccount
   * @return
   * @throws SYSException 
   */
  public void add(MasterStationAccount masterStationAccount) throws SYSException {
    OddNumberQuery query = new OddNumberQuery();
    query.setNumber_type(KXWPNumberRuleEnum.USER_NO);
    masterStationAccount.setUserNo(oddNumberService.newOddNumberViaProcedure(query));

    String encryptPassword = "";
    String random = "";

    random = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(3);
    encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    masterStationAccount.setRandom(random);
    masterStationAccount.setUserPassword(encryptPassword);
    masterStationAccount.setAccountStatus(AccountStatusEnum.NEEDRESET);
    masterStationAccount.setGrade("2");

    masterStationAccountMapper.insertSelective(masterStationAccount);
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
   * @throws ConfigurationException 
   * @throws SYSException 
   */
  public void addComposite(MasterStationAccount masterStationAccount) throws ConfigurationException, SYSException {

    // 存储账号信息
    this.add(masterStationAccount);

    List<MsRoleRelation> msRoleRelations = new ArrayList<MsRoleRelation>();

    MsRoleRelation msRoleRelation = null;

    for (MsRole msRole : masterStationAccount.getMsRoles()) {
      msRoleRelation = new MsRoleRelation();

      if (msRole.getId() == null)
        continue;
      msRoleRelation.setRoleId(msRole.getId());
      msRoleRelation.setAccountId(masterStationAccount.getId());
      msRoleRelation.setRrStatus(RoleStatusEnum.VALID);

      msRoleRelations.add(msRoleRelation);
    }

    // 存储账号和账号角色关系
    msRoleRelationService.addBatch(msRoleRelations);

    sendNotification(masterStationAccount);

  }

  private void sendNotification(MasterStationAccount masterStationAccount) throws ConfigurationException {
    KXWPLogUtils.logInfo(this.getClass(), "重置密码" + masterStationAccount.toString());

    SYSNotification notification = new SYSNotification();
    notification.setConsumer(NotificationConsumerEnum.SMS);
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("password", masterStationAccount.getRandom());
    notification
        .setMsgContent(SMSTemplateUtil.getTemplate(SMSTemplateEnum.INIT_PASSWORD, valuesMap));
    notification.setReception(masterStationAccount.getMobile().toString());
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
  public List<MsRole> findMsRole() {
    MasterStationRoleQuery masterStationRoleQuery = new MasterStationRoleQuery();
    masterStationRoleQuery.setRoleStatus(RoleStatusEnum.VALID);
    masterStationRoleQuery.setPageSize(0);

    return msRoleManageService.findAllRoles(masterStationRoleQuery);
  }

  /**
   * 
   * modifyBatch:(批量修改账号).
   *
   * 2016年8月3日 下午8:05:05
   * 
   * @author wangjun
   * @param mStationAccounts
   */
  public void modifyStatusBatch(List<MasterStationAccount> mStationAccounts) {

    masterStationAccountMapper.updateByPrimaryKeySelectiveBatch(mStationAccounts);

  }

  public void resetPassword(MasterStationAccount mStationAccounts) throws SYSException, ConfigurationException {
    String encryptPassword = "";
    String random = "";

    random = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(3);
    encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    mStationAccounts.setRandom(random);
    mStationAccounts.setUserPassword(encryptPassword);
    mStationAccounts.setAccountStatus(AccountStatusEnum.NEEDRESET);

    masterStationAccountMapper.updateByPrimaryKeySelective(mStationAccounts);

    sendNotification(mStationAccounts);

  }

  public MasterStationAccount get(Long id) {
    // 查询账号
    MasterStationAccount masterStationAccount = masterStationAccountMapper.selectByPrimaryKey(id);

    // 查询账号对应角色
    MsRoleRelation msRoleRelation = new MsRoleRelation();

    msRoleRelation.setAccountId(id);
    msRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<MsRoleRelation> msRoleRelations = msRoleRelationService.list(msRoleRelation);

    // 查询角色
    List<MsRole> msRoles = new ArrayList<MsRole>();
    MsRole msRole = null;

    for (MsRoleRelation msRoleRelation2 : msRoleRelations) {
      msRole = msRoleManageService.get(msRoleRelation2.getRoleId());

      msRoles.add(msRole);
    }

    masterStationAccount.setMsRoles(msRoles);

    return masterStationAccount;
  }

  public void modifyComposite(MasterStationAccount masterStationAccount) throws ConfigurationException {
    // 存储账号信息
    this.modify(masterStationAccount);

    List<MsRoleRelation> msRoleRelations = new ArrayList<MsRoleRelation>();

    MsRoleRelation msRoleRelation = null;

    for (MsRole msRole : masterStationAccount.getMsRoles()) {
      msRoleRelation = new MsRoleRelation();
      if (msRole.getId() == null)
        continue;
      msRoleRelation.setRoleId(msRole.getId());
      msRoleRelation.setAccountId(masterStationAccount.getId());
      msRoleRelation.setRrStatus(RoleStatusEnum.VALID);

      msRoleRelations.add(msRoleRelation);
    }

    // 存储账号和账号角色关系
    msRoleRelationService.remove(masterStationAccount);
    msRoleRelationService.addBatch(msRoleRelations);

    sendNotification(masterStationAccount);
  }

  public void modify(MasterStationAccount masterStationAccount) {
    masterStationAccountMapper.updateByPrimaryKeySelective(masterStationAccount);
  }

  public List<MasterStationAccount> getFinalResult(AccountQuery accountQuery, String flag) {

    accountQuery.setOffset(accountQuery.getPageSize() * (accountQuery.getCurrentPage() - 1));

    List<MasterStationAccount> masterStationAccounts;
    switch (flag) {
      case "alive":
        masterStationAccounts = masterStationAccountMapper.selectByAlive(accountQuery);
        break;
      case "dimission":
        masterStationAccounts = masterStationAccountMapper.selectByCondition(accountQuery);
        break;
      default:
        masterStationAccounts = masterStationAccountMapper.selectByCondition(accountQuery);
        break;
    }
    MsRoleRelation msRoleRelation = null;

    Iterator<MasterStationAccount> it = masterStationAccounts.iterator();
    MasterStationAccount masterStationAccount = null;

    while (it.hasNext()) {
      masterStationAccount = it.next();
      msRoleRelation = new MsRoleRelation();
      msRoleRelation.setRoleId(accountQuery.getRoleId());
      msRoleRelation.setAccountId(masterStationAccount.getId());

      List<MsRoleRelation> msRoleRelations = null;

      msRoleRelations = msRoleRelationService.list(msRoleRelation);

      // 筛选角色
      if (msRoleRelations.size() == 0) {
        it.remove();
        continue;
      }
      
      

      msRoleRelation.setRoleId(null);

      msRoleRelations = msRoleRelationService.list(msRoleRelation);
 
      List<MsRole> msRoles = new ArrayList<MsRole>();
      MsRole msRole = null;
      for (MsRoleRelation msRoleRelation2 : msRoleRelations) {
        msRole = new MsRole();

        msRole = msRoleManageService.get(msRoleRelation2.getRoleId());

        msRoles.add(msRole);
      }

      masterStationAccount.setMsRoles(msRoles);
    }

    return masterStationAccounts;
  }

  public ExchangeData<Object> exchangeData(AccountQuery accountQuery, String flag) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    accountQuery.setOffset(null);

    List<MasterStationAccount> masterStationAccounts;
    switch (flag) {
      case "alive":
        masterStationAccounts = masterStationAccountMapper.selectByAlive(accountQuery);
        break;
      case "dimission":
        masterStationAccounts = masterStationAccountMapper.selectByCondition(accountQuery);
        break;
      default:
        masterStationAccounts = masterStationAccountMapper.selectByCondition(accountQuery);
        break;
    }
    MsRoleRelation msRoleRelation = null;

    Iterator<MasterStationAccount> it = masterStationAccounts.iterator();
    MasterStationAccount masterStationAccount = null;

    while (it.hasNext()) {
      masterStationAccount = it.next();
      msRoleRelation = new MsRoleRelation();
      msRoleRelation.setRoleId(accountQuery.getRoleId());
      msRoleRelation.setAccountId(masterStationAccount.getId());

      List<MsRoleRelation> msRoleRelations = null;

      msRoleRelations = msRoleRelationService.list(msRoleRelation);

      // 筛选角色
      if (msRoleRelations.size() == 0) {
        it.remove();
      }
    }
    BasePager pager = new MySQLPager();
    pager.setCurrentPage(accountQuery.getCurrentPage());
    pager.setPageSize(accountQuery.getPageSize());
    pager.setTotoalResults(masterStationAccounts.size());
    pager.setTotalPages((int) Math.ceil(masterStationAccounts.size() / accountQuery.getPageSize()));

    exchangeData.setPager(pager);

    return exchangeData;
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
  public MasterStationAccount getAccountInfo(Long id) {
    // 查询账号
    MasterStationAccount masterStationAccount = masterStationAccountMapper.selectByPrimaryKey(id);

    // 查询账号对应角色
    MsRoleRelation msRoleRelation = new MsRoleRelation();

    msRoleRelation.setAccountId(id);
    msRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<MsRoleRelation> msRoleRelations = msRoleRelationService.list(msRoleRelation);

    // 查询角色
    List<MsRole> msRoles = new ArrayList<MsRole>();
    MsRole msRole = null;
    
    //存放所有角色对应资源合并在一起后的集合
    Set<MsResource> msResourcesFinal1 = new TreeSet<MsResource>();
    Set<MsResource> msResourcesFinal2 = new TreeSet<MsResource>();
    
    for (MsRoleRelation msRoleRelation2 : msRoleRelations) {
      // 获取角色信息
      msRole = msRoleManageService.getMixture(msRoleRelation2.getRoleId());


      // 查询角色对应资源
      List<MsResource> msResources = new ArrayList<MsResource>();
      MsResource msResource = null;

      for (MsRoleResourceRelation msRoleResourceRelation : msRole.getMsRoleResourceRelations()) {
        msResource = msResourceManageService.get(msRoleResourceRelation.getResourceId());
        
        msResources.add(msResource);
        
        if(StringUtils.equals(msResource.getResourceStatus().name(), ResourceStatusEnum.OFFLINE.name())){
          continue;
        }
        //判断是第几层级的资源
        if(msResource.getParentId() == 0){
          //如果msResourcesFinal1中没有数据
          if(msResourcesFinal1.size() == 0){
            msResourcesFinal1.add(msResource);
          }
          //如果msResourcesFinal1中有数据
          else{
            //使用flag标记一级资源是否存在
            boolean flag = false;
            
            //循环判断资源是否存在
            for(MsResource msResource0 : msResourcesFinal1){
              if(msResource0.getId().longValue() == msResource.getId().longValue()){
                flag = true;
              }
            }
            
            //如果资源不存在
            if(!flag){
              msResourcesFinal1.add(msResource);
            }
          }
          
        }else if(msResource.getParentId() != 0){
          msResourcesFinal2.add(msResource);
        }
        
      }

      msRole.setMsResources(msResources);


      // 将角色信息放入集合
      msRoles.add(msRole);
    }

    // 将角色信息放入账号信息
    masterStationAccount.setMsRoles(msRoles);
    
   for(MsResource msResource : msResourcesFinal1){
     for(MsResource msResource0 : msResourcesFinal2){
       if(msResource0.getParentId().longValue() == msResource.getId().longValue()){
         if(msResource.getMsResources() == null){
           List<MsResource> msResources = new ArrayList<MsResource>();
           
           msResource.setMsResources(msResources);
         }
         msResource.getMsResources().add(msResource0);
       }
     }
   }
    //将存放所有角色对应资源合并在一起后的集合放入账号信息
    masterStationAccount.setMsResourcesFinal(msResourcesFinal1);
    
    return masterStationAccount;
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
    MsRoleRelation msRoleRelation = new MsRoleRelation();

    msRoleRelation.setAccountId(id);
    msRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<MsRoleRelation> msRoleRelations = msRoleRelationService.list(msRoleRelation);

    // 查询角色
    MsRole msRole = null;

    for (MsRoleRelation msRoleRelation2 : msRoleRelations) {
      // 获取角色信息
      msRole = msRoleManageService.getMixture(msRoleRelation2.getRoleId());


      // 查询角色对应资源
      MsResource msResource = null;

      for (MsRoleResourceRelation msRoleResourceRelation : msRole.getMsRoleResourceRelations()) {
        msResource = msResourceManageService.get(msRoleResourceRelation.getResourceId());
        
        if(StringUtils.equals(msResource.getResourceStatus().name(), ResourceStatusEnum.OFFLINE.name())){
          continue;
        }
        
        if (StringUtils.equals(url, msResource.getUrl())) {
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
    MsRoleRelation msRoleRelation = new MsRoleRelation();
    msRoleRelation.setRoleId(id);
    msRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    if (msRoleRelationService.list(msRoleRelation).size() == 0) {
      return false;
    }

    return true;
  }

  public Boolean checkMobileIsUsed(Long mobile) {
    MasterStationAccountExample example = new MasterStationAccountExample();
    
    example.createCriteria().andMobileEqualTo(mobile);
    
    if (masterStationAccountMapper.countByExample(example) == 0) {
      return false;
    }

    return true;
  }

  public List<MsRole> getAllRole() {
    MasterStationRoleQuery masterStationRoleQuery = new MasterStationRoleQuery();
    masterStationRoleQuery.setRoleStatus(RoleStatusEnum.VALID);
    
    return msRoleManageService.getAllRole(masterStationRoleQuery);
  }
  
  /**
   * 
   * previewResourceInfo:(预览资源信息).
   *
   * 2016年9月7日 下午2:39:06
   * @author wangjun
   * @param msRoles
   */
  public Set<MsResource> previewResourceInfo(List<MsRole> msRoles){
    List<MsRoleResourceRelation> msRoleResourceRelations = null;
    
    Set<MsResource> mSet = new TreeSet<MsResource>();
    Set<MsResource> mSet0 = new TreeSet<MsResource>();
    
    for(MsRole msRole : msRoles){
      msRoleResourceRelations = msRoleManageService.getMixture(msRole.getId()).getMsRoleResourceRelations();
      
      MsResource msResource = null;
      for(MsRoleResourceRelation msRoleResourceRelation : msRoleResourceRelations){
        msResource = msResourceManageService.get(msRoleResourceRelation.getResourceId());
        
        if(msResource.getParentId() == 0){
          mSet.add(msResource);
        }else{
          mSet0.add(msResource);
        }
      }
    }
    
    List<MsResource> msResources = null;
    for(MsResource msResource : mSet){
      msResources = new ArrayList<MsResource>();
      
      for(MsResource msResource0 : mSet0){
        if(msResource0.getParentId() == msResource.getId()){
          msResources.add(msResource0);
        }
      }
      
      msResource.setMsResources(msResources);
    }
    
    return mSet;
  }
}
