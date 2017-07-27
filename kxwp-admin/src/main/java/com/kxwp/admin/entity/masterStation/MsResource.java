package com.kxwp.admin.entity.masterStation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.kxwp.admin.constants.ResourceStatusEnum;
import com.kxwp.common.constants.SystemTypeEnum;

public class MsResource implements Serializable,Cloneable,Comparable<MsResource>{
    @Override
  public String toString() {
    return "MsResource [id=" + id + ", name=" + name + ", parentId=" + parentId + ", parentName="
        + parentName + ", grade=" + grade + ", url=" + url + ", description=" + description
        + ", ordernum=" + ordernum + ", resourceStatus=" + resourceStatus + ", createUserId="
        + createUserId + ", createTime=" + createTime + ", updateTime=" + updateTime
        + ", ownSystem=" + ownSystem + ", moduleUrls=" + moduleUrls + ", msResources=" + msResources
        + "]";
  }

    private static final long serialVersionUID = -183488997900061284L;

    private Long id;

    private String name;

    private Long parentId;
    
    private String parentName;

    private Integer grade;

    private String url;

    private String description;
    
    private Integer ordernum;//默认为空，可以手动从数据库修改，前台不提供设置功能

    private ResourceStatusEnum resourceStatus = ResourceStatusEnum.ONLINE;
    
    private Long createUserId;

    private Date createTime;

    private Date updateTime;
    
    private SystemTypeEnum ownSystem = SystemTypeEnum.MasterStation;//所属系统
    
    private List<String> moduleUrls;
    
    private List<MsResource> msResources;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public ResourceStatusEnum getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(ResourceStatusEnum resourceStatus) {
        this.resourceStatus = resourceStatus;
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
        MsResource other = (MsResource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getGrade() == null ? other.getGrade() == null : this.getGrade().equals(other.getGrade()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getOrdernum() == null ? other.getOrdernum() == null : this.getOrdernum().equals(other.getOrdernum()))
            && (this.getResourceStatus() == null ? other.getResourceStatus() == null : this.getResourceStatus().equals(other.getResourceStatus()))
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
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getGrade() == null) ? 0 : getGrade().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getOrdernum() == null) ? 0 : getOrdernum().hashCode());
        result = prime * result + ((getResourceStatus() == null) ? 0 : getResourceStatus().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    /**
     * @return  ownSystem
    
     */
    public SystemTypeEnum getOwnSystem() {
      return ownSystem;
    }

    public void setOwnSystem(SystemTypeEnum ownSystem) {
      this.ownSystem = ownSystem;
    }

    /**
     * @return  moduleUrls
    
     */
    public List<String> getModuleUrls() {
      return moduleUrls;
    }

    public void setModuleUrls(List<String> moduleUrls) {
      this.moduleUrls = moduleUrls;
    }
    
    @Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
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
     * @return  parentName
    
     */
    public String getParentName() {
      return parentName;
    }

    public void setParentName(String parentName) {
      this.parentName = parentName;
    }

    /**
     * @return  msResources
    
     */
    public List<MsResource> getMsResources() {
      return msResources;
    }

    public void setMsResources(List<MsResource> msResources) {
      this.msResources = msResources;
    }

    @Override
    public int compareTo(MsResource o) {
      if(this.getOrdernum() != null && o.getOrdernum() != null){
        return this.getOrdernum().compareTo(o.getOrdernum());
      }else{
        return this.getId().compareTo(o.getId());
      }
      
    }  
}