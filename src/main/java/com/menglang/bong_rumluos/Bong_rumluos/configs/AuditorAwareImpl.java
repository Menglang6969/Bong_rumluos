package com.menglang.bong_rumluos.Bong_rumluos.configs;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated())
//            return Optional.empty();
//        return Optional.of(authentication.getName());
      return Optional.of("Anonymous");

    }
}