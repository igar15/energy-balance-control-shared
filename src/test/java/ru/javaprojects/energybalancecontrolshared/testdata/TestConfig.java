package ru.javaprojects.energybalancecontrolshared.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.javaprojects.energybalancecontrolshared.web.json.JacksonObjectMapper;
import ru.javaprojects.energybalancecontrolshared.web.security.JwtAuthorizationFilter;
import ru.javaprojects.energybalancecontrolshared.web.security.JwtProvider;
import ru.javaprojects.energybalancecontrolshared.web.security.RestAccessDeniedHandler;
import ru.javaprojects.energybalancecontrolshared.web.security.RestAuthenticationEntryPoint;

@Configuration
public class TestConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(environment);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(environment, jwtProvider());
    }

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public UserRestController userRestController() {
        return new UserRestController();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonObjectMapper.getMapper();
    }
}