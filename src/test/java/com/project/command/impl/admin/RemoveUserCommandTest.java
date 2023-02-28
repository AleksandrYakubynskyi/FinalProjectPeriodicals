package com.project.command.impl.admin;

import com.project.context.ApplicationContext;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoveUserCommandTest {
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
    private RemoveUserCommand removeUserCommand;

    @Before
    public void setUp() {
        when(applicationContext.getUserService()).thenReturn(userService);
        when(securityService.checkAccess(any(), any())).thenReturn(true);

        removeUserCommand = new RemoveUserCommand(applicationContext, securityService);
    }

    @Test
    public void shouldReturnCorrectUrlWhenNoErrors() {
        String actual = removeUserCommand.execute(request, response);

        Assert.assertEquals("controller?action=users", actual);
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = removeUserCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldInvokeRemoveMethodInService() {
        removeUserCommand.execute(request, response);

        verify(userService, atLeast(1)).removeUser(any());
    }
}