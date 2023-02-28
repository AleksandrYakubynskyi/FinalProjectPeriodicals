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
                        <li data-filter="*"><a href="profile.jsp"><fmt:message
                                key="profile.main.link"/></a></li>
                        <li data-filter=".des"><a class="active" href="controller?action=subscriptions"><fmt:message
                                key="profile.main.link2"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<c:if test="${errorSubscription == true}">
    <div style="color: red;text-align: center"><fmt:message key="subscription.errorSubscription" /></div>
</c:if>
<div class="products" style="padding-top: 0">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="filters-content">
                    <div class="row grid">
                        <c:forEach items="${listOfSubscriptions}" var="subscription">
                            <div class="col-lg-4 col-md-4 all des">
                                <div class="product-item">
                                    <a href="#"><img src="assets/images/product_photo.jpg" alt=""></a>
                                    <div class="down-content">
                                        <a href="#"><h4>${subscription.topic}</h4></a>
                                        <p style="color: #f33f3f; font-size: 16px; font-weight: 500">${subscription.name}</p>
                                        <h6>${subscription.price} грн</h6>
                                        <p>${subscription.content}</p>
                                        <ul class="stars">
                                            <li><i class="fa fa-star"></i></li>
                                            <li><i class="fa fa-star"></i></li>
                                            <li><i class="fa fa-star"></i></li>
                                            <li><i class="fa fa-star"></i></li>
                                            <li><i class="fa fa-star"></i></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <ul class="pages">
                    <c:if test="${currentPage != 1}">
                        <li><a
                                href="${pageContext.request.contextPath}/controller?action=subscriptions&${queryString}&page=${currentPage - 1}"><i
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
                                    <a href="${pageContext.request.contextPath}/controller?action=subscriptions&${queryString}&page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt numberOfPages}">
                        <li><a
                                href="${pageContext.request.contextPath}/controller?action=subscriptions&${queryString}&page=${currentPage + 1}"><i
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
