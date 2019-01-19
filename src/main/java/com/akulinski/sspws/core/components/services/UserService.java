package com.akulinski.sspws.core.components.services;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity getUserEntityResponseEntity(String id) {
        try {
            return getUserByIdResponseEntity(id);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>(new UserEntity(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity getUserByIdResponseEntity(String id) {
        Integer idInteger = Integer.valueOf(id);
        return new ResponseEntity<>(userRepository.getById(idInteger), HttpStatus.ACCEPTED);
    }

    public ResponseEntity getUserAlbumsResponseEntity(Principal principal) {
        UserEntity userEntity = getUserEntityFromPrincipal(principal);
        ArrayList<AlbumEntity> albumEntities = getAlbumEntitiesFromUserEntity(userEntity);
        return new ResponseEntity<>(albumEntities, HttpStatus.ACCEPTED);
    }

    private UserEntity getUserEntityFromPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.getByUsername(username);
    }

    private ArrayList<AlbumEntity> getAlbumEntitiesFromUserEntity(UserEntity userEntity) {
        return new ArrayList<>(userEntity.getAlbumEntitySet());
    }
}
