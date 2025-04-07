package com.menglang.bong_rumluos.Bong_rumluos.customerComponents;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userAuth")
public class CheckPermissionUser {
    private final CustomSecurityEvaluator serviceEvaluator;

    public CheckPermissionUser(CheckAdminAndSuperAdmin checkAdminAndSuperAdmin, CustomSecurityEvaluator customSecurityEvaluator) {
        this.serviceEvaluator = customSecurityEvaluator;

    }

    public boolean canCreateWithPermission(Authentication authentication, String permission){
        return serviceEvaluator.byPassAdminOrSuperAdminPermission(authentication,permission,"CREATE_");
    }
    public boolean canEditWithPermission(Authentication authentication, String permission){
        return serviceEvaluator.byPassAdminOrSuperAdminPermission(authentication,permission,"UPDATE_");
    }
    public boolean canViewWithPermission(Authentication authentication, String permission){
        return serviceEvaluator.byPassAdminOrSuperAdminPermission(authentication,permission,"VIEW_");
    }
    public boolean canDeleteWithPermission(Authentication authentication, String permission){
        return serviceEvaluator.byPassAdminOrSuperAdminPermission(authentication,permission,"DELETE_");
    }

}
