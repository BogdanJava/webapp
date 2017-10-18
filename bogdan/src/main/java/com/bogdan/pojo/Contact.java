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
    private Date birthDate;
    private String state;
    private String city;
    private String street;
    private String house_number;
    private AttachedFile photo;
    private String comment;

    public Contact(){}

    public Contact(Integer id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
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

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob_place() {
        return job_place;
    }

    public void setJob_place(String job_place) {
        this.job_place = job_place;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public AttachedFile getPhoto() {
        return photo;
    }

    public void setPhoto(AttachedFile photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return  "id: " + getId() + "; first_name: " + getFirst_name() + "; last_name: " + getLast_name() +
                "; patronymic: " + getPatronymic() + " gender: " + getGender() + "; marital_status: " + getMarital_status() +
                "; website: " + getWebsite_url() + "; email: " + getEmail() + "; job_place: " + getJob_place() +
                "; postal: " + getPostal_code() + "; birth_date: " + getBirthDate() + "; country: " + getState()+
                "; city: " + getCity() + "; street: " + getStreet() + "; house: " + getHouse_number() +
                "; comment: " + getComment();
    }
}
