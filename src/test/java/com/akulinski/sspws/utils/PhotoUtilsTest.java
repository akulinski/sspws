package com.akulinski.sspws.utils;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.akulinski.sspws.models.AddPhotoRequest;
import com.akulinski.sspws.models.PhotosResponseModel;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PhotoUtilsTest {

    @Autowired
    private UserRepository userRepository;

    private AddPhotoRequest addPhotoRequest;

    @Before
    public void setUp() throws Exception {
        addPhotoRequest = new AddPhotoRequest();
        addPhotoRequest.setData(base64());
        addPhotoRequest.setAlbum("testAlbum");
        addPhotoRequest.setTitle("test");
        addPhotoRequest.setUser("tomeczek");
    }

    @After
    public void tearDown() throws Exception {
        File toDelete = new File(addPhotoRequest.getPath());
        toDelete.delete();
    }

    @Test
    public void fileDosntExist() {
        assertFalse(PhotoUtils.checkIfDirExists(addPhotoRequest.getPath()));
    }

    @Test
    public void convertToFile() throws IOException {
        PhotoUtils.convertToFile(addPhotoRequest, userRepository.getByUsername("tomeczek"));
        assertTrue(PhotoUtils.checkIfDirExists(addPhotoRequest.getPath()));
    }

    @Test
    public void convertPhotosToJson() throws IOException {
        PhotosResponseModel mock = new PhotosResponseModel();
        modelSetUp(mock);
        mock.getPhotos().add(base64());

        PhotosResponseModel testModel = PhotoUtils.convertPhotosToJson(setUpPhotosList());
        String actual = mock.getPhotos().get(0).replaceAll("\\s+","");
        String test = testModel.getPhotos().get(0).replaceAll("\\s+","");
        TestCase.assertEquals(actual, test);
    }

    private ArrayList<PhotoEntity> setUpPhotosList() {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setLink("target/test-classes/tomeczek.jpg");
        photoEntity.setAlbumEntity(getAlbumEntity());
        photoEntity.setTitle("test");
        ArrayList<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(photoEntity);

        return photoEntities;
    }

    private AlbumEntity getAlbumEntity() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName("testAlbum");
        albumEntity.setAlbumDescription("mock");
        albumEntity.setUserEntity(userRepository.getByUsername("tomeczek"));
        return albumEntity;
    }

    private void modelSetUp(PhotosResponseModel mock) {
        mock.setUser("tomeczek");
        mock.setTitle("test");
        mock.setAlbum("testAlbum");
    }

    public String base64() throws IOException {

        File file = new File("target/test-classes/base.txt");
        return IOUtils.toString(new FileInputStream(file), "UTF-8");
    }
}