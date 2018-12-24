package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/getAllUsers",method={RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<List<UserEntity>>(userRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        try {
            Integer idInteger = Integer.valueOf(id);
            return new ResponseEntity<UserEntity>(userRepository.getById(idInteger), HttpStatus.ACCEPTED);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<UserEntity>(new UserEntity(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAlbums")
    public ResponseEntity<ArrayList<AlbumEntity>> getAlbums(Principal principal) {

        UserEntity userEntity = getUserEntityFromPrincipal(principal);

        ArrayList<AlbumEntity> albumEntities = getAlbumEntitiesFromUserEntity(userEntity);

        return new ResponseEntity<ArrayList<AlbumEntity>>(albumEntities, HttpStatus.ACCEPTED);
    }

    private UserEntity getUserEntityFromPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.getByUsername(username);
    }

    private ArrayList<AlbumEntity> getAlbumEntitiesFromUserEntity(UserEntity userEntity) {
        return new ArrayList<>(userEntity.getAlbumEntitySet());
    }
}
