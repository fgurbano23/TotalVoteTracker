package com.cgts.services.model.entity;

import javax.persistence.*;

@Entity
@Table(name="USR_ACCOUNT")
public class UserAccount {

    @Id
    @Column(name="ACCT_USERNAME_ID")
    private String acct_USERNAME_ID;

    @Column(name="ACCT_EMAIL")
    private String acct_EMAIL;

    @Column(name="ACCT_PASSWORD")
    private String acct_PASSWORD;

    @Column(name="ACCT_CREATE_DATE")
    private String acct_CREATE_DATE;

    @Column(name="ACCT_EFFECTIVE_DATE")
    private String acct_EFFECTIVE_DATE;

    @Column(name="ACCT_VALID_PASS")
    private Integer acct_VALID_PASS;

    @Column(name="ACCT_DISABLED_DATE")
    private String acct_DISABLED_DATE;

    @Column(name="ACCT_STATUS")
    private String acct_STATUS;

    @Column(name="ACCT_USR_ID")
    private Integer acct_USR_ID;

    public UserAccount(){
    }

    public String getAcct_USERNAME_ID() {
        return acct_USERNAME_ID;
    }

    public void setAcct_USERNAME_ID(String acct_USERNAME_ID) {
        this.acct_USERNAME_ID = acct_USERNAME_ID;
    }

    public String getAcct_EMAIL() {
        return acct_EMAIL;
    }

    public void setAcct_EMAIL(String acct_EMAIL) {
        this.acct_EMAIL = acct_EMAIL;
    }

    public String getAcct_PASSWORD() {
        return acct_PASSWORD;
    }

    public void setAcct_PASSWORD(String acct_PASSWORD) {
        this.acct_PASSWORD = acct_PASSWORD;
    }

    public String getAcct_CREATE_DATE() {
        return acct_CREATE_DATE;
    }

    public void setAcct_CREATE_DATE(String acct_CREATE_DATE) {
        this.acct_CREATE_DATE = acct_CREATE_DATE;
    }

    public String getAcct_EFFECTIVE_DATE() {
        return acct_EFFECTIVE_DATE;
    }

    public void setAcct_EFFECTIVE_DATE(String acct_EFFECTIVE_DATE) {
        this.acct_EFFECTIVE_DATE = acct_EFFECTIVE_DATE;
    }

    public Integer getAcct_VALID_PASS() {
        return acct_VALID_PASS;
    }

    public void setAcct_VALID_PASS(Integer acct_VALID_PASS) {
        this.acct_VALID_PASS = acct_VALID_PASS;
    }

    public String getAcct_DISABLED_DATE() {
        return acct_DISABLED_DATE;
    }

    public void setAcct_DISABLED_DATE(String acct_DISABLED_DATE) {
        this.acct_DISABLED_DATE = acct_DISABLED_DATE;
    }

    public String getAcct_STATUS() {
        return acct_STATUS;
    }

    public void setAcct_STATUS(String acct_STATUS) {
        this.acct_STATUS = acct_STATUS;
    }

    public Integer getAcct_USR_ID() {
        return acct_USR_ID;
    }

    public void setAcct_USR_ID(Integer acct_USR_ID) {
        this.acct_USR_ID = acct_USR_ID;
    }

    @Override
    public String toString() {
        return "UserAccount [ACCT_USERNAME_ID=" + acct_USERNAME_ID + ", ACCT_EMAIL=" + acct_EMAIL + ", ACCT_PASSWORD=" + acct_PASSWORD + ", ACCT_CREATE_DATE=" + acct_CREATE_DATE + ", ACCT_EFFECTIVE_DATE=" + acct_EFFECTIVE_DATE +
                ", ACCT_VALID_PASS=" + acct_VALID_PASS + ", ACCT_DISABLED_DATE=" + acct_DISABLED_DATE + ", ACCT_STATUS=" + acct_STATUS + ", ACCT_USR_ID=" + acct_USR_ID +"]";
    }

}
