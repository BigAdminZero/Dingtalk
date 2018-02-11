package com.dingtalk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScheduleTableExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScheduleTableExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andScheduleTableIdIsNull() {
            addCriterion("schedule_table_id is null");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdIsNotNull() {
            addCriterion("schedule_table_id is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdEqualTo(Integer value) {
            addCriterion("schedule_table_id =", value, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdNotEqualTo(Integer value) {
            addCriterion("schedule_table_id <>", value, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdGreaterThan(Integer value) {
            addCriterion("schedule_table_id >", value, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("schedule_table_id >=", value, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdLessThan(Integer value) {
            addCriterion("schedule_table_id <", value, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdLessThanOrEqualTo(Integer value) {
            addCriterion("schedule_table_id <=", value, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdIn(List<Integer> values) {
            addCriterion("schedule_table_id in", values, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdNotIn(List<Integer> values) {
            addCriterion("schedule_table_id not in", values, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdBetween(Integer value1, Integer value2) {
            addCriterion("schedule_table_id between", value1, value2, "scheduleTableId");
            return (Criteria) this;
        }

        public Criteria andScheduleTableIdNotBetween(Integer value1, Integer value2) {
            addCriterion("schedule_table_id not between", value1, value2, "scheduleTableId");
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

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterionForJDBCDate("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterionForJDBCDate("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterionForJDBCDate("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterionForJDBCDate("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date not between", value1, value2, "date");
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

        public Criteria andIfOndutyIsNull() {
            addCriterion("if_onduty is null");
            return (Criteria) this;
        }

        public Criteria andIfOndutyIsNotNull() {
            addCriterion("if_onduty is not null");
            return (Criteria) this;
        }

        public Criteria andIfOndutyEqualTo(String value) {
            addCriterion("if_onduty =", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyNotEqualTo(String value) {
            addCriterion("if_onduty <>", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyGreaterThan(String value) {
            addCriterion("if_onduty >", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyGreaterThanOrEqualTo(String value) {
            addCriterion("if_onduty >=", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyLessThan(String value) {
            addCriterion("if_onduty <", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyLessThanOrEqualTo(String value) {
            addCriterion("if_onduty <=", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyLike(String value) {
            addCriterion("if_onduty like", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyNotLike(String value) {
            addCriterion("if_onduty not like", value, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyIn(List<String> values) {
            addCriterion("if_onduty in", values, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyNotIn(List<String> values) {
            addCriterion("if_onduty not in", values, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyBetween(String value1, String value2) {
            addCriterion("if_onduty between", value1, value2, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andIfOndutyNotBetween(String value1, String value2) {
            addCriterion("if_onduty not between", value1, value2, "ifOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyIsNull() {
            addCriterion("type_onduty is null");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyIsNotNull() {
            addCriterion("type_onduty is not null");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyEqualTo(String value) {
            addCriterion("type_onduty =", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyNotEqualTo(String value) {
            addCriterion("type_onduty <>", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyGreaterThan(String value) {
            addCriterion("type_onduty >", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyGreaterThanOrEqualTo(String value) {
            addCriterion("type_onduty >=", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyLessThan(String value) {
            addCriterion("type_onduty <", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyLessThanOrEqualTo(String value) {
            addCriterion("type_onduty <=", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyLike(String value) {
            addCriterion("type_onduty like", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyNotLike(String value) {
            addCriterion("type_onduty not like", value, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyIn(List<String> values) {
            addCriterion("type_onduty in", values, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyNotIn(List<String> values) {
            addCriterion("type_onduty not in", values, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyBetween(String value1, String value2) {
            addCriterion("type_onduty between", value1, value2, "typeOnduty");
            return (Criteria) this;
        }

        public Criteria andTypeOndutyNotBetween(String value1, String value2) {
            addCriterion("type_onduty not between", value1, value2, "typeOnduty");
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