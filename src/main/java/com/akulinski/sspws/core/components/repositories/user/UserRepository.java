package com.akulinski.sspws.core.components.repositories.user;

import com.akulinski.sspws.core.components.entites.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Integer> {

    ArrayList<UserEntity> findAll();

    UserEntity getById(Integer id);

    UserEntity getByUsername(String username);


}
