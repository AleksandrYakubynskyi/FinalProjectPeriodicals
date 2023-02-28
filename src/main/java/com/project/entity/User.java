package com.project.entity;

import com.project.entity.enums.Gender;
import com.project.entity.enums.Role;

import java.math.BigDecimal;

public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Gender gender;
    private Role role;

    private BigDecimal balance;

    public User() {
    }

    public User(String id, String firstname, String lastname, String email, String password, Gender gender, Role role, BigDecimal balance) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.balance = balance;
    }

    public User(String firstname, String lastname, String email, String password, Gender gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", gender=" + gender + ", role=" + role + '}';
    }
}