package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoveSubscribeCommandTest {
    @Mock
    private SecurityService securityService;
    @Mock
    private PublicationService publicationService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ApplicationContext applicationContext;
    private RemoveSubscribeCommand removeSubscribeCommand;

    @Before
    public void setUp() {
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(applicationContext.getPublicationService()).thenReturn(publicationService);
        when(request.getParameter(AttributeNameConstant.PUBLICATION_ID_ATTRIBUTE)).thenReturn("id");
        when(request.getParameter(AttributeNameConstant.USER_ID_ATTRIBUTE)).thenReturn("id");

        removeSubscribeCommand = new RemoveSubscribeCommand(applicationContext, securityService);
    }

    @Test
    public void shouldRedirectToErrorPageWhenUserHasNoAccess() throws ServletException, IOException {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = removeSubscribeCommand.execute(request, response);

        assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldInvokeRemovePublicationMethod() throws ServletException, IOException {
        removeSubscribeCommand.execute(request, response);

        verify(publicationService).removePublicationForUser(any(), any());
    }

}