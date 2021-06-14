package com.aoslec.humanconnect.Bean;

public class AddressBook {

    private int acode;
    private String name;
    private int phone;
    private String email;
    private String filePath;

    public AddressBook(int acode, String name, int phone, String email, String filePath) {
        this.acode = acode;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.filePath = filePath;
    }

    public AddressBook(int acode) {
        this.acode = acode;
    }

    public int getAcode() {
        return acode;
    }

    public void setAcode(int acode) {
        this.acode = acode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
