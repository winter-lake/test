package com.kxwp.admin.actions.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.admin.model.common.KXWPEnumProperty;
import com.kxwp.admin.query.common.KXWPEnumQuery;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.model.exception.EnumNotFoundException;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * 获取枚举属性值 date: 2016年7月29日 下午2:07:20
 *
 * @author lou jian wen
 */
@Controller
@RequestMapping("/common")
public class KXWPEnumAction {

  @Autowired
  private AbstractCacheService cacheService;

  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/getEnumListByName.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData getEnumListByName(KXWPEnumQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData exchangeData = new ExchangeData();
    try {
      exchangeData.setData(getListByEnumName(query.getEnumname()));
    } catch (Exception e) {
      KXWPLogUtils.logException(KXWPEnumAction.class, e);
      exchangeData.markException("根据枚举名称转化list异常", e);
    }

    return exchangeData;
  }

  @SuppressWarnings("rawtypes")
  List<KXWPEnumProperty> getListByEnumName(String enumname) throws Exception {
    // 查找缓存中是否存在枚举列表
    List<KXWPEnumProperty> retList = 
        cacheService.getValue(CachekeyPrefix.ADMIN_COMMON_ENUM + enumname);
    if (retList == null) {
      Reflections reflections = new Reflections("com.kxwp.admin.constants");
      Set<Class<? extends Enum>> allEnums = reflections.getSubTypesOf(Enum.class);
      if (allEnums != null && allEnums.size() > 0) {
        Class<? extends Enum> enumclass_ = null;
        // 循环查询所有枚举类型class
        for (Class<? extends Enum> class_ : allEnums) {
          if (StringUtils.equals(class_.getSimpleName(), enumname)) {
            enumclass_ = class_;
          }
        }
        
        if(enumclass_ == null){
          // 尝试通过common包获取
          reflections = new Reflections("com.kxwp.common.constants");
          allEnums = reflections.getSubTypesOf(Enum.class);
          if (allEnums != null && allEnums.size() > 0) {
            enumclass_ = null;
            // 循环查询所有枚举类型class
            for (Class<? extends Enum> class_ : allEnums) {
              if (StringUtils.equals(class_.getSimpleName(), enumname)) {
                enumclass_ = class_;
              }
            }
            if (enumclass_ == null) {
              throw new EnumNotFoundException(enumname + "未查到枚举类型");
            }

            Enum[] consts = enumclass_.getEnumConstants();
            if (consts != null && consts.length > 0) {
              retList = new ArrayList<KXWPEnumProperty>();
              for (int i = 0; i < consts.length; i++) {
                KXWPEnumProperty p = new KXWPEnumProperty();
                Class<?> sub = consts[i].getClass();
                Method descmth = sub.getDeclaredMethod("getDesc");
                Method codemth = sub.getDeclaredMethod("getCode");
                String desc = (String) descmth.invoke(consts[i]);
                Integer code = (Integer) codemth.invoke(consts[i]);
                p.setCode(code);
                p.setDesc(desc);
                p.setName(consts[i].name());
                retList.add(p);
              }
              if (retList != null && retList.size() > 0) {
                cacheService.putKey(CachekeyPrefix.ADMIN_COMMON_ENUM + enumname, retList,
                    AbstractCacheService.ONEDAY);
                return retList;
              }
            }
          }
        }
        
       /* if (enumclass_ == null) {
          throw new EnumNotFoundException(enumname + "未查到枚举类型");
        }*/

        Enum[] consts = enumclass_.getEnumConstants();
        if (consts != null && consts.length > 0) {
          retList = new ArrayList<KXWPEnumProperty>();
          for (int i = 0; i < consts.length; i++) {
            KXWPEnumProperty p = new KXWPEnumProperty();
            Class<?> sub = consts[i].getClass();
            Method descmth = sub.getDeclaredMethod("getDesc");
            Method codemth = sub.getDeclaredMethod("getCode");
            String desc = (String) descmth.invoke(consts[i]);
            Integer code = (Integer) codemth.invoke(consts[i]);
            p.setCode(code);
            p.setDesc(desc);
            p.setName(consts[i].name());
            retList.add(p);
          }
          if (retList != null && retList.size() > 0) {
            cacheService.putKey(CachekeyPrefix.ADMIN_COMMON_ENUM + enumname, retList,
                AbstractCacheService.ONEDAY);
          } 
        }
      }
    }
    return retList;
  }
}
