package com.kxwp.admin.service.supplier;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.entity.supplier.SuResourceExample;
import com.kxwp.admin.entity.supplier.SuRole;
import com.kxwp.admin.entity.supplier.SuRoleResourceRelation;
import com.kxwp.admin.mapper.supplier.SuResourceMapper;
import com.kxwp.admin.mapper.supplier.SuRoleMapper;
import com.kxwp.admin.mapper.supplier.SuRoleResourceRelationMapper;
import com.kxwp.admin.query.supplier.SupplierQuery;
import com.kxwp.admin.query.supplier.SupplierRoleQuery;
import com.kxwp.common.constants.SystemTypeEnum;

/**
 * 资源管理service date: 2016年7月27日 下午2:53:00
 *
 * @author wangjun
 */
@Service("suResourceManageService")
public class SuResourceManageService {

  @Autowired
  private SuResourceMapper resourceMapper;
  
  @Autowired
  private SuRoleMapper roleMapper;
  
  @Autowired
  private SuRoleResourceRelationMapper suRoleResourceRelationMapper;
  
  /**
   * 
   * getMsResourceTree:(获取资源信息结构).
   *
   * 2016年8月6日 上午10:18:32
   * @author wangjun
   * @return
   */
  public List<SuResource> getSuResourceTree()
  {
    SupplierQuery supplierQuery=new SupplierQuery();
    
    //查找所有的一级资源
    supplierQuery.setGrade(1);
    supplierQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
    supplierQuery.setPageSize(0);
    
    List<SuResource> oneResList = this.list(supplierQuery);
    
    //查找所有二级资源
    List<SuResource> twoResList = null;
    
    supplierQuery.setGrade(2);
    
    for(SuResource xx : oneResList)
    {
      supplierQuery.setParentId(xx.getId());
      
      twoResList = this.list(supplierQuery);
      
      xx.setSuResources(twoResList);
    }
    
    return oneResList;
  }

  /**
   * 
   * add:(添加资源).
   *
   * 2016年7月27日 下午2:53:24
   * 
   * @author wangjun
   * @return
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   */
  public int add(SuResource resource) throws IllegalAccessException, InvocationTargetException{
    // 设置资源信息
    resource.setResourceStatus(ResourceStatusEnum.ONLINE);
    if(resource.getParentId() == null){
      resource.setParentId(0L);
    }
    //定义批量参数
    List<SuResource> resources = new ArrayList<SuResource>();
    
    //加入父级
    resourceMapper.insertSelective(resource);
    
  //更新系统创建的默认角色与资源关联信息--开始
    //查询系统创建的默认角色
  SupplierRoleQuery supplierRoleQuery = new SupplierRoleQuery();
  
  supplierRoleQuery.setPageSize(0);
  supplierRoleQuery.setName("admin");
  
  List<SuRole> suRoles = roleMapper.selectByCondition(supplierRoleQuery);
    //如果存在系统创建的默认角色则更新角色与资源关联信息
  if(suRoles.size() != 0){
    List<SuRoleResourceRelation> suRoleResourceRelations = new ArrayList<SuRoleResourceRelation>();
    
    SuRoleResourceRelation suRoleResourceRelation = null;
    for(SuRole suRole : suRoles){
      suRoleResourceRelation = new SuRoleResourceRelation();
      
      suRoleResourceRelation.setResourceId(resource.getId());
      suRoleResourceRelation.setRoleId(suRole.getId());
      suRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
      suRoleResourceRelation.setCreateUserId(resource.getCreateUserId());
      
      suRoleResourceRelations.add(suRoleResourceRelation);
    }
    
    suRoleResourceRelationMapper.insertSelectiveBatch(suRoleResourceRelations);
  }
//更新系统创建的默认角色与资源关联信息--结束
    
    //加入子级
    if(resource.getModuleUrls() != null)
    {
      SuResource resourceTemp = null;
      for(String url : resource.getModuleUrls())
      {
        resourceTemp = new SuResource();
        
         BeanUtilsBean2.getInstance().copyProperties(resourceTemp, resource);
        
        resourceTemp.setName(resourceTemp.getName()+"-模块URL");
        resourceTemp.setUrl(url);
        resourceTemp.setGrade(resourceTemp.getGrade()+1);
        resourceTemp.setParentId(resourceTemp.getId());
        resources.add(resourceTemp);
      }
    }
    
    if(resources.size() != 0){
      return resourceMapper.insertSelectiveBatch(resources);
    }else{
      return 1;
    }
  }
  
  /**
   * 
   * modify:(修改资源).
   *
   * 2016年7月28日 上午11:01:42
   * @author wangjun
   * @param msResources
   * @return
   */
  public int modify(List<SuResource>  suResources){
    return resourceMapper.updateByPrimaryKeySelectiveBatch(suResources);
  }
  
  /**
   * 
   * get:(根据id获取资源).
   *
   * 2016年7月28日 下午1:52:17
   * @author wangjun
   * @param id
   * @return
   */
  public SuResource get(Long id){
      //根据id查询资源
      SuResource suResource = resourceMapper.selectByPrimaryKey(id);
      
      if(suResource != null){
        suResource.setOwnSystem(SystemTypeEnum.Supplier);
        //如果不是二级菜单
        if(suResource.getGrade() != 2)
          return suResource;
        
        //定义资源子集
        List<String> moduleUrls = new ArrayList<String>();
        
        //定义查询条件
        SupplierQuery supplierQuery = new SupplierQuery();
        
        supplierQuery.setParentId(id);
        supplierQuery.setPageSize(0);
        supplierQuery.setOffset(0);
        supplierQuery.setFlag("");
        
        //查询子集
        List<SuResource> suResources = resourceMapper.selectByCondition(supplierQuery);
        
        for(SuResource suResource2 : suResources){
          moduleUrls.add(suResource2.getUrl());
        }
        
        suResource.setModuleUrls(moduleUrls);
      }
      
      return suResource;
    
  }
  
  /**
   * 
   * list:(根据查询条件查询资源).
   *
   * 2016年7月28日 下午2:48:08
   * @author wangjun
   * @param masterStationQuery
   * @return
   */
  public List<SuResource> list(SupplierQuery supplierQuery){
    return resourceMapper.selectByCondition(supplierQuery);
  }
  
  /**
   * 
   * countTotalRecords:(计算总条数).
   *
   * 2016年7月28日 下午6:05:20
   * @author wangjun
   * @param masterStationQuery
   * @return
   */
  public int countTotalRecords(SupplierQuery supplierQuery){
    SuResourceExample example = new SuResourceExample();
    
    example.setDistinct(true);
    if(supplierQuery.getResourceStatus() != null)
    example.createCriteria().andResourceStatusEqualTo(supplierQuery.getResourceStatus());
    example.createCriteria().andGradeNotEqualTo(3);
    
    return resourceMapper.countByExample(example);
  }
  
  /**
   * 
   * checkURL:(判断该url的资源是否存在).
   *
   * 2016年8月19日 下午3:08:57
   * @author wangjun
   * @param fieldValue
   */
  public Boolean checkURL(String fieldValue) {
    SuResourceExample example = new SuResourceExample();
    
    example.createCriteria().andUrlEqualTo(fieldValue);
    
    int count = 0;
    count = resourceMapper.countByExample(example);
    
    if(count==0){
      return true;
    }else{
      return false;
    }
    
  }
}
