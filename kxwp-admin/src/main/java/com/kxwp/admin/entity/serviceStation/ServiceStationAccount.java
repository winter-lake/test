package com.kxwp.admin.entity.serviceStation;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kxwp.common.constants.AccountStatusEnum;

public class ServiceStationAccount {

  @Override
  public String toString() {
    return "ServiceStationAccount [id=" + id + ", serviceStationId=" + serviceStationId
        + ", userNo=" + userNo + ", alias=" + alias + ", name=" + name + ", userPassword="
        + userPassword + ", mobile=" + mobile + ", accountStatus=" + accountStatus + ", parentId="
        + parentId + ", grade=" + grade + ", createUserId=" + createUserId + ", createTime="
        + createTime + ", updateTime=" + updateTime + "]";
  }

  private Long id;

  private Long serviceStationId;

  private String userNo;

  private String alias;

  private String name;

  private String userPassword;

  private Long mobile;

  private AccountStatusEnum accountStatus;

  private Long parentId;

  private String grade;

  private Long createUserId;

  private Date createTime;

  private Date updateTime;

  /**
   * 生成的随机密码
   */
  private String random;

  private List<SsRole> ssRoles;// ~
  
  /**
   * 所有角色对应资源合并在一起后的集合
   */
  private Set<SsResource> ssResourcesFinal;
  
  private String isInit;
  
  private String serviceStationName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getServiceStationId() {
    return serviceStationId;
  }

  public void setServiceStationId(Long serviceStationId) {
    this.serviceStationId = serviceStationId;
  }

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo == null ? null : userNo.trim();
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias == null ? null : alias.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword == null ? null : userPassword.trim();
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

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade == null ? null : grade.trim();
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
    ServiceStationAccount other = (ServiceStationAccount) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getServiceStationId() == null ? other.getServiceStationId() == null
            : this.getServiceStationId().equals(other.getServiceStationId()))
        && (this.getUserNo() == null ? other.getUserNo() == null
            : this.getUserNo().equals(other.getUserNo()))
        && (this.getAlias() == null ? other.getAlias() == null
            : this.getAlias().equals(other.getAlias()))
        && (this.getName() == null ? other.getName() == null
            : this.getName().equals(other.getName()))
        && (this.getUserPassword() == null ? other.getUserPassword() == null
            : this.getUserPassword().equals(other.getUserPassword()))
        && (this.getMobile() == null ? other.getMobile() == null
            : this.getMobile().equals(other.getMobile()))
        && (this.getAccountStatus() == null ? other.getAccountStatus() == null
            : this.getAccountStatus().equals(other.getAccountStatus()))
        && (this.getParentId() == null ? other.getParentId() == null
            : this.getParentId().equals(other.getParentId()))
        && (this.getGrade() == null ? other.getGrade() == null
            : this.getGrade().equals(other.getGrade()))
        && (this.getCreateUserId() == null ? other.getCreateUserId() == null
            : this.getCreateUserId().equals(other.getCreateUserId()))
        && (this.getCreateTime() == null ? other.getCreateTime() == null
            : this.getCreateTime().equals(other.getCreateTime()))
        && (this.getUpdateTime() == null ? other.getUpdateTime() == null
            : this.getUpdateTime().equals(other.getUpdateTime()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result =
        prime * result + ((getServiceStationId() == null) ? 0 : getServiceStationId().hashCode());
    result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
    result = prime * result + ((getAlias() == null) ? 0 : getAlias().hashCode());
    result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
    result = prime * result + ((getUserPassword() == null) ? 0 : getUserPassword().hashCode());
    result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
    result = prime * result + ((getAccountStatus() == null) ? 0 : getAccountStatus().hashCode());
    result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
    result = prime * result + ((getGrade() == null) ? 0 : getGrade().hashCode());
    result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
    result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
    return result;
  }

  /**
   * @return random
   * 
   */
  public String getRandom() {
    return random;
  }

  public void setRandom(String random) {
    this.random = random;
  }

  /**
   * @return ssRoles
   * 
   */
  public List<SsRole> getSsRoles() {
    return ssRoles;
  }

  public void setSsRoles(List<SsRole> ssRoles) {
    this.ssRoles = ssRoles;
  }

  /**
   * @return  ssResourcesFinal
  
   */
  public Set<SsResource> getSsResourcesFinal() {
    return ssResourcesFinal;
  }

  public void setSsResourcesFinal(Set<SsResource> ssResourcesFinal) {
    this.ssResourcesFinal = ssResourcesFinal;
  }

  /**
   * @return  isInit
  
   */
  public String getIsInit() {
    return isInit;
  }

  public void setIsInit(String isInit) {
    this.isInit = isInit;
  }

  /**
   * @return  serviceStationName
  
   */
  public String getServiceStationName() {
    return serviceStationName;
  }

  public void setServiceStationName(String serviceStationName) {
    this.serviceStationName = serviceStationName;
  }
}
