package com.bogdan.pojo;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {
    private Integer id;
    private String first_name;
    private String last_name;
    private String patronymic;
    private String gender;
    private String marital_status;
    private String website_url;
    private String email;
    private String job_place;
    private String postal_code;
    private Date date_of_birth;
    private String state;
    private String city;
    private String street;
    private String house_number;
    private AttachedFile photo_url;
    private String comment;
    private Date dateFrom;
    private Date dateTo;

    public String getFullName(){
        return String.format("%s %s %s", last_name, first_name, patronymic);
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Contact(){}

    public Contact(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return marital_status;
    }

    public void setMaritalStatus(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getWebsiteUrl() {
        return website_url;
    }

    public void setWebsiteUrl(String website_url) {
        this.website_url = website_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobPlace() {
        return job_place;
    }

    public void setJobPlace(String job_place) {
        this.job_place = job_place;
    }

    public String getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(String postal_code) {
        this.postal_code = postal_code;
    }

    public Date getBirthDate() {
        return date_of_birth;
    }

    public void setBirthDate(Date birthDate) {
        this.date_of_birth = birthDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return house_number;
    }

    public void setHouseNumber(String house_number) {
        this.house_number = house_number;
    }

    public AttachedFile getPhoto() {
        return photo_url;
    }

    public void setPhoto(AttachedFile photo) {
        this.photo_url = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return  "id: " + getId() + "; first_name: " + getFirstName() + "; last_name: " + getLastName() +
                "; patronymic: " + getPatronymic() + " gender: " + getGender() + "; marital_status: " + getMaritalStatus() +
                "; website: " + getWebsiteUrl() + "; email: " + getEmail() + "; job_place: " + getJobPlace() +
                "; postal: " + getPostalCode() + "; birth_date: " + getBirthDate() + "; country: " + getState()+
                "; city: " + getCity() + "; street: " + getStreet() + "; house: " + getHouseNumber() +
                "; comment: " + getComment();
    }
}
