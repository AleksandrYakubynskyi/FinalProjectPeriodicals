package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.entity.User;
import com.project.service.PublicationService;
import com.project.util.ParamHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewPublicationsCommand implements Command {
    private final PublicationService publicationService;
    private final ParamHolder paramHolder;

    public ViewPublicationsCommand(ApplicationContext applicationContext, ParamHolder paramHolder) {
        this.publicationService = applicationContext.getPublicationService();
        this.paramHolder = paramHolder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse resp) {
        paramHolder.setUpParametersFromRequest(request);
        Map<String, String[]> paramMap = request.getParameterMap();

        String queryStr = StringUtils.isEmpty(request.getQueryString()) ? StringUtils.EMPTY :
                request.getQueryString().replaceFirst(AttributeNameConstant.QUERY_REGEX, StringUtils.EMPTY);

        request.setAttribute("mapOfPublication", addIsSubscribedFlagToPublication(publicationService.getAllPublications(paramMap), request));
        request.setAttribute(AttributeNameConstant.NUMBER_OF_PAGES_ATTRIBUTE, paramHolder.getNumberOfPages(publicationService.getNumberOfPublication(paramMap)));
        request.setAttribute(AttributeNameConstant.QUERY_STRING, queryStr);

        return "publications.jsp";
    }

    private Map<Publication, Boolean> addIsSubscribedFlagToPublication(List<Publication> publicationList, HttpServletRequest request) {
        Map<Publication, Boolean> publicationMap = new LinkedHashMap<>();
        User user = (User) request.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);

        if (ObjectUtils.isEmpty(user)) {
            publicationList.forEach(publication -> publicationMap.put(publication, false));
        } else {
            List<Publication> userPublications = publicationService.getPublicationForUser(user.getId());

            publicationList.forEach(publication -> {
                if (userPublications.contains(publication)) {
                    publicationMap.put(publication, true);
                } else {
                    publicationMap.put(publication, false);
                }
            });
        }
        return publicationMap;
    }
}