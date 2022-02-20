package ru.javaprojects.energybalancecontrolshared.testdata;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaprojects.energybalancecontrolshared.web.json.JsonUtil;

import static ru.javaprojects.energybalancecontrolshared.testdata.UserTestData.user;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("ROLE_ADMIN")
public class UserRestController {

    @GetMapping("/{id}")
    public String get(@PathVariable long id) {
        return JsonUtil.writeValue(user);
    }
}