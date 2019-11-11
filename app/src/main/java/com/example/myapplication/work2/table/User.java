package com.example.myapplication.work2.table;

public class User {
    private Integer userId;

    private String userPhone;

    private String userPassword;

    private String userPhoneandpasswordandid;

    private String userName;

    private String userGender;

    private String userPhonenumber;

    private String userEmail;

    private String userType;

    private String userRandnumber;

    private byte[] userAvatarurl;

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserPhoneandpasswordandid() {
        return userPhoneandpasswordandid;
    }

    public void setUserPhoneandpasswordandid(String userPhoneandpasswordandid) {
        this.userPhoneandpasswordandid = userPhoneandpasswordandid == null ? null : userPhoneandpasswordandid.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender == null ? null : userGender.trim();
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserPhonenumber(String userPhonenumber) {
        this.userPhonenumber = userPhonenumber == null ? null : userPhonenumber.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getUserRandnumber() {
        return userRandnumber;
    }

    public void setUserRandnumber(String userRandnumber) {
        this.userRandnumber = userRandnumber == null ? null : userRandnumber.trim();
    }

    public byte[] getUserAvatarurl() {
        return userAvatarurl;
    }

    public void setUserAvatarurl(byte[] userAvatarurl) {
        this.userAvatarurl = userAvatarurl;
    }
}