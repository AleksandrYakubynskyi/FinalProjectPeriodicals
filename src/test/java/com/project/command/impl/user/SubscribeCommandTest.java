package com.project.command.impl.user;

import com.project.constant.AttributeNameConstant;
import com.project.context.ApplicationContext;
import com.project.entity.Publication;
import com.project.entity.User;
import com.project.security.SecurityService;
import com.project.service.PublicationService;
import com.project.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscribeCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private UserService userService;
    @Mock
    private PublicationService publicationService;
    @Mock
    private SecurityService securityService;
    @Mock
    private ApplicationContext applicationContext;
    private SubscribeCommand subscribeCommand;
    private User user;
    private Publication publication;

    @Before
    public void setUp() {
        user = new User();
        user.setBalance(BigDecimal.TEN);

        publication = new Publication();
        publication.setPrice(BigDecimal.ONE);

        when(applicationContext.getPublicationService()).thenReturn(publicationService);
        when(applicationContext.getUserService()).thenReturn(userService);
        when(publicationService.getPublicationById(any())).thenReturn(Optional.of(publication));
        when(securityService.checkAccess(any(), any())).thenReturn(true);
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        subscribeCommand = new SubscribeCommand(applicationContext, securityService);
    }

    @Test
    public void shouldSetPublicationForUser() {
        subscribeCommand.execute(request, response);

        verify(publicationService).setPublicationForUser(user, publication);
    }

    @Test
    public void shouldUpdateBalanceForUser() {
        BigDecimal userBalance = user.getBalance();

        subscribeCommand.execute(request, response);

        Assert.assertEquals(userBalance.subtract(publication.getPrice()), user.getBalance());
    }

    @Test
    public void shouldSetErrorMessageWhenUserHasNoEnoughMoney() {
        user.setBalance(BigDecimal.ZERO);

        subscribeCommand.execute(request, response);

        verify(request).setAttribute("errorBalance", true);
    }

    @Test
    public void shouldReturnErrorPageWhenUserHasNoAccess() {
        when(securityService.checkAccess(any(), any())).thenReturn(false);

        String actual = subscribeCommand.execute(request, response);

        Assert.assertEquals("error/403.jsp", actual);
    }

    @Test
    public void shouldReturnCorrectPage() {
        String actual = subscribeCommand.execute(request, response);

        Assert.assertEquals("controller?action=publications", actual);
    }
}