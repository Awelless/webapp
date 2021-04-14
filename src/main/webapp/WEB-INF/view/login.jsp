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
                    <fmt:message key="local.credentials.login" />
                </div>

                <c:if test="${badCredentials}">
                    <div class="alert">
                        <fmt:message key="local.credentials.bad_credentials_error" />
                    </div>
                </c:if>

                <form class="form" action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="login" />

                    <label>
                        <input class="form-input"
                               type="text" name="username"
                               placeholder="<fmt:message key="local.credentials.username" />"
                               value="${username != null ? username : ""}" />
                    </label>
                    <br />

                    <label>
                        <input class="form-input" type="password" name="password" placeholder="<fmt:message key="local.credentials.password" />" />
                    </label>
                    <br />

                    <input class="button-primary" type="submit" value="<fmt:message key="local.credentials.login" />" />
                    <a href="${pageContext.request.contextPath}/controller?command=registrationPage" class="button-light">
                        <fmt:message key="local.credentials.register" />
                    </a>

                </form>
            </div>
        </div>
    </body>
</html>
