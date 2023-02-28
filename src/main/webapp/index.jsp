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

    <title>Live Journal</title>

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

<div class="main_banner">
    <figure>
        <img src="assets/images/slide_01.jpg">
    </figure>
    <div class="text-content">
        <h4><fmt:message
                key="banner.main.title"/></h4>
        <h2><fmt:message
                key="banner.main.title2"/></h2>
    </div>
</div>

<div class="latest-products">
    <div class="container">
        <div class="row">

            <div class="col-md-12">
                <div class="section-heading">
                    <h2>Latest Products</h2>
                    <a href="/publications">view all products <i class="fa fa-angle-right"></i></a>
                </div>
            </div>

            <div class="col-md-4">
                <div class="product-item">
                    <a href="#"><img src="assets/images/product_01.jpg" alt=""></a>
                    <div class="down-content">
                        <a href="#"><h4>Tittle goes here</h4></a>
                        <h6>$25.75</h6>
                        <p>Lorem ipsume dolor sit amet, adipisicing elite. Itaque, corporis nulla aspernatur.</p>
                        <ul class="stars">
                            <li><i class="fa fa-star"></i></li>
                            <li><i class="fa fa-star"></i></li>
                            <li><i class="fa fa-star"></i></li>
                            <li><i class="fa fa-star"></i></li>
                            <li><i class="fa fa-star"></i></li>
                        </ul>
                        <span>Reviews (24)</span>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<div class="best-features">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="section-heading">
                    <h2>About Live Journal</h2>
                </div>
            </div>
            <div class="col-md-6">
                <div class="left-content">
                    <h4>Looking for the best periodicals?</h4>
                    <p><a rel="nofollow" href="##" target="_parent">This
                        template</a> is free to use for your business websites. However, you have no permission to
                        redistribute the downloadable ZIP file on any template collection website. <a rel="nofollow"
                                                                                                      href="##">Contact
                            us</a> for more info.</p>
                    <ul class="featured-list">
                        <li><a href="#">Lorem ipsum dolor sit amet</a></li>
                        <li><a href="#">Consectetur an adipisicing elit</a></li>
                        <li><a href="#">It aquecorporis nulla aspernatur</a></li>
                        <li><a href="#">Corporis, omnis doloremque</a></li>
                        <li><a href="#">Non cum id reprehenderit</a></li>
                    </ul>
                    <a href="about.jsp" class="filled-button">Read More</a>
                </div>
            </div>
            <div class="col-md-6">
                <div class="right-image">
                    <img src="assets/images/feature-image.jpg" alt="">
                </div>
            </div>
        </div>
    </div>
</div>


<div class="call-to-action">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="inner-content">
                    <div class="row">
                        <div class="col-md-8">
                            <h4>Creative &amp; Unique <em>Live</em> Journal</h4>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque corporis amet elite
                                author nulla.</p>
                        </div>
                        <div class="col-md-4">
                            <a href="#" class="filled-button">Purchase Now</a>
                        </div>
                    </div>
                </div>
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
<script src="assets/js/form.js"></script>




</body>

</html>
