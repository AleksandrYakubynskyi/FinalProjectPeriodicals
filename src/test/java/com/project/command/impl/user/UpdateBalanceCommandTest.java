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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateBalanceCommandTest {
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
    private UpdateBalanceCommand updateBalanceCommand;
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setBalance(BigDecimal.ZERO);

        when(request.getParameter(AttributeNameConstant.BALANCE_ATTRIBUTE)).thenReturn("50");
        when(applicationContext.getUserService()).thenReturn(userService);
        when(request.getSession()).thenReturn(session);
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);

        updateBalanceCommand = new UpdateBalanceCommand(applicationContext, securityService);
    }

    @Test
    public void shouldUpdateUserBalance() throws ServletException, IOException {
        BigDecimal balanceBeforeUpdate = user.getBalance();

        updateBalanceCommand.execute(request, response);

        assertEquals(balanceBeforeUpdate.add(new BigDecimal(50)), user.getBalance());
        verify(userService).updateUser(user);
    }

    @Test
    public void shouldNotUpdateWhenTheBalanceValueIsIncorrect() throws ServletException, IOException {
        BigDecimal balanceBeforeUpdate = user.getBalance();
        when(request.getParameter(AttributeNameConstant.BALANCE_ATTRIBUTE)).thenReturn("-50");

        updateBalanceCommand.execute(request, response);

        assertEquals(balanceBeforeUpdate, user.getBalance());
        verify(userService, never()).updateUser(user);
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() throws ServletException, IOException {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = updateBalanceCommand.execute(request, response);

        assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldUpdateUserAttributeInSession() throws ServletException, IOException {
        updateBalanceCommand.execute(request, response);

        verify(session).setAttribute(AttributeNameConstant.USER_ATTRIBUTE, user);
    }

    @Test
    public void shouldReturnCorrectUrl() throws ServletException, IOException {
        String actual = updateBalanceCommand.execute(request, response);

        assertEquals("profile.jsp", actual);
    }
}