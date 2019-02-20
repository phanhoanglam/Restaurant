/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class UsersModel {

    private int user_id;
    private String user_name;
    private String user_pass;
    private String fullname;
    private String role;
    private String address;
    private int age;
    private String gender;
    private Date datetime;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public UsersModel() {
    }

    public UsersModel(String user_name) {
        this.user_name = user_name;
    }

    public UsersModel(int user_id, String user_name, String user_pass, String fullname, String role, String address, int age, String gender, Date datetime) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.fullname = fullname;
        this.role = role;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.datetime = datetime;
    }

}
