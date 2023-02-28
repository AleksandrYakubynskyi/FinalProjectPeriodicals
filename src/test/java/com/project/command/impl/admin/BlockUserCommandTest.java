package com.project.command.impl.admin;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockUserCommandTest {
    @Mock
    private SecurityService securityService;
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ApplicationContext applicationContext;
    private User user;
    private BlockUserCommand blockUserCommand;

    @Before
    public void setUp() {
        user = new User();

        when(request.getParameter(AttributeNameConstant.ID_ATTRIBUTE)).thenReturn("id");
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(userService.getUserById(any())).thenReturn(Optional.of(user));
        when(applicationContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).updateUser(user);

        blockUserCommand = new BlockUserCommand(applicationContext, securityService);
    }

    @Test
    public void shouldReturnCorrectUrlWhenNoErrors() throws ServletException, IOException {
        String actual = blockUserCommand.execute(request, response);

        Assert.assertEquals("controller?action=users", actual);
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() throws ServletException, IOException {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = blockUserCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldInvokeUpdateMethod() throws ServletException, IOException {
        blockUserCommand.execute(request, response);

        verify(userService).updateUser(user);
    }

    @Test
    public void shouldSetBannedRoleForUser() throws ServletException, IOException {
        blockUserCommand.execute(request, response);

        assertEquals(Role.BAN, user.getRole());
    }
}