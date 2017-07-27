package com.kxwp.admin.service.supplier;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.entity.supplier.SuRole;
import com.kxwp.admin.entity.supplier.SuRoleRelation;
import com.kxwp.admin.entity.supplier.SupplierAccount;
import com.kxwp.admin.entity.supplier.SupplierAccountExample;
import com.kxwp.admin.form.fwz.CreateGysForm;
import com.kxwp.admin.mapper.supplier.SuRoleRelationMapper;
import com.kxwp.admin.mapper.supplier.SupplierAccountMapper;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.supplier.SupplierQuery;
import com.kxwp.admin.query.supplier.SupplierRoleQuery;
import com.kxwp.admin.service.supermarket.SupermarketService;
import com.kxwp.common.constants.AccountStatusEnum;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.constants.GoodsStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.NotificationConsumerEnum;
import com.kxwp.common.constants.OrganizationStatusEnum;
import com.kxwp.common.constants.ReviewTypeEnum;
import com.kxwp.common.constants.SMSTemplateEnum;
import com.kxwp.common.constants.SystemTypeEnum;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.entity.SYSNotification;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.SysReview;
import com.kxwp.common.entity.supplier.AgentBrand;
import com.kxwp.common.entity.supplier.BusinessScope;
import com.kxwp.common.entity.supplier.GoodsShippingArea;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.mapper.supplier.AgentBrandMapper;
import com.kxwp.common.mapper.supplier.BusinessScopeMapper;
import com.kxwp.common.mapper.supplier.GoodsShippingAreaMapper;
import com.kxwp.common.mapper.supplier.SupplierMapper;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.uc.LoginUserInfo;
import com.kxwp.common.query.goods.UpdateGoodsStatusQuery;
import com.kxwp.common.query.supplier.SupplierManagerQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.agentBrand.AgentBrandService;
import com.kxwp.common.service.businessScope.BusinessScopeService;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.service.core.NotificationService;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.service.core.ReviewService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.PhoneNumberFormat;
import com.kxwp.common.utils.SMSTemplateUtil;

/**
 * date: 2016年8月13日 下午3:29:07
 *
 * @author zhaojn
 */
@Service
public class GYSManagerService {

  @Autowired
  private SupplierMapper supplierMapper;

  @Autowired
  private SupermarketService supermarketService;

  @Autowired
  private SupplierAccountMapper supplierAccountMapper;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private BusinessScopeService businessScopeService;

  @Autowired
  private OddNumberService oddNumberService;

  @Autowired
  private AgentBrandService agentBrandService;

  @Autowired
  private GoodsShippingAreaMapper goodsShippingAreaMapper;

  @Autowired
  private SuRoleManageService suRoleManageService;

  @Autowired
  private SuResourceManageService suResourceManageService;

  @Autowired
  private SuRoleRelationMapper suRoleRelationMapper;
  
  @Autowired
  private ReviewService reviewService;

  @Autowired
  private BusinessScopeMapper businessScopeMapper;
  
  @Autowired
  private AgentBrandMapper agentBrandMapper;
  
  @Autowired
  private AbstractCacheService cacheService;
  
  @Autowired
  private GoodsRepoService goodsRepoService;


  public List<Supplier> selectSupplierByQuery(SupplierManagerQuery supplierManagerQuery) {
    List<Supplier> supplier_list = new ArrayList<>();

    if (supplierManagerQuery.getId() != null) {
      // 按供应商搜索，直接返回
      Supplier supplier = supplierMapper.selectByPrimaryKey(supplierManagerQuery.getId());
      supplier_list.add(supplier);
    } else {
      supplier_list = supplierMapper.selectByQuery(supplierManagerQuery);
    }

    for (Supplier supplier : supplier_list) {
      supplier = searchFullAddress(supplier);
      // 返回的数据差业务范围相关 ，现在没有相关逻辑故省略
    }
    return supplier_list;

  }

