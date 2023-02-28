package com.project.command.impl.admin;

import com.project.context.ApplicationContext;
import com.project.security.SecurityService;
import com.project.service.UserService;
import com.project.util.ParamHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewUsersCommandTest {
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
    @Mock
    private ParamHolder paramHolder;
    private ViewUsersCommand viewUsersCommand;

    @Before
    public void setUp() {
        when(applicationContext.getUserService()).thenReturn(userService);
        when(securityService.checkAccess(any(), any())).thenReturn(true);

        viewUsersCommand = new ViewUsersCommand(applicationContext, paramHolder, securityService);
    }

    @Test
    public void shouldReturnCorrectUrlWhenNoErrors() {
        String actual = viewUsersCommand.execute(request, response);

        Assert.assertEquals("adminProfile.jsp", actual);
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = viewUsersCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldInvokeGetMethodInService() {
        viewUsersCommand.execute(request, response);

        verify(userService, atLeast(1)).getAllUsers(any());
    }

    @Test
    public void shouldSetAttributeWithUsersToRequest() {
        viewUsersCommand.execute(request, response);

        verify(request).setAttribute("listUsers", new ArrayList<>());
    }
}