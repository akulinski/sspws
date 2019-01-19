package com.akulinski.sspws.core.components.services;

import com.akulinski.sspws.core.components.controllers.api.PhotoRequestParser;
import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.models.AddPhotoRequest;
import com.akulinski.sspws.utils.PhotoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;

@Service
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final PhotoRequestParser photoRequestParser;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, UserRepository userRepository, AlbumRepository albumRepository, PhotoRequestParser photoRequestParser) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.photoRequestParser = photoRequestParser;
    }

    public ResponseEntity getPhotosByUsernameResponseEntity(@PathVariable String name) {
        LinkedList<PhotoEntity> photoEntityLinkedList = getAllPhotoEntitiesOfUser(name);
        return new ResponseEntity<>(photoEntityLinkedList, HttpStatus.ACCEPTED);
    }

    private LinkedList<PhotoEntity> getAllPhotoEntitiesOfUser(@PathVariable String name) {
        UserEntity userEntity = userRepository.getByUsername(name);

        LinkedList<PhotoEntity> photoEntityLinkedList = new LinkedList<>();

        userEntity.getAlbumEntitySet().forEach(albumEntity -> {
            photoEntityLinkedList.addAll(albumEntity.getPhotoEntitySet());
        });

        return photoEntityLinkedList;
    }

    public PhotoEntity getPhotoEntityAndCreateAlbumIfDosntExists(AddPhotoRequest addPhotoRequest) throws IOException {
        UserEntity userEntity = userRepository.getByUsername(addPhotoRequest.getUser());
        PhotoEntity photoEntity = PhotoUtils.convertToFile(addPhotoRequest, userEntity);

        AlbumEntity albumEntity = albumRepository.findAllByUserEntityAndAlbumName(userEntity, addPhotoRequest.getTitle());

        CreateAlbumIfNull(addPhotoRequest, userEntity, albumEntity);

        photoEntity.setAlbumEntity(albumEntity);
        photoRepository.save(photoEntity);

        return photoEntity;
    }

    private void CreateAlbumIfNull(AddPhotoRequest addPhotoRequest, UserEntity userEntity, AlbumEntity albumEntity) {
        if (albumEntity == null) {
            albumEntity = new AlbumEntity();
            albumEntity.setAlbumName(addPhotoRequest.getTitle());
            albumEntity.setUserEntity(userEntity);
            albumRepository.save(albumEntity);
        }
    }

    public ResponseEntity getBase64OfPhotoResponseEntity(@PathVariable String albumName, @PathVariable String title, Principal principal) {
        String base64 = photoRequestParser.getBase64OfRequestedPhoto(albumName, title, principal);

        return new ResponseEntity<>(base64, HttpStatus.ACCEPTED);
    }
}
