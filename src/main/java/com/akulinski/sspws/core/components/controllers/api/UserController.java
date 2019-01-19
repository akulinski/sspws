package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.core.components.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/getAllUsers", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {
        return userService.getUserEntityResponseEntity(id);
    }

    @GetMapping("/getAlbums")
    public ResponseEntity getAlbums(Principal principal) {
        return userService.getUserAlbumsResponseEntity(principal);
    }

}
