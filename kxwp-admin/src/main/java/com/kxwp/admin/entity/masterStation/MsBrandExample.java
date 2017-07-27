package com.kxwp.admin.entity.masterStation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kxwp.admin.constants.BrandStatusEnum;

public class MsBrandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsBrandExample() {
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
        protected List<Criterion> brandStatusCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            brandStatusCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getBrandStatusCriteria() {
            return brandStatusCriteria;
        }

        protected void addBrandStatusCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            brandStatusCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        protected void addBrandStatusCriterion(String condition, BrandStatusEnum value1, BrandStatusEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            brandStatusCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || brandStatusCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(brandStatusCriteria);
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

        public Criteria andBrandNameIsNull() {
            addCriterion("brand_name is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("brand_name is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("brand_name =", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("brand_name <>", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("brand_name >", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("brand_name >=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("brand_name <", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("brand_name <=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("brand_name like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("brand_name not like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIn(List<String> values) {
            addCriterion("brand_name in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotIn(List<String> values) {
            addCriterion("brand_name not in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("brand_name between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("brand_name not between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrIsNull() {
            addCriterion("brand_name_abbr is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrIsNotNull() {
            addCriterion("brand_name_abbr is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrEqualTo(String value) {
            addCriterion("brand_name_abbr =", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrNotEqualTo(String value) {
            addCriterion("brand_name_abbr <>", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrGreaterThan(String value) {
            addCriterion("brand_name_abbr >", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrGreaterThanOrEqualTo(String value) {
            addCriterion("brand_name_abbr >=", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrLessThan(String value) {
            addCriterion("brand_name_abbr <", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrLessThanOrEqualTo(String value) {
            addCriterion("brand_name_abbr <=", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrLike(String value) {
            addCriterion("brand_name_abbr like", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrNotLike(String value) {
            addCriterion("brand_name_abbr not like", value, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrIn(List<String> values) {
            addCriterion("brand_name_abbr in", values, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrNotIn(List<String> values) {
            addCriterion("brand_name_abbr not in", values, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrBetween(String value1, String value2) {
            addCriterion("brand_name_abbr between", value1, value2, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandNameAbbrNotBetween(String value1, String value2) {
            addCriterion("brand_name_abbr not between", value1, value2, "brandNameAbbr");
            return (Criteria) this;
        }

        public Criteria andBrandStatusIsNull() {
            addCriterion("brand_status is null");
            return (Criteria) this;
        }

        public Criteria andBrandStatusIsNotNull() {
            addCriterion("brand_status is not null");
            return (Criteria) this;
        }

        public Criteria andBrandStatusEqualTo(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status =", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusNotEqualTo(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status <>", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusGreaterThan(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status >", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusGreaterThanOrEqualTo(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status >=", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusLessThan(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status <", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusLessThanOrEqualTo(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status <=", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusLike(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status like", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusNotLike(BrandStatusEnum value) {
            addBrandStatusCriterion("brand_status not like", value, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusIn(List<BrandStatusEnum> values) {
            addBrandStatusCriterion("brand_status in", values, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusNotIn(List<BrandStatusEnum> values) {
            addBrandStatusCriterion("brand_status not in", values, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusBetween(BrandStatusEnum value1, BrandStatusEnum value2) {
            addBrandStatusCriterion("brand_status between", value1, value2, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andBrandStatusNotBetween(BrandStatusEnum value1, BrandStatusEnum value2) {
            addBrandStatusCriterion("brand_status not between", value1, value2, "brandStatus");
            return (Criteria) this;
        }

        public Criteria andFeBidIsNull() {
            addCriterion("fe_bid is null");
            return (Criteria) this;
        }

        public Criteria andFeBidIsNotNull() {
            addCriterion("fe_bid is not null");
            return (Criteria) this;
        }

        public Criteria andFeBidEqualTo(Long value) {
            addCriterion("fe_bid =", value, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidNotEqualTo(Long value) {
            addCriterion("fe_bid <>", value, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidGreaterThan(Long value) {
            addCriterion("fe_bid >", value, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidGreaterThanOrEqualTo(Long value) {
            addCriterion("fe_bid >=", value, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidLessThan(Long value) {
            addCriterion("fe_bid <", value, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidLessThanOrEqualTo(Long value) {
            addCriterion("fe_bid <=", value, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidIn(List<Long> values) {
            addCriterion("fe_bid in", values, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidNotIn(List<Long> values) {
            addCriterion("fe_bid not in", values, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidBetween(Long value1, Long value2) {
            addCriterion("fe_bid between", value1, value2, "feBid");
            return (Criteria) this;
        }

        public Criteria andFeBidNotBetween(Long value1, Long value2) {
            addCriterion("fe_bid not between", value1, value2, "feBid");
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

        public Criteria andBrandNoIsNull() {
            addCriterion("brand_no is null");
            return (Criteria) this;
        }

        public Criteria andBrandNoIsNotNull() {
            addCriterion("brand_no is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNoEqualTo(String value) {
            addCriterion("brand_no =", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotEqualTo(String value) {
            addCriterion("brand_no <>", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoGreaterThan(String value) {
            addCriterion("brand_no >", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoGreaterThanOrEqualTo(String value) {
            addCriterion("brand_no >=", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoLessThan(String value) {
            addCriterion("brand_no <", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoLessThanOrEqualTo(String value) {
            addCriterion("brand_no <=", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoLike(String value) {
            addCriterion("brand_no like", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotLike(String value) {
            addCriterion("brand_no not like", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoIn(List<String> values) {
            addCriterion("brand_no in", values, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotIn(List<String> values) {
            addCriterion("brand_no not in", values, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoBetween(String value1, String value2) {
            addCriterion("brand_no between", value1, value2, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotBetween(String value1, String value2) {
            addCriterion("brand_no not between", value1, value2, "brandNo");
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