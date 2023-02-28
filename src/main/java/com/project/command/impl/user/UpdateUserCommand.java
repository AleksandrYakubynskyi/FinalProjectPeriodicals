package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Gender;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class UpdateUserCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(UpdateUserCommand.class);
    private final UserService userService;
    private final SecurityService securityService;

    public UpdateUserCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.userService = applicationContext.getUserService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.USER)) {
            return "error/403.jsp";
        }
        LOGGER.info("Update user started");
        String redirect = "profile.jsp";
        String id = req.getParameter(AttributeNameConstant.ID_ATTRIBUTE);
        String firstname = req.getParameter(AttributeNameConstant.FIRSTNAME_ATTRIBUTE);
        String lastname = req.getParameter(AttributeNameConstant.LASTNAME_ATTRIBUTE);
        String email = req.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE);
        String password = req.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE);
        String gender = req.getParameter(AttributeNameConstant.GENDER_ATTRIBUTE);
        String balance = req.getParameter(AttributeNameConstant.BALANCE_ATTRIBUTE);
        String role = req.getParameter(AttributeNameConstant.ROLE_ATTRIBUTE);

        User user = new User(id, firstname, lastname, email, password, Gender.valueOf(gender),
                Role.valueOf(role), BigDecimal.valueOf(Long.parseLong(balance)));
        userService.updateUser(user);
        req.getSession().setAttribute(AttributeNameConstant.USER_ATTRIBUTE, user);
        LOGGER.info("User successfully updated");

        return redirect;
    }
}