package com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Lazy
public class AuthoritiesExtraction {

    public Set<SimpleGrantedAuthority> extractRoleGrantAuthorities(Set<Role> roles){
        Set<SimpleGrantedAuthority> authorities1 = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getName()))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorityList=roles.stream().flatMap(this::extractPermission).collect(Collectors.toSet());
        authorityList.addAll(authorities1);
        return authorityList;
    }

    public Stream<SimpleGrantedAuthority> extractPermission(Role role){
        return  role.getPermissions().stream().map(per->new SimpleGrantedAuthority(per.getName()));
    }
}
