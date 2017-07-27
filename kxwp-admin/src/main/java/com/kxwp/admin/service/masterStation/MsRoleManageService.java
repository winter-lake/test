package com.kxwp.admin.service.masterStation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.DelAllowedStatusEnum;
import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.masterStation.MsRole;
import com.kxwp.admin.entity.masterStation.MsRoleExample;
import com.kxwp.admin.entity.masterStation.MsRoleResourceRelation;
import com.kxwp.admin.mapper.masterStation.MsRoleMapper;
import com.kxwp.admin.query.masterStation.MasterStationQuery;
import com.kxwp.admin.query.masterStation.MasterStationRoleQuery;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.page.BasePager;
import com.kxwp.common.model.page.MySQLPager;

/**
 * 角色管理Service
 * date: 2016年7月29日 下午5:36:00 
 * @author Liuzibing
 */
@Service("msRoleManageService")
public class MsRoleManageService {
  @Autowired
  private MsRoleMapper roleMapper;
  
  @Autowired
  private MsRoReRelationService msRoReRelationService;
  
  @Autowired
  private MsResourceManageService resourceManageService;
  
  
  /**
   * getResourceForView:(调用权限Service传递给角色添加视图).
   * 2016年8月3日 下午12:00:37
   * @author Liuzibing
   * @param masterStationQuery
   * @return
   */
  public List<MsResource> getResourceForView(MasterStationQuery masterStationQuery)
  {
    masterStationQuery.setGrade(1);
    masterStationQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
    
    return resourceManageService.list(masterStationQuery);
  }
  
  /** 
   * add:(添加角色).
   * 2016年7月29日 下午5:41:10
   * @author Liuzibing
   * @param role
   * @return
   */
  
  public void addMixture(MsRole role) 
  {
    //设置资源信息
    role.setDeleteAllowed(DelAllowedStatusEnum.N);
    role.setRoleStatus(RoleStatusEnum.VALID);
    
    //插入角色
    roleMapper.insertSelective(role);
    
    
    //准备待插入角色资源数据
    List<MsRoleResourceRelation> msRoleResourceRelations = new ArrayList<MsRoleResourceRelation>();
    MsRoleResourceRelation msRoleResourceRelation = null;
    
    for(MsResource msResource : role.getMsResources()){
      msRoleResourceRelation = new MsRoleResourceRelation();
      
      if(msResource.getId() == null)
        continue;
      msRoleResourceRelation.setResourceId(msResource.getId());
      msRoleResourceRelation.setRoleId(role.getId());
      msRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
      
      msRoleResourceRelations.add(msRoleResourceRelation);
      
      if(msResource.getMsResources() == null)
        continue;
      for(MsResource msResource0 : msResource.getMsResources()){
        msRoleResourceRelation = new MsRoleResourceRelation();
        
        if(msResource0.getId() == null)
          continue;
        msRoleResourceRelation.setResourceId(msResource0.getId());
        msRoleResourceRelation.setRoleId(role.getId());
        msRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        
        msRoleResourceRelations.add(msRoleResourceRelation);
      }
    }
    
    //批量插入角色资源关系
    msRoReRelationService.insertSelectiveBatch(msRoleResourceRelations);
  }
  
  /**
   * getMsResourceList:(获取各个一级资源的二级资源).
   * 2016年8月3日 下午2:51:22
   * @author Liuzibing
   * @param masterStationQuery
   * @return
   */
  public List<MsResource> getMsResourceList()
  {
    return resourceManageService.getMsResourceTree();
  }
  
  
  /**
   * modify:(修改角色信息).
   *
   * 2016年7月29日 下午5:42:43
   * @author Liuzibing
   * @param role
   * @return
   */
  
  public int modify(MsRole role)
  {
      return roleMapper.updateByPrimaryKeySelective(role);
  }
  
  
  /**
   * get:(根据ID获取角色信息).
   *
   * 2016年7月29日 下午5:44:11
   * @author Liuzibing
   * @param id
   * @return
   */
  public MsRole get(Long id)
  {
      return roleMapper.selectByPrimaryKey(id);
  }
 
  /**
   * delete:(根据ID删除角色).
   *
   * 2016年7月29日 下午5:45:37
   * @author Liuzibing
   * @param id
   * @return
  
   */
  public int delete(Long id)
  { 
    return roleMapper.deleteByPrimaryKey(id);
  } 
   
