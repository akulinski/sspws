package com.akulinski.sspws.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddPhotoRequest {

    private String data;
    private String title;
    private String user;
    private String album;

    public AddPhotoRequest(String data) {
        this.data = data;
    }
    public String getPath(){
        return String.format("photos/%s/%s/%s.jpg",user,album,title);
    }
}
