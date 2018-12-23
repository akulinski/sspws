package com.akulinski.sspws.core.components.repositories.photo;

import com.akulinski.sspws.core.components.entites.photo.PhotoEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {
}
