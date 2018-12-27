package com.akulinski.sspws.core.components.repositories.photo;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<AlbumEntity, Integer> {

    List<AlbumEntity> findAlbumEntitiesByUserEntity(UserEntity userEntity);
    AlbumEntity findAllByUserEntityAndAlbumName(UserEntity userEntity,String albumName);
}
