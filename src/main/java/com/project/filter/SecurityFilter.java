package com.project.filter;

import com.project.constant.AttributeNameConstant;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.Constraint;
import com.project.security.XmlParser;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    private ArrayList<Constraint> securityConstraints;

    @Override
    public void init(FilterConfig config) {
        securityConstraints = XmlParser.parse();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        User user = (User) httpRequest.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);
        String url = httpRequest.getRequestURI();


        if (isUserHasPermissionToUrl(user, url)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (url.equalsIgnoreCase("/login.jsp") || url.equalsIgnoreCase("/registration.jsp")) {
                httpResponse.sendRedirect("/profile.jsp");
                return;
            }
            httpResponse.sendRedirect("/error/403.jsp");
        }
    }

    private boolean isUserHasPermissionToUrl(User user, String url) {
        Constraint constraint = getConstrain(url);

        if (ObjectUtils.isNotEmpty(constraint)) {
            if (ObjectUtils.isEmpty(user)) {
                return constraint.hasRole(Role.ANONYMOUS);
            } else {
                return constraint.hasRole(user.getRole());
            }
        }
        return true;
    }

    private Constraint getConstrain(String url) {
        return securityConstraints.stream()
                .filter(constraint -> constraint.isThisUrl(url))
                .findFirst()
                .orElse(null);
    }
}
