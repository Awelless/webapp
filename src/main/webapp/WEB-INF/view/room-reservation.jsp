<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="fragments/i18n.jsp" />

<html>
    <jsp:include page="fragments/head.jsp" />

    <body>
        <jsp:include page="fragments/navbar.jsp" />

        <div class="container">

            <div class="form-wrapper">

                <div class="form-name">
                    <fmt:message key="local.room.order.new" />
                </div>

                <form class="form" action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="reservation" />

                    <label class="form-label" for="beds">
                        <fmt:message key="local.room.beds" />
                    </label>
                    <input class="form-input"
                           id="beds"
                           type="number"
                           min="1"
                           name="numberOfBeds"
                           value="${numberOfBeds}"
                    />
                    <c:if test="${bedsError}">
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.room.order.invalid.beds" />
                        </div>
                    </c:if>
                    <br />
                    <br />

                    <label class="form-label">
                        <fmt:message key="local.room.rating" />
                    </label>
                    <br />
                    <br />
                    <div class="form-radio">
                        <input type="radio"
                               id="rating3"
                               name="rating"
                               value="3"
                                <c:if test="${'3'.equals(rating)}">
                                    checked
                                </c:if>
                        />
                        <label for="rating3">3</label>

                        <input type="radio"
                               id="rating4"
                               name="rating"
                               value="4"
                                <c:if test="${'4'.equals(rating)}">
                                    checked
                                </c:if>
                        />
                        <label for="rating4">4</label>

                        <input type="radio"
                               id="rating5"
                               name="rating"
                               value="5"
                                <c:if test="${'5'.equals(rating)}">
                                    checked
                                </c:if>
                        />
                        <label for="rating5">5</label>
                    </div>
                    <c:if test="${ratingError}">
                        <br />
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.room.order.invalid.rating" />
                        </div>
                    </c:if>
                    <br />
                    <br />

                    <label class="form-label" for="checkIn">
                        <fmt:message key="local.room.order.check_in" />
                    </label>
                    <input class="form-input"
                           id="checkIn"
                           type="date"
                           name="checkIn"
                           value="${checkIn}"
                    />
                    <br />
                    <br />

                    <label class="form-label" for="checkOut">
                        <fmt:message key="local.room.order.check_out" />
                    </label>
                    <input class="form-input"
                           id="checkOut"
                           type="date"
                           name="checkOut"
                           value="${checkOut}"
                    />
                    <c:if test="${dateError}">
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.room.order.invalid.date" />
                        </div>
                        <br />
                    </c:if>

                    <input class="button-primary" type="submit" value="<fmt:message key="local.room.submit" />" />
                </form>

            </div>
        </div>
    </body>
</html>
