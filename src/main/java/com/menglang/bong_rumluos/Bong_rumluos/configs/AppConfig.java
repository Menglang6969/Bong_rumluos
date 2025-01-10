package com.menglang.bong_rumluos.Bong_rumluos.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableScheduling
@EnableJpaAuditing
@EnableAsync
public class AppConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // Set the number of threads in the pool
        scheduler.setThreadNamePrefix("IsolatedScheduler-");
        return scheduler;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //register Object mapper prevent it unknown some Java library like time
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper obj=new ObjectMapper();
        obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        obj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        obj.registerModule(new JavaTimeModule());
        obj.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return obj;
    }

}
