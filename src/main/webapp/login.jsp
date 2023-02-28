<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resource"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Live Journal - Login page</title>
    <link rel="stylesheet" href="assets/css/registration.css">
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-sixteen.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
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

<div class="main__form">
    <a href="index.jsp" class="form__area"></a>
    <div class="body__form">
        <div class="form__content">
            <a class="close__form" href="index.jsp"><img src="assets/images/icon-cross.png"></a>
            <form action="controller?action=login" method="post" >
                <label  class="title_form"><fmt:message
                        key="form.login.title"/></label>
                <c:if test="${errorMail == true}">
                <div class="error"><fmt:message
                        key="form.login.errorMail"/></div>
                </c:if>
                <c:if test="${errorPassword == true}">
                    <div class="error"><fmt:message
                            key="form.login.errorPassword"/></div>
                </c:if>
                <c:if test="${errorLogin == true}">
                    <div class="error"><fmt:message
                            key="form.login.errorLogin"/></div>
                </c:if>
                <div class="email">
                    <input value="${email}" required placeholder="<fmt:message
                        key="form.login.input.email"/>" type="email" name="email">
                </div>
                <div class="password">
                    <input value="${password}" required placeholder="<fmt:message
                        key="form.login.input.password"/>" type="password" name="password" minlength="8">
                </div>
                <div class="confirm">
                    <input id="confirm" required type="checkbox" name="confirm">
                    <label for="confirm" class="confirm_form"> <fmt:message
                            key="form.login.check_box.text"/> <a href="##"><fmt:message
                            key="form.login.check_box.link"/></a></label>
                </div>
                <div class="submit">
                    <input style="text-transform: uppercase" type="submit" value="<fmt:message
                        key="form.login.button"/>">
                </div>
            </form>
        </div>
    </div>
</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="assets/js/form.js"></script>
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