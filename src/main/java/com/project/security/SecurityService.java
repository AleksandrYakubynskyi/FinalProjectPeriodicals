package com.project.security;

import com.project.constant.AttributeNameConstant;
import com.project.entity.User;
import com.project.entity.enums.Role;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SecurityService {
    public boolean checkAccess(HttpServletRequest req, Role role) {
        User user = (User) req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);

        return !ObjectUtils.isEmpty(user) && Objects.equals(user.getRole(), role);
    }
}
