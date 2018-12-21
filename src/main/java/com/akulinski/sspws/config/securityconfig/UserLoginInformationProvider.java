package com.akulinski.sspws.config.securityconfig;

import com.akulinski.sspws.core.components.entites.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserLoginInformationProvider implements UserDetails {

    private UserEntity user;

    private Set<GrantedAuthority> authorities;

    public UserLoginInformationProvider(UserEntity user, Set<GrantedAuthority> userAuthorities) {

        this.user = user;
        this.authorities = userAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
