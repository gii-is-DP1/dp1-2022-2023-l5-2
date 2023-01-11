<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="currentGames">
<spring:url value="/adminOptions" htmlEscape="true" var="adminOptions"/>
<div class="white-panel">
    <h2>Audit Users</h2>
    <table id="currentGamesListTable" class="table table-striped">
        <thead>
            <tr>
                <th>Revision</th>
                <th>Usuario modificado</th>
                <th>Modificado por</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${revision}" var="audit">
                <tr>
                    <td>
                        <c:out value="${audit.rev}"/>
                    </td>
                    <td>
                        <c:out value="${audit.user.username}"/>
                    </td>
                    <td>
                        <c:out value="${audit.modifiedBy}"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="centered-view">     
        <a class="btn btn-title" href="${adminOptions}">Back</a>
    </div>
</div>
</bossmonster:layout>