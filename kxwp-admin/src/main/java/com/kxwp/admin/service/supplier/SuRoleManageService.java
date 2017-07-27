package com.kxwp.admin.service.supplier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.DelAllowedStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.entity.supplier.SuRole;
import com.kxwp.admin.entity.supplier.SuRoleExample;
import com.kxwp.admin.entity.supplier.SuRoleResourceRelation;
import com.kxwp.admin.mapper.supplier.SuRoleMapper;
import com.kxwp.admin.query.supplier.SupplierRoleQuery;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.page.BasePager;
import com.kxwp.common.model.page.MySQLPager;

/**
 * 角色管理Service
 * date: 2016年7月29日 下午5:50:02 
 *
 * @author Liuzibing
 */
@Service("suRoleManageService")
public class SuRoleManageService {
    @Autowired
    private SuRoleMapper roleMapper;
    @Autowired
    private SuResourceManageService resourceManageService;
    @Autowired
    private SuRoReRelationService suRoReRelationService;
    
    public SuRole getMixture(Long id) {
      SuRole suRole = this.get(id);
      
      List<SuRoleResourceRelation> suRoleResourceRelations = suRoReRelationService.listForRelation(id);
      
      suRole.setSuRoleResourceRelations(suRoleResourceRelations);
      
      return suRole;
    }
    
    /**
     * getMsResourceList:(获取各个一级资源的二级资源).
     * 2016年8月3日 下午2:51:22
     * @author Liuzibing
     * @return
     */
    public List<SuResource> getSuResourceList()
    {
      return resourceManageService.getSuResourceTree();
    }
    
    /**
     * 
     * findAllRoles:(获取所有角色信息).
     *
     * 2016年8月3日 下午7:07:12
     * @author wangjun
     * @return
     */
    public List<SuRole> findAllRoles(SupplierRoleQuery supplierRoleQuery) {
      List<SuRole> suRoles=this.roleMapper.selectByCondition(supplierRoleQuery);
      return suRoles;
    }
    
    /**
     * getAllRole:(获取所有角色信息+资源信息).
     * 2016年8月2日 下午4:21:37
     * @author Liuzibing
     * @return
     */
    public List<SuRole> getAllRole(SupplierRoleQuery supplierRoleQuery)
    {
      supplierRoleQuery.setOffset((supplierRoleQuery.getCurrentPage()-1)*supplierRoleQuery.getPageSize());
      
      List<SuRole> suRoles = findAllRoles(supplierRoleQuery);
      /*List<SuRoleResourceRelation> suRoleResourceRelations=new ArrayList<SuRoleResourceRelation>();
      for(SuRole x:suRoles)
      {
        String key="";
        suRoleResourceRelations=suRoReRelationService.listForRelation(x.getId());
         for(SuRoleResourceRelation w:suRoleResourceRelations)
         {
           SuResource resource=resourceManageService.get(w.getResourceId());
           String str=resource.getName();
           key+=str+"/";
           x.setRoleDescription(key.substring(0,key.length()-1));
         }
      } */
      return suRoles;
    }
    
    public ExchangeData<Object> exchangeData(SupplierRoleQuery supplierRoleQuery)
    {
      //定义返回数据
      ExchangeData<Object> exchangeData=new ExchangeData<Object>();
      
      BasePager pager=new MySQLPager();
      pager.setCurrentPage(supplierRoleQuery.getCurrentPage());
      pager.setPageSize(supplierRoleQuery.getPageSize());
      int tempSs=0;
      tempSs = this.countTotalRecords(supplierRoleQuery);
      
      int totalResults=tempSs;
      
      pager.setTotalPages((int)Math.ceil(totalResults/supplierRoleQuery.getPageSize()));
      pager.setTotoalResults(totalResults);
      
      exchangeData.setPager(pager);
      
      return exchangeData;
    }
    
    /**
     * add:(添加角色).
     *
     * 2016年7月29日 下午5:54:04
     * @author Liuzibing
     * @param role
     * @return
    
     */
    public int add(SuRole role)
    {
      role.setRoleStatus(RoleStatusEnum.VALID);
      return roleMapper.insertSelective(role);
    }
    
    /**
     * modify:(修改角色信息).
     *
     * 2016年7月29日 下午6:38:29
     * @author Liuzibing
     * @param role
     * @return
    
     */
    public int modify(SuRole role)
    {
      return roleMapper.updateByPrimaryKeySelective(role);
    }
    
