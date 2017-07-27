package com.kxwp.admin.service.supplier;

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
import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.entity.supplier.SuRole;
import com.kxwp.admin.entity.supplier.SuRoleRelation;
import com.kxwp.admin.entity.supplier.SuRoleResourceRelation;
import com.kxwp.admin.entity.supplier.SupplierAccount;
import com.kxwp.admin.entity.supplier.SupplierAccountExample;
import com.kxwp.admin.mapper.supplier.SuRoleRelationMapper;
import com.kxwp.admin.mapper.supplier.SupplierAccountMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.common.AccountQuery;
import com.kxwp.admin.query.supplier.SupplierQuery;
import com.kxwp.admin.query.supplier.SupplierRoleQuery;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.NotificationConsumerEnum;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.SYSNotification;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.mapper.supplier.SupplierMapper;
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
public class SuAccountService {
  @Autowired
  private SupplierAccountMapper supplierAccountMapper;

  @Autowired
  private SuRoleRelationService suRoleRelationService;

  @Autowired
  private SuRoleManageService suRoleManageService;

  @Autowired
  private OddNumberService oddNumberService;

  @Autowired
  private SuResourceManageService suResourceManageService;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private SupplierMapper supplierMapper;

  @Autowired
  private SuRoleRelationMapper suRoleRelationMapper;

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
  public void add(SupplierAccount supplierAccount) throws SYSException {
    OddNumberQuery query = new OddNumberQuery();
    query.setNumber_type(KXWPNumberRuleEnum.USER_NO);
    supplierAccount.setUserNo(oddNumberService.newOddNumberViaProcedure(query));

    String encryptPassword = "";
    String random = "";

    random = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(3);
    encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    supplierAccount.setRandom(random);
    supplierAccount.setUserPassword(encryptPassword);
    supplierAccount.setAccountStatus(AccountStatusEnum.NEEDRESET);
    supplierAccount.setGrade("2");

    supplierAccountMapper.insertSelective(supplierAccount);
  }

  public SupplierAccount get(Long id) {
    return supplierAccountMapper.selectByPrimaryKey(id);
  }

  public SupplierAccount getMixture(Long id) {
    SupplierAccount supplierAccount = this.get(id);

    // 查询账号对应角色
    SuRoleRelation suRoleRelation = new SuRoleRelation();

    suRoleRelation.setAccountId(id);
    suRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<SuRoleRelation> suRoleRelations = suRoleRelationService.list(suRoleRelation);

    List<SuRole> suRoles = new ArrayList<SuRole>();
    for (SuRoleRelation suRoleRelation2 : suRoleRelations) {
      SuRole suRole = suRoleManageService.get(suRoleRelation2.getRoleId());

      suRoles.add(suRole);
    }

    supplierAccount.setSuRoles(suRoles);

    return supplierAccount;
  }

  public void modifyBatch(List<SupplierAccount> records) {
    supplierAccountMapper.updateByPrimaryKeySelectiveBatch(records);
  }

  public void modify(SupplierAccount record) {
    supplierAccountMapper.updateByPrimaryKeySelective(record);
  }

  public List<SupplierAccount> selectByCondition(AccountQuery accountQuery) {
    return supplierAccountMapper.selectByCondition(accountQuery);
  }

  public List<SupplierAccount> selectByAlive(AccountQuery accountQuery) {
    return supplierAccountMapper.selectByAlive(accountQuery);
  }

  public ExchangeData<Object> exchangeData(AccountQuery accountQuery, String flag) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    accountQuery.setOffset(null);

    List<SupplierAccount> supplierAccounts;
    switch (flag) {
      case "alive":
        supplierAccounts = this.selectByAlive(accountQuery);
        break;
      case "dimission":
        supplierAccounts = this.selectByCondition(accountQuery);
        break;
      default:
        supplierAccounts = this.selectByCondition(accountQuery);
        break;
    }

    SuRoleRelation suRoleRelation = null;

