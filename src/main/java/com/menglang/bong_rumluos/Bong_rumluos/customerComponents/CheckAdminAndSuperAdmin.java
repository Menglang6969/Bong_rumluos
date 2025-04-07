package com.menglang.bong_rumluos.Bong_rumluos.customerComponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("bypass")
public class CheckAdminAndSuperAdmin {
    private static final Logger log = LoggerFactory.getLogger(CustomSecurityEvaluator.class);

    public boolean isAdminOrSuperAdmin(Authentication auth) {
        var authorities = auth.getAuthorities();
        boolean admin = authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_ADMIN"));
        boolean superAdmin = authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_SUPER_ADMIN"));
        return admin || superAdmin;
    }

    public boolean isAdmin(Authentication auth) {
        var authorities = auth.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_ADMIN"));

    }
    public boolean isSuperAdmin(Authentication auth) {
        var authorities = auth.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_SUPER_ADMIN"));

    }

}
