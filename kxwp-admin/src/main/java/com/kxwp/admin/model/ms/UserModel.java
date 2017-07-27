package com.kxwp.admin.model.ms;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.common.constants.AccountStatusEnum;

/**
 * Date: 2016年7月30日 下午1:35:20
 * 
 * @author lou jian wen
 */
public class UserModel implements Serializable {


  private static final long serialVersionUID = 436068936762203405L;
  
  
 public final static String FWZ_HOST = "fwz.kxp1688.com";
  
  public final static String ZZ_HOST = "zz.kxp1688.com";
  
  public  final static String GYS_HOST = "gys.kxp1688.com";
  
  //是否来来自总站系统
  public static boolean isZZ(String host){
    return StringUtils.contains(host, ZZ_HOST);
  }
  
  //是否来来自服务站
  public static boolean isFWZ(String host){
    return StringUtils.contains(host, FWZ_HOST);
  }
  
  //是否来自供应商
  public static boolean isGYS(String host){
    return StringUtils.contains(host, GYS_HOST);
  }
  // 登录用户id
  private Long id;

  // 用户对应的组织/部门id
  private Long organizationId;

  // 组织名称(供应商/服务站名称)
  private String organizationName;


  
  //角色名称
  private String role_name;



  private String userNo;

  private String alias;

  private String name;
  
  

  private Long mobile;

  private ServiceStation serviceStation;
  
  
  /**
   * @return  role_name
  
   */
  public String getRole_name() {
    return role_name;
  }

  public void setRole_name(String role_name) {
    this.role_name = role_name;
  }


  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  /**
   * @return serviceStation
   * 
   */
  public ServiceStation getServiceStation() {
    return serviceStation;
  }

  public void setServiceStation(ServiceStation serviceStation) {
    this.serviceStation = serviceStation;
  }

  /**
   * @return id
   * 
   */
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return userNo
   * 
   */
  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  /**
   * @return alias
   * 
   */
  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  /**
   * @return name
   * 
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return mobile
   * 
   */
  public Long getMobile() {
    return mobile;
  }

  public void setMobile(Long mobile) {
    this.mobile = mobile;
  }

  /**
   * @return accountStatus
   * 
   */
  public AccountStatusEnum getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
  }



  private AccountStatusEnum accountStatus;

  /**
   * @return organizationId
   * 
   */
  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }


}

