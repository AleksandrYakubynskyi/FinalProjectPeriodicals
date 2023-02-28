package com.project.command.impl.common;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    @Mock
    private UserService userService;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private User user;
    private RegistrationCommand registrationCommand;

    @Before
    public void setUp() {
        user = new User();
        user.setPassword("password");

        when(req.getParameter(AttributeNameConstant.FIRSTNAME_ATTRIBUTE)).thenReturn("firstName");
        when(req.getParameter(AttributeNameConstant.LASTNAME_ATTRIBUTE)).thenReturn("lastName");
        when(req.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE)).thenReturn("email");
        when(req.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE)).thenReturn("password");
        when(req.getParameter(AttributeNameConstant.CONFIRM_PASSWORD)).thenReturn("password");
        when(req.getParameter(AttributeNameConstant.GENDER_ATTRIBUTE)).thenReturn("MALE");

        when(userService.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(applicationContext.getUserService()).thenReturn(userService);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(null);

        registrationCommand = new RegistrationCommand(applicationContext);
    }

    @Test
    public void shouldInvokeSaveMethod() {
        when(userService.getUserByEmail("email")).thenReturn(Optional.empty());

        registrationCommand.execute(req, response);

        verify(userService).addUser(any());
    }

    @Test
    public void shouldReturnToRegistrationJspWhenUserWithThisEmailIsAlreadyExist() {
        String actual = registrationCommand.execute(req, response);

        Assert.assertEquals("/registration.jsp", actual);
    }

    @Test
    public void shouldReturnToIndexJspWhenUserAlreadyLoggedIn() {
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);

        String actual = registrationCommand.execute(req, response);

        Assert.assertEquals("index.jsp", actual);
    }

    @Test
    public void shouldReturnToRegistrationJspWhenPasswordsAreDifferent() {
        when(userService.getUserByEmail("email")).thenReturn(Optional.empty());
        when(req.getParameter(AttributeNameConstant.CONFIRM_PASSWORD)).thenReturn("password1");

        String actual = registrationCommand.execute(req, response);

        Assert.assertEquals("/registration.jsp", actual);
    }

    @Test
    public void shouldReturnToRegistrationJspWhenOneOfFieldsIsEmpty() {
        when(userService.getUserByEmail("email")).thenReturn(Optional.empty());
        when(req.getParameter(AttributeNameConstant.CONFIRM_PASSWORD)).thenReturn("");

        String actual = registrationCommand.execute(req, response);

        Assert.assertEquals("/registration.jsp", actual);
    }
}