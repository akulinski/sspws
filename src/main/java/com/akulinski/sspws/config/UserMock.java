package com.akulinski.sspws.config;

import com.akulinski.sspws.core.components.entites.photo.PhotoDescriptionEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
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

    private Lorem lorem;

    private SecureRandom secureRandom;

    @Autowired
    public UserMock(UserRepository userRepository, PasswordEncoder passwordEncoder, PhotoDescriptionRepository photoDescriptionRepository, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.nameGenerator = new NameGenerator();
        this.passwordEncoder = passwordEncoder;
        this.photoDescriptionRepository = photoDescriptionRepository;
        this.photoRepository = photoRepository;
        this.lorem = LoremIpsum.getInstance();
        this.secureRandom = new SecureRandom();
    }

    public void mockData() {
        List<Name> names = nameGenerator.generateNames(20);

        names.forEach(name -> {
            try {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(name.getFirstName());
                userEntity.setPassword(passwordEncoder.encode(name.getLastName()));
                userEntity.setGender(name.getGender().name());

                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setLink("/" + userEntity.getUsername() + "/" + lorem.getTitle(1));

                PhotoDescriptionEntity photoDescriptionEntity = new PhotoDescriptionEntity();
                photoDescriptionEntity.setDescription(lorem.getWords(secureRandom.nextInt(10)));
                photoDescriptionEntity.setPhoto(photoEntity);
                photoEntity.setUserEntity(userEntity);

                userRepository.save(userEntity);
                photoRepository.save(photoEntity);
                photoDescriptionRepository.save(photoDescriptionEntity);
            } catch (Exception ignored) {

            }
        });


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("tomeczek");
        userEntity.setPassword(passwordEncoder.encode("wodeczka"));
        userEntity.setGender("MALE");

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setLink("/" + userEntity.getUsername() + "/" + lorem.getTitle(1));

        PhotoDescriptionEntity photoDescriptionEntity = new PhotoDescriptionEntity();
        photoDescriptionEntity.setDescription(lorem.getWords(secureRandom.nextInt(10)));
        photoDescriptionEntity.setPhoto(photoEntity);
        photoEntity.setUserEntity(userEntity);

        userRepository.save(userEntity);
        photoRepository.save(photoEntity);
        photoDescriptionRepository.save(photoDescriptionEntity);

    }
}
