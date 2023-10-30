package com.example.prm392_shopping_project.model;

import java.sql.Date;

public class Account {
    private String phone;
    private String password;
    private String createdAt;
    private boolean isAdmin;

    public Account(String phone, String password, String createdAt, boolean isAdmin) {
        this.phone = phone;
        this.password = password;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
