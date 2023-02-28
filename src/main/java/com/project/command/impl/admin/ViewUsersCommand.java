package com.project.command.impl.admin;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import com.project.util.ParamHolder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ViewUsersCommand implements Command {
    private final UserService userService;

    private final ParamHolder paramHolder;

    private final SecurityService securityService;

    public ViewUsersCommand(ApplicationContext applicationContext, ParamHolder paramHolder, SecurityService securityService) {
        this.userService = applicationContext.getUserService();
        this.paramHolder = paramHolder;
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse resp) {
        if (!securityService.checkAccess(request, Role.ADMIN)) {
            return "error/403.jsp";
        }
        paramHolder.setUpParametersFromRequest(request);
        Map<String, String[]> paramMap = request.getParameterMap();

        String queryStr = StringUtils.isEmpty(request.getQueryString()) ? StringUtils.EMPTY :
                request.getQueryString().replaceFirst(AttributeNameConstant.QUERY_REGEX, StringUtils.EMPTY);

        request.setAttribute("listUsers", (userService.getAllUsers(paramMap)));
        request.setAttribute(AttributeNameConstant.NUMBER_OF_PAGES_ATTRIBUTE, paramHolder.getNumberOfPages(userService.getNumberOfUser(paramMap)));
        request.setAttribute(AttributeNameConstant.QUERY_STRING, queryStr);

        return "adminProfile.jsp";
    }
}