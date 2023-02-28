package com.project.command;

import com.project.command.impl.admin.*;
import com.project.command.impl.common.LoginCommand;
import com.project.command.impl.common.LogoutCommand;
import com.project.command.impl.common.RegistrationCommand;
import com.project.command.impl.user.*;
import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.security.SecurityService;
import com.project.util.ParamHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory commandFactory = new CommandFactory();
    private static final Map<String, Command> commands = new HashMap<>();
    private static final ApplicationContext APPLICATION_CONTEXT = ApplicationContext.getContext();
    private static final ParamHolder PARAM_HOLDER = new ParamHolder();
    private static final SecurityService SECURITY_SERVICE = new SecurityService();

    private CommandFactory() {
    }

    public static CommandFactory commandFactory() {
        if (commandFactory == null) {
            commandFactory = new CommandFactory();
        }
        return commandFactory;
    }

    static {
        commands.put("login", new LoginCommand(APPLICATION_CONTEXT));
        commands.put("registration", new RegistrationCommand(APPLICATION_CONTEXT));
        commands.put("logout", new LogoutCommand());
        commands.put("users", new ViewUsersCommand(APPLICATION_CONTEXT, PARAM_HOLDER, SECURITY_SERVICE));
        commands.put("updateUser", new UpdateUserCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("removeUser", new RemoveUserCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("publications", new ViewPublicationsCommand(APPLICATION_CONTEXT, PARAM_HOLDER));
        commands.put("subscribe", new SubscribeCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("subscriptions", new ViewSubscriptionsCommand(APPLICATION_CONTEXT, SECURITY_SERVICE, PARAM_HOLDER));
        commands.put("updatePublication", new UpdatePublicationCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("editPublication", new EditPublicationCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("removePublication", new RemovePublicationCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("unSubscribe", new RemoveSubscribeCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("addPublication", new AddPublicationCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("searchPublication", new SearchPublicationByNameCommand(APPLICATION_CONTEXT));
        commands.put("updateBalance", new UpdateBalanceCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("updatePassword", new UpdatePasswordCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("blockUser", new BlockUserCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
        commands.put("unlockUser", new UnlockUserCommand(APPLICATION_CONTEXT, SECURITY_SERVICE));
    }

    public Command getCommand(HttpServletRequest req) {
        String action = req.getParameter(AttributeNameConstant.ACTION_ATTRIBUTE);
        return commands.get(action);
    }
}
