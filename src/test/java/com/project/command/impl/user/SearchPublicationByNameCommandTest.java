package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
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
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SearchPublicationByNameCommandTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private PublicationService publicationService;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private HttpSession session;
    private SearchPublicationByNameCommand searchPublicationByNameCommand;

    @Before
    public void setUp() {
        when(request.getParameter(AttributeNameConstant.NAME_ATTRIBUTE)).thenReturn("name");
        when(applicationContext.getPublicationService()).thenReturn(publicationService);
        when(publicationService.searchPublicationByName("name")).thenReturn(Optional.of(new Publication()));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(null);

        searchPublicationByNameCommand = new SearchPublicationByNameCommand(applicationContext);
    }

    @Test
    public void shouldSetErrorAttributeIfPublicationIsAbsent() throws ServletException, IOException {
        when(publicationService.searchPublicationByName("name")).thenReturn(Optional.empty());

        searchPublicationByNameCommand.execute(request, response);

        verify(request).setAttribute("errorSearch", true);
    }

    @Test
    public void shouldSetAttributeWithPublication() throws ServletException, IOException {
        searchPublicationByNameCommand.execute(request, response);

        verify(request).setAttribute(any(), any());
    }

    @Test
    public void shouldReturnCorrectJsp() throws ServletException, IOException {
        String actual = searchPublicationByNameCommand.execute(request, response);

        Assert.assertEquals("publications.jsp", actual);
    }
}