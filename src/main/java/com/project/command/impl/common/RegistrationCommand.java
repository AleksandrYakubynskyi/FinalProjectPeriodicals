package com.project.command.impl.common;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Gender;
import com.project.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);
    private final UserService userService;

    public RegistrationCommand(ApplicationContext applicationContext) {
        this.userService = applicationContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (ObjectUtils.isNotEmpty(req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE))) {
            return "index.jsp";
        }
        LOGGER.info("User registration start");
        String redirect = "/registration.jsp";
        String firstname = req.getParameter(AttributeNameConstant.FIRSTNAME_ATTRIBUTE);
        String lastname = req.getParameter(AttributeNameConstant.LASTNAME_ATTRIBUTE);
        String email = req.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE);
        String password = req.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE);
        String confirmPassword = req.getParameter(AttributeNameConstant.CONFIRM_PASSWORD);
        String gender = req.getParameter(AttributeNameConstant.GENDER_ATTRIBUTE);

        Optional<User> user = (userService.getUserByEmail(email));

        if (user.isPresent()) {
            req.setAttribute("errorEmail", true);
            req.setAttribute("firstname", firstname);
            req.setAttribute("lastname", lastname);
            req.setAttribute("email", email);
            LOGGER.error("User with this email address " + email + " is exist");
            return redirect;
        }
        if (!validateParameters(firstname, lastname, email, password, gender)) {
            req.setAttribute("errorEmptyField", true);
            req.setAttribute("firstname", firstname);
            req.setAttribute("lastname", lastname);
            req.setAttribute("email", email);
            LOGGER.error("One or more fields are empty");
            return redirect;
        }
        if (!StringUtils.equals(password, confirmPassword)) {
            req.setAttribute("errorPassword", true);
            req.setAttribute("firstname", firstname);
            req.setAttribute("lastname", lastname);
            req.setAttribute("email", email);
            LOGGER.error("Passwords do not match");
            return redirect;
        }
        redirect = "/";
        userService.addUser(new User(firstname, lastname, email, password, Gender.valueOf(gender)));
        LOGGER.info("User successfully registered");
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