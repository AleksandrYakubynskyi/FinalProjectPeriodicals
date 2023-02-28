<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="resource"/>

<header class="header">
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="index.jsp"><h2 style="margin-top: 16px">Live <em>Journal</em></h2></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item ">
                        <a class="nav-link" href="index.jsp"><fmt:message
                                key="header.link.home"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=publications"><fmt:message
                                key="header.link.periodicals"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="about.jsp"><fmt:message
                                key="header.link.about"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contact.jsp"><fmt:message
                                key="header.link.contact"/></a>
                    </li>
                    <c:choose>
                        <c:when test="${sessionScope.user!=null}">
                            <li class="nav-item">
                                <a class="nav-link" href="##">
                                    <img class="icon_exit" src="assets/images/icon-user.png" alt="">
                                        ${user.firstname} ${user.lastname} &#x25be
                                </a>
                                <ul>
                                    <c:if test="${sessionScope.user.role=='USER'}">
                                        <li>
                                            <a href="profile.jsp"><fmt:message
                                                    key="header.link.profile"/></a>
                                        </li>
                                        <li>
                                            <a href="controller?action=subscriptions"><fmt:message
                                                    key="profile.main.link2"/></a>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.user.role=='ADMIN'}">
                                        <li>
                                            <a href="controller?action=users"><fmt:message
                                                    key="header.link.users"/></a>
                                        </li>
                                        <li>
                                            <a href="controller?action=publications&filter=all"><fmt:message
                                                    key="header.link.periodicals"/></a>
                                        </li>
                                    </c:if>
                                    <li>
                                        <a href="controller?action=logout"><fmt:message
                                                key="header.link.logout"/></a>
                                    </li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="login.jsp"><fmt:message
                                        key="header.link.login"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="registration.jsp"><fmt:message
                                        key="header.link.registration"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <li class="language">
                        <form method="post">
                            <select class="language_select" name="lang" onchange='submit();'>
                                <option value="en" ${sessionScope.lang eq 'en' ? 'selected' : ''}>
                                    <fmt:message key="header.lang_en"/>
                                </option>
                                <option value="ua" ${sessionScope.lang eq 'ua' ? 'selected' : ''}>
                                    <fmt:message key="header.lang_ua"/>
                                </option>
                            </select>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
