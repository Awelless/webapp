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

            <div class="name">
                <fmt:message key="local.room.order.all" />
            </div>

            <table class="table">
                <tr>
                    <th style="width: 10%"><fmt:message key="local.room.number" /></th>
                    <th style="width: 10%"><fmt:message key="local.room.beds" /></th>
                    <th><fmt:message key="local.room.rating" /></th>
                    <th><fmt:message key="local.room.order.check_in" /></th>
                    <th><fmt:message key="local.room.order.check_out" /></th>
                    <th><fmt:message key="local.room.order.status" /></th>
                    <th style="width: 10%"></th>
                </tr>

                <c:forEach var="reservation" items="${reservationsPage.content}">
                    <tr>
                        <td>${reservation.roomId != null ? reservation.roomId : "-"}</td>
                        <td>${reservation.numberOfBeds}</td>
                        <td>${reservation.rating}</td>
                        <td>${reservation.checkIn}</td>
                        <td>${reservation.checkOut}</td>
                        <td>
                            <c:choose>
                                <c:when test="${RoomReservationStatus.PENDING.equals(reservation.status)}">
                                    <fmt:message key="local.room.order.status.pending" />
                                </c:when>
                                <c:when test="${RoomReservationStatus.APPROVED.equals(reservation.status)}">
                                    <fmt:message key="local.room.order.status.approved" />
                                </c:when>
                                <c:when test="${RoomReservationStatus.REJECTED.equals(reservation.status)}">
                                    <fmt:message key="local.room.order.status.rejected" />
                                </c:when>
                                <c:when test="${RoomReservationStatus.PAID.equals(reservation.status)}">
                                    <fmt:message key="local.room.order.status.paid" />
                                </c:when>
                                <c:when test="${RoomReservationStatus.CANCELED.equals(reservation.status)}">
                                    <fmt:message key="local.room.order.status.canceled" />
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="button-wrapper">
                            <c:if test="${RoomReservationStatus.APPROVED.equals(reservation.status)}">
                                <form action="${pageContext.request.contextPath}/controller" method="get">
                                    <input type="hidden" name="command" value="paymentPage" />
                                    <input type="hidden" name="reservationId" value="${reservation.id}" />
                                    <input class="button-primary" type="submit" value="<fmt:message key="local.room.order.pay" />" />
                                </form>
                            </c:if>
                            <c:if test="${RoomReservationStatus.PENDING.equals(reservation.status) ||
                                          RoomReservationStatus.APPROVED.equals(reservation.status)}">
                                <form action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="cancel" />
                                    <input type="hidden" name="reservationId" value="${reservation.id}" />
                                    <input class="button-light" type="submit" value="<fmt:message key="local.room.order.cancel" />" />
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="fragments/pagination.jsp">
                <jsp:param name="command" value="userReservationsPage"/>
            </jsp:include>

        </div>
    </body>
</html>
