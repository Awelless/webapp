<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="text-align: center">
    <ul class="pagination">
        <c:forEach var="pageNumber" items="${pageNumbers}">
            <c:choose>
                <c:when test="${pageNumber == null}">
                    <span>...</span>
                </c:when>
                <c:when test="${pageNumber == reservationsPage.currentPage}">
                    <span class="current">${pageNumber}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&page=${pageNumber}">${pageNumber}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</div>