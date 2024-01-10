package com.example.firebasemymetro.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    private String user_name;
    private String email_id;
    private String password;
    private String user_id;
    private int no_of_coins;
    private List<String> coupons;
    private List<String> bookings;

    public UserData() {
    }

    public UserData(String user_id, String user_name, String password, int no_of_coins, List<String> coupons, List<String> bookings) {
        this.bookings = bookings;
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.no_of_coins = no_of_coins;
        this.coupons = coupons;
    }

    public List<String> getBookings() {
        return bookings;
    }

    public int getNo_of_coins() {
        return no_of_coins;
    }

    public List<String> getCoupons() {
        return coupons;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setNo_of_coins(int no_of_coins) {
        this.no_of_coins = no_of_coins;
    }

    public void setBookings(List<String> bookings) {
        this.bookings = bookings;
    }

    public void setCoupons(List<String> coupons) {
        this.coupons = coupons;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("user_name", user_name);
        userDataMap.put("email_id", email_id);
        userDataMap.put("password", password);
        userDataMap.put("user_id", user_id);
        userDataMap.put("no_of_coins", no_of_coins);
        userDataMap.put("coupons", coupons);
        userDataMap.put("bookings", bookings);
        return userDataMap;
    }
}
