package com.example.ocrappthird.entities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Visitor
{
    public static final String TABLE_NAME = "card";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MOB_ONE = "mobone";
    public static final String COLUMN_MOB_TWO = "mobtwo";
    public static final String COLUMN_EMAIL_ONE = "emailone";
    public static final String COLUMN_EMAIL_TWO = "emailtwo";
    public static final String COLUMN_WEB_ONE = "webone";
    public static final String COLUMN_WEB_TWO = "webtwo";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_CARD_NOTE = "cardnote";
    public static final String COLUMN_IMAGE = "image";




    private int id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String name;
    private String mobone;
    private String mobtwo;
    private String emailone;
    private String emailtwo;
    private String webone;
    private String webtwo;
    private String address;
    private String company;

    private String cardnote;

    public String getCardnote() {
        return cardnote;
    }

    public void setCardnote(String cardnote) {
        this.cardnote = cardnote;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    byte[] imageByteArray;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobone() {
        return mobone;
    }

    public void setMobone(String mobone) {
        this.mobone = mobone;
    }

    public String getMobtwo() {
        return mobtwo;
    }

    public void setMobtwo(String mobtwo) {
        this.mobtwo = mobtwo;
    }

    public String getEmailone() {
        return emailone;
    }

    public void setEmailone(String emailone) {
        this.emailone = emailone;
    }

    public String getEmailtwo() {
        return emailtwo;
    }

    public void setEmailtwo(String emailtwo) {
        this.emailtwo = emailtwo;
    }

    public String getWebone() {
        return webone;
    }

    public void setWebone(String webone) {
        this.webone = webone;
    }

    public String getWebtwo() {
        return webtwo;
    }

    public void setWebtwo(String webtwo) {
        this.webtwo = webtwo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_MOB_ONE + " TEXT,"
                    + COLUMN_MOB_TWO + " TEXT,"
                    + COLUMN_EMAIL_ONE + " TEXT,"
                    + COLUMN_EMAIL_TWO + " TEXT,"
                    + COLUMN_WEB_ONE + " TEXT,"
                    + COLUMN_WEB_TWO + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_COMPANY + " TEXT,"
                    + COLUMN_CARD_NOTE + " TEXT,"
                    + COLUMN_IMAGE + " BLOB,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT (datetime('now','localtime'))"
                    + ")";

    public Visitor() {
    }

    public Visitor(int id,String username,String name,String mobone,String mobtwo,String emainone,String emaintwo,String webone,String webtwo,String address,String note,String company,byte[] imaByteArr,String time) {
        this.id=id;
        this.username=username;
        this.name=name;
        this.mobone=mobone;
        this.mobtwo=mobtwo;
        this.emailone=emainone;
        this.emailtwo=emaintwo;
        this.webone=webone;
        this.webtwo=webtwo;
        this.address=address;
        this.cardnote=note;
        this.company=company;
        this.imageByteArray=imaByteArr;
        this.timestamp=time;
    }




}
