package com.kxwp.admin.entity.serviceStation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kxwp.common.constants.FWZTypeEnum;
import com.kxwp.common.constants.OrganizationStatusEnum;

public class ServiceStationExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public ServiceStationExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  protected abstract static class GeneratedCriteria {
    protected List<Criterion> stationStatusCriteria;

    protected List<Criterion> serviceStaionTypeCriteria;

    protected List<Criterion> allCriteria;

    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
      stationStatusCriteria = new ArrayList<Criterion>();
      serviceStaionTypeCriteria = new ArrayList<Criterion>();
    }

    public List<Criterion> getStationStatusCriteria() {
      return stationStatusCriteria;
    }

    protected void addStationStatusCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      stationStatusCriteria
          .add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
      allCriteria = null;
    }

    protected void addStationStatusCriterion(String condition, OrganizationStatusEnum value1,
        OrganizationStatusEnum value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      stationStatusCriteria
          .add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
      allCriteria = null;
    }

    public List<Criterion> getServiceStaionTypeCriteria() {
      return serviceStaionTypeCriteria;
    }

    protected void addServiceStaionTypeCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      serviceStaionTypeCriteria
          .add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
      allCriteria = null;
    }

    protected void addServiceStaionTypeCriterion(String condition, FWZTypeEnum value1,
        FWZTypeEnum value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      serviceStaionTypeCriteria
          .add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
      allCriteria = null;
    }

    public boolean isValid() {
      return criteria.size() > 0 || stationStatusCriteria.size() > 0
          || serviceStaionTypeCriteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      if (allCriteria == null) {
        allCriteria = new ArrayList<Criterion>();
        allCriteria.addAll(criteria);
        allCriteria.addAll(stationStatusCriteria);
        allCriteria.addAll(serviceStaionTypeCriteria);
      }
      return allCriteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
      allCriteria = null;
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
      allCriteria = null;
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
      allCriteria = null;
    }

    public Criteria andIdIsNull() {
      addCriterion("id is null");
      return (Criteria) this;
    }

    public Criteria andIdIsNotNull() {
      addCriterion("id is not null");
      return (Criteria) this;
    }

    public Criteria andIdEqualTo(Long value) {
      addCriterion("id =", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotEqualTo(Long value) {
      addCriterion("id <>", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThan(Long value) {
      addCriterion("id >", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThanOrEqualTo(Long value) {
      addCriterion("id >=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThan(Long value) {
      addCriterion("id <", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThanOrEqualTo(Long value) {
      addCriterion("id <=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdIn(List<Long> values) {
      addCriterion("id in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotIn(List<Long> values) {
      addCriterion("id not in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdBetween(Long value1, Long value2) {
      addCriterion("id between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotBetween(Long value1, Long value2) {
      addCriterion("id not between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdIsNull() {
      addCriterion("master_station_id is null");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdIsNotNull() {
      addCriterion("master_station_id is not null");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdEqualTo(Long value) {
      addCriterion("master_station_id =", value, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdNotEqualTo(Long value) {
      addCriterion("master_station_id <>", value, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdGreaterThan(Long value) {
      addCriterion("master_station_id >", value, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdGreaterThanOrEqualTo(Long value) {
      addCriterion("master_station_id >=", value, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdLessThan(Long value) {
      addCriterion("master_station_id <", value, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdLessThanOrEqualTo(Long value) {
      addCriterion("master_station_id <=", value, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdIn(List<Long> values) {
      addCriterion("master_station_id in", values, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdNotIn(List<Long> values) {
      addCriterion("master_station_id not in", values, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdBetween(Long value1, Long value2) {
      addCriterion("master_station_id between", value1, value2, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andMasterStationIdNotBetween(Long value1, Long value2) {
      addCriterion("master_station_id not between", value1, value2, "masterStationId");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameIsNull() {
      addCriterion("service_station_name is null");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameIsNotNull() {
      addCriterion("service_station_name is not null");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameEqualTo(String value) {
      addCriterion("service_station_name =", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameNotEqualTo(String value) {
      addCriterion("service_station_name <>", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameGreaterThan(String value) {
      addCriterion("service_station_name >", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameGreaterThanOrEqualTo(String value) {
      addCriterion("service_station_name >=", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameLessThan(String value) {
      addCriterion("service_station_name <", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameLessThanOrEqualTo(String value) {
      addCriterion("service_station_name <=", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameLike(String value) {
      addCriterion("service_station_name like", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameNotLike(String value) {
      addCriterion("service_station_name not like", value, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameIn(List<String> values) {
      addCriterion("service_station_name in", values, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameNotIn(List<String> values) {
      addCriterion("service_station_name not in", values, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameBetween(String value1, String value2) {
      addCriterion("service_station_name between", value1, value2, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationNameNotBetween(String value1, String value2) {
      addCriterion("service_station_name not between", value1, value2, "serviceStationName");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminIsNull() {
      addCriterion("service_station_admin is null");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminIsNotNull() {
      addCriterion("service_station_admin is not null");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminEqualTo(String value) {
      addCriterion("service_station_admin =", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminNotEqualTo(String value) {
      addCriterion("service_station_admin <>", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminGreaterThan(String value) {
      addCriterion("service_station_admin >", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminGreaterThanOrEqualTo(String value) {
      addCriterion("service_station_admin >=", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminLessThan(String value) {
      addCriterion("service_station_admin <", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminLessThanOrEqualTo(String value) {
      addCriterion("service_station_admin <=", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminLike(String value) {
      addCriterion("service_station_admin like", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminNotLike(String value) {
      addCriterion("service_station_admin not like", value, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminIn(List<String> values) {
      addCriterion("service_station_admin in", values, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminNotIn(List<String> values) {
      addCriterion("service_station_admin not in", values, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminBetween(String value1, String value2) {
      addCriterion("service_station_admin between", value1, value2, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andServiceStationAdminNotBetween(String value1, String value2) {
      addCriterion("service_station_admin not between", value1, value2, "serviceStationAdmin");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeIsNull() {
      addCriterion("platform_fee is null");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeIsNotNull() {
      addCriterion("platform_fee is not null");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeEqualTo(BigDecimal value) {
      addCriterion("platform_fee =", value, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeNotEqualTo(BigDecimal value) {
      addCriterion("platform_fee <>", value, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeGreaterThan(BigDecimal value) {
      addCriterion("platform_fee >", value, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("platform_fee >=", value, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeLessThan(BigDecimal value) {
      addCriterion("platform_fee <", value, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeLessThanOrEqualTo(BigDecimal value) {
      addCriterion("platform_fee <=", value, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeIn(List<BigDecimal> values) {
      addCriterion("platform_fee in", values, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeNotIn(List<BigDecimal> values) {
      addCriterion("platform_fee not in", values, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("platform_fee between", value1, value2, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformFeeNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("platform_fee not between", value1, value2, "platformFee");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjIsNull() {
      addCriterion("platform_bzj is null");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjIsNotNull() {
      addCriterion("platform_bzj is not null");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjEqualTo(BigDecimal value) {
      addCriterion("platform_bzj =", value, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjNotEqualTo(BigDecimal value) {
      addCriterion("platform_bzj <>", value, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjGreaterThan(BigDecimal value) {
      addCriterion("platform_bzj >", value, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("platform_bzj >=", value, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjLessThan(BigDecimal value) {
      addCriterion("platform_bzj <", value, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjLessThanOrEqualTo(BigDecimal value) {
      addCriterion("platform_bzj <=", value, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjIn(List<BigDecimal> values) {
      addCriterion("platform_bzj in", values, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjNotIn(List<BigDecimal> values) {
      addCriterion("platform_bzj not in", values, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("platform_bzj between", value1, value2, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPlatformBzjNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("platform_bzj not between", value1, value2, "platformBzj");
      return (Criteria) this;
    }

    public Criteria andPhoneIsNull() {
      addCriterion("phone is null");
      return (Criteria) this;
    }

    public Criteria andPhoneIsNotNull() {
      addCriterion("phone is not null");
      return (Criteria) this;
    }

    public Criteria andPhoneEqualTo(Long value) {
      addCriterion("phone =", value, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneNotEqualTo(Long value) {
      addCriterion("phone <>", value, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneGreaterThan(Long value) {
      addCriterion("phone >", value, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneGreaterThanOrEqualTo(Long value) {
      addCriterion("phone >=", value, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneLessThan(Long value) {
      addCriterion("phone <", value, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneLessThanOrEqualTo(Long value) {
      addCriterion("phone <=", value, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneIn(List<Long> values) {
      addCriterion("phone in", values, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneNotIn(List<Long> values) {
      addCriterion("phone not in", values, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneBetween(Long value1, Long value2) {
      addCriterion("phone between", value1, value2, "phone");
      return (Criteria) this;
    }

    public Criteria andPhoneNotBetween(Long value1, Long value2) {
      addCriterion("phone not between", value1, value2, "phone");
      return (Criteria) this;
    }

    public Criteria andProvinceIsNull() {
      addCriterion("province is null");
      return (Criteria) this;
    }

    public Criteria andProvinceIsNotNull() {
      addCriterion("province is not null");
      return (Criteria) this;
    }

    public Criteria andProvinceEqualTo(Integer value) {
      addCriterion("province =", value, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceNotEqualTo(Integer value) {
      addCriterion("province <>", value, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceGreaterThan(Integer value) {
      addCriterion("province >", value, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceGreaterThanOrEqualTo(Integer value) {
      addCriterion("province >=", value, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceLessThan(Integer value) {
      addCriterion("province <", value, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceLessThanOrEqualTo(Integer value) {
      addCriterion("province <=", value, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceIn(List<Integer> values) {
      addCriterion("province in", values, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceNotIn(List<Integer> values) {
      addCriterion("province not in", values, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceBetween(Integer value1, Integer value2) {
      addCriterion("province between", value1, value2, "province");
      return (Criteria) this;
    }

    public Criteria andProvinceNotBetween(Integer value1, Integer value2) {
      addCriterion("province not between", value1, value2, "province");
      return (Criteria) this;
    }

    public Criteria andCityIsNull() {
      addCriterion("city is null");
      return (Criteria) this;
    }

    public Criteria andCityIsNotNull() {
      addCriterion("city is not null");
      return (Criteria) this;
    }

    public Criteria andCityEqualTo(Integer value) {
      addCriterion("city =", value, "city");
      return (Criteria) this;
    }

    public Criteria andCityNotEqualTo(Integer value) {
      addCriterion("city <>", value, "city");
      return (Criteria) this;
    }

    public Criteria andCityGreaterThan(Integer value) {
      addCriterion("city >", value, "city");
      return (Criteria) this;
    }

    public Criteria andCityGreaterThanOrEqualTo(Integer value) {
      addCriterion("city >=", value, "city");
      return (Criteria) this;
    }

    public Criteria andCityLessThan(Integer value) {
      addCriterion("city <", value, "city");
      return (Criteria) this;
    }

    public Criteria andCityLessThanOrEqualTo(Integer value) {
      addCriterion("city <=", value, "city");
      return (Criteria) this;
    }

    public Criteria andCityIn(List<Integer> values) {
      addCriterion("city in", values, "city");
      return (Criteria) this;
    }

    public Criteria andCityNotIn(List<Integer> values) {
      addCriterion("city not in", values, "city");
      return (Criteria) this;
    }

    public Criteria andCityBetween(Integer value1, Integer value2) {
      addCriterion("city between", value1, value2, "city");
      return (Criteria) this;
    }

    public Criteria andCityNotBetween(Integer value1, Integer value2) {
      addCriterion("city not between", value1, value2, "city");
      return (Criteria) this;
    }

    public Criteria andCountyIsNull() {
      addCriterion("county is null");
      return (Criteria) this;
    }

    public Criteria andCountyIsNotNull() {
      addCriterion("county is not null");
      return (Criteria) this;
    }

    public Criteria andCountyEqualTo(Integer value) {
      addCriterion("county =", value, "county");
      return (Criteria) this;
    }

    public Criteria andCountyNotEqualTo(Integer value) {
      addCriterion("county <>", value, "county");
      return (Criteria) this;
    }

    public Criteria andCountyGreaterThan(Integer value) {
      addCriterion("county >", value, "county");
      return (Criteria) this;
    }

    public Criteria andCountyGreaterThanOrEqualTo(Integer value) {
      addCriterion("county >=", value, "county");
      return (Criteria) this;
    }

    public Criteria andCountyLessThan(Integer value) {
      addCriterion("county <", value, "county");
      return (Criteria) this;
    }

    public Criteria andCountyLessThanOrEqualTo(Integer value) {
      addCriterion("county <=", value, "county");
      return (Criteria) this;
    }

    public Criteria andCountyIn(List<Integer> values) {
      addCriterion("county in", values, "county");
      return (Criteria) this;
    }

    public Criteria andCountyNotIn(List<Integer> values) {
      addCriterion("county not in", values, "county");
      return (Criteria) this;
    }

    public Criteria andCountyBetween(Integer value1, Integer value2) {
      addCriterion("county between", value1, value2, "county");
      return (Criteria) this;
    }

    public Criteria andCountyNotBetween(Integer value1, Integer value2) {
      addCriterion("county not between", value1, value2, "county");
      return (Criteria) this;
    }

    public Criteria andTownIsNull() {
      addCriterion("town is null");
      return (Criteria) this;
    }

    public Criteria andTownIsNotNull() {
      addCriterion("town is not null");
      return (Criteria) this;
    }

    public Criteria andTownEqualTo(Integer value) {
      addCriterion("town =", value, "town");
      return (Criteria) this;
    }

    public Criteria andTownNotEqualTo(Integer value) {
      addCriterion("town <>", value, "town");
      return (Criteria) this;
    }

    public Criteria andTownGreaterThan(Integer value) {
      addCriterion("town >", value, "town");
      return (Criteria) this;
    }

    public Criteria andTownGreaterThanOrEqualTo(Integer value) {
      addCriterion("town >=", value, "town");
      return (Criteria) this;
    }

    public Criteria andTownLessThan(Integer value) {
      addCriterion("town <", value, "town");
      return (Criteria) this;
    }

    public Criteria andTownLessThanOrEqualTo(Integer value) {
      addCriterion("town <=", value, "town");
      return (Criteria) this;
    }

    public Criteria andTownIn(List<Integer> values) {
      addCriterion("town in", values, "town");
      return (Criteria) this;
    }

    public Criteria andTownNotIn(List<Integer> values) {
      addCriterion("town not in", values, "town");
      return (Criteria) this;
    }

    public Criteria andTownBetween(Integer value1, Integer value2) {
      addCriterion("town between", value1, value2, "town");
      return (Criteria) this;
    }

    public Criteria andTownNotBetween(Integer value1, Integer value2) {
      addCriterion("town not between", value1, value2, "town");
      return (Criteria) this;
    }

    public Criteria andAdressIsNull() {
      addCriterion("adress is null");
      return (Criteria) this;
    }

    public Criteria andAdressIsNotNull() {
      addCriterion("adress is not null");
      return (Criteria) this;
    }

    public Criteria andAdressEqualTo(String value) {
      addCriterion("adress =", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressNotEqualTo(String value) {
      addCriterion("adress <>", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressGreaterThan(String value) {
      addCriterion("adress >", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressGreaterThanOrEqualTo(String value) {
      addCriterion("adress >=", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressLessThan(String value) {
      addCriterion("adress <", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressLessThanOrEqualTo(String value) {
      addCriterion("adress <=", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressLike(String value) {
      addCriterion("adress like", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressNotLike(String value) {
      addCriterion("adress not like", value, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressIn(List<String> values) {
      addCriterion("adress in", values, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressNotIn(List<String> values) {
      addCriterion("adress not in", values, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressBetween(String value1, String value2) {
      addCriterion("adress between", value1, value2, "adress");
      return (Criteria) this;
    }

    public Criteria andAdressNotBetween(String value1, String value2) {
      addCriterion("adress not between", value1, value2, "adress");
      return (Criteria) this;
    }

    public Criteria andLicenseNumIsNull() {
      addCriterion("license_num is null");
      return (Criteria) this;
    }

    public Criteria andLicenseNumIsNotNull() {
      addCriterion("license_num is not null");
      return (Criteria) this;
    }

    public Criteria andLicenseNumEqualTo(String value) {
      addCriterion("license_num =", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumNotEqualTo(String value) {
      addCriterion("license_num <>", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumGreaterThan(String value) {
      addCriterion("license_num >", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumGreaterThanOrEqualTo(String value) {
      addCriterion("license_num >=", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumLessThan(String value) {
      addCriterion("license_num <", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumLessThanOrEqualTo(String value) {
      addCriterion("license_num <=", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumLike(String value) {
      addCriterion("license_num like", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumNotLike(String value) {
      addCriterion("license_num not like", value, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumIn(List<String> values) {
      addCriterion("license_num in", values, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumNotIn(List<String> values) {
      addCriterion("license_num not in", values, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumBetween(String value1, String value2) {
      addCriterion("license_num between", value1, value2, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andLicenseNumNotBetween(String value1, String value2) {
      addCriterion("license_num not between", value1, value2, "licenseNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumIsNull() {
      addCriterion("identity_card_num is null");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumIsNotNull() {
      addCriterion("identity_card_num is not null");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumEqualTo(String value) {
      addCriterion("identity_card_num =", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumNotEqualTo(String value) {
      addCriterion("identity_card_num <>", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumGreaterThan(String value) {
      addCriterion("identity_card_num >", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumGreaterThanOrEqualTo(String value) {
      addCriterion("identity_card_num >=", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumLessThan(String value) {
      addCriterion("identity_card_num <", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumLessThanOrEqualTo(String value) {
      addCriterion("identity_card_num <=", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumLike(String value) {
      addCriterion("identity_card_num like", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumNotLike(String value) {
      addCriterion("identity_card_num not like", value, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumIn(List<String> values) {
      addCriterion("identity_card_num in", values, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumNotIn(List<String> values) {
      addCriterion("identity_card_num not in", values, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumBetween(String value1, String value2) {
      addCriterion("identity_card_num between", value1, value2, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andIdentityCardNumNotBetween(String value1, String value2) {
      addCriterion("identity_card_num not between", value1, value2, "identityCardNum");
      return (Criteria) this;
    }

    public Criteria andLongitudeIsNull() {
      addCriterion("longitude is null");
      return (Criteria) this;
    }

    public Criteria andLongitudeIsNotNull() {
      addCriterion("longitude is not null");
      return (Criteria) this;
    }

    public Criteria andLongitudeEqualTo(String value) {
      addCriterion("longitude =", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeNotEqualTo(String value) {
      addCriterion("longitude <>", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeGreaterThan(String value) {
      addCriterion("longitude >", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeGreaterThanOrEqualTo(String value) {
      addCriterion("longitude >=", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeLessThan(String value) {
      addCriterion("longitude <", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeLessThanOrEqualTo(String value) {
      addCriterion("longitude <=", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeLike(String value) {
      addCriterion("longitude like", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeNotLike(String value) {
      addCriterion("longitude not like", value, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeIn(List<String> values) {
      addCriterion("longitude in", values, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeNotIn(List<String> values) {
      addCriterion("longitude not in", values, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeBetween(String value1, String value2) {
      addCriterion("longitude between", value1, value2, "longitude");
      return (Criteria) this;
    }

    public Criteria andLongitudeNotBetween(String value1, String value2) {
      addCriterion("longitude not between", value1, value2, "longitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeIsNull() {
      addCriterion("lantitude is null");
      return (Criteria) this;
    }

    public Criteria andLantitudeIsNotNull() {
      addCriterion("lantitude is not null");
      return (Criteria) this;
    }

    public Criteria andLantitudeEqualTo(String value) {
      addCriterion("lantitude =", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeNotEqualTo(String value) {
      addCriterion("lantitude <>", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeGreaterThan(String value) {
      addCriterion("lantitude >", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeGreaterThanOrEqualTo(String value) {
      addCriterion("lantitude >=", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeLessThan(String value) {
      addCriterion("lantitude <", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeLessThanOrEqualTo(String value) {
      addCriterion("lantitude <=", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeLike(String value) {
      addCriterion("lantitude like", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeNotLike(String value) {
      addCriterion("lantitude not like", value, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeIn(List<String> values) {
      addCriterion("lantitude in", values, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeNotIn(List<String> values) {
      addCriterion("lantitude not in", values, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeBetween(String value1, String value2) {
      addCriterion("lantitude between", value1, value2, "lantitude");
      return (Criteria) this;
    }

    public Criteria andLantitudeNotBetween(String value1, String value2) {
      addCriterion("lantitude not between", value1, value2, "lantitude");
      return (Criteria) this;
    }

    public Criteria andStationStatusIsNull() {
      addCriterion("station_status is null");
      return (Criteria) this;
    }

    public Criteria andStationStatusIsNotNull() {
      addCriterion("station_status is not null");
      return (Criteria) this;
    }

    public Criteria andStationStatusEqualTo(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status =", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusNotEqualTo(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status <>", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusGreaterThan(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status >", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusGreaterThanOrEqualTo(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status >=", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusLessThan(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status <", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusLessThanOrEqualTo(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status <=", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusLike(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status like", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusNotLike(OrganizationStatusEnum value) {
      addStationStatusCriterion("station_status not like", value, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusIn(List<OrganizationStatusEnum> values) {
      addStationStatusCriterion("station_status in", values, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusNotIn(List<OrganizationStatusEnum> values) {
      addStationStatusCriterion("station_status not in", values, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusBetween(OrganizationStatusEnum value1,
        OrganizationStatusEnum value2) {
      addStationStatusCriterion("station_status between", value1, value2, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andStationStatusNotBetween(OrganizationStatusEnum value1,
        OrganizationStatusEnum value2) {
      addStationStatusCriterion("station_status not between", value1, value2, "stationStatus");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIsNull() {
      addCriterion("create_user_id is null");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIsNotNull() {
      addCriterion("create_user_id is not null");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdEqualTo(Long value) {
      addCriterion("create_user_id =", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotEqualTo(Long value) {
      addCriterion("create_user_id <>", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdGreaterThan(Long value) {
      addCriterion("create_user_id >", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("create_user_id >=", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLessThan(Long value) {
      addCriterion("create_user_id <", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLessThanOrEqualTo(Long value) {
      addCriterion("create_user_id <=", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIn(List<Long> values) {
      addCriterion("create_user_id in", values, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotIn(List<Long> values) {
      addCriterion("create_user_id not in", values, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdBetween(Long value1, Long value2) {
      addCriterion("create_user_id between", value1, value2, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotBetween(Long value1, Long value2) {
      addCriterion("create_user_id not between", value1, value2, "createUserId");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeIsNull() {
      addCriterion("service_staion_type is null");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeIsNotNull() {
      addCriterion("service_staion_type is not null");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeEqualTo(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type =", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeNotEqualTo(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type <>", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeGreaterThan(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type >", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeGreaterThanOrEqualTo(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type >=", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeLessThan(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type <", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeLessThanOrEqualTo(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type <=", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeLike(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type like", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeNotLike(FWZTypeEnum value) {
      addServiceStaionTypeCriterion("service_staion_type not like", value, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeIn(List<FWZTypeEnum> values) {
      addServiceStaionTypeCriterion("service_staion_type in", values, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeNotIn(List<FWZTypeEnum> values) {
      addServiceStaionTypeCriterion("service_staion_type not in", values, "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeBetween(FWZTypeEnum value1, FWZTypeEnum value2) {
      addServiceStaionTypeCriterion("service_staion_type between", value1, value2,
          "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andServiceStaionTypeNotBetween(FWZTypeEnum value1, FWZTypeEnum value2) {
      addServiceStaionTypeCriterion("service_staion_type not between", value1, value2,
          "serviceStaionType");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNull() {
      addCriterion("create_time is null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNotNull() {
      addCriterion("create_time is not null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
      addCriterion("create_time =", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
      addCriterion("create_time <>", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
      addCriterion("create_time >", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("create_time >=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
      addCriterion("create_time <", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
      addCriterion("create_time <=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIn(List<Date> values) {
      addCriterion("create_time in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotIn(List<Date> values) {
      addCriterion("create_time not in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
      addCriterion("create_time between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
      addCriterion("create_time not between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNull() {
      addCriterion("update_time is null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNotNull() {
      addCriterion("update_time is not null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeEqualTo(Date value) {
      addCriterion("update_time =", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotEqualTo(Date value) {
      addCriterion("update_time <>", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThan(Date value) {
      addCriterion("update_time >", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("update_time >=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThan(Date value) {
      addCriterion("update_time <", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
      addCriterion("update_time <=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIn(List<Date> values) {
      addCriterion("update_time in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotIn(List<Date> values) {
      addCriterion("update_time not in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeBetween(Date value1, Date value2) {
      addCriterion("update_time between", value1, value2, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
      addCriterion("update_time not between", value1, value2, "updateTime");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdIsNull() {
      addCriterion("fe_service_station_id is null");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdIsNotNull() {
      addCriterion("fe_service_station_id is not null");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdEqualTo(String value) {
      addCriterion("fe_service_station_id =", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdNotEqualTo(String value) {
      addCriterion("fe_service_station_id <>", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdGreaterThan(String value) {
      addCriterion("fe_service_station_id >", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdGreaterThanOrEqualTo(String value) {
      addCriterion("fe_service_station_id >=", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdLessThan(String value) {
      addCriterion("fe_service_station_id <", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdLessThanOrEqualTo(String value) {
      addCriterion("fe_service_station_id <=", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdLike(String value) {
      addCriterion("fe_service_station_id like", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdNotLike(String value) {
      addCriterion("fe_service_station_id not like", value, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdIn(List<String> values) {
      addCriterion("fe_service_station_id in", values, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdNotIn(List<String> values) {
      addCriterion("fe_service_station_id not in", values, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdBetween(String value1, String value2) {
      addCriterion("fe_service_station_id between", value1, value2, "feServiceStationId");
      return (Criteria) this;
    }

    public Criteria andFeServiceStationIdNotBetween(String value1, String value2) {
      addCriterion("fe_service_station_id not between", value1, value2, "feServiceStationId");
      return (Criteria) this;
    }
  }

  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  public static class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      } else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}
