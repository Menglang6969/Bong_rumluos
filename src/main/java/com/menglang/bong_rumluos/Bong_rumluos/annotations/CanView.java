package com.menglang.bong_rumluos.Bong_rumluos.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("@auth.hasPermissionWithPrefix(authentication,'VIEW_')")
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
public @interface CanView {
}
