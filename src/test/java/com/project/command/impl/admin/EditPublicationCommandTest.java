package com.project.command.impl.admin;

import com.project.context.ApplicationContext;
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
public class EditPublicationCommandTest {
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
    private EditPublicationCommand editPublicationCommand;

    @Before
    public void setUp() {
        when(applicationContext.getPublicationService()).thenReturn(publicationService);
        when(securityService.checkAccess(any(), any())).thenReturn(true);

        editPublicationCommand = new EditPublicationCommand(applicationContext, securityService);
    }

    @Test
    public void shouldReturnCorrectUrlWhenNoErrors() {
        String actual = editPublicationCommand.execute(request, response);

        Assert.assertEquals("updatePublication.jsp", actual);
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = editPublicationCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldInvokeGetMethodInService() {
        editPublicationCommand.execute(request, response);

        verify(publicationService, atLeast(1)).getPublicationById(any());
    }
}