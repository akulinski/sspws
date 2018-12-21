package com.akulinski.sspws.models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

/**
 * Model used for responding to photo requests
 */
@Getter
@Setter
public class PhotosResponseModel {

    private LinkedList<String> photos;
    private String user;
    private String album;
    private String title;

    public PhotosResponseModel(){
        this.photos = new LinkedList<>();
    }

}