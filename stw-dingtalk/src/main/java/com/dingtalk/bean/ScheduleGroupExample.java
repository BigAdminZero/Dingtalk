package com.dingtalk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScheduleGroupExample() {
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

        public Criteria andScheduleGroupIdIsNull() {
            addCriterion("schedule_group_id is null");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdIsNotNull() {
            addCriterion("schedule_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdEqualTo(Integer value) {
            addCriterion("schedule_group_id =", value, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdNotEqualTo(Integer value) {
            addCriterion("schedule_group_id <>", value, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdGreaterThan(Integer value) {
            addCriterion("schedule_group_id >", value, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("schedule_group_id >=", value, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdLessThan(Integer value) {
            addCriterion("schedule_group_id <", value, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("schedule_group_id <=", value, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdIn(List<Integer> values) {
            addCriterion("schedule_group_id in", values, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdNotIn(List<Integer> values) {
            addCriterion("schedule_group_id not in", values, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("schedule_group_id between", value1, value2, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("schedule_group_id not between", value1, value2, "scheduleGroupId");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameIsNull() {
            addCriterion("\"schedule_group _name\" is null");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameIsNotNull() {
            addCriterion("\"schedule_group _name\" is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameEqualTo(String value) {
            addCriterion("\"schedule_group _name\" =", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameNotEqualTo(String value) {
            addCriterion("\"schedule_group _name\" <>", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameGreaterThan(String value) {
            addCriterion("\"schedule_group _name\" >", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("\"schedule_group _name\" >=", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameLessThan(String value) {
            addCriterion("\"schedule_group _name\" <", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameLessThanOrEqualTo(String value) {
            addCriterion("\"schedule_group _name\" <=", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameLike(String value) {
            addCriterion("\"schedule_group _name\" like", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameNotLike(String value) {
            addCriterion("\"schedule_group _name\" not like", value, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameIn(List<String> values) {
            addCriterion("\"schedule_group _name\" in", values, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameNotIn(List<String> values) {
            addCriterion("\"schedule_group _name\" not in", values, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameBetween(String value1, String value2) {
            addCriterion("\"schedule_group _name\" between", value1, value2, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andScheduleGroupNameNotBetween(String value1, String value2) {
            addCriterion("\"schedule_group _name\" not between", value1, value2, "scheduleGroupName");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andWeekIsNull() {
            addCriterion("week is null");
            return (Criteria) this;
        }

        public Criteria andWeekIsNotNull() {
            addCriterion("week is not null");
            return (Criteria) this;
        }

        public Criteria andWeekEqualTo(String value) {
            addCriterion("week =", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotEqualTo(String value) {
            addCriterion("week <>", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThan(String value) {
            addCriterion("week >", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThanOrEqualTo(String value) {
            addCriterion("week >=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThan(String value) {
            addCriterion("week <", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThanOrEqualTo(String value) {
            addCriterion("week <=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLike(String value) {
            addCriterion("week like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotLike(String value) {
            addCriterion("week not like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekIn(List<String> values) {
            addCriterion("week in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotIn(List<String> values) {
            addCriterion("week not in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekBetween(String value1, String value2) {
            addCriterion("week between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotBetween(String value1, String value2) {
            addCriterion("week not between", value1, value2, "week");
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