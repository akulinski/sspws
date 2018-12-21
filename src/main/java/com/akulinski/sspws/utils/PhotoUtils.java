package com.akulinski.sspws.utils;

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

    public static PhotoEntity convertToFile(String file, AddPhotoRequest addPhotoRequest, UserEntity userEntity) throws IOException {

        File tmpDir = new File(file);

        PhotoEntity photoEntity = null;
        boolean exists = tmpDir.exists();
        if (!exists) {
            Files.createDirectories(Paths.get(file));

            byte[] decodedString = Base64.decode(addPhotoRequest.getData().getBytes());

            BufferedImage img = ImageIO.read(new ByteArrayInputStream(decodedString));

            File outputFile = new File(file);

            ImageIO.write(img, "jpg", outputFile);

            photoEntity = new PhotoEntity();
            photoEntity.setTitle(addPhotoRequest.getTitle());
            photoEntity.setUserEntity(userEntity);
            photoEntity.setAlbum(addPhotoRequest.getAlbum());
            photoEntity.setLink(file);
        }
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
        });

        photosResponseModel.setTitle(photoEntities.get(0).getTitle());
        photosResponseModel.setUser(photoEntities.get(0).getUserEntity().getUsername());

        return photosResponseModel;
    }
}
