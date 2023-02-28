package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveSubscribeCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(RemoveSubscribeCommand.class);
    private final PublicationService publicationService;
    private final SecurityService securityService;

    public RemoveSubscribeCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.publicationService = applicationContext.getPublicationService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!securityService.checkAccess(req, Role.USER)) {
            return "error/403.jsp";
        }
        LOGGER.info("Subscription removing start");
        String redirect = "controller?action=publications";
        String publicationId = req.getParameter(AttributeNameConstant.PUBLICATION_ID_ATTRIBUTE);
        String userId = req.getParameter(AttributeNameConstant.USER_ID_ATTRIBUTE);
        publicationService.removePublicationForUser(publicationId, userId);
        LOGGER.info("Subscription is removed");
        return redirect;
    }
}