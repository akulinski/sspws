package com.akulinski.sspws.core.components.controllers.frontend;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class IndexController {
    private static final String USER = "user";
    private static final String ALBUMS = "albums";

    private final UserRepository userRepository;

    private final AlbumRepository albumRepository;

    @Autowired
    public IndexController(UserRepository userRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
    }

    @GetMapping("/")
    public String renderIndex(Model model, Principal principal) {

        loadModelForIndexPage(model, principal);
        return "index";
    }

    private void loadModelForIndexPage(Model model, Principal principal) {
        String userName = principal.getName();

        ArrayList<AlbumEntity> albumEntities = (ArrayList<AlbumEntity>) albumRepository.findAlbumEntitiesByUserEntity(userRepository.getByUsername(userName));

        model.addAttribute(USER, userRepository.getByUsername(userName));

        model.addAttribute(ALBUMS, albumEntities);
    }
}
