package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getAllUsers")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<List<UserEntity>>(userRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        try{
            Integer idInteger = Integer.valueOf(id);
            return new ResponseEntity<UserEntity>(userRepository.getById(idInteger), HttpStatus.ACCEPTED);
        }catch (NumberFormatException numberFormatException){
            return new ResponseEntity<UserEntity>(new UserEntity(),HttpStatus.BAD_REQUEST);
        }
    }

}
