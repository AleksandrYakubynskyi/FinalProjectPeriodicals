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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePublicationCommandTest {
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
    private UpdatePublicationCommand updatePublicationCommand;

    @Before
    public void setUp() {
        when(request.getParameter(AttributeNameConstant.ID_ATTRIBUTE)).thenReturn("id");
        when(request.getParameter(AttributeNameConstant.TOPIC_ATTRIBUTE)).thenReturn("SPORT");
        when(request.getParameter(AttributeNameConstant.NAME_ATTRIBUTE)).thenReturn("Name");
        when(request.getParameter(AttributeNameConstant.PRICE_ATTRIBUTE)).thenReturn("50");
        when(request.getParameter(AttributeNameConstant.CONTENT_ATTRIBUTE)).thenReturn("Content");
        when(applicationContext.getPublicationService()).thenReturn(publicationService);

        updatePublicationCommand = new UpdatePublicationCommand(applicationContext, securityService);
    }

    @Test
    public void shouldReturnCorrectUrlWhenNoErrors() {
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        doNothing().when(publicationService).updatePublication(any(Publication.class));

        String actual = updatePublicationCommand.execute(request, response);

        Assert.assertEquals("controller?action=publications", actual);
    }

    @Test
    public void shouldInvokeUpdateMethodInService() {
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        updatePublicationCommand.execute(request, response);

        verify(publicationService, atLeast(1)).updatePublication(any(Publication.class));
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = updatePublicationCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }
}