package com.example.firebasemymetro.models;

public class Station {
    private String station_name;
    private String url1;
    private String url2;
    private String url3;
    private String url4;
    private String url5;

    public Station() {
        // Default constructor
    }

    public Station(String station_name, String url1, String url2, String url3, String url4, String url5) {
        this.station_name = station_name;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
        this.url4 = url4;
        this.url5 = url5;
    }

    // Getter methods
    public String getStationName() {
        return station_name;
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }

    public String getUrl4() {
        return url4;
    }

    public String getUrl5() {
        return url5;
    }

    // Setter methods
    public void setStationName(String station_name) {
        this.station_name = station_name;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public void setUrl4(String url4) {
        this.url4 = url4;
    }

    public void setUrl5(String url5) {
        this.url5 = url5;
    }
}
