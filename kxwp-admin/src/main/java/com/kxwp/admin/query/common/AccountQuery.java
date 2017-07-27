package com.kxwp.admin.query.common;

import com.kxwp.common.constants.AccountStatusEnum;

/**
 * 查询实体
 * date: 2016年7月28日 下午2:34:25 
 *
 * @author wangjun
 */
public class AccountQuery{
  private AccountStatusEnum accountStatus;
  
  private Long roleId;
  
  private Long supplier_id;
  
  // 默认页面条数
  private int pageSize = 10;

  // 当前页,从1开始
  private int currentPage = 1;
  
  private Integer offset; 
  
  private Long serviceStationId;
  
  private String name;
  
  private Long mobile;

  /**
   * @return  accountStatus
  
   */
  public AccountStatusEnum getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
  }

  /**
   * @return  roleId
  
   */
  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
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


  public Long getSupplier_id() {
    return supplier_id;
  }

  public void setSupplier_id(Long supplier_id) {
    this.supplier_id = supplier_id;
  }
  
  

  /**
   * @return  serviceStationId
  
   */
  public Long getServiceStationId() {
    return serviceStationId;
  }

  public void setServiceStationId(Long serviceStationId) {
    this.serviceStationId = serviceStationId;
  }

  /**
   * @return  offset
  
   */
  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
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

  /**
   * @return  mobile
  
   */
  public Long getMobile() {
    return mobile;
  }

  public void setMobile(Long mobile) {
    this.mobile = mobile;
  }
}
