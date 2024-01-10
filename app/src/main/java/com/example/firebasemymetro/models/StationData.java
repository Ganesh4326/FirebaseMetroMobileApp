package com.example.firebasemymetro.models;

import java.util.List;
import java.util.Map;

public class StationData {

    private String full_name;
    private String crowd_levels;
    private int next_train_in;
    private String parking_avail;
    private String rtc_avail;
    private List<String> places_names;
    private List<String> places_images;
    private List<String> places_links;
    private String platform1;
    private String platform2;
    private String platform3;
    private String platform4;
    private Map<String, String> event;

    public StationData() {
    }

    public StationData(String stationFullName, String crowdLevels, int nextTrainIn,
                       String parkingAvail, String rtcAvail, List<String> nearbyPlaces,
                       List<String> pictures, String platform1, String platform2, String platform3,
                       String platform4, Map<String, String> event) {
        this.full_name = stationFullName;
        this.crowd_levels = crowdLevels;
        this.next_train_in = nextTrainIn;
        this.parking_avail = parkingAvail;
        this.rtc_avail = rtcAvail;
        this.places_names = nearbyPlaces;
        this.places_links = pictures;
        this.platform1 = platform1;
        this.platform2 = platform2;
        this.platform3 = platform3;
        this.platform4 = platform4;
        this.event = event;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCrowd_levels() {
        return crowd_levels;
    }

    public void setCrowd_levels(String crowd_levels) {
        this.crowd_levels = crowd_levels;
    }

    public void setRtc_avail(String rtc_avail) {
        this.rtc_avail = rtc_avail;
    }

    public void setPlatform4(String platform4) {
        this.platform4 = platform4;
    }

    public void setPlatform3(String platform3) {
        this.platform3 = platform3;
    }

    public void setPlatform2(String platform2) {
        this.platform2 = platform2;
    }

    public void setPlatform1(String platform1) {
        this.platform1 = platform1;
    }

    public void setPlaces_links(List<String> places_links) {
        this.places_links = places_links;
    }

    public void setParking_avail(String parking_avail) {
        this.parking_avail = parking_avail;
    }

    public void setNext_train_in(int next_train_in) {
        this.next_train_in = next_train_in;
    }

    public void setPlaces_names(List<String> places_names) {
        this.places_names = places_names;
    }

    public String getPlatform4() {
        return platform4;
    }

    public String getRtc_avail() {
        return rtc_avail;
    }

    public String getPlatform3() {
        return platform3;
    }

    public String getPlatform2() {
        return platform2;
    }

    public String getParking_avail() {
        return parking_avail;
    }

    public String getPlatform1() {
        return platform1;
    }

    public List<String> getPlaces_links() {
        return places_links;
    }

    public List<String> getPlaces_names() {
        return places_names;
    }

    public int getNext_train_in() {
        return next_train_in;
    }

    public Map<String, String> getEvent() {
        return event;
    }

    public void setEvent(Map<String, String> event) {
        this.event = event;
    }

    public List<String> getPlaces_images() {
        return places_images;
    }

    public void setPlaces_images(List<String> places_images) {
        this.places_images = places_images;
    }
}
