package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.entity.User;
import com.project.service.PublicationService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SearchPublicationByNameCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(SearchPublicationByNameCommand.class);
    private final PublicationService publicationService;

    public SearchPublicationByNameCommand(ApplicationContext applicationContext) {
        this.publicationService = applicationContext.getPublicationService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOGGER.info("Star searching for a publication by name");
        String redirect = "publications.jsp";
        String name = req.getParameter(AttributeNameConstant.NAME_ATTRIBUTE);

        Optional<Publication> publication = publicationService.searchPublicationByName(name);

        if (!publication.isPresent()) {
            req.setAttribute("errorSearch", true);
            LOGGER.error("Publication with this name " + "(" + name + ")" + " was not found ");
            return redirect;
        }

        Map<Publication, Boolean> publicationMap = getMapOfPublicationWithIsSubscribedFlag(publication.get(), req);

        req.setAttribute("mapOfPublication", publicationMap);
        LOGGER.info("Publication successfully found");
        return redirect;
    }

    private Map<Publication, Boolean> getMapOfPublicationWithIsSubscribedFlag(Publication publication, HttpServletRequest request) {
        Map<Publication, Boolean> publicationMap = new LinkedHashMap<>();
        User user = (User) request.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);

        if (ObjectUtils.isEmpty(user)) {
            publicationMap.put(publication, false);
        } else {
            List<Publication> userPublications = publicationService.getPublicationForUser(user.getId());

            if (userPublications.contains(publication)) {
                publicationMap.put(publication, true);
            } else {
                publicationMap.put(publication, false);
            }
        }
        return publicationMap;
    }
}