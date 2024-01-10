package com.example.firebasemymetro.models;

public class RewardsData {
    private String reward_name;
    private String reward_id;
    private String reward_type;
    private String applicable_on;
    private int coins_req;
    private int discount_per;
    private int dis_amount;
    private int no_of_bookings;
    private int tickets_needed;
    private int expires_on;

    public int getCoins_req() {
        return coins_req;
    }

    public int getDis_amount() {
        return dis_amount;
    }

    public int getDiscount_per() {
        return discount_per;
    }

    public int getExpires_on() {
        return expires_on;
    }

    public int getNo_of_bookings() {
        return no_of_bookings;
    }

    public int getTickets_needed() {
        return tickets_needed;
    }

    public String getApplicable_on() {
        return applicable_on;
    }

    public String getReward_name() {
        return reward_name;
    }

    public String getReward_type() {
        return reward_type;
    }

    public void setApplicable_on(String applicable_on) {
        this.applicable_on = applicable_on;
    }

    public void setCoins_req(int coins_req) {
        this.coins_req = coins_req;
    }

    public void setDis_amount(int dis_amount) {
        this.dis_amount = dis_amount;
    }

    public void setDiscount_per(int discount_per) {
        this.discount_per = discount_per;
    }

    public void setExpires_on(int expires_on) {
        this.expires_on = expires_on;
    }

    public void setNo_of_bookings(int no_of_bookings) {
        this.no_of_bookings = no_of_bookings;
    }

    public void setReward_name(String reward_name) {
        this.reward_name = reward_name;
    }

    public void setReward_type(String reward_type) {
        this.reward_type = reward_type;
    }

    public void setTickets_needed(int tickets_needed) {
        this.tickets_needed = tickets_needed;
    }

    public String getReward_id() {
        return reward_id;
    }

    public void setReward_id(String reward_id) {
        this.reward_id = reward_id;
    }

    public RewardsData() {
    }

    public RewardsData(String reward_name, String reward_type, String applicable_on, int no_of_bookings, int expires_on, int dis_amount, int discount_per, int tickets_needed, int coins_req, String reward_id) {
        this.reward_name = reward_name;
        this.applicable_on = applicable_on;
        this.reward_type = reward_type;
        this.no_of_bookings = no_of_bookings;
        this.coins_req = coins_req;
        this.dis_amount = dis_amount;
        this.discount_per = discount_per;
        this.tickets_needed = tickets_needed;
        this.expires_on = expires_on;
        this.reward_id = reward_id;
    }
}
