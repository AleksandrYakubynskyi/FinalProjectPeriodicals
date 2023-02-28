package com.project.filter;

import com.project.constant.AttributeNameConstant;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String locale = request.getParameter(AttributeNameConstant.LANG_ATTRIBUTE);
        if (StringUtils.isEmpty(locale)) {
            locale = AttributeNameConstant.DEFAULT_LOCALE;
            if (ObjectUtils.isNotEmpty(session.getAttribute(AttributeNameConstant.LANG_ATTRIBUTE))) {
                locale = (String) session.getAttribute(AttributeNameConstant.LANG_ATTRIBUTE);
            }
        }
        session.setAttribute(AttributeNameConstant.LANG_ATTRIBUTE, locale);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}