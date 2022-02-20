package ru.javaprojects.energybalancecontrolshared.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaprojects.energybalancecontrolshared.testdata.TestConfig;
import ru.javaprojects.energybalancecontrolshared.testdata.UserTestData;
import ru.javaprojects.energybalancecontrolshared.testdata.WebSecurity;
import ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaprojects.energybalancecontrolshared.testdata.UserTestData.*;
import static ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType.ACCESS_DENIED_ERROR;
import static ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType.BAD_TOKEN_ERROR;
import static ru.javaprojects.energybalancecontrolshared.web.security.JwtProvider.AUTHORITIES;
import static ru.javaprojects.energybalancecontrolshared.web.security.SecurityConstants.ACCESS_DENIED;
import static ru.javaprojects.energybalancecontrolshared.web.security.SecurityConstants.BAD_TOKEN;

@SpringBootTest(classes = {TestConfig.class, WebSecurity.class})
@AutoConfigureMockMvc
class JwtAuthorizationFilterTest {
    private static final String REST_URL = "/api/users/";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private Environment environment;

    private HttpHeaders userJwtHeader;
    private HttpHeaders adminJwtHeader;
    private HttpHeaders adminJwtExpiredHeader;
    private HttpHeaders adminJwtInvalidHeader;

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    public ResultMatcher detailMessage(String code) {
        return jsonPath("$.details").value(code);
    }

    @PostConstruct
    private void postConstruct() {
        String secretKey = environment.getProperty("jwt.secretKey");
        userJwtHeader = getHeaders(jwtProvider.generateAuthorizationToken(USER_ID_STRING, USER_ROLE));
        adminJwtHeader = getHeaders(jwtProvider.generateAuthorizationToken(ADMIN_ID_STRING, USER_ROLE, ADMIN_ROLE));
        adminJwtExpiredHeader = getHeaders(generateCustomAuthorizationToken(USER_DISABLED_ID_STRING,
                (new Date(System.currentTimeMillis() - 10000)), secretKey, USER_ROLE));
        adminJwtInvalidHeader = getHeaders(generateCustomAuthorizationToken(ADMIN_ID_STRING,
                (new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("authorization.token.expiration-time")))),
                UUID.randomUUID().toString(), USER_ROLE, ADMIN_ROLE));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + USER_ID)
                .headers(adminJwtHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + USER_ID)
                .headers(userJwtHeader))
                .andExpect(status().isForbidden())
                .andExpect(errorType(ACCESS_DENIED_ERROR))
                .andExpect(detailMessage(ACCESS_DENIED));
    }

    @Test
    void getTokenExpired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .headers(adminJwtExpiredHeader))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(BAD_TOKEN_ERROR))
                .andExpect(detailMessage(BAD_TOKEN));
    }

    @Test
    void getTokenInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .headers(adminJwtInvalidHeader))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(BAD_TOKEN_ERROR))
                .andExpect(detailMessage(BAD_TOKEN));
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, environment.getProperty("authorization.token.header.prefix") + token);
        return httpHeaders;
    }

    private String generateCustomAuthorizationToken(String userId, Date expirationDate, String secretKey, String ... authorities) {
        return JWT.create()
                .withIssuer(environment.getProperty("authorization.token.issuer"))
                .withAudience(environment.getProperty("authorization.token.audience"))
                .withIssuedAt(new Date())
                .withSubject(userId)
                .withArrayClaim(AUTHORITIES, authorities)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(secretKey));
    }

}