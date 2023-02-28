<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="v" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resource"/>
<c:set var="controller" value="controller?action=publications"/>
<c:set var="priceAsc" value="&sort=price Asc"/>
<c:set var="priceDesc" value="&sort=price Desc"/>
<c:set var="nameAsc" value="&sort=name Asc"/>
<c:set var="nameDesc" value="&sort=name Desc"/>
<c:set var="Politics" value="&topic=POLITICS"/>
<c:set var="Sport" value="&topic=SPORT"/>
<c:set var="Economy" value="&topic=ECONOMY"/>
<c:set var="Science" value="&topic=SCIENCE"/>
<c:set var="Weather" value="&topic=WEATHER"/>
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
                <div class="filters ">
                    <ul>
                        <li>
                            <a href=""><fmt:message
                                    key="publication.filter.link.topic"/> &#x25be</a>
                            <ul>
                                <li>
                                    <a href="${controller.concat(Politics)}"><fmt:message
                                            key="publication.filter.link.politics"/></a>
                                </li>
                                <li>
                                    <a href="${controller.concat(Economy)}"><fmt:message
                                            key="publication.filter.link.economy"/></a>
                                </li>
                                <li>
                                    <a href="${controller.concat(Sport)}"><fmt:message
                                            key="publication.filter.link.sport"/></a>
                                </li>
                                <li>
                                    <a href="${controller.concat(Science)}"><fmt:message
                                            key="publication.filter.link.science"/></a>
                                </li>
                                <li>
                                    <a href="${controller.concat(Weather)}"><fmt:message
                                            key="publication.filter.link.weather"/></a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href=""><fmt:message
                                    key="publication.filter.link.price"/> &#9662</a>
                            <ul>
                                <li>
                                    <a href="${controller.concat(priceAsc)}"><fmt:message
                                            key="publication.filter.link.price"/> &#8593</a>
                                </li>
                                <li>
                                    <a href="${controller.concat(priceDesc)}"><fmt:message
                                            key="publication.filter.link.price"/> &#8595</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href=""><fmt:message
                                    key="publication.filter.link.name"/> &#9662</a>
                            <ul>
                                <li>
                                    <a href="${controller.concat(nameAsc)}"><fmt:message
                                            key="publication.filter.link.name_a-z"/></a>
                                </li>
                                <li>
                                    <a href="${controller.concat(nameDesc)}"><fmt:message
                                            key="publication.filter.link.name_z-a"/></a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <form action="controller?action=searchPublication" method="post">
                                <input class="search" type="search" name="name" placeholder="<fmt:message
                                            key="publication.filter.input.search"/>...">
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
            <c:if test="${errorSearch == true}">
                <div style="color: red"><fmt:message
                        key="publication.errorSearch"/></div>
            </c:if>
            <div class="create">
                <c:if test="${sessionScope.user.role=='ADMIN'}">
                    <div>
                        <h6><fmt:message key="publication.main.title.create"/></h6>
                    </div>
                    <div style="margin-left: 15px; width: 10%">
                        <a style="display: block" class="btn_product" href="addPublication.jsp"><fmt:message
                                key="publication.main.button.create"/></a>
                    </div>
                </c:if>
            </div>

            <div class="col-md-12">
                <div class="filters-content">
                    <div class="row grid">
                        <c:forEach items="${mapOfPublication}" var="publication">
                            <div class="col-lg-4 col-md-4 all des">
                                <div class="product-item">
                                    <a href="#"><img src="assets/images/product_photo.jpg" alt=""></a>
                                    <div class="down-content">
                                        <a href="#"><h4>${publication.key.topic}</h4></a>
                                        <p style="color: #f33f3f; font-size: 16px; font-weight: 500">${publication.key.name}</p>
                                        <h6>${publication.key.price} <fmt:message
                                                key="publication.main.price_uah"/></h6>
                                        <p>${publication.key.content}</p>
                                        <div style="display: flex; justify-content: space-between; width: 100%">
                                            <ul class="stars">
                                                <li><i class="fa fa-star"></i></li>
                                                <li><i class="fa fa-star"></i></li>
                                                <li><i class="fa fa-star"></i></li>
                                                <li><i class="fa fa-star"></i></li>
                                                <li><i class="fa fa-star"></i></li>
                                            </ul>
                                            <div class="buttons">
                                                <c:if test="${sessionScope.user.role=='ADMIN'}">
                                                    <form method="post" action="controller?action=editPublication">
                                                        <input type="hidden" name="id" value="${publication.key.id}">
                                                        <input class="btn_product" type="submit" value="<fmt:message
                                    key="publication.main.button.update"/>">
                                                    </form>
                                                    <form method="post"
                                                          action="controller?action=removePublication">
                                                        <input type="hidden" name="id" value="${publication.key.id}">
                                                        <input class="btn_product" type="submit" value="<fmt:message
                                    key="publication.main.button.delete"/>">
                                                    </form>
                                                </c:if>
                                                <c:if test="${sessionScope.user.role=='USER'}">
                                                    <c:choose>
                                                        <c:when test="${not publication.value}">
                                                            <form action="controller?action=subscribe"
                                                                  method="post">
                                                                <input type="hidden" name="id"
                                                                       value="${publication.key.id}">
                                                                <input class="btn_product" type="submit" value="<fmt:message
                                    key="publication.main.button.subscribe"/>">
                                                            </form>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form action="controller?action=unSubscribe"
                                                                  method="post">
                                                                <input type="hidden" name="publication_id"
                                                                       value="${publication.key.id}">
                                                                <input type="hidden" name="user_id"
                                                                       value="${user.id}">
                                                                <input class="btn_product"
                                                                       style="background-color: black" type="submit"
                                                                       value="<fmt:message
                                    key="publication.main.button.unsubscribe"/>">
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </div>
                                        </div>
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
                        <li>
                            <a href="${pageContext.request.contextPath}/controller?action=publications&${queryString}&page=${currentPage - 1}"><i
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
                                    <a href="${pageContext.request.contextPath}/controller?action=publications&${queryString}&page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt numberOfPages}">
                        <li><a
                                href="${pageContext.request.contextPath}/controller?action=publications&${queryString}&page=${currentPage + 1}"><i
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
