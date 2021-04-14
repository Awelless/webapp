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
                    <fmt:message key="local.credentials.register" />
                </div>

                <form class="form" action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="registration"/>

                    <label>
                        <input class="form-input"
                               type="text" name="username"
                               placeholder="<fmt:message key="local.credentials.username" />"
                               value="${username != null ? username : ""}" />
                    </label>
                    <c:if test="${usernameError}">
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.credentials.invalid.username" />
                        </div>
                    </c:if>
                    <c:if test="${uniquenessError}">
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.credentials.invalid.uniqueness" />
                        </div>
                    </c:if>
                    <br />

                    <label>
                        <input class="form-input" type="password" name="password" placeholder="<fmt:message key="local.credentials.password" />" />
                    </label>
                    <c:if test="${passwordError}">
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.credentials.invalid.password" />
                        </div>
                    </c:if>
                    <br />

                    <label>
                        <input class="form-input" type="password" name="passwordConfirmation" placeholder="<fmt:message key="local.credentials.password_confirmation" />" />
                    </label>
                    <c:if test="${passwordConfirmationError}">
                        <div class="invalid-form-feedback">
                            <fmt:message key="local.credentials.invalid.password_confirmation" />
                        </div>
                    </c:if>
                    <br />

                    <input class="button-primary" type="submit" value="<fmt:message key="local.credentials.register" />">
                    <a class="button-light" href="${pageContext.request.contextPath}/controller?command=loginPage">
                        <fmt:message key="local.credentials.login" />
                    </a>

                </form>
           </div>
        </div>
    </body>
</html>
