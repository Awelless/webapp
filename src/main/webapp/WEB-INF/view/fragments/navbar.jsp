<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<nav class="navbar">
    <a class="navbar-logo" href="${pageContext.request.contextPath}/controller">HotelBooking</a>

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