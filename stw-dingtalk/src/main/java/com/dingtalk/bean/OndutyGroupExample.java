package com.dingtalk.bean;

import java.util.ArrayList;
import java.util.List;

public class OndutyGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OndutyGroupExample() {
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
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOndutyGroupIdIsNull() {
            addCriterion("onduty_group_id is null");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdIsNotNull() {
            addCriterion("onduty_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdEqualTo(Integer value) {
            addCriterion("onduty_group_id =", value, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdNotEqualTo(Integer value) {
            addCriterion("onduty_group_id <>", value, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdGreaterThan(Integer value) {
            addCriterion("onduty_group_id >", value, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("onduty_group_id >=", value, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdLessThan(Integer value) {
            addCriterion("onduty_group_id <", value, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("onduty_group_id <=", value, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdIn(List<Integer> values) {
            addCriterion("onduty_group_id in", values, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdNotIn(List<Integer> values) {
            addCriterion("onduty_group_id not in", values, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("onduty_group_id between", value1, value2, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("onduty_group_id not between", value1, value2, "ondutyGroupId");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameIsNull() {
            addCriterion("\"onduty_group _name\" is null");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameIsNotNull() {
            addCriterion("\"onduty_group _name\" is not null");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameEqualTo(String value) {
            addCriterion("\"onduty_group _name\" =", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameNotEqualTo(String value) {
            addCriterion("\"onduty_group _name\" <>", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameGreaterThan(String value) {
            addCriterion("\"onduty_group _name\" >", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("\"onduty_group _name\" >=", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameLessThan(String value) {
            addCriterion("\"onduty_group _name\" <", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameLessThanOrEqualTo(String value) {
            addCriterion("\"onduty_group _name\" <=", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameLike(String value) {
            addCriterion("\"onduty_group _name\" like", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameNotLike(String value) {
            addCriterion("\"onduty_group _name\" not like", value, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameIn(List<String> values) {
            addCriterion("\"onduty_group _name\" in", values, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameNotIn(List<String> values) {
            addCriterion("\"onduty_group _name\" not in", values, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameBetween(String value1, String value2) {
            addCriterion("\"onduty_group _name\" between", value1, value2, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andOndutyGroupNameNotBetween(String value1, String value2) {
            addCriterion("\"onduty_group _name\" not between", value1, value2, "ondutyGroupName");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNull() {
            addCriterion("\"employee _id\" is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNotNull() {
            addCriterion("\"employee _id\" is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdEqualTo(Long value) {
            addCriterion("\"employee _id\" =", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotEqualTo(Long value) {
            addCriterion("\"employee _id\" <>", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThan(Long value) {
            addCriterion("\"employee _id\" >", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("\"employee _id\" >=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThan(Long value) {
            addCriterion("\"employee _id\" <", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThanOrEqualTo(Long value) {
            addCriterion("\"employee _id\" <=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIn(List<Long> values) {
            addCriterion("\"employee _id\" in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotIn(List<Long> values) {
            addCriterion("\"employee _id\" not in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdBetween(Long value1, Long value2) {
            addCriterion("\"employee _id\" between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotBetween(Long value1, Long value2) {
            addCriterion("\"employee _id\" not between", value1, value2, "employeeId");
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