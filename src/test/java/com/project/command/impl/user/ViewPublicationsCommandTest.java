package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
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
public class ViewPublicationsCommandTest {
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
    private ParamHolder paramHolder;
    private ViewPublicationsCommand viewPublicationsCommand;

    @Before
    public void setUp() {
        doNothing().when(paramHolder).setUpParametersFromRequest(request);

        when(applicationContext.getPublicationService()).thenReturn(publicationService);

        when(request.getParameterMap()).thenReturn(new HashMap<>());
        when(request.getQueryString()).thenReturn("");
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(null);

        when(publicationService.getAllPublications(any())).thenReturn(new ArrayList<>());
        when(publicationService.getNumberOfPublication(any())).thenReturn(5);

        when(paramHolder.getNumberOfPages(anyInt())).thenReturn(1);

        viewPublicationsCommand = new ViewPublicationsCommand(applicationContext, paramHolder);
    }

    @Test
    public void shouldSetAttributesToRequest() {
        viewPublicationsCommand.execute(request, response);

        verify(request, atLeast(3)).setAttribute(any(), any());
    }

    @Test
    public void shouldReturnCorrectUrl() {
        String actual = viewPublicationsCommand.execute(request, response);

        assertEquals("publications.jsp", actual);
    }
}