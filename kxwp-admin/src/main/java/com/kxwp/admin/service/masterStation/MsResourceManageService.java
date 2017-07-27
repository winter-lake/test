package com.kxwp.admin.service.masterStation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.masterStation.MsResourceExample;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.mapper.masterStation.MsResourceMapper;
import com.kxwp.admin.query.masterStation.MasterStationQuery;
import com.kxwp.admin.query.serviceStation.ServiceStationQuery;
import com.kxwp.admin.query.supplier.SupplierQuery;
import com.kxwp.admin.service.serviceStation.SsResourceManageService;
import com.kxwp.admin.service.supplier.SuResourceManageService;
import com.kxwp.common.constants.SystemTypeEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.page.BasePager;
import com.kxwp.common.model.page.MySQLPager;

/**
 * 资源管理service date: 2016年7月27日 下午2:53:00
 *
 * @author wangjun
 */
@Service("msResourceManageService")
public class MsResourceManageService {

  @Autowired
  private MsResourceMapper resourceMapper;
  
  @Autowired
  private SsResourceManageService ssResourceManageService;
  
  @Autowired
  private SuResourceManageService suResourceManageService;
  

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
   * @throws CloneNotSupportedException 
   */
  public int add(MsResource resource) throws IllegalAccessException, InvocationTargetException{
    // 设置资源信息
    resource.setResourceStatus(ResourceStatusEnum.ONLINE);
    if(resource.getParentId() == null){
      resource.setParentId(0L);
    }
    //定义批量参数
    List<MsResource> resources = new ArrayList<MsResource>();
    
    //加入父级
    resourceMapper.insertSelective(resource);
    
    //加入子级
    if(resource.getModuleUrls() != null)
    {
      MsResource resourceTemp = null;
      for(String url : resource.getModuleUrls())
      {
        resourceTemp = new MsResource();
        
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
  public int modify(List<MsResource>  msResources){
      return resourceMapper.updateByPrimaryKeySelectiveBatch(msResources);
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
  public MsResource get(Long id){
      //根据id查询资源
      MsResource msResource = resourceMapper.selectByPrimaryKey(id);
      
      if(msResource != null){
        //如果不是二级菜单
        if(msResource.getGrade() != 2)
          return msResource;
        
        //定义资源子集
        List<String> moduleUrls = new ArrayList<String>();
        
        //定义查询条件
        MasterStationQuery mStationQuery = new MasterStationQuery();
        
        mStationQuery.setParentId(id);
        mStationQuery.setPageSize(0);
        mStationQuery.setOffset(0);
        mStationQuery.setFlag("");
        
        //查询子集
        List<MsResource> msResources = resourceMapper.selectByCondition(mStationQuery);
        
        for(MsResource msResource2 : msResources){
          moduleUrls.add(msResource2.getUrl());
        }
        
        msResource.setModuleUrls(moduleUrls);
      }
      
      return msResource;
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
  public List<MsResource> list(MasterStationQuery masterStationQuery){
    return resourceMapper.selectByCondition(masterStationQuery);
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
  public int countTotalRecords(MasterStationQuery masterStationQuery){
    MsResourceExample example = new MsResourceExample();
    
    example.setDistinct(true);
    if(masterStationQuery.getResourceStatus() != null)
    example.createCriteria().andResourceStatusEqualTo(masterStationQuery.getResourceStatus());
    example.createCriteria().andGradeNotEqualTo(3);
    
    return resourceMapper.countByExample(example);
  }
  
  /**
   * 
   * getFinalResult:(根据条件获取结果集).
   *
   * 2016年7月29日 下午6:23:02
   * @author wangjun
   * @param masterStationQuery
   * @return
   */
  public List<MsResource> getFinalResult(MasterStationQuery masterStationQuery) {
    //定义存放表格数据列表
    List<MsResource> finalResult = new ArrayList<MsResource>();
    
    //如果是全部
    if(masterStationQuery.getSystemType() == null)
    {
      //创建服务站查询对象
      ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
      serviceStationQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      
      //定义供应商查询对象
      SupplierQuery supplierQuery = new SupplierQuery();
      supplierQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      
      masterStationQuery.setOffset((masterStationQuery.getCurrentPage() - 1)*masterStationQuery.getPageSize());
      List<MsResource> msResources = this.list(masterStationQuery);
      for(MsResource msResource : msResources){
        MsResource temp = resourceMapper.selectByPrimaryKey(msResource.getParentId());
        msResource.setParentName(temp == null ? "" : temp.getName());
      }
      finalResult.addAll(msResources);
      
      MsResource msResource;
      
      //如果从总站获取到的数据条数小于每页条数
      if(finalResult.size() < masterStationQuery.getPageSize()){
        //从服务站获取
        serviceStationQuery.setPageSize(masterStationQuery.getPageSize() - finalResult.size());
        serviceStationQuery.setOffset(Math.max((masterStationQuery.getCurrentPage() - 1)*masterStationQuery.getPageSize() - this.countTotalRecords(masterStationQuery), 0));
        List<SsResource> ssResources = ssResourceManageService.list(serviceStationQuery);
        for(SsResource ssResource : ssResources){
          SsResource temp = ssResourceManageService.get(ssResource.getParentId());
          ssResource.setParentName(temp == null ? "" : temp.getName());
          ssResource.setOwnSystem(SystemTypeEnum.ServiceStation);
          msResource = new MsResource();
          msResource = ssResource;
          finalResult.add(msResource);
        }
        //从供应商获取
        if(finalResult.size() < masterStationQuery.getPageSize()){
          supplierQuery.setPageSize(masterStationQuery.getPageSize() - finalResult.size());
          supplierQuery.setOffset(Math.max((masterStationQuery.getCurrentPage() - 1) 
              * masterStationQuery.getPageSize() 
              - this.countTotalRecords(masterStationQuery)
              - ssResourceManageService.countTotalRecords(serviceStationQuery), 0));
          List<SuResource> suResources = suResourceManageService.list(supplierQuery);
          for(SuResource suResource : suResources){
            SuResource temp = suResourceManageService.get(suResource.getParentId());
            suResource.setParentName(temp == null ? "" : temp.getName());
            suResource.setOwnSystem(SystemTypeEnum.Supplier);
            msResource = new MsResource();
            msResource = suResource;
            finalResult.add(msResource);
          }
        }
      }
      
    }
    //如果是总站
    else if(masterStationQuery.getSystemType() == SystemTypeEnum.MasterStation){
      masterStationQuery.setOffset((masterStationQuery.getCurrentPage() - 1)*masterStationQuery.getPageSize());
      List<MsResource> msResources = this.list(masterStationQuery);
      
      for(MsResource msResource : msResources){
        MsResource temp = resourceMapper.selectByPrimaryKey(msResource.getParentId());
        msResource.setParentName(temp == null ? "" : temp.getName());
      }
      
      finalResult.addAll(msResources);
      
    }
    //如果是服务站
    else if(masterStationQuery.getSystemType() == SystemTypeEnum.ServiceStation){
      //创建服务站查询对象
      ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
      serviceStationQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      serviceStationQuery.setCurrentPage(masterStationQuery.getCurrentPage());
      serviceStationQuery.setPageSize(masterStationQuery.getPageSize());
      serviceStationQuery.setOffset((serviceStationQuery.getCurrentPage() - 1)*serviceStationQuery.getPageSize());
      
      MsResource msResource;
      
      List<SsResource> ssResources = ssResourceManageService.list(serviceStationQuery);
      for(SsResource ssResource : ssResources){
        SsResource temp = ssResourceManageService.get(ssResource.getParentId());
        ssResource.setParentName(temp == null ? "" : temp.getName());
        ssResource.setOwnSystem(SystemTypeEnum.ServiceStation);
        msResource = new MsResource();
        msResource = ssResource;
        finalResult.add(msResource);
      }
      
    }
  //如果是供应商
    else if(masterStationQuery.getSystemType() == SystemTypeEnum.Supplier){
      //定义供应商查询对象
      SupplierQuery supplierQuery = new SupplierQuery();
      supplierQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      supplierQuery.setCurrentPage(masterStationQuery.getCurrentPage());
      supplierQuery.setPageSize(masterStationQuery.getPageSize());
      supplierQuery.setOffset((supplierQuery.getCurrentPage() - 1)*supplierQuery.getPageSize());
      
      MsResource msResource;
      
      List<SuResource> suResources = suResourceManageService.list(supplierQuery);
      for(SuResource suResource : suResources){
        SuResource temp = suResourceManageService.get(suResource.getParentId());
        suResource.setParentName(temp == null ? "" : temp.getName());
        suResource.setOwnSystem(SystemTypeEnum.Supplier);
        msResource = new MsResource();
        msResource = suResource;
        finalResult.add(msResource);
      }
      
    }
    
    return finalResult;
  }
  
  /**
   * 
   * getFinalResult:(获取基本信息).
   *
   * 2016年7月29日 下午6:23:02
   * @author wangjun
   * @param masterStationQuery
   * @return
   */
  public ExchangeData<Object> exchangeData(MasterStationQuery masterStationQuery) {
    //定义返回数据
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    
    //如果是全部
    if(masterStationQuery.getSystemType() == null)
    {
      BasePager pager = new MySQLPager();
      pager.setCurrentPage(masterStationQuery.getCurrentPage());
      pager.setPageSize(masterStationQuery.getPageSize());
      
      int tempMs = 0;
      tempMs = this.countTotalRecords(masterStationQuery);
      
      //创建服务站查询对象
      ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
      serviceStationQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      serviceStationQuery.setCurrentPage(masterStationQuery.getCurrentPage());
      serviceStationQuery.setPageSize(masterStationQuery.getPageSize());
      
      int tempSs = 0;
      tempSs = ssResourceManageService.countTotalRecords(serviceStationQuery);
      
      //定义供应商查询对象
      SupplierQuery supplierQuery = new SupplierQuery();
      supplierQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      supplierQuery.setCurrentPage(masterStationQuery.getCurrentPage());
      supplierQuery.setPageSize(masterStationQuery.getPageSize());
      
      int tempSu = 0;
      tempSu = suResourceManageService.countTotalRecords(supplierQuery);
      
      
      int totalResults = tempMs + tempSs + tempSu;
      
      pager.setTotalPages((int) Math.ceil(totalResults/masterStationQuery.getPageSize()));
      pager.setTotoalResults(totalResults);
      
      exchangeData.setPager(pager);
    }
    //如果是总站
    else if(masterStationQuery.getSystemType() == SystemTypeEnum.MasterStation){
      BasePager pager = new MySQLPager();
      pager.setCurrentPage(masterStationQuery.getCurrentPage());
      pager.setPageSize(masterStationQuery.getPageSize());
      
      int temp = 0;
      temp = this.countTotalRecords(masterStationQuery);
      
      pager.setTotalPages((int) Math.ceil(temp/masterStationQuery.getPageSize()));
      pager.setTotoalResults(temp);
      
      exchangeData.setPager(pager);
    }
    //如果是服务站
    else if(masterStationQuery.getSystemType() == SystemTypeEnum.ServiceStation){
      //创建服务站查询对象
      ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
      serviceStationQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      serviceStationQuery.setCurrentPage(masterStationQuery.getCurrentPage());
      serviceStationQuery.setPageSize(masterStationQuery.getPageSize());
      
      BasePager pager = new MySQLPager();
      pager.setCurrentPage(serviceStationQuery.getCurrentPage());
      pager.setPageSize(serviceStationQuery.getPageSize());
      
      int temp = 0;
      temp = ssResourceManageService.countTotalRecords(serviceStationQuery);
      
      pager.setTotalPages((int) Math.ceil(temp/serviceStationQuery.getPageSize()));
      pager.setTotoalResults(temp);
      
      exchangeData.setPager(pager);
    }
    //如果是供应商
    else if(masterStationQuery.getSystemType() == SystemTypeEnum.Supplier){
      //定义供应商查询对象
      SupplierQuery supplierQuery = new SupplierQuery();
      supplierQuery.setResourceStatus(masterStationQuery.getResourceStatus());
      supplierQuery.setCurrentPage(masterStationQuery.getCurrentPage());
      supplierQuery.setPageSize(masterStationQuery.getPageSize());
      
      BasePager pager = new MySQLPager();
      pager.setCurrentPage(supplierQuery.getCurrentPage());
      pager.setPageSize(supplierQuery.getPageSize());
      
      int temp = 0;
      temp = suResourceManageService.countTotalRecords(supplierQuery);
      pager.setTotalPages((int) Math.ceil(temp/supplierQuery.getPageSize()));
      pager.setTotoalResults(temp);
      
      exchangeData.setPager(pager);
    }
    
    return exchangeData;
  }

  public List<MsResource> listResource(String ownSystem, Integer grade) throws IllegalAccessException, InvocationTargetException {
    List<MsResource> msResources = new ArrayList<MsResource>();
    MsResource msResource = null;
    
    if("".equals(ownSystem))
      return msResources;
    
    switch (SystemTypeEnum.valueOf(ownSystem)) {
      case MasterStation:
        MasterStationQuery masterStationQuery = new MasterStationQuery();
        masterStationQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
        masterStationQuery.setGrade(grade);
        
        msResources =  resourceMapper.selectByCondition(masterStationQuery);
        break;
      case ServiceStation:
        ServiceStationQuery serviceStationQuery = new ServiceStationQuery();
        serviceStationQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
        serviceStationQuery.setGrade(grade);
        
        List<SsResource> ssResources = ssResourceManageService.list(serviceStationQuery);
        for(SsResource ssResource : ssResources){
          msResource = new MsResource();
          BeanUtilsBean2.getInstance().copyProperties(msResource, ssResource);
          
          msResources.add(msResource);
        }
        break;
      
      case Supplier:
        SupplierQuery supplierQuery = new SupplierQuery();
        supplierQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
        supplierQuery.setGrade(grade);
        
        List<SuResource> suResources =  suResourceManageService.list(supplierQuery);
        for(SuResource suResource : suResources){
          msResource = new MsResource();
          BeanUtilsBean2.getInstance().copyProperties(msResource, suResource);
          
          msResources.add(suResource);
        }
        break;
      default:
        break;
    }
    
    return msResources;
  }
  
  /**
   * 
   * getMsResourceTree:(获取资源信息结构).
   *
   * 2016年8月6日 上午10:18:32
   * @author wangjun
   * @return
   */
  public List<MsResource> getMsResourceTree()
  {
    MasterStationQuery masterStationQuery=new MasterStationQuery();
    
    //查找所有的一级资源
    masterStationQuery.setGrade(1);
    masterStationQuery.setResourceStatus(ResourceStatusEnum.ONLINE);
    masterStationQuery.setPageSize(0);
    
    List<MsResource> oneResList = this.list(masterStationQuery);
    
    //查找所有二级资源
    List<MsResource> twoResList = null;
    
    masterStationQuery.setGrade(2);
    
    for(MsResource xx : oneResList)
    {
      masterStationQuery.setParentId(xx.getId());
      
      twoResList = this.list(masterStationQuery);
      
      xx.setMsResources(twoResList);
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
    MsResourceExample example = new MsResourceExample();
    
    example.createCriteria().andUrlEqualTo(fieldValue);
    
    int count = 0;
    count = resourceMapper.countByExample(example);
    
    if(count==0){
      return true;
    }else{
      return false;
    }
    
  }
  /**
   * 
   * checkURL:(判断该url的资源是否存在).
   *
   * 2016年8月19日 下午3:08:57
   * @author wangjun
   * @param fieldValue
   */
  public Boolean checkURLMixture(String fieldValue) {
   return this.checkURL(fieldValue) && ssResourceManageService.checkURL(fieldValue) && suResourceManageService.checkURL(fieldValue);
  }
}
