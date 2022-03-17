package ru.javaprojects.energybalancecontrolshared.test;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaprojects.energybalancecontrolshared.test.TestData.*;
import static ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType.ACCESS_DENIED_ERROR;
import static ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType.UNAUTHORIZED_ERROR;
import static ru.javaprojects.energybalancecontrolshared.web.security.SecurityConstants.ACCESS_DENIED;
import static ru.javaprojects.energybalancecontrolshared.web.security.SecurityConstants.NOT_AUTHORIZED;

public abstract class ActuatorTest extends AbstractControllerTest {
    @Test
    @WithMockCustomUser(userId = ADMIN_ID_STRING, userRoles = {ADMIN_ROLE})
    void actuator() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ACTUATOR_PATH))
                .andExpect(status().isOk());
    }

    @Test
    void actuatorUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ACTUATOR_PATH))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(UNAUTHORIZED_ERROR))
                .andExpect(detailMessage(NOT_AUTHORIZED));
    }

    @Test
    @WithMockCustomUser(userId = USER_ID_STRING, userRoles = {USER_ROLE})
    void actuatorNotAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ACTUATOR_PATH))
                .andExpect(status().isForbidden())
                .andExpect(errorType(ACCESS_DENIED_ERROR))
                .andExpect(detailMessage(ACCESS_DENIED));
    }
}