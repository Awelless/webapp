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
                    <fmt:message key="local.room" />
                </div>

                <c:if test="${reservation != null}">
                    <table class="table">
                        <tr>
                            <td class="side-td">
                                <fmt:message key="local.room.number" />: ${reservation.roomId}
                            </td>
                        </tr>
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
                    <fmt:message key="local.room.order.cost" />
                </div>

                <c:if test="${reservation != null}">
                    <div class="table">
                        <div class="name" style="font: 32px Arial; margin: 5%;">
                            ${reservation.cost}
                        </div>

                        <div class="button-wrapper">
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="pay" />
                                <input type="hidden" name="reservationId" value="${reservation.id}" />
                                <input class="button-primary" type="submit" value="<fmt:message key="local.room.order.pay" />" />
                            </form>
                            <a href="${pageContext.request.contextPath}/controller?command=userReservationsPage" class="button-light">
                                <fmt:message key="local.button.back" />
                            </a>
                        </div>
                    </div>

                </c:if>
            </div>

        </div>
    </body>
</html>
