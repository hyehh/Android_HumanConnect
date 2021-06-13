package com.aoslec.humanconnect.Bean;

public class Member {

    // Property
    private int mid;
    private String pw;
    private String name;

    public Member(int mid, String pw, String name) {
        this.mid = mid;
        this.pw = pw;
        this.name = name;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
