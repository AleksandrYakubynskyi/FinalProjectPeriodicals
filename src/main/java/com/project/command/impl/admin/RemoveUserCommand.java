package com.project.command.impl.admin;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveUserCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(RemoveUserCommand.class);
    private final UserService userService;
    private final SecurityService securityService;

    public RemoveUserCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.userService = applicationContext.getUserService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.ADMIN)) {
            return "error/403.jsp";
        }
        LOGGER.info("User removing start");
        String redirect = "controller?action=users";
        String id = req.getParameter(AttributeNameConstant.ID_ATTRIBUTE);
        userService.removeUser(id);
        LOGGER.info("User is removed");
        return redirect;
    }
}