  /**
   * searchFullAddress:(查询供应商地址完整地址信息).
   *
   * 2016年8月13日 下午3:27:32
   * 
   * @author zhaojn
   * @param supplier
   * @return
   * 
   */
  public Supplier searchFullAddress(Supplier supplier) {
    StringBuilder full_address = new StringBuilder();
    SYSRegion ssx =
        supermarketService.selectRegionNameByCurrentRegionId(supplier.getProvince().longValue());
    full_address.append(ssx.getName());
    ssx = supermarketService.selectRegionNameByCurrentRegionId(supplier.getCity().longValue());
    full_address.append(ssx.getName());
    ssx = supermarketService.selectRegionNameByCurrentRegionId(supplier.getCounty().longValue());
    if (ssx != null) {
      full_address.append(ssx.getName());
    }
    ssx = supermarketService.selectRegionNameByCurrentRegionId(supplier.getTown().longValue());
    if (ssx != null) {
      full_address.append(ssx.getName());
    }
    if (StringUtils.isNotBlank(supplier.getAddress())) {
      full_address.append(supplier.getAddress());
    }
    supplier.setFull_address(full_address.toString());

    return supplier;
  }

  public int countByQuery(SupplierManagerQuery query) {
    if(query.getId() != null){
      return 1;
    }
    return supplierMapper.countByQuery(query);
  }


