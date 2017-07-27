package com.kxwp.admin.actions.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kxwp.common.constants.HostNameConst;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * Date:     2016年8月5日 下午4:20:48 
 * @author   lou jian wen 
 */
@Controller
@RequestMapping("/common/home")
public class HomePageAction {

  
  @RequestMapping(value = "/index.do")
  public String index(HttpServletRequest request, HttpServletResponse response) {
    String host = request.getHeader("host");
    KXWPLogUtils.logInfo(this.getClass(), host);
    if(StringUtils.containsIgnoreCase(host, HostNameConst.ZZ_HOST)){
      return "zz/zz_login";
    }
    
    if(StringUtils.containsIgnoreCase(host, HostNameConst.FWZ_HOST)){
      return "fwz/fwz_login";
    }
    
    if(StringUtils.containsIgnoreCase(host, HostNameConst.GYS_HOST)){
      return "gys/gys_login";
    }
    return "zz/zz_login";
  }
}