    Iterator<SupplierAccount> it = supplierAccounts.iterator();
    SupplierAccount supplierAccount = null;
    while (it.hasNext()) {
      supplierAccount = it.next();
      suRoleRelation = new SuRoleRelation();
      suRoleRelation.setRoleId(accountQuery.getRoleId());
      suRoleRelation.setAccountId(supplierAccount.getId());

      List<SuRoleRelation> suRoleRelations = null;

      suRoleRelations = suRoleRelationService.list(suRoleRelation);

      // 筛选角色
      if (suRoleRelations.size() == 0) {
        it.remove();
      }
    }
    BasePager pager = new MySQLPager();
    pager.setCurrentPage(accountQuery.getCurrentPage());
    pager.setPageSize(accountQuery.getPageSize());
    pager.setTotoalResults(supplierAccounts.size());
    pager.setTotalPages((int) Math.ceil(supplierAccounts.size() / accountQuery.getPageSize()));

    exchangeData.setPager(pager);

    return exchangeData;

  }

  public ExchangeData<Object> exchangeDataImportData(AccountQuery accountQuery, String flag) {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();

    accountQuery.setOffset(null);

    List<SupplierAccount> supplierAccounts = supplierAccountMapper.selectImportData(accountQuery);

    BasePager pager = new MySQLPager();
    pager.setCurrentPage(accountQuery.getCurrentPage());
    pager.setPageSize(accountQuery.getPageSize());
    pager.setTotoalResults(supplierAccounts.size());
    pager.setTotalPages((int) Math.ceil(supplierAccounts.size() / accountQuery.getPageSize()));

    exchangeData.setPager(pager);

    return exchangeData;

  }

  public List<SupplierAccount> getFinalResult(AccountQuery accountQuery, String flag) {
    accountQuery.setOffset(accountQuery.getPageSize() * (accountQuery.getCurrentPage() - 1));

    List<SupplierAccount> supplierAccounts;
    switch (flag) {
      case "alive":
        supplierAccounts = this.selectByAlive(accountQuery);
        break;
      case "dimission":
        supplierAccounts = this.selectByCondition(accountQuery);
        break;
      default:
        supplierAccounts = this.selectByCondition(accountQuery);
        break;
    }

    SuRoleRelation suRoleRelation = null;

    Iterator<SupplierAccount> it = supplierAccounts.iterator();
    SupplierAccount supplierAccount = null;
    while (it.hasNext()) {
      supplierAccount = it.next();
      suRoleRelation = new SuRoleRelation();
      suRoleRelation.setRoleId(accountQuery.getRoleId());
      suRoleRelation.setAccountId(supplierAccount.getId());

      List<SuRoleRelation> suRoleRelations = null;

      suRoleRelations = suRoleRelationService.list(suRoleRelation);

      // 筛选角色
      if (suRoleRelations.size() == 0) {
        it.remove();
        continue;
      }

      suRoleRelation.setRoleId(null);

      suRoleRelations = suRoleRelationService.list(suRoleRelation);

      List<SuRole> suRoles = new ArrayList<SuRole>();
      SuRole suRole = null;
      for (SuRoleRelation suRoleRelation2 : suRoleRelations) {
        suRole = new SuRole();

        suRole = suRoleManageService.get(suRoleRelation2.getRoleId());

        suRoles.add(suRole);
      }

      supplierAccount.setSuRoles(suRoles);
    }

    return supplierAccounts;
  }

  public List<SupplierAccount> getFinalResultImportData(AccountQuery accountQuery, String flag) {
    accountQuery.setOffset(accountQuery.getPageSize() * (accountQuery.getCurrentPage() - 1));

    List<SupplierAccount> supplierAccounts = supplierAccountMapper.selectImportData(accountQuery);

    return supplierAccounts;
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
  public List<SuRole> findSuRole(UserModel userModel) {
    SupplierRoleQuery supplierRoleQuery = new SupplierRoleQuery();
    supplierRoleQuery.setRoleStatus(RoleStatusEnum.VALID);
    supplierRoleQuery.setPageSize(0);
    supplierRoleQuery.setSupplierId(userModel.getOrganizationId());

    List<SuRole> suRoles = suRoleManageService.list(supplierRoleQuery);

    return suRoles;
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
  public void addComposite(SupplierAccount supplierAccount)
      throws SYSException, ConfigurationException {
    this.add(supplierAccount);

    List<SuRoleRelation> suRoleRelations = new ArrayList<SuRoleRelation>();

    SuRoleRelation suRoleRelation = null;

    for (SuRole suRole : supplierAccount.getSuRoles()) {
      suRoleRelation = new SuRoleRelation();

      suRoleRelation.setRoleId(suRole.getId());
      suRoleRelation.setAccountId(supplierAccount.getId());
      suRoleRelation.setRrStatus(RoleStatusEnum.VALID);

      suRoleRelations.add(suRoleRelation);
    }

    // 存储账号和账号角色关系
    suRoleRelationService.addBatch(suRoleRelations);

    sendNotification(supplierAccount);

  }

  private void sendNotification(SupplierAccount supplierAccount) throws ConfigurationException {
    KXWPLogUtils.logInfo(this.getClass(), "重置密码" + supplierAccount.toString());

    SYSNotification notification = new SYSNotification();
    notification.setConsumer(NotificationConsumerEnum.SMS);
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("password", supplierAccount.getRandom());
    notification
        .setMsgContent(SMSTemplateUtil.getTemplate(SMSTemplateEnum.INIT_PASSWORD, valuesMap));
    notification.setReception(supplierAccount.getMobile().toString());
    notificationService.addNotification(notification);
  }

  public void modifyComposite(SupplierAccount supplierAccount) throws ConfigurationException {
    this.modify(supplierAccount);

    List<SuRoleRelation> suRoleRelations = new ArrayList<SuRoleRelation>();

    SuRoleRelation suRoleRelation = null;

    for (SuRole suRole : supplierAccount.getSuRoles()) {
      suRoleRelation = new SuRoleRelation();
      if (suRole.getId() == null)
        continue;
      suRoleRelation.setRoleId(suRole.getId());
      suRoleRelation.setAccountId(supplierAccount.getId());
      suRoleRelation.setRrStatus(RoleStatusEnum.VALID);

      suRoleRelations.add(suRoleRelation);
    }

    // 存储账号和账号角色关系
    suRoleRelationService.remove(supplierAccount);
    suRoleRelationService.addBatch(suRoleRelations);

    sendNotification(supplierAccount);
  }

  public void resetPassword(SupplierAccount supplierAccount)
      throws SYSException, ConfigurationException {
    String encryptPassword = "";
    String random = "";

    random = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(3);
    encryptPassword = KXWPEncryptUtils.encryptPassword(random);

    supplierAccount.setRandom(random);
    supplierAccount.setUserPassword(encryptPassword);
    supplierAccount.setAccountStatus(AccountStatusEnum.NEEDRESET);

    this.modify(supplierAccount);

    sendNotification(supplierAccount);
  }

  /**
   * 
   * modifyBatch:(批量修改账号).
   *
   * 2016年8月3日 下午8:05:05
   * 
   * @author wangjun
   */
  public void modifyStatusBatch(List<SupplierAccount> supplierAccounts) {
    this.modifyBatch(supplierAccounts);
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
  public SupplierAccount getAccountInfo(Long id) {
    // 查询账号
    SupplierAccount supplierAccount = supplierAccountMapper.selectByPrimaryKey(id);

    // 查询账号对应角色
    SuRoleRelation suRoleRelation = new SuRoleRelation();

    suRoleRelation.setAccountId(id);
    suRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<SuRoleRelation> suRoleRelations = suRoleRelationService.list(suRoleRelation);

    // 查询角色
    List<SuRole> suRoles = new ArrayList<SuRole>();
    SuRole suRole = null;

    // 存放所有角色对应资源合并在一起后的集合
    Set<SuResource> suResourcesFinal1 = new TreeSet<SuResource>();
    Set<SuResource> suResourcesFinal2 = new TreeSet<SuResource>();

    for (SuRoleRelation suRoleRelation2 : suRoleRelations) {
      // 获取角色信息
      suRole = suRoleManageService.getMixture(suRoleRelation2.getRoleId());


      // 查询角色对应资源
      List<SuResource> suResources = new ArrayList<SuResource>();
      SuResource suResource = null;

      for (SuRoleResourceRelation suRoleResourceRelation : suRole.getSuRoleResourceRelations()) {
        suResource = suResourceManageService.get(suRoleResourceRelation.getResourceId());

        if (StringUtils.equals(suResource.getResourceStatus().name(),
            ResourceStatusEnum.OFFLINE.name())) {
          continue;
        }

        suResources.add(suResource);

        // 判断是第几层级的资源
        if (suResource.getParentId() == 0) {
          // 如果msResourcesFinal1中没有数据
          if (suResourcesFinal1.size() == 0) {
            suResourcesFinal1.add(suResource);
          }
          // 如果msResourcesFinal1中有数据
          else {
            // 使用flag标记一级资源是否存在
            boolean flag = false;

            // 循环判断资源是否存在
            for (SuResource suResource0 : suResourcesFinal1) {
              if (suResource0.getId() == suResource.getId()) {
                flag = true;
              }
            }

            // 如果资源不存在
            if (!flag) {
              suResourcesFinal1.add(suResource);
            }
          }

        } else if (suResource.getParentId() != 0) {
          suResourcesFinal2.add(suResource);
        }
      }

      suRole.setSuResources(suResources);


      // 将角色信息放入集合
      suRoles.add(suRole);
    }

    // 将角色信息放入账号信息
    supplierAccount.setSuRoles(suRoles);

    for (SuResource suResource : suResourcesFinal1) {
      for (SuResource suResource0 : suResourcesFinal2) {
        if (suResource0.getParentId() == suResource.getId()) {
          if (suResource.getSuResources() == null) {
            List<SuResource> suResources = new ArrayList<SuResource>();

            suResource.setSuResources(suResources);
          }
            suResource.getSuResources().add(suResource0);
        }
      }
    }
    // 将存放所有角色对应资源合并在一起后的集合放入账号信息
    supplierAccount.setSuResourcesFinal(suResourcesFinal1);

    return supplierAccount;
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
    SuRoleRelation suRoleRelation = new SuRoleRelation();

    suRoleRelation.setAccountId(id);
    suRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    List<SuRoleRelation> suRoleRelations = suRoleRelationService.list(suRoleRelation);

    // 查询角色
    SuRole suRole = null;

    for (SuRoleRelation suRoleRelation2 : suRoleRelations) {
      // 获取角色信息
      suRole = suRoleManageService.getMixture(suRoleRelation2.getRoleId());


      // 查询角色对应资源
      SuResource suResource = null;

      for (SuRoleResourceRelation suRoleResourceRelation : suRole.getSuRoleResourceRelations()) {
        suResource = suResourceManageService.get(suRoleResourceRelation.getResourceId());

        if (StringUtils.equals(suResource.getResourceStatus().name(),
            ResourceStatusEnum.OFFLINE.name())) {
          continue;
        }

        if (StringUtils.equals(url, suResource.getUrl())) {
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
    SuRoleRelation suRoleRelation = new SuRoleRelation();
    suRoleRelation.setRoleId(id);
    suRoleRelation.setRrStatus(RoleStatusEnum.VALID);

    if (suRoleRelationService.list(suRoleRelation).size() == 0) {
      return false;
    }

    return true;
  }

  public Boolean checkMobileIsUsed(Long mobile) {
    SupplierAccountExample example = new SupplierAccountExample();

    example.createCriteria().andMobileEqualTo(mobile);

    if (supplierAccountMapper.countByExample(example) == 0) {
      return false;
    }

    return true;
  }

  public List<SuRole> getAllRole(AccountQuery accountQuery) {
    SupplierRoleQuery supplierRoleQuery = new SupplierRoleQuery();
    
    supplierRoleQuery.setRoleStatus(RoleStatusEnum.VALID);
    supplierRoleQuery.setSupplierId(accountQuery.getSupplier_id());
    
    return suRoleManageService.getAllRole(supplierRoleQuery);
  }

  public void initRole(SupplierAccount supplierAccount, UserModel user) throws KXWPException {
    SupplierAccount supplierAccountTemp =
        supplierAccountMapper.selectByPrimaryKey(supplierAccount.getId());

    Supplier supplier = supplierMapper.selectByPrimaryKey(supplierAccountTemp.getSupplierId());
    // 给账号添加角色---开始
    // 查询该服务站的角色是否存在
    SupplierRoleQuery supplierRoleQuery = new SupplierRoleQuery();

    supplierRoleQuery.setPageSize(0);
    supplierRoleQuery.setSupplierId(supplier.getId());

    List<SuRole> suRoles = suRoleManageService.list(supplierRoleQuery);

    SuRole role = null;

    if (suRoles.size() <= 0) {
      // 初始化角色信息
      role = new SuRole();

      role.setName("admin");
      role.setRoleDescription("服务站创建的供应商角色");
      role.setSupplierId(supplier.getId());
      role.setCreateUserId(user.getId());

      // 初始化资源信息
      SupplierQuery supplierQuery = new SupplierQuery();

      supplierQuery.setPageSize(0);

      List<SuResource> suResources = suResourceManageService.list(supplierQuery);

      role.setSuResources(suResources);

      // 插入角色及相关资源信息
      suRoleManageService.addMixture(role);
    } else {
      role = suRoles.get(0);
    }

    // 插入账号角色关系信息
    SuRoleRelation record = new SuRoleRelation();
    
    record.setRoleId(role.getId());
    record.setAccountId(supplier.getId());
    
    List<SuRoleRelation> suRoleRelations = suRoleRelationMapper.selectByCondition(record);
    
    if(suRoleRelations.size() == 0){
      record.setCreateUserId(user.getId());
      record.setRrStatus(RoleStatusEnum.VALID);
      record.setCreateTime(new Date());
      record.setUpdateTime(new Date());
      
      suRoleRelationMapper.insertSelective(record);
    }
  }
  
  /**
   * 
   * previewResourceInfo:(预览资源信息).
   *
   * 2016年9月7日 下午2:39:06
   * @author wangjun
   * @param msRoles
   */
  public Set<SuResource> previewResourceInfo(List<SuRole> suRoles){
    List<SuRoleResourceRelation> suRoleResourceRelations = null;
    
    Set<SuResource> mSet = new TreeSet<SuResource>();
    Set<SuResource> mSet0 = new TreeSet<SuResource>();
    
    for(SuRole suRole : suRoles){
      suRoleResourceRelations = suRoleManageService.getMixture(suRole.getId()).getSuRoleResourceRelations();
      
      SuResource suResource = null;
      for(SuRoleResourceRelation suRoleResourceRelation : suRoleResourceRelations){
        suResource = suResourceManageService.get(suRoleResourceRelation.getResourceId());
        
        if(suResource.getParentId() == 0){
          mSet.add(suResource);
        }else{
          mSet0.add(suResource);
        }
      }
    }
    
    List<SuResource> suResources = null;
    for(SuResource suResource : mSet){
      suResources = new ArrayList<SuResource>();
      
      for(SuResource suResource0 : mSet0){
        if(suResource0.getParentId() == suResource.getId()){
          suResources.add(suResource0);
        }
      }
      
      suResource.setSuResources(suResources);
    }
    
    return mSet;
  }
}
