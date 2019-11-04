package com.example.myapplication.work2.table;

import java.util.Date;

public class Entryexit {
    private Integer entryexitId;

    private Integer userId;

    private String userPhone;

    private String userName;

    private String userType;

    private String entryexitSign;

    // 因为要对数据库中的DateTime进行转换所以被迫将Date类型entryexitTime变成long类型entryexitTime
    private long entryexitTime;

    private String entryexitDirection;

    public Integer getEntryexitId() {
        return entryexitId;
    }

    public void setEntryexitId(Integer entryexitId) {
        this.entryexitId = entryexitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getEntryexitSign() {
        return entryexitSign;
    }

    public void setEntryexitSign(String entryexitSign) {
        this.entryexitSign = entryexitSign == null ? null : entryexitSign.trim();
    }

    public long getEntryexitTime() {
        return entryexitTime;
    }

    public void setEntryexitTime(long entryexitTime) {
        this.entryexitTime = entryexitTime;
    }

    public String getEntryexitDirection() {
        return entryexitDirection;
    }

    public void setEntryexitDirection(String entryexitDirection) {
        this.entryexitDirection = entryexitDirection == null ? null : entryexitDirection.trim();
    }
}