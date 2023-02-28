package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import com.project.util.ParamHolder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ViewSubscriptionsCommand implements Command {
    private final PublicationService publicationService;
    private final SecurityService securityService;
    private final ParamHolder paramHolder;

    public ViewSubscriptionsCommand(ApplicationContext applicationContext, SecurityService securityService, ParamHolder paramHolder) {
        this.publicationService = applicationContext.getPublicationService();
        this.securityService = securityService;
        this.paramHolder = paramHolder;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!securityService.checkAccess(req, Role.USER)) {
            return "error/403.jsp";
        }

        String redirect = "/subscriptions.jsp";

        User user = (User) req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);
        Map<String, String[]> paramMap = req.getParameterMap();

        paramHolder.setUpParametersFromRequest(req);

        String queryStr = StringUtils.isEmpty(req.getQueryString()) ? StringUtils.EMPTY :
                req.getQueryString().replaceFirst(AttributeNameConstant.QUERY_REGEX, StringUtils.EMPTY);
        req.setAttribute("listOfSubscriptions", (publicationService.getPublicationForUserWithParams(user.getId(), paramMap)));
        req.setAttribute(AttributeNameConstant.NUMBER_OF_PAGES_ATTRIBUTE, paramHolder.getNumberOfPages(publicationService.getNumberOfPublicationForUser(user)));
        req.setAttribute(AttributeNameConstant.QUERY_STRING, queryStr);

        return redirect;
    }
}