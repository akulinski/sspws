package com.akulinski.sspws.core.components.controllers.frontend;

import com.akulinski.sspws.core.components.controllers.api.PhotoRequestParser;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class AlbumController {

    private static String USERNAME_KEY = "username";
    private static String USER_ENTITY_KEY = "user";
    private static String ALBUM_NAME_KEY = "album";
    private static String PHOTOS_KEY = "photos";

    private final UserRepository userRepository;

    private final PhotoRepository photoRepository;

    private final AlbumRepository albumRepository;

    private final PhotoRequestParser photoRequestParser;

    @Autowired
    public AlbumController(UserRepository userRepository, PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;

        this.photoRequestParser = new PhotoRequestParser(photoRepository, userRepository, albumRepository);
    }

    @GetMapping("/album/{albumName}")
    public String renderAlbum(@PathVariable String albumName, Principal principal, Model model) {
        populateModelForAlbumPage(albumName, principal, model);
        return "album";
    }

    private void populateModelForAlbumPage(String albumName, Principal principal, Model model) {
        model.addAttribute(USERNAME_KEY, principal.getName());
        model.addAttribute(USER_ENTITY_KEY, userRepository.getByUsername(principal.getName()));
        model.addAttribute(ALBUM_NAME_KEY, albumName);
        model.addAttribute(PHOTOS_KEY, photoRequestParser.getListOfBase64OfAllPhotosInAlbum(albumName, principal));
    }

}
