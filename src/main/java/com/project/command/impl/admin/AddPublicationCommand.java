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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class AddPublicationCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(AddPublicationCommand.class);
    private final PublicationService publicationService;
    private final SecurityService securityService;

    public AddPublicationCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.publicationService = applicationContext.getPublicationService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!securityService.checkAccess(req, Role.ADMIN)) {
            return "error/403.jsp";
        }
        LOGGER.info("Publication adding  start");
        String redirect = "addPublication.jsp";
        String topic = req.getParameter(AttributeNameConstant.TOPIC_ATTRIBUTE);
        String name = req.getParameter(AttributeNameConstant.NAME_ATTRIBUTE);
        String price = req.getParameter(AttributeNameConstant.PRICE_ATTRIBUTE);
        String content = req.getParameter(AttributeNameConstant.CONTENT_ATTRIBUTE);

        if (!validateParameters(topic, name, price, content)) {
            req.setAttribute("errorEmptyField", true);
            LOGGER.error("One or more fields are empty");
            return redirect;
        }

        publicationService.addPublication(new Publication
                (Topic.valueOf(topic), name, BigDecimal.valueOf(Long.parseLong(price)), content));
        LOGGER.info("Publication added successfully");
        redirect = "controller?action=publications";
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