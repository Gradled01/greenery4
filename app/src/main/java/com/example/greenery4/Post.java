package com.example.greenery4;

import java.util.List;

public class Post {
    private String title;
    private List<String> picture;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
