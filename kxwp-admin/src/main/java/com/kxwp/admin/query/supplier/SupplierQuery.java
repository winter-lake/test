package com.kxwp.admin.query.supplier;

import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.common.constants.SystemTypeEnum;

/**
 * 总站查询实体
 * date: 2016年7月28日 下午2:34:25 
 *
 * @author wangjun
 */
public class SupplierQuery{
  private ResourceStatusEnum resourceStatus;
  
  private SystemTypeEnum systemType;
  
  private Integer grade;
  
  private Long parentId;
  
  /**
   * 操作标识
   */
  private String flag;
  
  // 默认页面条数
  private int pageSize = 10;

  // 当前页,从1开始
  private int currentPage = 1;

  private int offset;
  /**
   * @return  resourceStatus
  
   */
  public ResourceStatusEnum getResourceStatus() {
    return resourceStatus;
  }

  public void setResourceStatus(ResourceStatusEnum resourceStatus) {
    this.resourceStatus = resourceStatus;
  }

  /**
   * @return  systemType
  
   */
  public SystemTypeEnum getSystemType() {
    return systemType;
  }

  public void setSystemType(SystemTypeEnum systemType) {
    this.systemType = systemType;
  }

  /**
   * @return  pageSize
  
   */
  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return  currentPage
  
   */
  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * @return  offset
  
   */
  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * @return  grade
  
   */
  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  /**
   * @return  parentId
  
   */
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  /**
   * @return  flag
  
   */
  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }
}
