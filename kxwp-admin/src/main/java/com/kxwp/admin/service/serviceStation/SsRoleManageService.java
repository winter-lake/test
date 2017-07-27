package com.kxwp.admin.service.serviceStation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.DelAllowedStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.entity.serviceStation.SsRoleExample;
import com.kxwp.admin.entity.serviceStation.SsRoleResourceRelation;
import com.kxwp.admin.mapper.serviceStation.SsRoleMapper;
import com.kxwp.admin.query.serviceStation.ServiceStationRoleQuery;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.page.BasePager;
import com.kxwp.common.model.page.MySQLPager;

/**
 * 角色管理Service
 * date: 2016年7月29日 下午5:36:00 
 * @author Liuzibing
 */
@Service("ssRoleManageService")
public class SsRoleManageService {
  @Autowired
  private SsRoleMapper roleMapper;
  
  @Autowired
  private SsRoReRelationService ssRoReRelationService;
  
  @Autowired
  private SsResourceManageService resourceManageService;
  
  
  /**
   * get:(根据ID获取角色信息).
   *
   * 2016年7月29日 下午5:49:23
   * @author Liuzibing
   * @param id
   * @return
  
   */
  public SsRole get(Long id)
  {
    return roleMapper.selectByPrimaryKey(id);
  }
  
  /**
   * list:(根据条件查询资源).
   *
   * 2016年8月1日 下午6:39:07
   * @author Liuzibing
   * @param serviceStationRoleQuery
   * @return
  
   */
  public List<SsRole> list(ServiceStationRoleQuery serviceStationRoleQuery)
  {
    return roleMapper.selectByCondition(serviceStationRoleQuery);
  }
//  /**
//   * getResourceForView:(调用权限Service传递给角色添加视图).
//   * 2016年8月3日 下午12:00:37
//   * @author Liuzibing
//   * @param masterStationQuery
//   * @return
//   */
//  public List<SsResource> getResourceForView(MasterStationQuery masterStationQuery)
//  {
//    masterStationQuery.setGrade(1);
//    masterStationQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
//    
//    return resourceManageService.list(masterStationQuery);
//  }
//  
  /** 
   * add:(添加角色).
   * 2016年7月29日 下午5:41:10
   * @author Liuzibing
   * @param role
   * @return
   */
  
  public void addMixture(SsRole role) 
  {
    //设置资源信息
    role.setDeleteAllowed(DelAllowedStatusEnum.N);
    role.setRoleStatus(RoleStatusEnum.VALID);
    
    //插入角色
    roleMapper.insertSelective(role);
    
    
    //准备待插入角色资源数据
    List<SsRoleResourceRelation> ssRoleResourceRelations = new ArrayList<SsRoleResourceRelation>();
    SsRoleResourceRelation ssRoleResourceRelation = null;
    
    for(SsResource SsResource : role.getSsResources()){
      ssRoleResourceRelation = new SsRoleResourceRelation();
      
      ssRoleResourceRelation.setResourceId(SsResource.getId());
      ssRoleResourceRelation.setRoleId(role.getId());
      ssRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
      
      ssRoleResourceRelations.add(ssRoleResourceRelation);
      
      if(SsResource.getSsResources() == null)
        continue;
      for(SsResource SsResource0 : SsResource.getSsResources()){
        ssRoleResourceRelation = new SsRoleResourceRelation();
        
        if(SsResource0.getId() == null)
          continue;
        ssRoleResourceRelation.setResourceId(SsResource0.getId());
        ssRoleResourceRelation.setRoleId(role.getId());
        ssRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        
        ssRoleResourceRelations.add(ssRoleResourceRelation);
      }
    }
    
    //批量插入角色资源关系
    ssRoReRelationService.insertSelectiveBatch(ssRoleResourceRelations);
  }
  
  /**
   * getSsResourceList:(获取各个一级资源的二级资源).
   * 2016年8月3日 下午2:51:22
   * @author Liuzibing
   * @return
   */
  public List<SsResource> getSsResourceList()
  {
    return resourceManageService.getSsResourceTree();
  }
//  
//  
//  /**
//   * modify:(修改角色信息).
//   *
//   * 2016年7月29日 下午5:42:43
//   * @author Liuzibing
//   * @param role
//   * @return
//   */
//  
//  public int modify(ssRole role)
//  {
//    try
//    {
//      return roleMapper.updateByPrimaryKeySelective(role);
//    }
//    catch(Exception ex){
//    }
//    return 0;
//  }
//  
//  
//  /**
//   * get:(根据ID获取角色信息).
//   *
//   * 2016年7月29日 下午5:44:11
//   * @author Liuzibing
//   * @param id
//   * @return
//   */
//  public ssRole get(Long id)
//  {
//    try
//    {
//      return roleMapper.selectByPrimaryKey(id);
//    }
//    catch(Exception ex)
//    {
//    }
//    return null;
//  }
// 
//  /**
//   * delete:(根据ID删除角色).
//   *
//   * 2016年7月29日 下午5:45:37
//   * @author Liuzibing
//   * @param id
//   * @return
//  
//   */
//  public int delete(Long id)
//  { 
//    return roleMapper.deleteByPrimaryKey(id);
//  } 
//   
  /**
   * countTotalRecords:(计算总条数).
   *
   * 2016年8月1日 下午5:40:19
   * @author Liuzibing
   * @return
   */
  public int countTotalRecords(ServiceStationRoleQuery serviceStationRoleQuery)
  {
    SsRoleExample example=new SsRoleExample();
    
    example.setDistinct(true);
      example.createCriteria().andRoleStatusEqualTo(serviceStationRoleQuery.getRoleStatus())
      .andServiceStationIdEqualTo(serviceStationRoleQuery.getServiceStationId());
      
    return roleMapper.countByExample(example);
  }
  
  
  /**
   * getAllRole:(获取所有角色信息+资源信息).
   * 2016年8月2日 下午4:21:37
   * @author Liuzibing
   * @param masterStationRoleQuery
   * @return
   */
  
