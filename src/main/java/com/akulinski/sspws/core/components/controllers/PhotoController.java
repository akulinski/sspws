package com.akulinski.sspws.core.components.controllers;

import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
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

import java.util.ArrayList;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final UserRepository userRepository;

    private final PhotoRepository photoRepository;

    private final PhotoDescriptionRepository photoDescriptionRepository;

    @Autowired
    public PhotoController(UserRepository userRepository, PhotoRepository photoRepository, PhotoDescriptionRepository photoDescriptionRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.photoDescriptionRepository = photoDescriptionRepository;
    }

    @GetMapping(value = "/getByUsername/{name}")
    public ResponseEntity<ArrayList<PhotoEntity>> getByUsername(@PathVariable String name) {

        UserEntity userEntity = userRepository.getByUsername(name);
        ArrayList<PhotoEntity> photoEntityArrayList = photoRepository.findByUserEntity(userEntity);

        return new ResponseEntity<ArrayList<PhotoEntity>>(photoEntityArrayList, HttpStatus.ACCEPTED);
    }
}
