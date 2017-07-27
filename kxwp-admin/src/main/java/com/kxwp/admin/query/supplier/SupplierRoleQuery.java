package com.kxwp.admin.query.supplier;

import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.common.constants.SystemTypeEnum;

/**
 * 供应商查询角色实体
 * date: 2016年8月1日 下午5:46:03 
 *
 * @author Liuzibing
 */
public class SupplierRoleQuery {
private RoleStatusEnum roleStatus;
  
  private SystemTypeEnum systemType;
  
  private Long supplierId;
  
  private String name;
  
  // 默认页面条数
  private int pageSize = 10;

  // 当前页,从1开始
  private int currentPage = 1;
  
  private int offset;

  
  
  public RoleStatusEnum getRoleStatus() {
    return roleStatus;
  }

  public void setRoleStatus(RoleStatusEnum roleStatus) {
    this.roleStatus = roleStatus;
  }

  public SystemTypeEnum getSystemType() {
    return systemType;
  }

  public void setSystemType(SystemTypeEnum systemType) {
    this.systemType = systemType;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * @return  supplierId
  
   */
  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  /**
   * @return  name
  
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
}
