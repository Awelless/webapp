<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.web.entity.UserRole" %>

<nav class="navbar">
    <div class="navbar-logo">
        HotelBooking
    </div>

    <c:if test="${sessionScope.user != null && UserRole.USER.equals(sessionScope.user.role)}">
        <div class="navbar-link">
            <a class="navbar-button-link" href="${pageContext.request.contextPath}/controller?command=newReservationPage">
                <fmt:message key="local.navbar.reservations.new" />
            </a>
        </div>
        <div class="navbar-link">
            <a class="navbar-button-link" href="${pageContext.request.contextPath}/controller?command=userReservationsPage">
                <fmt:message key="local.navbar.reservations.my" />
            </a>
        </div>
    </c:if>

    <c:if test="${sessionScope.user != null && UserRole.ADMIN.equals(sessionScope.user.role)}">
        <div class="navbar-link">
            <a class="navbar-button-link" href="${pageContext.request.contextPath}/controller?command=allReservationsPage">
                <fmt:message key="local.navbar.reservations.all" />
            </a>
        </div>
    </c:if>

    <div class=navbar-container>
        <form class="navbar-link" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="localization" />
            <input type="hidden" name="value" value="EN" />
            <input class="link-button" type="submit" value="EN" />
        </form>

        <form class="navbar-link" style="margin-right: 40px" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="localization" />
            <input type="hidden" name="value" value="RU" />
            <input class="link-button" type="submit" value="RU" />
        </form>

        <c:if test="${sessionScope.user != null}">
            <form class="navbar-button" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="logout" />
                <input class="button-light" type="submit" value="<fmt:message key="local.navbar.logout" />" />
            </form>
        </c:if>
    </div>
</nav>