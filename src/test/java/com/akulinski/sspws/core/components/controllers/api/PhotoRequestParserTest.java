package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.utils.TestingUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhotoRequestParserTest {

    private final String mockAlbum = "mockAlbum";
    private final String mockdescription = "mockdescription";
    private final String username = "tomeczek";
    private final String mockTitle = "mockTitle";
    private final String link = "target/test-classes/tomeczek.jpg";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private PhotoRequestParser photoRequestParser;

    private TestingUtils testingUtils;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.photoRequestParser = new PhotoRequestParser(photoRepository, userRepository, albumRepository);
        this.testingUtils = new TestingUtils();
    }

    @Test
    @Transactional
    public void getBase64OfRequestedPhoto() throws Exception {
        mockTestEntites();
        Principal mockPrincipal = getMockPrincipal();
        String testBase64 = photoRequestParser.getBase64OfRequestedPhoto(mockAlbum, mockTitle, mockPrincipal);
        assertEquals(testBase64.replaceAll("\\s+", ""), testingUtils.base64().replaceAll("\\s+", ""));
    }

    private Principal getMockPrincipal() {
        return () -> username;
    }

    private void mockTestEntites() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName(mockAlbum);
        albumEntity.setAlbumDescription(mockdescription);
        albumEntity.setUserEntity(userRepository.getByUsername(username));

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setAlbumEntity(albumEntity);
        photoEntity.setTitle(mockTitle);
        photoEntity.setLink(link);

        albumRepository.save(albumEntity);
        photoRepository.save(photoEntity);
    }

    @Test
    public void getPhotoEntityFromRequest() {
        mockTestEntites();
        Principal mockPrincipal = getMockPrincipal();
        PhotoEntity photoEntity = photoRequestParser.getPhotoEntityFromRequest(mockAlbum, mockTitle, mockPrincipal);
        assertEquals(photoEntity.getTitle(), mockTitle);
    }

    @Test
    public void getAlbumEntityByAlbumName() {
        mockTestEntites();
        AlbumEntity albumEntity = photoRequestParser.getAlbumEntityByAlbumName(mockAlbum, userRepository.getByUsername(username));
        assertEquals(albumEntity.getAlbumName(), mockAlbum);
    }
}