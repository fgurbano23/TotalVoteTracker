package com.development.model.entity;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name="USR_USER")
public class User {

    @Id
    @Column(name="USR_ID")
    Double usr_ID;

    @Column(name="USR_TYPE_ID")
    String usr_TYPE_ID;

    @Column(name="USR_FIRST_NAME")
    String usr_FIRST_NAME;

    @Column(name="USR_LAST_NAME")
    String usr_LAST_NAME;

    @Column(name="USR_SEX")
    char usr_SEX;

    @Column(name="USR_AGE")
    Integer usr_AGE;

    @Column(name="USR_CONTRY_CODE_PHONE")
    String usr_CONTRY_CODE_PHONE;

    @Column(name="USR_TELEPHONE")
    Double usr_TELEPHONE;

    @Column(name="USR_ADDRESS")
    String usr_ADDRES;

    @Column(name="USR_DELETE")
    char usr_DELETE;

    @Column(name="USER_PHOTO")
    @Lob
    Blob usr_PHOTO;

    @Column(name="USR_USER_PROFILE_ID")
    Double usr_PROFILE_ID;


    public User() {
    }

    public Double getUsr_ID() {
        return usr_ID;
    }

    public void setUsr_ID(Double usr_ID) {
        this.usr_ID = usr_ID;
    }

    public String getUsr_TYPE_ID() {
        return usr_TYPE_ID;
    }

    public void setUsr_TYPE_ID(String usr_TYPE_ID) {
        this.usr_TYPE_ID = usr_TYPE_ID;
    }

    public String getUsr_LAST_NAME() {
        return usr_LAST_NAME;
    }

    public void setUsr_LAST_NAME(String usr_LAST_NAME) {
        this.usr_LAST_NAME = usr_LAST_NAME;
    }

    public char getUsr_SEX() {
        return usr_SEX;
    }

    public void setUsr_SEX(char usr_SEX) {
        this.usr_SEX = usr_SEX;
    }

    public Integer getUsr_AGE() {
        return usr_AGE;
    }

    public void setUsr_AGE(Integer usr_AGE) {
        this.usr_AGE = usr_AGE;
    }

    public String getUsr_CONTRY_CODE_PHONE() {
        return usr_CONTRY_CODE_PHONE;
    }

    public void setUsr_CONTRY_CODE_PHONE(String usr_CONTRY_CODE_PHONE) { this.usr_CONTRY_CODE_PHONE = usr_CONTRY_CODE_PHONE; }

    public Double getUsr_TELEPHONE() {
        return usr_TELEPHONE;
    }

    public void setUsr_TELEPHONE(Double usr_TELEPHONE) {
        this.usr_TELEPHONE = usr_TELEPHONE;
    }

    public String getUsr_ADDRES() {
        return usr_ADDRES;
    }

    public void setUsr_ADDRES(String usr_ADDRES) {
        this.usr_ADDRES = usr_ADDRES;
    }

    public char getUsr_DELETE() {
        return usr_DELETE;
    }

    public void setUsr_DELETE(char usr_DELETE) {
        this.usr_DELETE = usr_DELETE;
    }

    public Blob getUsr_PHOTO() {
        return usr_PHOTO;
    }

    public void setUsr_PHOTO(Blob usr_PHOTO) {
        this.usr_PHOTO = usr_PHOTO;
    }

    public Double getUsr_PROFILE_ID() {
        return usr_PROFILE_ID;
    }

    public String getUsr_FIRST_NAME() {
        return usr_FIRST_NAME;
    }

    public void setUsr_FIRST_NAME(String usr_FIRST_NAME) {
        this.usr_FIRST_NAME = usr_FIRST_NAME;
    }

    public void setUsr_PROFILE_ID(Double usr_PROFILE_ID) {
        this.usr_PROFILE_ID = usr_PROFILE_ID;
    }
}
