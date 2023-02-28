package com.project.security;

import com.project.entity.enums.Role;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Objects;

public class Constraint {
    private String urlPattern;
    private final ArrayList<Role> roles = new ArrayList<>();

    public Constraint() {

    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public boolean isThisUrl(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        int starIndex = urlPattern.indexOf('*');
        if (starIndex > NumberUtils.INTEGER_ZERO) {
            return url.startsWith(urlPattern.substring(NumberUtils.INTEGER_ZERO, starIndex));
        } else {
            return url.equals(urlPattern);
        }
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "urlPattern='" + urlPattern + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlPattern);
    }
}