  /**
   * countTotalRecords:(计算总条数).
   *
   * 2016年8月1日 下午5:40:19
   * @author Liuzibing
   * @param masterStationRoleQuery
   * @return
   */
  public int countTotalRecords(MasterStationRoleQuery masterStationRoleQuery)
  {
    MsRoleExample example=new MsRoleExample();
    
    example.setDistinct(true);
    if(masterStationRoleQuery.getRoleStatus()!=null) 
      example.createCriteria().andRoleStatusEqualTo(masterStationRoleQuery.getRoleStatus());
      
    return roleMapper.countByExample(example);
  }
  
  
  /**
   * getAllRole:(获取所有角色信息+资源信息).
   * 2016年8月2日 下午4:21:37
   * @author Liuzibing
   * @param masterStationRoleQuery
   * @return
   */
  
  public List<MsRole> getAllRole(MasterStationRoleQuery masterStationRoleQuery)
  {
    masterStationRoleQuery.setOffset((masterStationRoleQuery.getCurrentPage()-1)*masterStationRoleQuery.getPageSize());
    
    List<MsRole> msRoles = findAllRoles(masterStationRoleQuery);
    //角色管理-角色描述不显示权限，所以这段注释掉了
    //2016-08-12-16:40 刘子丙
   /* List<MsRoleResourceRelation> relations=new ArrayList<MsRoleResourceRelation>();
    for(MsRole x:msRoles)
    {
      String key="";
       relations=msRoReRelationService.listForRelation(x.getId());
       for(MsRoleResourceRelation w:relations)
       {
         MsResource resource=resourceManageService.get(w.getResourceId());
        String str=resource.getName();
         key+=str+"/";
         x.setRoleDescription(key.substring(0,key.length()-1));
       }
    } */
    return msRoles;
  }
  
  /**
   * 
   * findAllRoles:(获取所有角色信息).
   *
   * 2016年8月3日 下午7:07:12
   * @author wangjun
   * @return
   */
  public List<MsRole> findAllRoles(MasterStationRoleQuery masterStationRoleQuery) {
    List<MsRole> msRoles=this.roleMapper.selectByCondition(masterStationRoleQuery);
    return msRoles;
  }
  
  public ExchangeData<Object> exchangeData(MasterStationRoleQuery masterStationRoleQuery)
  {
    //定义返回数据
    ExchangeData<Object> exchangeData=new ExchangeData<Object>();
    
    BasePager pager=new MySQLPager();
    pager.setCurrentPage(masterStationRoleQuery.getCurrentPage());
    pager.setPageSize(masterStationRoleQuery.getPageSize());
    int tempMs=0;
    tempMs = this.countTotalRecords(masterStationRoleQuery);
    
    int totalResults=tempMs;
    
    pager.setTotalPages((int)Math.ceil(totalResults/masterStationRoleQuery.getPageSize()));
    pager.setTotoalResults(totalResults);
    
    exchangeData.setPager(pager);
    
    return exchangeData;
  }

  public MsRole getMixture(Long id) {
    MsRole msRole = this.get(id);
    
    List<MsRoleResourceRelation> msRoleResourceRelations = msRoReRelationService.listForRelation(id);
    
    msRole.setMsRoleResourceRelations(msRoleResourceRelations);
    
    return msRole;
  }

  public void modifyMixture(MsRole role) {
    //更新role
    roleMapper.updateByPrimaryKeySelective(role);
    
    //删除角色资源关系
    MsRoleResourceRelation msRoleResourceRelation0 = new MsRoleResourceRelation();
    msRoleResourceRelation0.setRoleId(role.getId());
    
    msRoReRelationService.remove(msRoleResourceRelation0);
    
    //准备待插入角色资源数据
    List<MsRoleResourceRelation> msRoleResourceRelations = new ArrayList<MsRoleResourceRelation>();
    MsRoleResourceRelation msRoleResourceRelation = null;
    
    for(MsResource msResource : role.getMsResources()){
      msRoleResourceRelation = new MsRoleResourceRelation();
      
      if(msResource.getId() == null)
        continue;
      msRoleResourceRelation.setResourceId(msResource.getId());
      msRoleResourceRelation.setRoleId(role.getId());
      msRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
      
      msRoleResourceRelations.add(msRoleResourceRelation);
      
      if(msResource.getMsResources() == null)
        continue;
      for(MsResource msResource0 : msResource.getMsResources()){
        msRoleResourceRelation = new MsRoleResourceRelation();
        
        if(msResource0.getId() == null)
          continue;
        msRoleResourceRelation.setResourceId(msResource0.getId());
        msRoleResourceRelation.setRoleId(role.getId());
        msRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        
        msRoleResourceRelations.add(msRoleResourceRelation);
      }
    }
    
    //批量插入角色资源关系
    msRoReRelationService.insertSelectiveBatch(msRoleResourceRelations);
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
    MsRole msRole = new MsRole();
    
    msRole.setId(id);
    msRole.setRoleStatus(RoleStatusEnum.INVALID);
    
    roleMapper.updateByPrimaryKeySelective(msRole);
  }
}
