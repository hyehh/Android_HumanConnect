package com.aoslec.humanconnect.Bean;

public class Member {

    // Property
    private int id;
    private String pw;

    // Constructor
    public Member(int id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    // Method
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
