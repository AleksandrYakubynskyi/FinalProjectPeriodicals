package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePasswordCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(UpdatePasswordCommand.class);
    private final UserService userService;
    private final SecurityService securityService;

    public UpdatePasswordCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.userService = applicationContext.getUserService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.USER)) {
            return "error/403.jsp";
        }
        LOGGER.info("Update password started");
        String redirect = "updatePassword.jsp";

        User user = (User) req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);

        String password = req.getParameter(AttributeNameConstant.CURRENT_PASSWORD);
        String newPassword = req.getParameter(AttributeNameConstant.NEW_PASSWORD);
        String confirmPassword = req.getParameter(AttributeNameConstant.CONFIRM_PASSWORD);

        if (!validateParameters(password, newPassword, confirmPassword)) {
            req.setAttribute("errorEmptyField", true);
            LOGGER.error("One or more fields are empty");
            return redirect;
        }

        if (!StringUtils.equals(password, user.getPassword())) {
            req.setAttribute("currentPasswordError", true);
            LOGGER.error("User entered wrong password");
            return redirect;
        }

        if (!StringUtils.equals(newPassword, confirmPassword)) {
            req.setAttribute("errorPassword", true);
            LOGGER.error("Passwords do not match");
            return redirect;
        }
        user.setPassword(newPassword);
        userService.updateUser(user);
        req.getSession().setAttribute(AttributeNameConstant.USER_ATTRIBUTE, user);
        redirect = "profile.jsp";
        LOGGER.info("Password successfully updated");
        return redirect;

    }

    private boolean validateParameters(String... strings) {
        for (String string : strings) {
            if (string.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}