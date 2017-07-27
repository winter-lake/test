package com.kxwp.admin.entity.supplier;

import java.util.Date;
import java.util.List;

import com.kxwp.admin.constants.DelAllowedStatusEnum;
import com.kxwp.admin.constants.RoleStatusEnum;

public class SuRole {
    private Long id;

    private String name;

    private DelAllowedStatusEnum deleteAllowed;

    private RoleStatusEnum roleStatus;
    
    private Long supplierId;
    
    private Long createUserId;

    private Date createTime;

    private Date updateTime;

    private String roleDescription;
    
    private List<SuResource> suResources;
    
    private List<SuRoleResourceRelation> suRoleResourceRelations;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public RoleStatusEnum getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(RoleStatusEnum roleStatus) {
        this.roleStatus = roleStatus;
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
        SuRole other = (SuRole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDeleteAllowed() == null ? other.getDeleteAllowed() == null : this.getDeleteAllowed().equals(other.getDeleteAllowed()))
            && (this.getRoleStatus() == null ? other.getRoleStatus() == null : this.getRoleStatus().equals(other.getRoleStatus()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDeleteAllowed() == null) ? 0 : getDeleteAllowed().hashCode());
        result = prime * result + ((getRoleStatus() == null) ? 0 : getRoleStatus().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    /**
     * @return  roleDescription
    
     */
    public String getRoleDescription() {
      return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
      this.roleDescription = roleDescription;
    }

    /**
     * @return  deleteAllowed
    
     */
    public DelAllowedStatusEnum getDeleteAllowed() {
      return deleteAllowed;
    }

    public void setDeleteAllowed(DelAllowedStatusEnum deleteAllowed) {
      this.deleteAllowed = deleteAllowed;
    }

    /**
     * @return  suResources
    
     */
    public List<SuResource> getSuResources() {
      return suResources;
    }

    public void setSuResources(List<SuResource> suResources) {
      this.suResources = suResources;
    }

    /**
     * @return  suRoleResourceRelations
    
     */
    public List<SuRoleResourceRelation> getSuRoleResourceRelations() {
      return suRoleResourceRelations;
    }

    public void setSuRoleResourceRelations(List<SuRoleResourceRelation> suRoleResourceRelations) {
      this.suRoleResourceRelations = suRoleResourceRelations;
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
}