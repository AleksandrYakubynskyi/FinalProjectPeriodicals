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
    <link rel="stylesheet" href="assets/css/registration.css">
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-sixteen.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <title>Live Journal - Update Publication Page</title>

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

<jsp:include page="header.jsp"/>

<div class="middle-top publication ">
    <h2><fmt:message key="updatePublication.main.title"/></h2>
    <form method="post" action="controller?action=updatePublication">
        <div class="information-content">
            <input type="hidden" name="id" value="${publication.id}">
            <div class="rows">
                <label class="label"><fmt:message key="addPublication.main.label_topic"/>:</label>
                <div class="grid-wrap">
                    <select class="input" required name="topic">
                        <option class="input" name="" value="" selected disabled>${publication.topic}</option>
                        <option class="input" name="topic" value="POLITICS">POLITICS</option>
                        <option class="input" name="topic" value="ECONOMY">ECONOMY</option>
                        <option class="input" name="topic" value="SPORT">SPORT</option>
                        <option class="input" name="topic" value="SCIENCE">SCIENCE</option>
                        <option class="input" name="topic" value="WEATHER">WEATHER</option>
                    </select>
                </div>
            </div>
            <div class="rows">
                <label class="label"><fmt:message key="addPublication.main.label_name"/>:</label>
                <div class="grid-wrap">
                    <input type="text" name="name" value="${publication.name}" placeholder="<fmt:message key="addPublication.main.label_name"/>">
                </div>
            </div>
            <div class="rows">
                <label class="label"><fmt:message key="addPublication.main.label_price"/>:</label>
                <div class="grid-wrap">
                    <input type="number" name="price" value="${publication.price}"
                           placeholder="<fmt:message key="addPublication.main.label_price"/>">
                </div>
            </div>
            <div class="rows">
                <label class="label"><fmt:message key="addPublication.main.label_description"/>:</label>
                <div class="grid-wrap">
                    <textarea class="textarea" placeholder="<fmt:message key="addPublication.main.label_description"/>" required
                              name="content">${publication.content}</textarea>
                </div>
            </div>
        </div>
        <input type="submit" value="<fmt:message key="updatePublication.main.button_save"/>" class="filled-button">
    </form>
</div>
<footer>
    <div class="inner-content">
        <p style="margin-bottom: 0">Copyright &copy; 2023 Live Journal</p>
    </div>
</footer>
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>
<script src="assets/js/slick.js"></script>
<script src="assets/js/isotope.js"></script>
<script src="assets/js/accordions.js"></script>
<script src="assets/js/form.js"></script>

</body>

</html>


