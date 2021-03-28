<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.web.entity.RoomReservationStatus" %>

<jsp:include page="fragments/i18n.jsp" />

<html>
<jsp:include page="fragments/head.jsp" />

<body>
<jsp:include page="fragments/navbar.jsp" />

<div class="container">

    <div>
        <fmt:message key="local.room.order.all" />
    </div>

    <table>
        <tr>
            <th><fmt:message key="local.room.beds" /></th>
            <th><fmt:message key="local.room.rating" /></th>
            <th><fmt:message key="local.room.check_in" /></th>
            <th><fmt:message key="local.room.check_out" /></th>
            <th><fmt:message key="local.room.order.status" /></th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <td>${reservation.numberOfBeds}</td>
                <td>${reservation.rating}</td>
                <td>${reservation.checkIn}</td>
                <td>${reservation.checkOut}</td>
                <td>${reservation.status}</td>
                <td>
                    <c:if test="${RoomReservationStatus.PENDING.equals(reservation.status)}">
                        <a>
                            approve
                        </a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${RoomReservationStatus.PENDING.equals(reservation.status)}">
                        <a>
                            reject
                        </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>

    </table>

</div>
</body>
</html>
