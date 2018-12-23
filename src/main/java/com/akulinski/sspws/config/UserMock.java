package com.akulinski.sspws.config;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoDescriptionEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.photo.AlbumRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoDescriptionRepository;
import com.akulinski.sspws.core.components.repositories.photo.PhotoRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Component
public class UserMock {

    private UserRepository userRepository;

    private NameGenerator nameGenerator;

    private PasswordEncoder passwordEncoder;

    private final PhotoDescriptionRepository photoDescriptionRepository;

    private final PhotoRepository photoRepository;

    private AlbumRepository albumRepository;

    private final Lorem lorem;

    private SecureRandom secureRandom;

    @Autowired
    public UserMock(UserRepository userRepository, PasswordEncoder passwordEncoder, PhotoDescriptionRepository photoDescriptionRepository, PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.nameGenerator = new NameGenerator();
        this.passwordEncoder = passwordEncoder;
        this.photoDescriptionRepository = photoDescriptionRepository;
        this.photoRepository = photoRepository;
        this.lorem = LoremIpsum.getInstance();
        this.secureRandom = new SecureRandom();

    }

    public void mockData() {
        GenerateAndSaveDatabaseData();

        createAndSaveEntitesWithSpecificUser();
    }

    private void GenerateAndSaveDatabaseData() {
        List<Name> names = nameGenerator.generateNames(20);

        names.forEach(name -> {
            try {
                createAndSaveEntites(name);
            } catch (Exception ignored) {

            }
        });
    }

    private void createAndSaveEntitesWithSpecificUser() {
        UserEntity userEntity = getUserEntity("tomeczek", "tomeczek", "MALE");

        AlbumEntity albumEntity = getSpecyficAlbumEntiy(userEntity);

        AlbumEntity albumEntity1 = getAlbumEntity(userEntity);

        PhotoEntity photoEntity = getPhotoEntity(userEntity, albumEntity);

        PhotoDescriptionEntity photoDescriptionEntity = getDescriptionEntity(photoEntity);


        saveEntitesWith2Albums(userEntity, albumEntity, albumEntity1, photoEntity, photoDescriptionEntity);
    }

    private void createAndSaveEntites(Name name) {
        UserEntity userEntity = getUserEntity(name.getFirstName(), name.getLastName(), name.getGender().name());

        AlbumEntity albumEntity = getAlbumEntity(userEntity);

        PhotoEntity photoEntity = getPhotoEntity(userEntity, albumEntity);

        PhotoDescriptionEntity photoDescriptionEntity = getDescriptionEntity(photoEntity);
        saveEntites(userEntity, albumEntity, photoEntity, photoDescriptionEntity);
    }

    private void saveEntitesWith2Albums(UserEntity userEntity, AlbumEntity albumEntity, AlbumEntity albumEntity1, PhotoEntity photoEntity, PhotoDescriptionEntity photoDescriptionEntity) {
        userRepository.save(userEntity);
        albumRepository.save(albumEntity);
        albumRepository.save(albumEntity1);
        photoRepository.save(photoEntity);
        photoDescriptionRepository.save(photoDescriptionEntity);
    }

    private PhotoDescriptionEntity getDescriptionEntity(PhotoEntity photoEntity) {
        PhotoDescriptionEntity photoDescriptionEntity = new PhotoDescriptionEntity();
        photoDescriptionEntity.setDescription(lorem.getWords(secureRandom.nextInt(10)));
        photoDescriptionEntity.setPhoto(photoEntity);
        return photoDescriptionEntity;
    }

    private void saveEntites(UserEntity userEntity, AlbumEntity albumEntity, PhotoEntity photoEntity, PhotoDescriptionEntity photoDescriptionEntity) {
        userRepository.save(userEntity);
        albumRepository.save(albumEntity);
        photoRepository.save(photoEntity);
        photoDescriptionRepository.save(photoDescriptionEntity);
    }

    private PhotoEntity getPhotoEntity(UserEntity userEntity, AlbumEntity albumEntity) {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setLink("/" + userEntity.getUsername() + "/" + lorem.getTitle(1));
        photoEntity.setAlbumEntity(albumEntity);
        return photoEntity;
    }

    private AlbumEntity getAlbumEntity(UserEntity userEntity) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName(lorem.getCity());
        albumEntity.setUserEntity(userEntity);
        albumEntity.setAlbumDescription(lorem.getWords(10));
        return albumEntity;
    }

    private AlbumEntity getSpecyficAlbumEntiy(UserEntity userEntity) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName("mockName");
        albumEntity.setUserEntity(userEntity);
        albumEntity.setAlbumDescription("MockDescription");
        return albumEntity;
    }

    private UserEntity getUserEntity(String firstName, String lastName, String name2) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(firstName);
        userEntity.setPassword(passwordEncoder.encode(lastName));
        userEntity.setGender(name2);
        return userEntity;
    }
}
