package com.kxwp.admin.mapper.serviceStation;

import java.util.List;

import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.ServiceStationAccountExample;
import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.LoginForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.query.common.AccountQuery;

public interface ServiceStationAccountMapper {
  /**
   * 
   * selectImportData:(查询导入的数据).
   *
   * 2016年8月30日 下午5:40:32
   * @author wangjun
   * @param accountQuery
   * @return
   */
  List<ServiceStationAccount> selectImportData(AccountQuery accountQuery);
  /**
   * selectByAlive:(只查找有效/失效的账号)
   * 2016年8月16日 下午5:45:07
   * @author Liuzibing
   * @param accountQuery
   * @return
   */
  List<ServiceStationAccount> selectByAlive(AccountQuery accountQuery);
  /**
   * selectAlivePhone:(查找数据库中是否存在此手机号的用户). 2016年8月9日 下午2:13:14
   * 
   * @author Liuzibing
   * @param mobile
   * @return
   */
  int selectAlivePhone(Long mobile);

  /**
   * resetPWD:(服务站/重置密码). 2016年8月9日 下午1:58:07
   * 
   * @author Liuzibing
   * @param resetPWDForm
   * @return
   */
  int resetPWD(ResetPWDForm resetPWDForm);

  /**
   * forgotPWD:(忘记密码/找回密码). 2016年8月9日 下午2:11:54
   * 
   * @author Liuzibing
   * @param forgotPWDForm
   * @return
   */
  int forgotPWD(ForgotPWDForm forgotPWDForm);

  /**
   * 
   * fwzLogin:(服务站登录).
   *
   * 2016年7月29日 下午4:23:07
   * 
   * @author lou jian wen
   * @param loginForm
   * @return
   */
  ServiceStationAccount fwzLogin(LoginForm loginForm);

  int countByExample(ServiceStationAccountExample example);

  int deleteByPrimaryKey(Long id);

  int insert(ServiceStationAccount record);

  int insertSelective(ServiceStationAccount record);

  ServiceStationAccount selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(ServiceStationAccount record);

  int updateByPrimaryKeySelectiveBatch(List<ServiceStationAccount> records);

  int updateByPrimaryKey(ServiceStationAccount record);

  List<ServiceStationAccount> selectByCondition(AccountQuery accountQuery);

}
