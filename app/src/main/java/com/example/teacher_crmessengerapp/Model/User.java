package com.example.teacher_crmessengerapp.Model;

public class User {

    private String id;
    private String username;
    private String imageURL;
    private String status;
    private String search;
    private String series;
    private String section;
    private String email;
    private String roll;
    private String phnNumber;
    private String designation;
    private String phoneNUmber;

    public User(String id, String username, String imageURL,String status,String search,String series,String section,String email,String roll,String phnNumber,String designation,String phoneNUmber) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
        this.series = series;
        this.section = section;
        this.email = email;
        this.roll = roll;
        this.phnNumber = phnNumber;
        this.designation = designation;
        this.phoneNUmber = phoneNUmber;

    }
    public User()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPhnNumber() {
        return phnNumber;
    }

    public void setPhnNumber(String phnNumber) {
        this.phnNumber = phnNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhoneNUmber() {
        return phoneNUmber;
    }

    public void setPhoneNUmber(String phoneNUmber) {
        this.phoneNUmber = phoneNUmber;
    }
}
