package ru.javaprojects.energybalancecontrolshared.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    protected ResultMatcher detailMessage(String code) {
        return jsonPath("$.details").value(code);
    }
}