package com.example.firebasemymetro.models;

public class CustomItem {
    private String text;
    private int imageResource;

    public CustomItem(String text, int imageResource) {
        this.text = text;
        this.imageResource = imageResource;
    }

    public String getText() {
        return text;
    }

    public int getImageResource() {
        return imageResource;
    }
}