  public List<SsRole> getAllRole(ServiceStationRoleQuery serviceStationRoleQuery)
  {
    serviceStationRoleQuery.setOffset((serviceStationRoleQuery.getCurrentPage()-1)*serviceStationRoleQuery.getPageSize());
    
    List<SsRole> ssRoles = findAllRoles(serviceStationRoleQuery);
    /*List<SsRoleResourceRelation> relations=new ArrayList<SsRoleResourceRelation>();
    for(SsRole x:ssRoles)
    {
      String key="";
       relations=ssRoReRelationService.listForRelation(x.getId());
       for(SsRoleResourceRelation w:relations)
       {
         SsResource resource=resourceManageService.get(w.getResourceId());
         String str=resource.getName();
         key+=str+"/";
         x.setRoleDescription(key.substring(0,key.length()-1));
       }
    } */
    return ssRoles;
  }
  
  /**
   * 
   * findAllRoles:(获取所有角色信息).
   *
   * 2016年8月3日 下午7:07:12
   * @author wangjun
   * @return
   */
  public List<SsRole> findAllRoles(ServiceStationRoleQuery serviceStationRoleQuery) {
    List<SsRole> ssRoles=this.roleMapper.selectByCondition(serviceStationRoleQuery);
    return ssRoles;
  }
  
 public ExchangeData<Object> exchangeData(ServiceStationRoleQuery serviceStationRoleQuery)
  {
    //定义返回数据
    ExchangeData<Object> exchangeData=new ExchangeData<Object>();
    
    BasePager pager=new MySQLPager();
    pager.setCurrentPage(serviceStationRoleQuery.getCurrentPage());
    pager.setPageSize(serviceStationRoleQuery.getPageSize());
    int tempSs=0;
    tempSs = this.countTotalRecords(serviceStationRoleQuery);
    int totalResults=tempSs;
    
    pager.setTotalPages((int)Math.ceil(totalResults/serviceStationRoleQuery.getPageSize()));
    pager.setTotoalResults(totalResults);
    
    exchangeData.setPager(pager);
    
    return exchangeData;
  }

  public SsRole getMixture(Long id) {
    SsRole ssRole = this.get(id);
    
    List<SsRoleResourceRelation> ssRoleResourceRelations = ssRoReRelationService.listForRelation(id);
    
    ssRole.setSsRoleResourceRelations(ssRoleResourceRelations);
    
    return ssRole;
  }

  public void modifyMixture(SsRole role) {
    //更新role
    roleMapper.updateByPrimaryKeySelective(role);
    
    //删除角色资源关系
    SsRoleResourceRelation ssRoleResourceRelation0 = new SsRoleResourceRelation();
    ssRoleResourceRelation0.setRoleId(role.getId());
    
    ssRoReRelationService.remove(ssRoleResourceRelation0);
    
    //准备待插入角色资源数据
    List<SsRoleResourceRelation> ssRoleResourceRelations = new ArrayList<SsRoleResourceRelation>();
    SsRoleResourceRelation ssRoleResourceRelation = null;
    
    for(SsResource SsResource : role.getSsResources()){
      ssRoleResourceRelation = new SsRoleResourceRelation();
      
      ssRoleResourceRelation.setResourceId(SsResource.getId());
      ssRoleResourceRelation.setRoleId(role.getId());
      ssRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
      
      ssRoleResourceRelations.add(ssRoleResourceRelation);
      
      if(SsResource.getSsResources() == null)
        continue;
      for(SsResource SsResource0 : SsResource.getSsResources()){
        ssRoleResourceRelation = new SsRoleResourceRelation();
        
        if(SsResource0.getId() == null)
          continue;
        ssRoleResourceRelation.setResourceId(SsResource0.getId());
        ssRoleResourceRelation.setRoleId(role.getId());
        ssRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        
        ssRoleResourceRelations.add(ssRoleResourceRelation);
      }
    }
    
    //批量插入角色资源关系
    ssRoReRelationService.insertSelectiveBatch(ssRoleResourceRelations);
  }

  /**
   * 
   * remove:(根据id删除角色).
   *
   * 2016年8月6日 下午6:04:41
   * @author wangjun
   * @param id
   */
  public void remove(Long id) {
    SsRole ssRole = new SsRole();
    
    ssRole.setId(id);
    ssRole.setRoleStatus(RoleStatusEnum.INVALID);
    
    roleMapper.updateByPrimaryKeySelective(ssRole);
  }
  
  
}
