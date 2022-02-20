package ru.javaprojects.energybalancecontrolshared.testdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String email;
    private Integer weight;
    private Integer growth;
    private Integer age;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean enabled = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String name, String email, Integer weight, Integer growth, Integer age, String password,
                boolean enabled, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.weight = weight;
        this.growth = growth;
        this.age = age;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}