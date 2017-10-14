package com.bogdan.pojo;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String gender;
    private String maritalStatus;
    private String websiteURL;
    private String email;
    private String jobPlace;
    private String postalCode;
    private Date birthDate;
    private String state;
    private String city;
    private String street;
    private String houseNumber;
    private AttachedFile photo;
    private String comment;


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobPlace() {
        return jobPlace;
    }

    public void setJobPlace(String jobPlace) {
        this.jobPlace = jobPlace;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public AttachedFile getPhoto() {
        return photo;
    }

    public void setPhoto(AttachedFile photoURL) {
        this.photo = photoURL;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return  "id: " + id + "; first_name: " + getFirstName() + "; last_name: " + getLastName() +
                "; patronymic: " + getPatronymic() + " gender: " + getGender() + "; marital_status: " + getMaritalStatus() +
                "; website: " + getWebsiteURL() + "; email: " + getEmail() + "; job_place: " + getJobPlace() +
                "; postal: " + getPostalCode() + "; birth_date: " + getBirthDate() + "; country: " + getState()+
                "; city: " + getCity() + "; street: " + getStreet() + "; house: " + getHouseNumber() +
                "; comment: " + getComment();
    }
}
