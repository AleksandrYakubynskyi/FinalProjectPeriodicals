package com.project.command.impl.user;

import com.project.command.Command;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class UpdateBalanceCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(UpdateBalanceCommand.class);
    private final UserService userService;
    private final SecurityService securityService;

    public UpdateBalanceCommand(ApplicationContext applicationContext, SecurityService securityService) {
        this.userService = applicationContext.getUserService();
        this.securityService = securityService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!securityService.checkAccess(req, Role.USER)) {
            return "error/403.jsp";
        }
        LOGGER.info("Top up account started");
        String redirect = "top_up_balance.jsp";
        User user = (User) req.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE);
        String balance = req.getParameter(AttributeNameConstant.BALANCE_ATTRIBUTE);

        if (StringUtils.isEmpty(balance) || Integer.parseInt(balance) <= NumberUtils.INTEGER_ZERO) {
            req.setAttribute("errorTopUp", true);
            LOGGER.error("User entered invalid amount");
            return redirect;
        }

        user.setBalance(user.getBalance().add(BigDecimal.valueOf(Integer.parseInt(balance))));
        userService.updateUser(user);
        req.getSession().setAttribute(AttributeNameConstant.USER_ATTRIBUTE, user);
        LOGGER.info("Account successfully replenished");
        redirect = "profile.jsp";
        return redirect;
    }
}