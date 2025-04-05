package com.menglang.bong_rumluos.Bong_rumluos.customerComponents;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("auth")
public class CustomSecurityEvaluator {
    public boolean hasPermissionWithPrefix(Authentication auth, String prefix) {
        return auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .startsWith(prefix));
    }

    // Operation indicated to Function Like [Production,Category,File,...]
    public boolean hasAllPermissionInOperation(Authentication auth, String... operations) {
        return auth.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> {
                    String authority=grantedAuthority.getAuthority();
                    for(String value:operations){
                        if(authority.equals(value)) return true;
                    }
                    return false;
                });
    }
}
