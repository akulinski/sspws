package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoDescriptionRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final UserRepository userRepository;

    private final PhotoRepository photoRepository;

    private final PhotoDescriptionRepository photoDescriptionRepository;

    private final AlbumRepository albumRepository;

    @Autowired
    public PhotoController(UserRepository userRepository, PhotoRepository photoRepository, PhotoDescriptionRepository photoDescriptionRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.photoDescriptionRepository = photoDescriptionRepository;
        this.albumRepository = albumRepository;
    }

    @GetMapping(value = "/getByUsername/{name}")
    public ResponseEntity<LinkedList<PhotoEntity>> getByUsername(@PathVariable String name) {

        LinkedList<PhotoEntity> photoEntityLinkedList = getAllPhotoEntitiesOfUser(name);

        return new ResponseEntity<LinkedList<PhotoEntity>>(photoEntityLinkedList, HttpStatus.ACCEPTED);
    }

    private LinkedList<PhotoEntity> getAllPhotoEntitiesOfUser(@PathVariable String name) {
        UserEntity userEntity = userRepository.getByUsername(name);

        LinkedList<PhotoEntity> photoEntityLinkedList = new LinkedList<>();

        userEntity.getAlbumEntitySet().forEach(albumEntity -> {
            photoEntityLinkedList.addAll(albumEntity.getPhotoEntitySet());
        });
        return photoEntityLinkedList;
    }
}
