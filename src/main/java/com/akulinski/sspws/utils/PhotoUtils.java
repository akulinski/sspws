package com.akulinski.sspws.utils;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.models.AddPhotoRequest;
import com.akulinski.sspws.models.PhotosResponseModel;
import org.glassfish.jersey.internal.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PhotoUtils {

    public static PhotoEntity convertToFile(AddPhotoRequest addPhotoRequest, UserEntity userEntity) throws IOException {

        PhotoEntity photoEntity = null;

        if (!checkIfDirExists(addPhotoRequest.getPath())) {

            writeBase64ToFile(addPhotoRequest.getPath(), addPhotoRequest);

            AlbumEntity albumEntity = getAlbumEntityWithSetup(addPhotoRequest, userEntity);

            photoEntity = getPhotoEntityWithSetup(addPhotoRequest.getPath(), addPhotoRequest, albumEntity);
        }
        return photoEntity;
    }

    private static void writeBase64ToFile(String file, AddPhotoRequest addPhotoRequest) throws IOException {
        Files.createDirectories(Paths.get(file));

        byte[] decodedString = Base64.decode(addPhotoRequest.getData().getBytes());

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(decodedString));

        File outputFile = new File(file);

        ImageIO.write(img, "jpg", outputFile);
    }

    public static boolean checkIfDirExists(String file) {
        File tmpDir = new File(file);
        return tmpDir.exists();
    }

    private static AlbumEntity getAlbumEntityWithSetup(AddPhotoRequest addPhotoRequest, UserEntity userEntity) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName(addPhotoRequest.getAlbum());
        albumEntity.setUserEntity(userEntity);
        return albumEntity;
    }

    private static PhotoEntity getPhotoEntityWithSetup(String file, AddPhotoRequest addPhotoRequest, AlbumEntity albumEntity) {
        PhotoEntity photoEntity;
        photoEntity = new PhotoEntity();
        photoEntity.setTitle(addPhotoRequest.getTitle());
        photoEntity.setAlbumEntity(albumEntity);
        photoEntity.setLink(file);
        return photoEntity;
    }

    /**
     * Takes in array of photo entities and converts then to json
     *
     * @param photoEntities
     * @return
     */
    public static PhotosResponseModel convertPhotosToJson(ArrayList<PhotoEntity> photoEntities) {

        PhotosResponseModel photosResponseModel = new PhotosResponseModel();

        photoEntities.forEach(photoEntity -> {

            convertFileToBase64AndAddToPhotosModel(photosResponseModel, photoEntity);
        });

        return getPhotosResponseModelValueWithSetup(photoEntities, photosResponseModel);
    }

    private static PhotosResponseModel getPhotosResponseModelValueWithSetup(ArrayList<PhotoEntity> photoEntities, PhotosResponseModel photosResponseModel) {
        photosResponseModel.setTitle(photoEntities.get(0).getTitle());
        String username = photoEntities.get(0).getAlbumEntity().getUserEntity().getUsername();
        photosResponseModel.setUser(username);

        return photosResponseModel;
    }

    private static void convertFileToBase64AndAddToPhotosModel(PhotosResponseModel photosResponseModel, PhotoEntity photoEntity) {
        Path path = Paths.get(photoEntity.getLink());

        ByteArrayOutputStream bytes = null;
        String base64 = null;


        try {

            bytes = new ByteArrayOutputStream((int) (Files.size(path) * 4 / 3 + 4));
            OutputStream base64Stream = java.util.Base64.getEncoder().wrap(bytes); //naming conflict with glassfish function
            Files.copy(path, base64Stream);
            base64 = bytes.toString("US-ASCII");

        } catch (IOException e) {
            e.printStackTrace();
        }

        photosResponseModel.getPhotos().add(base64);
    }


}
