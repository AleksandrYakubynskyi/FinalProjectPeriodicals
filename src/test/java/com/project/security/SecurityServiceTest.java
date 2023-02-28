package com.project.security;

import com.project.constant.AttributeNameConstant;
import com.project.entity.User;
import com.project.entity.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    private User user;

    @Before
    public void setUp() {
        user = new User();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(AttributeNameConstant.USER_ATTRIBUTE)).thenReturn(user);
    }


    @Test
    public void shouldReturnTrueWhenRolesAreEquals() {
        user.setRole(Role.BAN);

        boolean actual = new SecurityService().checkAccess(request, Role.BAN);

        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalseWhenRolesAreDifferent() {
        user.setRole(Role.BAN);

        boolean actual = new SecurityService().checkAccess(request, Role.USER);

        assertFalse(actual);
    }
}