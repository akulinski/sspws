package com.akulinski.sspws.core.components.services;

import com.akulinski.sspws.config.securityconfig.UserLoginInformationProvider;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.akulinski.sspws.core.components.repositories.user.AuthorityRepository;
import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;

@Service
public class MyUserDetailsService implements UserDetailsService, Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        HashSet<GrantedAuthority> hashSet = new HashSet<>();

        //AuthorityEntity cannot implement GrantedAuthority (serialization exception)
        authorityRepository.findAllByUserEntity(user).forEach(authorityEntity -> hashSet.add((GrantedAuthority) authorityEntity::getType));

        return new UserLoginInformationProvider(user, hashSet);
    }
}