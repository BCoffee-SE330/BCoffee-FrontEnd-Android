package com.example.coffeeshopmanagementandroid.domain.model;

import java.util.Date;

public class UserModel {
    private String userId;
    private String userEmail;
    private String userName;
    private String userLastName;
    private String userGender;
    private String userPhone;
    private String userAvatar;
    private Date userBirthday;

    public UserModel() {}

    public UserModel(String userId, String userName, String userLastName, String userGender, String userEmail, String userPhone, String userAvatar, Date userBirthday) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userAvatar = userAvatar;
        this.userBirthday = userBirthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getFullName() {
        return userName + " " + userLastName;
    }
}
