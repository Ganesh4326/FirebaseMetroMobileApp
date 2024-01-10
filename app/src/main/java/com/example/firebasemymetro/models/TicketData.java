package com.example.firebasemymetro.models;

import java.io.Serializable;

public class TicketData implements Serializable {
    private String user_id;
    private String ticket_id;
    private String source;
    private String destination;
    private int quantity;
    private int amount;
    private String time;
    private String rewardUsed;

    public int getAmount() {
        return amount;
    }

    public String getDestination() {
        return destination;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRewardUsed() {
        return rewardUsed;
    }

    public String getSource() {
        return source;
    }

    public String getTime() {
        return time;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setRewardUsed(String rewardUsed) {
        this.rewardUsed = rewardUsed;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TicketData() {
    }

    public TicketData(String user_id, String ticket_id, String source, String destination, String rewardUsed, int quantity, int amount, String time) {
        this.amount = amount;
        this.ticket_id = ticket_id;
        this.source = source;
        this.destination = destination;
        this.rewardUsed = rewardUsed;
        this.quantity = quantity;
        this.time = time;
        this.user_id = user_id;
    }
}
