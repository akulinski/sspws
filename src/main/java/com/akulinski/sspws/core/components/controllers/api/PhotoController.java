package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.models.AddPhotoRequest;
import com.akulinski.sspws.utils.PhotoUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;

@RestController
@RequestMapping("/photos")
@Getter
@Setter
public class PhotoController {

    private final UserRepository userRepository;

    private final PhotoRepository photoRepository;

    private final AlbumRepository albumRepository;

    private final PhotoRequestParser photoRequestParser;

    @Autowired
    public PhotoController(UserRepository userRepository, PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
        this.photoRequestParser = new PhotoRequestParser(photoRepository, userRepository, albumRepository);

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

    @RequestMapping(value = "/addPhoto", method = RequestMethod.POST)
    public ResponseEntity<PhotoEntity> addPhoto(@RequestBody AddPhotoRequest addPhotoRequest) throws IOException {

        PhotoEntity photoEntity = getPhotoEntityAndCreateAlbumIfDosntExists(addPhotoRequest);

        return new ResponseEntity<PhotoEntity>(photoEntity, HttpStatus.ACCEPTED);
    }

    private PhotoEntity getPhotoEntityAndCreateAlbumIfDosntExists(AddPhotoRequest addPhotoRequest) throws IOException {
        UserEntity userEntity = userRepository.getByUsername(addPhotoRequest.getUser());
        PhotoEntity photoEntity = PhotoUtils.convertToFile(addPhotoRequest, userEntity);

        AlbumEntity albumEntity = albumRepository.findAllByUserEntityAndAlbumName(userEntity, addPhotoRequest.getTitle());

        if (albumEntity == null) {
            albumEntity = new AlbumEntity();
            albumEntity.setAlbumName(addPhotoRequest.getTitle());
            albumEntity.setUserEntity(userEntity);
            albumRepository.save(albumEntity);
        }
        photoEntity.setAlbumEntity(albumEntity);

        photoRepository.save(photoEntity);
        return photoEntity;

    }

    @GetMapping(value = "/getPhoto/{albumName}/{title}")
    public ResponseEntity<String> getPhoto(@PathVariable String albumName, @PathVariable String title, Principal principal) {

        String base64 = photoRequestParser.getBase64OfRequestedPhoto(albumName, title, principal);

        return new ResponseEntity<String>(base64, HttpStatus.ACCEPTED);
    }

}
