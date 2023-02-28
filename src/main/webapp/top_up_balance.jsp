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
<div class="products">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="filters">
                    <ul>
                        <li class="active" data-filter="*"><a class="active" href="profile.jsp"><fmt:message
                                key="profile.main.link"/></a></li>
                        <li data-filter=".des"><a href="controller?action=subscriptions"><fmt:message
                                key="profile.main.link2"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="middle-top-balance ">
    <h2><fmt:message
            key="profile.main.title.top_up"/></h2>
    <c:if test="${errorTopUp == true}">
        <div class="error" style="width: 100%"><fmt:message
                key="top_up.errorTopUp"/></div>
    </c:if>
    <div class="information-content">
        <div class="rows">
            <label class="label_balance"><fmt:message
                    key="profile.main.label.top_up"/> :</label>
            <div class="balance">
                <form action="controller?action=updateBalance" method="post">
                    <input class="input_balance" type="number" name="balance">
                    <input style="padding:3px 10px" class="filled-button" type="submit" value="<fmt:message
                        key="profile.main.button_balance"/>">
                </form>
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