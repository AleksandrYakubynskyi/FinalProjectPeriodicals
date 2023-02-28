<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resource"/>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">

    <title>Live Journal - Periodicals Page </title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/registration.css">
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-sixteen.css">
    <link rel="stylesheet" href="assets/css/owl.css">

</head>

<body>

<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="jumper">
        <div></div>
        <div></div>
        <div></div>
    </div>
</div>
<!-- ***** Preloader End ***** -->

<!-- Header -->
<jsp:include page="header.jsp"/>

<!-- Page Content -->
<div class="products" style="padding-top: 115px">
    <div class="container">
        <div class="row" style="height: 100vh">
            <div class="mobile-table">
                <table class="table">
                    <h2 style="text-align: center; margin-bottom: 40px;"><fmt:message
                            key="header.link.users"/></h2>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th><fmt:message
                                key="profile.main.input.firstname"/></th>
                        <th><fmt:message
                                key="profile.main.input.lastname"/></th>
                        <th><fmt:message
                                key="profile.main.input.email"/></th>
                        <th><fmt:message
                                key="profile.main.input.gender"/></th>
                        <th><fmt:message
                                key="profile.main.input.role"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${listUsers}" var="user">
                    <tbody>
                    <tr>
                        <c:if test="${user.role=='USER'||user.role == 'BAN'}">
                            <td>${user.id}</td>
                            <td>${user.firstname}</td>
                            <td>${user.lastname}</td>
                            <td>${user.email}</td>
                            <td>${user.gender}</td>
                            <td>${user.role}</td>
                            <form method="post" action="controller?action=removeUser">
                                <input type="hidden" name="id" value="${user.id}">
                                <td><input class="btn_user" type="submit" value="<fmt:message
                                    key="profile.main.button_delete"/>"></td>
                            </form>
                            <c:if test="${user.role=='USER'}">
                                <form method="post" action="controller?action=blockUser">
                                    <input type="hidden" name="id" value="${user.id}">
                                    <td><input class="btn_user" type="submit" value="<fmt:message
                                    key="profile.main.button_block"/>"></td>
                                </form>
                            </c:if>
                        </c:if>
                        <c:if test="${user.role=='BAN'}">
                            <form method="post" action="controller?action=unlockUser">
                                <input type="hidden" name="id" value="${user.id}">
                                <td><input style="background-color: black" class="btn_user" type="submit" value="<fmt:message
                                    key="profile.main.button_unlock"/>"></td>
                            </form>
                        </c:if>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-md-12">
                <ul class="pages">
                    <c:if test="${currentPage != 1}">
                        <li><a
                                href="${pageContext.request.contextPath}/controller?action=users&${queryString}&page=${currentPage - 1}"><i
                                class="fa fa-angle-double-left"></i></a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="active"><a href="">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${pageContext.request.contextPath}/controller?action=users&${queryString}&page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt numberOfPages}">
                        <li><a
                                href="${pageContext.request.contextPath}/controller?action=users&${queryString}&page=${currentPage + 1}"><i
                                class="fa fa-angle-double-right"></i></a>
                        </li>
                    </c:if>
                </ul>
            </div>

        </div>
    </div>
</div>
<footer>
    <div class="inner-content">
        <p>Copyright &copy; 2023 Live Journal</p>
    </div>
</footer>


<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>
<script src="assets/js/slick.js"></script>
<script src="assets/js/isotope.js"></script>
<script src="assets/js/accordions.js"></script>

</body>


</html>