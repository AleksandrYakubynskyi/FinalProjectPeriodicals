package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import com.project.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SubscribeCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(SubscribeCommand.class);
    private final PublicationService publicationService;
    private final UserService userService;
    private final SecurityService securityService;

    public SubscribeCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.publicationService = applicationContext.getPublicationService();
        this.userService = applicationContext.getUserService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.USER)) {
            return "error/403.jsp";
        }

        String redirect = "controller?action=publications";

        User user = (User) req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);
        String publicationId = req.getParameter(AttributeNameConstant.ID_ATTRIBUTE);
        Optional<Publication> publicationOptional = publicationService.getPublicationById(publicationId);
        Publication publication = publicationOptional.orElseGet(Publication::new);

        if (user.getBalance().compareTo(publication.getPrice()) < NumberUtils.INTEGER_ZERO) {
            req.setAttribute("errorBalance", true);
            LOGGER.error("There is not enough money on the account to subscribe to the publication");
            return "profile.jsp";
        }
        user.setBalance(user.getBalance().subtract(publication.getPrice()));
        publicationService.setPublicationForUser(user, publication);
        userService.updateUser(user);
        LOGGER.info("User successfully subscribed to the publication");
        return redirect;
    }
}