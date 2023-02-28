package com.project.command.impl.admin;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.entity.enums.Role;
import com.project.entity.enums.Topic;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class UpdatePublicationCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(UnlockUserCommand.class);
    private final PublicationService publicationService;
    private final SecurityService securityService;

    public UpdatePublicationCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.publicationService = applicationContext.getPublicationService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.ADMIN)) {
            return "error/403.jsp";
        }
        LOGGER.info("Publication updating start");
        String redirect = "controller?action=publications";
        String id = req.getParameter(AttributeNameConstant.ID_ATTRIBUTE);
        String topic = req.getParameter(AttributeNameConstant.TOPIC_ATTRIBUTE);
        String name = req.getParameter(AttributeNameConstant.NAME_ATTRIBUTE);
        String price = req.getParameter(AttributeNameConstant.PRICE_ATTRIBUTE);
        String content = req.getParameter(AttributeNameConstant.CONTENT_ATTRIBUTE);
        publicationService.updatePublication(new Publication
                (id, Topic.valueOf(topic), name, BigDecimal.valueOf(Long.parseLong(price)), content));
        LOGGER.info("Publication successfully updated");
        return redirect;
    }
}
