package com.bogdan.pojo;

import java.util.ArrayList;

public class Row {
    private Contact contact;
    private ArrayList<Phone> phones;
    private ArrayList<AttachedFile> files;

    public Row(){}

    public Row(Contact c, ArrayList<Phone> p, ArrayList<AttachedFile> af){
        contact = c;
        phones = p;
        files = af;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<Phone> phones) {
        this.phones = phones;
    }

    public ArrayList<AttachedFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<AttachedFile> files) {
        this.files = files;
    }
}
