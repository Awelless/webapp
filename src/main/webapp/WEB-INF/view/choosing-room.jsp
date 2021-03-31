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

            <div class="side">
                <div class="name">
                    <fmt:message key="local.room.order" />
                </div>

                <c:if test="${reservation != null}">
                    <table class="table">
                        <tr>
                            <td class="side-td">
                                <fmt:message key="local.room.beds" />: ${reservation.numberOfBeds}
                            </td>
                        </tr>
                        <tr>
                            <td class="side-td">
                                <fmt:message key="local.room.rating" />: ${reservation.rating}
                            </td>
                        </tr>
                        <tr>
                            <td class="side-td">
                                <fmt:message key="local.room.order.check_in" />: ${reservation.checkIn}
                            </td>
                        </tr>
                        <tr>
                            <td class="side-td">
                                <fmt:message key="local.room.order.check_out" />: ${reservation.checkOut}
                            </td>
                        </tr>
                    </table>
                </c:if>
            </div>

            <div class="main">
                <div class="name">
                    <fmt:message key="local.room.suitable" />
                </div>

                <table class="table">
                    <tr>
                        <th style="width: 20%"><fmt:message key="local.room.number" /></th>
                        <th><fmt:message key="local.room.beds" /></th>
                        <th><fmt:message key="local.room.rating" /></th>
                        <th style="width: 10%"></th>
                    </tr>

                    <c:forEach var="room" items="${rooms}">
                        <tr>
                            <td>${room.id}</td>
                            <td>${room.numberOfBeds}</td>
                            <td>${room.rating}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="approve" />
                                    <input type="hidden" name="reservationId" value="${reservation.id}" />
                                    <input type="hidden" name="roomId" value="${room.id}" />
                                    <input class="button-primary" type="submit" value="<fmt:message key="local.room.select" />" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
    </body>
</html>
