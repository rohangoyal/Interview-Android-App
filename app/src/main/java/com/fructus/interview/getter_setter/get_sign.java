package com.fructus.interview.getter_setter;

public class get_sign
{
    String name;

    public get_sign(int id, String name, String username, String pass, String mobile, String collage, String reg_date, String roll) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.pass = pass;
        this.mobile = mobile;
        this.collage = collage;
        this.reg_date = reg_date;
        this.roll = roll;
    }

    String username;
    String pass;
    String mobile;
    String collage;
    String reg_date;
    String roll;
    int id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
