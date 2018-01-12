package com.kuruvatech.santoshkenchaamba.model;

/**
 * Created by dayas on 07-12-2017.
 */

public class Letter {
    public Letter() {
         name = new String("");
         emailid = new String("");
         phone = new String("");
         letter = new String("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
    String name;
    String emailid;
    String phone;
    String letter;
}
