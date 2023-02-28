package com.project.command.impl.common;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class LoginCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private final UserService userService;

    public LoginCommand(ApplicationContext applicationContext) {
        this.userService = applicationContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (ObjectUtils.isNotEmpty(req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE))) {
            return "index.jsp";
        }
        LOGGER.info("Login start");
        String redirect = "login.jsp";
        String email = req.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE);
        String password = req.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE);

        Optional<User> user = userService.getUserByEmail(email);

        if (!user.isPresent()) {
            req.setAttribute("errorMail", true);
            req.setAttribute("email", email);
            LOGGER.error("User with this email address " + email + " does not exist");
            return redirect;
        }
        if (user.get().getRole().equals(Role.BAN)) {
            req.setAttribute("errorLogin", true);
            LOGGER.error(email + " User is blocked");
            return redirect;
        }
        if (!Objects.equals(password, user.get().getPassword())) {
            req.setAttribute("errorPassword", true);
            req.setAttribute("email", email);
            LOGGER.error(email + " User password is wrong");
            return redirect;
        }
        redirect = "/";
        HttpSession session = req.getSession();
        session.setAttribute(AttributeNameConstant.USER_ATTRIBUTE, user.get());
        LOGGER.info("User successfully logged in");
        return redirect;
    }
}
