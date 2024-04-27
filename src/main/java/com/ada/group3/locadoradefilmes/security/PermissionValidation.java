package com.ada.group3.locadoradefilmes.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.function.BiFunction;

public class PermissionValidation {
    private PermissionValidation(){}

    public static BiFunction<Authentication, String, Boolean> validatePermission = (auth, username) ->
            !auth.getName().equals(username) && auth.getAuthorities().stream().noneMatch(it -> it.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));

}


