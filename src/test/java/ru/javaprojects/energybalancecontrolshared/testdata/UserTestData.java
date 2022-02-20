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
    public static final long NOT_FOUND = 10;
    public static final String NOT_FOUND_EMAIL = "notfound@test.com";

    public static final User user = new User(USER_ID, "John Smith", "user@gmail.com", 90, 185, 34, "password", true, Set.of(USER));
    public static final User admin = new User(ADMIN_ID, "Viktor Wran", "admin@gmail.com", 85, 178, 41, "admin", true, Set.of(USER, ADMIN));
    public static final User userDisabled = new User(USER_DISABLED_ID, "Jack London", "jack@gmail.com",  83, 174, 38, "password", false, Set.of(USER));


    public static final String NAME_KEYWORD = "wran";
    public static final String EMAIL_KEYWORD = "gmail.com";

    public static final String NEW_PASSWORD = "newPassword";

    public static final String LOGIN_ENDPOINT = "login";
    public static final String REGISTER_ENDPOINT = "register";
    public static final String CHANGE_PASSWORD_ENDPOINT = "password";
    public static final String PASSWORD_RESET_ENDPOINT = "password/reset";
    public static final String EMAIL_VERIFY_ENDPOINT = "email/verify";
    public static final String SEARCH_BY_KEYWORD_ENDPOINT = "by";

    public static final String PASSWORD_PROPERTY_NAME = "password";

    public static final String EMAIL_PARAM = "email";
    public static final String PASSWORD_PARAM = "password";
    public static final String PAGE_NUMBER_PARAM = "page";
    public static final String PAGE_SIZE_PARAM = "size";
    public static final String KEYWORD_PARAM = "keyword";

    public static final String JSON_USER_PAGE = "{\"content\":[{\"id\":100002,\"name\":\"Jack London\",\"email\":\"jack@gmail.com\",\"weight\":83,\"growth\":174,\"age\":38,\"enabled\":false,\"registered\":\"2022-02-17T17:28:15.116+00:00\",\"roles\":[\"USER\"]}," +
            "{\"id\":100000,\"name\":\"John Smith\",\"email\":\"user@gmail.com\",\"weight\":90,\"growth\":185,\"age\":34,\"enabled\":true,\"registered\":\"2022-02-17T17:28:15.116+00:00\",\"roles\":[\"USER\"]}]," +
            "\"pageable\":{\"page\":0,\"size\":2,\"sort\":{\"orders\":[]}},\"total\":3}";

    public static User getNew() {
        return new User(null, "new name", "new@test.com",  65, 170, 30, "newPassword", false, Set.of(USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "Updated name", "user@gmail.com",  95, 186, 35, "password", true, Set.of(USER));
    }

//    public static UserTo getUpdatedTo() {
//        return new UserTo(USER_ID, "Updated name", MAN, 95, 186, 35);
//    }
//
//    public static User getAdminUpdated() {
//        return new User(USER_ID, "Updated name", "updated@test.com", MAN, 95, 186, 35, "password", true, Set.of(USER, ADMIN));
//    }
//
//    public static AdminUserTo getAdminUpdatedTo() {
//        return new AdminUserTo(USER_ID, "Updated name", "updated@test.com", MAN, 95, 186, 35, Set.of(USER, ADMIN));
//    }
//
//    public static NewUserTo getNewUserTo() {
//        return new NewUserTo("new username", "newuser@test.com", WOMAN, 60, 175, 30, "newPass");
//    }

    public static User getNewForRegister() {
        return new User(null, "new username", "newuser@test.com",  60, 175, 30, "newPass", false, Set.of(USER));
    }
}