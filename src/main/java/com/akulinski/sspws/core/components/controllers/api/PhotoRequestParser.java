package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.utils.PhotoUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PhotoRequestParser {
    private final PhotoController photoController;

    public PhotoRequestParser(PhotoController photoController) {
        this.photoController = photoController;
    }

    String getBase64OfRequestedPhoto(@PathVariable String albumName, @PathVariable String title, Principal principal) {
        PhotoEntity photoEntity = getPhotoEntityFromRequest(albumName, title, principal);
        ArrayList<PhotoEntity> listOfPhotoEntites = new ArrayList<PhotoEntity>();
        listOfPhotoEntites.add(photoEntity);
        return PhotoUtils.convertPhotosToJson(listOfPhotoEntites).getPhotos().get(0);
    }

    PhotoEntity getPhotoEntityFromRequest(@PathVariable String albumName, @PathVariable String title, Principal principal) {
        String username = principal.getName();

        UserEntity userEntity = photoController.getUserRepository().getByUsername(username);

        AlbumEntity albumEntity = getAlbumEntityByAlbumName(albumName, userEntity);

        return photoController.getPhotoRepository().getByAlbumEntityAndTitle(albumEntity, title);
    }

    AlbumEntity getAlbumEntityByAlbumName(@PathVariable String albumName, UserEntity userEntity) {
        List<AlbumEntity> albumEntities = photoController.getAlbumRepository().findAlbumEntitiesByUserEntity(userEntity);

        AtomicReference<AlbumEntity> albumEntity = new AtomicReference<AlbumEntity>();

        albumEntities.forEach(ae -> {
            if (ae.getAlbumName().equals(albumName)) {
                albumEntity.set(ae);
            }
        });

        return albumEntity.get();
    }
}