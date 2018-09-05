package com.kugelschlag.SpringPwdValidator.models;

import com.kugelschlag.SpringPwdValidator.validators.CharterPWValid;

import javax.validation.constraints.NotNull;
import java.util.Objects;


/**
 * A super simple user data object just to drive testing of password validation
 * The "@CharterPWValid" tag is a custom annotation that checks for certain string inputs - configurable
 * The "@NotNull" is part of the standard valdation package
 */
public class User {

    @NotNull
    private String name;

    @CharterPWValid
    private String pw;


    public User() {

    }

    public User(String name, String pw) {
        this.name = name;
        this.pw = pw;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(pw, user.pw);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, pw);
    }
}
