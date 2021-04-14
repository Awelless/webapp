<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="${cookie.get('localization').value}" scope="request" />
<fmt:setBundle basename="local" scope="request" />

<html>
    <jsp:include page="../fragments/head.jsp" />

    <body>
        <jsp:include page="../fragments/navbar.jsp" />

        <div class="container">

            <div class="form-wrapper">
                <div class="form-name" style="font-size: 16px">
                    <fmt:message key="local.error.404" />
                    <br />
                    <br />
                    <a class="button-primary" href="${pageContext.request.contextPath}/controller?command=previousRequest">
                        OK
                    </a>
                </div>
            </div>

        </div>
    </body>
</html>
