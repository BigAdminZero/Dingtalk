package com.dingtalk.bean;

public class Authority {
    private Integer authorityId;

    private String authorityName;

    private Integer authorityLevel;

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName == null ? null : authorityName.trim();
    }

    public Integer getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(Integer authorityLevel) {
        this.authorityLevel = authorityLevel;
    }
}