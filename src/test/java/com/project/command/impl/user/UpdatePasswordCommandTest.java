package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
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
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePasswordCommandTest {
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
    private UpdatePasswordCommand updatePasswordCommand;
    private User user;

    private static final String NEW_PASSWORD = "newPassword";

    @Before
    public void setUp() {
        user = new User();
        user.setPassword("oldPassword");

        when(request.getParameter(AttributeNameConstant.CURRENT_PASSWORD)).thenReturn("oldPassword");
        when(request.getParameter(AttributeNameConstant.NEW_PASSWORD)).thenReturn(NEW_PASSWORD);
        when(request.getParameter(AttributeNameConstant.CONFIRM_PASSWORD)).thenReturn(NEW_PASSWORD);

        when(request.getSession()).thenReturn(session);
        when(applicationContext.getUserService()).thenReturn(userService);
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);
        when(securityService.checkAccess(any(), any())).thenReturn(true);

        updatePasswordCommand = new UpdatePasswordCommand(applicationContext, securityService);
    }

    @Test
    public void shouldUpdatePassword() {
        String passwordBeforeUpdate = user.getPassword();

        updatePasswordCommand.execute(request, response);

        assertEquals(NEW_PASSWORD, user.getPassword());
        assertNotEquals(passwordBeforeUpdate, user.getPassword());
        verify(userService).updateUser(user);
    }

    @Test
    public void shouldNotUpdatePasswordWhenOldPasswordIsIncorrect() {
        String passwordBeforeUpdate = user.getPassword();
        when(request.getParameter(AttributeNameConstant.CURRENT_PASSWORD)).thenReturn("oldPasswordIncorrect");

        updatePasswordCommand.execute(request, response);

        verify(userService, never()).updateUser(user);
        assertEquals(passwordBeforeUpdate, user.getPassword());
    }

    @Test
    public void shouldNotUpdatePasswordWhenNewPasswordsAreDifferent() {
        String passwordBeforeUpdate = user.getPassword();
        when(request.getParameter(AttributeNameConstant.CONFIRM_PASSWORD)).thenReturn("incorrect");

        updatePasswordCommand.execute(request, response);

        verify(userService, never()).updateUser(user);
        assertEquals(passwordBeforeUpdate, user.getPassword());
    }

}