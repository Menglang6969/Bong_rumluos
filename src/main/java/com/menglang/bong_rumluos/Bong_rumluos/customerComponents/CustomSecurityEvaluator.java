package com.menglang.bong_rumluos.Bong_rumluos.customerComponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("auth")
public class CustomSecurityEvaluator {
    private static final Logger log = LoggerFactory.getLogger(CustomSecurityEvaluator.class);

    private final CheckAdminAndSuperAdmin adminAndSuperAdmin;

    public CustomSecurityEvaluator(CheckAdminAndSuperAdmin adminAndSuperAdmin) {
        this.adminAndSuperAdmin = adminAndSuperAdmin;
    }

    public boolean byPassAdminOrSuperAdminPermission(Authentication authentication, String permission,String prefix) {
        boolean isAdminOrSuper = adminAndSuperAdmin.isAdminOrSuperAdmin(authentication);
        if (isAdminOrSuper) return true;
        return validatePermission(authentication,permission,prefix);
    }

    public boolean byPassSuperAdminPermission(Authentication authentication, String permission,String prefix) {
        boolean isSuperAdmin = adminAndSuperAdmin.isSuperAdmin(authentication);
        if (isSuperAdmin) return true;
        return validatePermission(authentication,permission,prefix);
    }

    public boolean validatePermission(Authentication authentication, String permission,String prefix){
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authName -> authName.equals(prefix + permission));
    }

}
