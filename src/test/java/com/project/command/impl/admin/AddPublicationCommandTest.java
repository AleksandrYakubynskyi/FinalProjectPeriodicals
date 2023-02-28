package com.project.command.impl.admin;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddPublicationCommandTest {
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
    private AddPublicationCommand addPublicationCommand;

    @Before
    public void setUp() {
        when(request.getParameter(AttributeNameConstant.TOPIC_ATTRIBUTE)).thenReturn("SPORT");
        when(request.getParameter(AttributeNameConstant.NAME_ATTRIBUTE)).thenReturn("Name");
        when(request.getParameter(AttributeNameConstant.PRICE_ATTRIBUTE)).thenReturn("50");
        when(request.getParameter(AttributeNameConstant.CONTENT_ATTRIBUTE)).thenReturn("Content");
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(applicationContext.getPublicationService()).thenReturn(publicationService);

        addPublicationCommand = new AddPublicationCommand(applicationContext, securityService);
    }

    @Test
    public void shouldReturnCorrectUrlWhenNoErrors() throws ServletException, IOException {
        doNothing().when(publicationService).addPublication(any(Publication.class));

        String actual = addPublicationCommand.execute(request, response);

        Assert.assertEquals("controller?action=publications", actual);
    }

    @Test
    public void shouldInvokeSaveMethodInService() throws ServletException, IOException {
        addPublicationCommand.execute(request, response);

        verify(publicationService, atLeast(1)).addPublication(any(Publication.class));
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() throws ServletException, IOException {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = addPublicationCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }
}