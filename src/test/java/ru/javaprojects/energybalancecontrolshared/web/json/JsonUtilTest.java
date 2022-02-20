package ru.javaprojects.energybalancecontrolshared.web.json;

import org.junit.jupiter.api.Test;
import ru.javaprojects.energybalancecontrolshared.testdata.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static ru.javaprojects.energybalancecontrolshared.testdata.UserTestData.*;

class JsonUtilTest {
    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(admin);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(List.of(admin, user));
        System.out.println(json);
        List<User> users = JsonUtil.readValues(json, User.class);
        USER_MATCHER.assertMatch(users, List.of(admin, user));
    }

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(user);
        System.out.println(json);
        assertThat(json, not(containsString(PASSWORD_PROPERTY_NAME)));

        String jsonWithPassword = JsonUtil.writeAdditionProps(user, PASSWORD_PROPERTY_NAME, NEW_PASSWORD);
        System.out.println(jsonWithPassword);
        User user = JsonUtil.readValue(jsonWithPassword, User.class);
        assertEquals(user.getPassword(), NEW_PASSWORD);
    }

    @Test
    void readContentFromPage() {
        List<User> users = JsonUtil.readContentFromPage(JSON_USER_PAGE, User.class);
        USER_MATCHER.assertMatch(users, userDisabled, user);
    }
}