    /**
     * get:(根据ID获取角色信息).
     *
     * 2016年7月29日 下午6:38:41
     * @author Liuzibing
     * @param id
     * @return
    
     */
    public SuRole get(Long id)
    {
      return roleMapper.selectByPrimaryKey(id);
    }
    
    /**
     * list:(根据条件查询资源).
     *
     * 2016年8月1日 下午7:07:39
     * @author Liuzibing
     * @param supplierRoleQuery
     * @return
    
     */
    public List<SuRole> list(SupplierRoleQuery supplierRoleQuery)
    {
      return roleMapper.selectByCondition(supplierRoleQuery);
    }
    /**
     * delete:(根据ID删除角色).
     *
     * 2016年7月29日 下午6:38:53
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
     * 2016年8月1日 下午7:13:50
     * @author Liuzibing
     * @param supplierRoleQuery
     * @return
    
     */
    public int countTotalRecords(SupplierRoleQuery supplierRoleQuery)
    {
      SuRoleExample example=new SuRoleExample();
      example.setDistinct(true);
      example.createCriteria().andRoleStatusEqualTo(supplierRoleQuery.getRoleStatus())
        .andSupplierIdEqualTo(supplierRoleQuery.getSupplierId());
      
      return roleMapper.countByExample(example);
    }

    /** 
     * add:(添加角色).
     * 2016年7月29日 下午5:41:10
     * @param role
     * @return
     */
    public void addMixture(SuRole role) 
    {
      //设置资源信息
      role.setDeleteAllowed(DelAllowedStatusEnum.N);
      role.setRoleStatus(RoleStatusEnum.VALID);
      
      //插入角色
      roleMapper.insertSelective(role);
      
      
      //准备待插入角色资源数据
      List<SuRoleResourceRelation> suRoleResourceRelations = new ArrayList<SuRoleResourceRelation>();
      SuRoleResourceRelation suRoleResourceRelation = null;
      
      for(SuResource suResource : role.getSuResources()){
        suRoleResourceRelation = new SuRoleResourceRelation();
        
        suRoleResourceRelation.setResourceId(suResource.getId());
        suRoleResourceRelation.setRoleId(role.getId());
        suRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        
        suRoleResourceRelations.add(suRoleResourceRelation);
        
        if(suResource.getSuResources() ==null)
            continue;
        for(SuResource suResource0 : suResource.getSuResources()){
          if(suResource0.getId() == null){
            continue;
          }
          suRoleResourceRelation = new SuRoleResourceRelation();
          
          suRoleResourceRelation.setResourceId(suResource0.getId());
          suRoleResourceRelation.setRoleId(role.getId());
          suRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
          
          suRoleResourceRelations.add(suRoleResourceRelation);
        }
      }
      
      //批量插入角色资源关系
      suRoReRelationService.insertSelectiveBatch(suRoleResourceRelations);
    }

     public void modifyMixture(SuRole role) {
    //更新role
    roleMapper.updateByPrimaryKeySelective(role);
    
    //删除角色资源关系
    SuRoleResourceRelation suRoleResourceRelation0 = new SuRoleResourceRelation();
    suRoleResourceRelation0.setRoleId(role.getId());
    
    suRoReRelationService.remove(suRoleResourceRelation0);
    
    //准备待插入角色资源数据
    List<SuRoleResourceRelation> suRoleResourceRelations = new ArrayList<SuRoleResourceRelation>();
    SuRoleResourceRelation suRoleResourceRelation = null;
    
    for(SuResource suResource : role.getSuResources()){
      suRoleResourceRelation = new SuRoleResourceRelation();
      
      suRoleResourceRelation.setResourceId(suResource.getId());
      suRoleResourceRelation.setRoleId(role.getId());
      suRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
      
      suRoleResourceRelations.add(suRoleResourceRelation);
      
      if(suResource.getSuResources() == null)
        continue;
      for(SuResource suResource0 : suResource.getSuResources()){
        suRoleResourceRelation = new SuRoleResourceRelation();
        
        if(suResource0.getId() == null)
          continue;
        suRoleResourceRelation.setResourceId(suResource0.getId());
        suRoleResourceRelation.setRoleId(role.getId());
        suRoleResourceRelation.setRrStatus(RoleStatusEnum.VALID);
        
        suRoleResourceRelations.add(suRoleResourceRelation);
      }
    }
    //批量插入角色资源关系
    suRoReRelationService.insertSelectiveBatch(suRoleResourceRelations);
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
       SuRole suRole = new SuRole();
       
       suRole.setId(id);
       suRole.setRoleStatus(RoleStatusEnum.INVALID);
       
       roleMapper.updateByPrimaryKeySelective(suRole);
     }
}
