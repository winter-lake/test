package com.kxwp.admin.mapper.masterStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MasterStationAccount;
import com.kxwp.admin.entity.masterStation.MasterStationAccountExample;
import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.LoginForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.query.common.AccountQuery;

public interface MasterStationAccountMapper {
  
  
    /**
     * selectByAlive:(只查找有效/失效的账号).
     * 2016年8月16日 下午2:14:20
     * @author Liuzibing
     * @param accountQuery
     * @return
     */
    List<MasterStationAccount> selectByAlive(AccountQuery accountQuery);
    
    /**
     * selectAlivePhone:(查找数据库中存不存在此手机号用户).
     * 说明：如果库里没有此手机号，则不能使用找回密码功能
     * 2016年8月8日 下午4:10:44
     * @author Liuzibing
     * @param mobile
     * @return
     */
    int selectAlivePhone(Long mobile);
  
    int countByExample(MasterStationAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MasterStationAccount record);

    int insertSelective(MasterStationAccount record);

    MasterStationAccount selectByPrimaryKey(Long id);
    
    /**
     * 
     * msLogin:(总站登录).
     *
     * 2016年7月29日 下午4:23:07
     * @author lou jian wen
     * @param loginForm
     * @return
     */
    MasterStationAccount msLogin(LoginForm loginForm);

    
    /**
     * resetPWD:(重置密码).
     *
     * 2016年8月4日 上午10:15:37
     * @author Liuzibing
     * @param resetPWDForm
     * @return
     */
    int resetPWD(ResetPWDForm resetPWDForm);
    
    /**
     * forgotPWD:(忘记密码/找回密码).
     *
     * 2016年8月6日 下午4:26:42
     * @author Liuzibing
     * @param forgotPWDForm
     * @return
     */
    int forgotPWD(ForgotPWDForm forgotPWDForm);
    
    int updateByPrimaryKeySelective(MasterStationAccount record);
    
    int updateByPrimaryKeySelectiveBatch(List<MasterStationAccount> records);

    int updateByPrimaryKey(MasterStationAccount record);

    List<MasterStationAccount> selectByCondition(AccountQuery accountQuery);
}