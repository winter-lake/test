package com.kxwp.admin.service.serviceStation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.serviceStation.SsResourceExample;
import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.entity.serviceStation.SsRoleResourceRelation;
import com.kxwp.admin.mapper.serviceStation.SsResourceMapper;
import com.kxwp.admin.mapper.serviceStation.SsRoleMapper;
import com.kxwp.admin.mapper.serviceStation.SsRoleResourceRelationMapper;
import com.kxwp.admin.query.serviceStation.ServiceStationQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationRoleQuery;
import com.kxwp.common.constants.SystemTypeEnum;

/**
 * 资源管理service date: 2016年7月27日 下午2:53:00
 *
 * @author wangjun
 */
@Service("ssResourceManageService")
public class SsResourceManageService {

  @Autowired
  private SsResourceMapper resourceMapper;
  
  @Autowired
  private SsRoleMapper roleMapper;
  
  @Autowired
  private SsRoleResourceRelationMapper ssRoleResourceRelationMapper;
  

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
  public int add(SsResource resource) throws IllegalAccessException, InvocationTargetException{
    // 设置资源信息
    resource.setResourceStatus(ResourceStatusEnum.ONLINE);
    if(resource.getParentId() == null){
      resource.setParentId(0L);
    }
    //定义批量参数
    List<SsResource> resources = new ArrayList<SsResource>();
    
    //加入父级
    resourceMapper.insertSelective(resource);
    
    //更新系统创建的默认角色与资源关联信息--开始
      //查询系统创建的默认角色
    ServiceStationRoleQuery serviceStationRoleQuery = new ServiceStationRoleQuery();
    
    serviceStationRoleQuery.setPageSize(0);
    serviceStationRoleQuery.setName("admin");
    
    List<SsRole> ssRoles = roleMapper.selectByCondition(serviceStationRoleQuery);
      //如果存在系统创建的默认角色则更新角色与资源关联信息
    if(ssRoles.size() != 0){
      List<SsRoleResourceRelation> ssRoleResourceRelations = new ArrayList<SsRoleResourceRelation>();
      
      SsRoleResourceRelation ssRoleResourceRelation = null;
      for(SsRole ssRole : ssRoles){
        ssRoleResourceRelation = new SsRoleResourceRelation();
        
        ssRoleResourceRelation.setResourceId(resource.getId());
        ssRoleResourceRelation.setRoleId(ssRole.getId());
        ssRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        ssRoleResourceRelation.setCreateUserId(resource.getCreateUserId());
        
        ssRoleResourceRelations.add(ssRoleResourceRelation);
      }
      
      ssRoleResourceRelationMapper.insertSelectiveBatch(ssRoleResourceRelations);
    }
  //更新系统创建的默认角色与资源关联信息--结束
    
    //加入子级
    if(resource.getModuleUrls() != null)
    {
      SsResource resourceTemp = null;
      
      for(String url : resource.getModuleUrls())
      {
        resourceTemp = new SsResource();
        
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
  public int modify(List<MsResource>  ssResources){
    return resourceMapper.updateByPrimaryKeySelectiveBatch(ssResources);
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
  public SsResource get(Long id){
      //根据id查询资源
      SsResource ssResource = resourceMapper.selectByPrimaryKey(id);
      
      
      if(ssResource != null){
        ssResource.setOwnSystem(SystemTypeEnum.ServiceStation);
        //如果不是二级菜单
        if(ssResource.getGrade() != 2)
          return ssResource;
        
        //定义资源子集
        List<String> moduleUrls = new ArrayList<String>();
        
        //定义查询条件
        ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
        
        serviceStationQuery.setParentId(id);
        serviceStationQuery.setPageSize(0);
        serviceStationQuery.setOffset(0);
        serviceStationQuery.setFlag("");
        
        //查询子集
        List<SsResource> ssResources = resourceMapper.selectByCondition(serviceStationQuery);
        
        for(SsResource ssResource2 : ssResources){
          moduleUrls.add(ssResource2.getUrl());
        }
        
        ssResource.setModuleUrls(moduleUrls);
      }
      
      return ssResource;
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
  public List<SsResource> list(ServiceStationQuery serviceStationQuery){
    return resourceMapper.selectByCondition(serviceStationQuery);
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
  public int countTotalRecords(ServiceStationQuery serviceStationQuery){
    SsResourceExample example = new SsResourceExample();
    
    example.setDistinct(true);
    if(serviceStationQuery.getResourceStatus() != null)
    example.createCriteria().andResourceStatusEqualTo(serviceStationQuery.getResourceStatus());
    example.createCriteria().andGradeNotEqualTo(3);
    
    return resourceMapper.countByExample(example);
  }
  
  /**
   * 
   * getMsResourceTree:(获取资源信息结构).
   *
   * 2016年8月6日 上午10:18:32
   * @author wangjun
   * @return
   */
  public List<SsResource> getSsResourceTree()
  {
    ServiceStationQuery serviceStationQuery=new ServiceStationQuery();
    
    //查找所有的一级资源
    serviceStationQuery.setGrade(1);
    serviceStationQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
    serviceStationQuery.setPageSize(0);
    
    List<SsResource> oneResList = this.list(serviceStationQuery);
    
    //查找所有二级资源
    List<SsResource> twoResList = null;
    
    serviceStationQuery.setGrade(2);
    
    for(SsResource xx : oneResList)
    {
      serviceStationQuery.setParentId(xx.getId());
      
      twoResList = this.list(serviceStationQuery);
      
      xx.setSsResources(twoResList);
    }
    
    return oneResList;
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
    SsResourceExample example = new SsResourceExample();
    
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
