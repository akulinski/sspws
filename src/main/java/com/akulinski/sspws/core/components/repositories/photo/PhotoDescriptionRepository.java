package com.akulinski.sspws.core.components.repositories.photo;

import com.akulinski.sspws.core.components.entites.photo.PhotoDescriptionEntity;
import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoDescriptionRepository extends CrudRepository<PhotoDescriptionEntity,Integer> {
    PhotoDescriptionEntity getByPhoto(PhotoEntity photoEntity);
}
