package com.kxwp.admin.mapper.supplier;

import java.util.List;

import com.kxwp.admin.entity.supplier.SupplierAccount;
import com.kxwp.admin.entity.supplier.SupplierAccountExample;
import com.kxwp.admin.form.user.ForgotPWDForm;
import com.kxwp.admin.form.user.LoginForm;
import com.kxwp.admin.form.user.ResetPWDForm;
import com.kxwp.admin.query.common.AccountQuery;

public interface SupplierAccountMapper {
  int updateByConditionSelective(SupplierAccount record);
  
  /**
   * 
   * selectImportData:(查询导入的数据).
   *
   * 2016年8月30日 下午7:14:20
   * @author wangjun
   * @param accountQuery
   * @return
   */
  List<SupplierAccount> selectImportData(AccountQuery accountQuery);
    /**
     * selectByAlive:(只查找有效/失效的账号).
     * 2016年8月16日 下午6:29:39
     * @author Liuzibing
     * @param accountQuery
     * @return
     */
    List<SupplierAccount> selectByAlive(AccountQuery accountQuery);
    
    /**
     * forgotPWD:(供应商忘记密码).
     * 2016年8月11日 下午3:31:37
     * @author Liuzibing
     * @param forgotPWDForm
     * @return
     */
    int forgotPWD(ForgotPWDForm forgotPWDForm);
  
    /**
     * selectAlivePhone:(查找数据库中是否存在此手机号的用户).
     * 2016年8月11日 下午3:30:10
     * @author Liuzibing
     * @param mobile
     * @return
     */
    int selectAlivePhone(Long mobile);
  
    /**
     * resetPWD:(供应商重置密码).
     * 2016年8月11日 下午3:27:03
     * @author Liuzibing
     * @param resetPWDForm
     * @return
     */
    int resetPWD(ResetPWDForm resetPWDForm);
    
    SupplierAccount gysLogin(LoginForm loginForm); 
  
    int countByExample(SupplierAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierAccount record);

    int insertSelective(SupplierAccount record);

    SupplierAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierAccount record);
    
    int updateByPrimaryKeySelectiveBatch(List<SupplierAccount> records);

    int updateByPrimaryKey(SupplierAccount record);

    List<SupplierAccount> selectByCondition(AccountQuery accountQuery);
}