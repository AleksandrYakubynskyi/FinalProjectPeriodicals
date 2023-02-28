package com.project.command.impl.common;

import com.project.constant.AttributeNameConstant;
import com.project.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private LogoutCommand logoutCommand;

    @Before
    public void setUp() {
        logoutCommand = new LogoutCommand();
        User user = new User();

        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldInvokeInvalidateMethod() {
        doNothing().when(session).setAttribute(any(), any());
        logoutCommand.execute(request, response);

        verify(session).invalidate();
    }

    @Test
    public void shouldReturnCorrectUrl() {
        String actual = new LogoutCommand().execute(request, response);

        Assert.assertEquals("/", actual);
    }
}