  /**
   * addSupplier:(添加供应商).
   *
   * 2016年8月15日 上午10:35:08
   * 
   * @author zhaojn
   * @param form
   * @param user
   * @throws ConfigurationException
   * @throws KXWPException
   * 
   */
  public void addSupplier(CreateGysForm form, UserModel user)
      throws ConfigurationException, KXWPException {

    // 账号绑定的手机号不允许重复
    SupplierAccountExample example = new SupplierAccountExample();
    example.createCriteria().andMobileEqualTo(form.getSupplier().getAccount_mobile());
    int count = supplierAccountMapper.countByExample(example);
    if (count > 0) {
      throw new SYSException("手机号 " + form.getSupplier().getAccount_mobile() + "已绑定账号");
    }

    Supplier supplier = form.getSupplier();
    // 添加供应商信息
    Date now = new Date();
    OddNumberQuery oddNumberQuery = new OddNumberQuery();
    oddNumberQuery.setNumber_type(KXWPNumberRuleEnum.SUPPLIER_NO);
    // 供应商联系人
    supplier.setSupplierAdmin(supplier.getName());
    supplier.setSupplierNo(oddNumberService.newOddNumberViaProcedure(oddNumberQuery));
    supplier.setServiceStationId(user.getOrganizationId());
    supplier.setCreateUserId(user.getId());
    supplier.setCreateTime(now);
    supplier.setUpdateTime(now);
    supplier.setSupplierStatus(OrganizationStatusEnum.VALID);
    supplier.setSupplierAdminMobile(form.getSupplier().getAccount_mobile());//供应商组织信息中添加负责人手机号
    if (StringUtils.isBlank(supplier.getLegalPerson())) {
      supplier.setLegalPerson(supplier.getName());
    }
    supplier.setPhone(new Long(PhoneNumberFormat.formatPhone(supplier.getService_phone())));
    supplierMapper.insertSelective(supplier);
    // 设置超市会员初始化账号
    SupplierAccount supplierAccount = new SupplierAccount();
    supplierAccount.setSupplierId(supplier.getId());
    oddNumberQuery.setNumber_type(KXWPNumberRuleEnum.USER_NO);
    supplierAccount.setUserNo(oddNumberService.newOddNumberViaProcedure(oddNumberQuery));
    supplierAccount.setName(supplier.getName());
    supplierAccount.setMobile(form.getSupplier().getAccount_mobile());

    // 加密前密码
    String randomPasswd = RandomStringUtils.randomAlphanumeric(8);
    supplierAccount.setUserPassword(KXWPEncryptUtils.encryptPassword(randomPasswd));
    supplierAccount.setCreateTime(now);
    supplierAccount.setUpdateTime(now);
    supplierAccount.setCreateUserId(user.getId());
    // 系统生成账号，需要重置密码 FIXME
    supplierAccount.setAccountStatus(AccountStatusEnum.VALID);
    supplierAccountMapper.insertSelective(supplierAccount);

    // 给账号添加角色---开始
    // 查询该服务站的角色是否存在
    SupplierRoleQuery supplierRoleQuery = new SupplierRoleQuery();

    supplierRoleQuery.setPageSize(0);
    supplierRoleQuery.setSupplierId(supplier.getId());

    List<SuRole> suRoles = suRoleManageService.list(supplierRoleQuery);

    if (suRoles.size() > 0) {
      throw new KXWPException("supplierId为" + supplier.getId() + "的供应商角色已经存在！");
    }

    // 初始化角色信息
    SuRole role = new SuRole();

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

    // 插入账号角色关系信息
    SuRoleRelation record = new SuRoleRelation();

    record.setRoleId(role.getId());
    record.setAccountId(supplier.getId());
    record.setCreateUserId(user.getId());
    record.setRrStatus(RoleStatusEnum.VALID);
    record.setCreateTime(new Date());
    record.setUpdateTime(new Date());

    suRoleRelationMapper.insertSelective(record);
    // 给账号添加角色---结束

    // 判断是否需要将生成的密码发送的手机账号中
    if (StringUtils.equalsIgnoreCase(form.getSendPassword(), YNEnum.Y.name())) {
      // 密码通过短信发送到指定手机账号
      SYSNotification sysNotification = new SYSNotification();
      sysNotification.setConsumer(NotificationConsumerEnum.SMS);
      sysNotification.setCreateTime(now);
      Map<String, String> valuesMap = new HashMap<>();
      valuesMap.put("password", randomPasswd);
      String msgContent = SMSTemplateUtil.getTemplate(SMSTemplateEnum.INIT_PASSWORD, valuesMap);
      sysNotification.setMsgContent(msgContent);
      sysNotification.setReception(form.getSupplier().getAccount_mobile() + "");
      notificationService.addNotification(sysNotification);
    }



    // 配送范围
    for (Long area_id : form.getShippingAreaIdList()) {
      GoodsShippingArea goodsShippingArea = new GoodsShippingArea();
      goodsShippingArea.setCreateTime(now);
      goodsShippingArea.setFwzId(user.getOrganizationId());
      goodsShippingArea.setRegionId(area_id);
      goodsShippingArea.setSupplierId(supplier.getId());
      goodsShippingArea.setUpdateTime(now);
      goodsShippingArea.setZzId(user.getServiceStation().getMasterStationId());
      goodsShippingAreaMapper.insertSelective(goodsShippingArea);
    }

    // 添加供应商的经营范围
    for (Long classificaionId : form.getClassificaionIdList()) {
      BusinessScope businessScope = new BusinessScope();
      businessScope.setZzId(user.getServiceStation().getMasterStationId());
      businessScope.setFwzId(user.getOrganizationId());
      businessScope.setSupplierId(supplier.getId());
      businessScope.setCategoryId(classificaionId);
      businessScope.setCreateTime(now);
      businessScope.setUpdateTime(now);
      businessScopeService.addBussinessScope(businessScope);
    }

    // 添加品牌
    for (Long agentBrandId : form.getBrandIdList()) {
      AgentBrand agentBrand = new AgentBrand();
      agentBrand.setBrandId(agentBrandId);
      agentBrand.setZzId(user.getServiceStation().getMasterStationId());
      agentBrand.setCreateTime(now);
      agentBrand.setUpdateTime(now);
      agentBrand.setFwzId(user.getOrganizationId());
      agentBrand.setSupplierId(supplier.getId());
      agentBrandService.addAgentBrand(agentBrand);
    }

  }
  
