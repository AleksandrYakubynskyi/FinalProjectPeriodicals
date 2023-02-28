package com.project.command.impl.common;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String redirect = "/";
        if (session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE) != null) {
            String locale = (String) session.getAttribute(AttributeNameConstant.LANG_ATTRIBUTE);
            session.invalidate();
            LOGGER.info("User logged out");
            req.getSession().setAttribute(AttributeNameConstant.LANG_ATTRIBUTE, locale);
        }
        return redirect;
    }
}