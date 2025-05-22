package com.example.family_budget.dto;

// в пакете com.example.myapplication.dto
public class ResponseModel {
    private String status;
    private int userId;
//    private String token;

    public ResponseModel(String status, int userId) {
        this.status = status;
        this.userId = userId;
    }
//    public ResponseModel(String status, int userId, String token) {
//        this.status = status;
//        this.userId = userId;
//        this.token = token;
//    }

    // геттеры и сеттеры
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

//    public String getToken() { return token; }
//    public void setToken(String token) { this.token = token; }
}