  public void editSupplier(CreateGysForm form, UserModel user)
      throws ConfigurationException, KXWPException {
    Supplier supplier = form.getSupplier();
    // 添加供应商信息
    supplierMapper.updateByPrimaryKeySelective(supplier);
    
    cacheService.removeLikeCache(CachekeyPrefix.ADMIN_GYS_INFO + supplier.getId());
    
    Date now = new Date();
    // 配送范围
    if(form.getShippingAreaIdList() != null && form.getShippingAreaIdList().size() != 0){
      goodsShippingAreaMapper.deleteByCondition(supplier.getId());
      for (Long area_id : form.getShippingAreaIdList()) {
        GoodsShippingArea goodsShippingArea = new GoodsShippingArea();
        goodsShippingArea.setCreateTime(now);
        goodsShippingArea.setFwzId(user.getOrganizationId());
        goodsShippingArea.setRegionId(area_id);
        goodsShippingArea.setSupplierId(supplier.getId());
        goodsShippingArea.setUpdateTime(now);
        goodsShippingArea.setZzId(user.getServiceStation().getMasterStationId());
        goodsShippingAreaMapper.insertSelective(goodsShippingArea);
      }
    }
    
    // 添加供应商的经营范围
    businessScopeMapper.deleteByCondition(supplier.getId());
    for (Long classificaionId : form.getClassificaionIdList()) {
      BusinessScope businessScope = new BusinessScope();
      businessScope.setZzId(user.getServiceStation().getMasterStationId());
      businessScope.setFwzId(user.getOrganizationId());
      businessScope.setSupplierId(supplier.getId());
      businessScope.setCategoryId(classificaionId);
      businessScope.setCreateTime(now);
      businessScope.setUpdateTime(now);
      businessScopeService.addBussinessScope(businessScope);
    }
    
    // 添加品牌
    agentBrandMapper.deleteByCondition(supplier.getId());
    for (Long agentBrandId : form.getBrandIdList()) {
      AgentBrand agentBrand = new AgentBrand();
      agentBrand.setBrandId(agentBrandId);
      agentBrand.setZzId(user.getServiceStation().getMasterStationId());
      agentBrand.setCreateTime(now);
      agentBrand.setUpdateTime(now);
      agentBrand.setFwzId(user.getOrganizationId());
      agentBrand.setSupplierId(supplier.getId());
      agentBrandService.addAgentBrand(agentBrand);
    }
    
    cacheService.removeLikeCache(CachekeyPrefix.AGENTBRAND_QUERY);
  }

  /**
   * 
   * update:(根据供应商id更新供应商状态及其对应账号的状态).
   *
   * 2016年9月10日 下午4:36:17
   * @author wangjun
   * @param supplier
   * @throws LoginException 
   * @throws KXWPValidException 
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   * @throws SYSException 
   */
  public void updateMixture(Supplier supplier, UserModel userModel) 
      throws LoginException, SYSException, IllegalAccessException, InvocationTargetException, KXWPValidException{
    //更新供应商
    supplierMapper.updateByPrimaryKeySelective(supplier);
    
    //更新供应商账号
    SupplierAccount record = new SupplierAccount();
    
    record.setSupplierId(supplier.getId());
    if(supplier.getSupplierStatus() == OrganizationStatusEnum.UNVALID){
      record.setAccountStatus(AccountStatusEnum.INVALID);
    }else{
      record.setAccountStatus(AccountStatusEnum.VALID);
    }
    
    supplierAccountMapper.updateByConditionSelective(record);
    
    if(supplier.getSupplierStatus() == OrganizationStatusEnum.UNVALID){
      //更新商品
      UpdateGoodsStatusQuery query = new UpdateGoodsStatusQuery();
      
      query.setSupplier_id(supplier.getId());
      query.setNew_status(GoodsStatusEnum.OFFSALE);
      query.setReview_mark("供应商下架引起商品下架");
      
      LoginUserInfo loginUserInfo = new LoginUserInfo();//转换登录信息
      BeanUtilsBean2.getInstance().copyProperties(loginUserInfo, userModel);
      
      query.setLoginUserInfo(loginUserInfo);
      
      goodsRepoService.updateGoodsStatus(query);
    }
    
    //记录操作日志
    SysReview review = new SysReview();
    review.setReviewSys(SystemTypeEnum.ServiceStation);
    review.setReviewType(ReviewTypeEnum.Supermarket);
    review.setReviewId(supplier.getId());
    review.setReviewUserId(userModel.getId());
    review.setBeforeReviewStatus(supplier.getSupplierStatus().getDesc());
    review.setAfterReviewStatus(record.getAccountStatus().getDesc());
    if (supplier.getSupplierStatus() == OrganizationStatusEnum.UNVALID) {
      review.setDescribtion(supplier.getStatusChangeReason());
    }else {
      review.setDescribtion("启用供应商");
    }

    review.setCreateTime(new Date());
    review.setUpdateTime(new Date());
    reviewService.addReview(review);
  }
  
  
  public Supplier get(Long id){
    return supplierMapper.selectByPrimaryKey(id);
  }
}
