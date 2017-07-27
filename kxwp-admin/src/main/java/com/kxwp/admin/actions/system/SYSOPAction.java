package com.kxwp.admin.actions.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.utils.ReflectionUtils;

/**
 * Date: 2016年9月4日 上午9:38:59
 * 
 * @author lou jian wen
 */
/**
 * 系统维护 date: 2016年9月4日 上午9:39:03
 *
 * @author lou jian wen
 */
@Controller
@RequestMapping("/zz/op")
public class SYSOPAction {


  @Autowired
  private AbstractCacheService cacheService;

  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/clearCache.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      method = {RequestMethod.GET})
  public @ResponseBody ExchangeData clearCache(String cacheKeyStr,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    List<String> cacheKeyList = new ArrayList<>();
    if(StringUtils.isNotBlank(cacheKeyStr)){
      cacheKeyList = Arrays.asList(cacheKeyStr.split(","));
    }
    try {
      if (cacheKeyList == null || cacheKeyList.size() == 0) {
        cacheKeyList = ReflectionUtils.getClassFiled("com.kxwp.common.constants.CachekeyPrefix");
      }
      for (String cachek_key : cacheKeyList) {
        cacheService.removeLikeCache(cachek_key);
      }
    } catch (Exception e) {
      edb.markException(e);
    }
    return edb;
  }
}

