package com.project.command.impl.admin;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.PublicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class EditPublicationCommand implements Command {
    private final PublicationService publicationService;
    private final SecurityService securityService;

    public EditPublicationCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.publicationService = applicationContext.getPublicationService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.ADMIN)) {
            return "error/403.jsp";
        }

        String redirect = "updatePublication.jsp";
        String id = req.getParameter(AttributeNameConstant.ID_ATTRIBUTE);
        Optional<Publication> publication = publicationService.getPublicationById(id);
        publication.ifPresent(value -> req.setAttribute("publication", value));
        return redirect;
    }
}
