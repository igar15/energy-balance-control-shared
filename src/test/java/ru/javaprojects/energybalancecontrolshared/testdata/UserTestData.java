package ru.javaprojects.energybalancecontrolshared.testdata;


import ru.javaprojects.energybalancecontrolshared.test.TestMatcher;

import java.util.Set;

import static ru.javaprojects.energybalancecontrolshared.testdata.Role.ADMIN;
import static ru.javaprojects.energybalancecontrolshared.testdata.Role.USER;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final long USER_ID = 100000;
    public static final long ADMIN_ID = 100001;
    public static final String USER_ID_STRING = "100000";
    public static final String ADMIN_ID_STRING = "100001";
    public static final String USER_DISABLED_ID_STRING = "100002";

    public static final long USER_DISABLED_ID = 100002;
    public static final String USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    public static final User user = new User(USER_ID, "John Smith", "user@gmail.com", 90, 185, 34, "password", true, Set.of(USER));
    public static final User admin = new User(ADMIN_ID, "Viktor Wran", "admin@gmail.com", 85, 178, 41, "admin", true, Set.of(USER, ADMIN));
    public static final User userDisabled = new User(USER_DISABLED_ID, "Jack London", "jack@gmail.com",  83, 174, 38, "password", false, Set.of(USER));

    public static final String NEW_PASSWORD = "newPassword";

    public static final String PASSWORD_PROPERTY_NAME = "password";

    public static final String JSON_USER_PAGE = "{\"content\":[{\"id\":100002,\"name\":\"Jack London\",\"email\":\"jack@gmail.com\",\"weight\":83,\"growth\":174,\"age\":38,\"enabled\":false,\"registered\":\"2022-02-17T17:28:15.116+00:00\",\"roles\":[\"USER\"]}," +
            "{\"id\":100000,\"name\":\"John Smith\",\"email\":\"user@gmail.com\",\"weight\":90,\"growth\":185,\"age\":34,\"enabled\":true,\"registered\":\"2022-02-17T17:28:15.116+00:00\",\"roles\":[\"USER\"]}]," +
            "\"pageable\":{\"page\":0,\"size\":2,\"sort\":{\"orders\":[]}},\"total\":3}";

}