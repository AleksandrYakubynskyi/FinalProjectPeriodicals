package com.project.command.impl.admin;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemovePublicationCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(RemovePublicationCommand.class);
    private final PublicationService publicationService;
    private final SecurityService securityService;

    public RemovePublicationCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.publicationService = applicationContext.getPublicationService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.ADMIN)) {
            return "error/403.jsp";
        }
        LOGGER.info("Publication removing start");
        String redirect = "controller?action=publications";
        String id = req.getParameter(AttributeNameConstant.ID_ATTRIBUTE);
        publicationService.removePublication(id);
        LOGGER.info("Publication is removed");
        return redirect;
    }
}
