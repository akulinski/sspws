package com.akulinski.sspws.core.components.repositories.photo;

import com.akulinski.sspws.core.components.entites.photo.AlbumEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {

    PhotoEntity getByAlbumEntityAndTitle(AlbumEntity albumEntity,String title);

    ArrayList<PhotoEntity> getByAlbumEntity(AlbumEntity albumEntity);
}
