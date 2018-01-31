package com.klgz.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.Permissions;

public class UserInfo implements Serializable {
    private String id;
    private String phone;
    private String password;
    private String name;
    private String sex;
    private String birthday;
    private String country;
    private String city;
    @SerializedName("graduatetime")
    private String graduateTime;
    @SerializedName("graduatemonth")
    private String graduateMonth;
    @SerializedName("graduateschool")
    private String graduateSchool;
    private String time;
    @SerializedName("is_vip")
    private String isVip;
    private String checkID;
    private String token;
    @SerializedName("is_child")
    private String isChild;
    private String realname;
    private Permissions role;

    public Permissions getRole() {
        return role;
    }

    public void setRole(Permissions role) {
        this.role = role;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getGraduateMonth() {
        return graduateMonth;
    }

    public void setGraduateMonth(String graduateMonth) {
        this.graduateMonth = graduateMonth;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsChild() {
        return isChild;
    }

    public void setIsChild(String isChild) {
        this.isChild = isChild;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", graduateTime='" + graduateTime + '\'' +
                ", graduateMonth='" + graduateMonth + '\'' +
                ", graduateSchool='" + graduateSchool + '\'' +
                ", time='" + time + '\'' +
                ", isVip='" + isVip + '\'' +
                ", checkID='" + checkID + '\'' +
                ", token='" + token + '\'' +
                ", isChild='" + isChild + '\'' +
                '}';
    }
}
