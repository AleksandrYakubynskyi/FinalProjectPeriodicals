package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.User;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import com.project.util.ParamHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewSubscriptionsCommandTest {
    @Mock
    private PublicationService publicationService;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private SecurityService securityService;
    @Mock
    private ParamHolder paramHolder;

    private ViewSubscriptionsCommand viewSubscriptionsCommand;

    @Before
    public void setUp() {
        User user = new User();
        user.setId("id");

        when(applicationContext.getPublicationService()).thenReturn(publicationService);
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(publicationService.getPublicationForUserWithParams(any(), any())).thenReturn(new ArrayList<>());

        when(request.getSession()).thenReturn(session);
        when(request.getParameterMap()).thenReturn(new HashMap<>());
        when(request.getQueryString()).thenReturn("");

        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);

        doNothing().when(paramHolder).setUpParametersFromRequest(request);

        viewSubscriptionsCommand = new ViewSubscriptionsCommand(applicationContext, securityService, paramHolder);
    }

    @Test
    public void shouldSetAttributesToRequest() {
        viewSubscriptionsCommand.execute(request, response);

        verify(request, atLeast(3)).setAttribute(any(), any());
    }

    @Test
    public void shouldReturnCorrectUrl() {
        String actual = viewSubscriptionsCommand.execute(request, response);

        assertEquals("/subscriptions.jsp", actual);
    }

    @Test
    public void shouldReturnIncorrectUrlWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = viewSubscriptionsCommand.execute(request, response);

        assertEquals("error/403.jsp", actual);
    }
}