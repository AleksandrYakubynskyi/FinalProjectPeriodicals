package com.project.command.impl.admin;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UnlockUserCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(UnlockUserCommand.class);
    private final UserService userService;
    private final SecurityService securityService;

    public UnlockUserCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.userService = applicationContext.getUserService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!securityService.checkAccess(req, Role.ADMIN)) {
            return "error/403.jsp";
        }
        LOGGER.info("User unlocking start");
        String redirect = "controller?action=users";
        Optional<User> optionalUser = userService.getUserById(req.getParameter(AttributeNameConstant.ID_ATTRIBUTE));
        User user = optionalUser.get();
        user.setRole(Role.USER);
        userService.updateUser(user);
        LOGGER.info(user + " is unlocked");
        return redirect;
    }
}
