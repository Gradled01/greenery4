package com.example.greenery4;

import cn.leancloud.AVFile;
import cn.leancloud.AVObject;

public class Test extends AVObject {
    private String username;
    private AVFile avFile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AVFile getAvFile() {
        return avFile;
    }

    public void setAvFile(AVFile avFile) {
        this.avFile = avFile;
    }
}
