package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.security.SecurityService;
import com.project.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserCommandTest {
    @Mock
    private UserService userService;
    @Mock
    private SecurityService securityService;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ApplicationContext applicationContext;
    private UpdateUserCommand updateUserCommand;

    @Before
    public void setUp() {
        when(request.getParameter(AttributeNameConstant.ID_ATTRIBUTE)).thenReturn("id");
        when(request.getParameter(AttributeNameConstant.FIRSTNAME_ATTRIBUTE)).thenReturn("firstName");
        when(request.getParameter(AttributeNameConstant.LASTNAME_ATTRIBUTE)).thenReturn("lastName");
        when(request.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE)).thenReturn("email");
        when(request.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE)).thenReturn("password");
        when(request.getParameter(AttributeNameConstant.GENDER_ATTRIBUTE)).thenReturn("MALE");
        when(request.getParameter(AttributeNameConstant.BALANCE_ATTRIBUTE)).thenReturn("50");
        when(request.getParameter(AttributeNameConstant.ROLE_ATTRIBUTE)).thenReturn("USER");

        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(applicationContext.getUserService()).thenReturn(userService);
        when(request.getSession()).thenReturn(session);
        updateUserCommand = new UpdateUserCommand(applicationContext, securityService);
    }

    @Test
    public void shouldUpdateUser() {
        updateUserCommand.execute(request, response);

        verify(userService).updateUser(any());
    }

    @Test
    public void shouldUpdateUserInSession() {
        updateUserCommand.execute(request, response);

        verify(session).setAttribute(any(), any());
    }

    @Test
    public void shouldReturnCorrectUrl() {
        String actual = updateUserCommand.execute(request, response);

        assertEquals("profile.jsp", actual);
    }

    @Test
    public void shouldReturnErrorUrlWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = updateUserCommand.execute(request, response);

        assertEquals("error/403.jsp", actual);
    }

}