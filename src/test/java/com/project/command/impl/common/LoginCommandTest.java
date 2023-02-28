package com.project.command.impl.common;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.entity.enums.Role;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private ApplicationContext applicationContext;
    private User user;
    private LoginCommand loginCommand;

    @Before
    public void setUp() {
        user = new User();
        user.setEmail("email@gmail.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(request.getSession()).thenReturn(session);
        when(userService.getUserByEmail("email@gmail.com")).thenReturn(Optional.ofNullable(user));
        when(request.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE)).thenReturn("email@gmail.com");
        when(request.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE)).thenReturn("password");
        when(applicationContext.getUserService()).thenReturn(userService);

        loginCommand = new LoginCommand(applicationContext);
    }

    @Test
    public void shouldReturnCorrectUrlWhenUserIsPresent() {
        String actual = loginCommand.execute(request, response);

        Assert.assertEquals("/", actual);
    }

    @Test
    public void shouldReturnCorrectUrlWhenUserEmailIsWrong() {
        when(request.getParameter(AttributeNameConstant.EMAIL_ATTRIBUTE)).thenReturn("email");

        String actual = loginCommand.execute(request, response);

        Assert.assertEquals("login.jsp", actual);
    }

    @Test
    public void shouldReturnCorrectUrlWhenUserPasswordIsInvalid() {
        when(request.getParameter(AttributeNameConstant.PASSWORD_ATTRIBUTE)).thenReturn("pass");

        String actual = loginCommand.execute(request, response);

        Assert.assertEquals("login.jsp", actual);
    }

    @Test
    public void shouldReturnCorrectUrlWhenUserIsBanned() {
        user.setRole(Role.BAN);

        String actual = loginCommand.execute(request, response);

        Assert.assertEquals("login.jsp", actual);
    }

    @Test
    public void shouldReturnCorrectUrlWhenUserIsLoggedIn() {
        when(request.getSession().getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);

        String actual = loginCommand.execute(request, response);

        Assert.assertEquals("index.jsp", actual);
    }
}