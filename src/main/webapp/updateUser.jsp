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

<div class="products">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="filters">
                    <ul>
                        <li class="active" data-filter="*"><a class="active" href="profile.jsp"><fmt:message
                                key="profile.main.link"/></a></li>
                        <li data-filter=".des"><a href="/subscriptions"><fmt:message
                                key="profile.main.link2"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="middle-top ">
    <h2><fmt:message
            key="profile.main.title"/></h2>
    <form method="post" action="controller?action=updateUser">
        <div class="error" style="width: 100%">${errorMessage}</div>
        <div class="information-content">
            <input type="hidden" name="id" value="${user.id}">
            <input type="hidden" name="password" value="${user.password}">
            <input type="hidden" name="balance" value="${user.balance}" >
            <input type="hidden" name="role" value="${user.role}" >
            <div class="rows">
                <label class="label"><fmt:message
                        key="profile.main.input.firstname"/>:</label>
                <div class="grid-wrap">
                    <input type="text" autofocus name="firstname" value="${user.firstname}"
                           placeholder="<fmt:message
                    key="profile.main.input.firstname"/>">
                </div>
            </div>
            <div class="rows">
                <label class="label"><fmt:message
                        key="profile.main.input.lastname"/>:</label>
                <div class="grid-wrap">
                    <input type="text" name="lastname" value="${user.lastname}"
                           placeholder="<fmt:message
                    key="profile.main.input.lastname"/>">
                </div>
            </div>
            <div class="rows">
                <label class="label"><fmt:message
                        key="profile.main.input.email"/>:</label>
                <div class="grid-wrap">
                    <input type="text" name="email" value="${user.email}"
                           placeholder="<fmt:message
                    key="profile.main.input.email"/>">
                </div>
            </div>
            <div class="rows">
                <label class="label"><fmt:message
                        key="profile.main.input.gender"/>:</label>
                <div class="grid-wrap">
                    <select required name="gender">
                        <option class="input" name="" value="" selected disabled>${user.gender}</option>
                        <option class="input" name="gender" value="MALE">MALE</option>
                        <option class="input" name="gender" value="FEMALE">FEMALE</option>
                    </select>
                </div>
            </div>
        </div>
        <input type="submit" value="<fmt:message
                        key="profile.main.button_save"/>" class="filled-button">
    </form>
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
