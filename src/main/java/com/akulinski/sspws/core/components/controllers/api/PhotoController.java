package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.core.components.services.PhotoService;
import com.akulinski.sspws.models.AddPhotoRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/photos")
@Getter
@Setter
public class PhotoController {

    private final UserRepository userRepository;

    private final PhotoRepository photoRepository;

    private final AlbumRepository albumRepository;

    private final PhotoRequestParser photoRequestParser;

    private final PhotoService photoService;

    @Autowired
    public PhotoController(UserRepository userRepository, PhotoRepository photoRepository, AlbumRepository albumRepository, PhotoRequestParser photoRequestParser, PhotoService photoService) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
        this.photoRequestParser = photoRequestParser;
        this.photoService = photoService;
    }

    @GetMapping(value = "/getByUsername/{name}")
    public ResponseEntity getByUsername(@PathVariable String name) {

        return photoService.getPhotosByUsernameResponseEntity(name);
    }

    @RequestMapping(value = "/addPhoto", method = RequestMethod.POST)
    public ResponseEntity addPhoto(@RequestBody AddPhotoRequest addPhotoRequest) throws IOException {

        PhotoEntity photoEntity = photoService.getPhotoEntityAndCreateAlbumIfDosntExists(addPhotoRequest);

        return new ResponseEntity<>(photoEntity, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getPhoto/{albumName}/{title}")
    public ResponseEntity getPhoto(@PathVariable String albumName, @PathVariable String title, Principal principal) {

        return photoService.getBase64OfPhotoResponseEntity(albumName, title, principal);
    }

}
