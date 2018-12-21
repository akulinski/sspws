package com.akulinski.sspws.core.components.repositories.user;

import com.akulinski.sspws.core.components.entites.user.AuthorityEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Integer> {
    List<AuthorityEntity> findAllByUserEntity(UserEntity userEntity);
}
