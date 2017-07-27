package com.kxwp.admin.entity.supermarket;

import java.util.Date;

import com.kxwp.common.constants.AccountStatusEnum;

public class SupermarketAccount {
  @Override
  public String toString() {
    return "SupermarketAccount [id=" + id + ", supmarketId=" + supmarketId + ", name=" + name
        + ", alias=" + alias + ", password=" + password + ", mobile=" + mobile + ", accountStatus="
        + accountStatus + ", createUserId=" + createUserId + ", createTime=" + createTime
        + ", updateTime=" + updateTime + "]";
  }

  private Long id;
  
  private String userNo;

  private Long supmarketId;

  private String name;

  private String alias;

  private String password;

  private Long mobile;

  private AccountStatusEnum accountStatus;

  private Long createUserId;

  private Date createTime;

  private Date updateTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSupmarketId() {
    return supmarketId;
  }

  public void setSupmarketId(Long supmarketId) {
    this.supmarketId = supmarketId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias == null ? null : alias.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password == null ? null : password.trim();
  }

  public Long getMobile() {
    return mobile;
  }

  public void setMobile(Long mobile) {
    this.mobile = mobile;
  }

  public AccountStatusEnum getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  @Override
  public boolean equals(Object that) {
      if (this == that) {
          return true;
      }
      if (that == null) {
          return false;
      }
      if (getClass() != that.getClass()) {
          return false;
      }
      SupermarketAccount other = (SupermarketAccount) that;
      return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
          && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
          && (this.getSupmarketId() == null ? other.getSupmarketId() == null : this.getSupmarketId().equals(other.getSupmarketId()))
          && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
          && (this.getAlias() == null ? other.getAlias() == null : this.getAlias().equals(other.getAlias()))
          && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
          && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
          && (this.getAccountStatus() == null ? other.getAccountStatus() == null : this.getAccountStatus().equals(other.getAccountStatus()))
          && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
          && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
          && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
  }

  @Override
  public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
      result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
      result = prime * result + ((getSupmarketId() == null) ? 0 : getSupmarketId().hashCode());
      result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
      result = prime * result + ((getAlias() == null) ? 0 : getAlias().hashCode());
      result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
      result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
      result = prime * result + ((getAccountStatus() == null) ? 0 : getAccountStatus().hashCode());
      result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
      result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
      result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
      return result;
  }
}
