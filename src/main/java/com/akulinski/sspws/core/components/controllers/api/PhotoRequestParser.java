package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.utils.PhotoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PhotoRequestParser {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public PhotoRequestParser(PhotoRepository photoRepository, UserRepository userRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
    }

    public String getBase64OfRequestedPhoto(String albumName, String title, Principal principal) {
        PhotoEntity photoEntity = getPhotoEntityFromRequest(albumName, title, principal);
        ArrayList<PhotoEntity> listOfPhotoEntites = new ArrayList<PhotoEntity>();
        listOfPhotoEntites.add(photoEntity);
        return PhotoUtils.convertPhotosToJson(listOfPhotoEntites).getPhotos().get(0);
    }

    public LinkedList<String> getListOfBase64OfAllPhotosInAlbum(String albumName, Principal principal) {

        AlbumEntity albumEntity = getAlbumEntityByAlbumName(albumName, userRepository.getByUsername(principal.getName()));

        ArrayList<PhotoEntity> photoEntities = photoRepository.getByAlbumEntity(albumEntity);

        return PhotoUtils.convertPhotosToJson(photoEntities).getPhotos();
    }

    PhotoEntity getPhotoEntityFromRequest(String albumName, String title, Principal principal) {
        String username = principal.getName();

        UserEntity userEntity = userRepository.getByUsername(username);

        AlbumEntity albumEntity = getAlbumEntityByAlbumName(albumName, userEntity);

        return photoRepository.getByAlbumEntityAndTitle(albumEntity, title);
    }

    AlbumEntity getAlbumEntityByAlbumName(String albumName, UserEntity userEntity) {
        List<AlbumEntity> albumEntities = albumRepository.findAlbumEntitiesByUserEntity(userEntity);

        AtomicReference<AlbumEntity> albumEntity = new AtomicReference<AlbumEntity>();

        albumEntities.forEach(ae -> {
            if (ae.getAlbumName().equals(albumName)) {
                albumEntity.set(ae);
            }
        });

        return albumEntity.get();
    }
}
