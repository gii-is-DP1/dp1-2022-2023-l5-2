<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="statistics">

<spring:url value="/users/statistics/rankings/wins" htmlEscape="true" var="rankWins"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>

    <table class="table table-striped">
        <tr><h1>Ranking By Win Rate</h1></tr>
        <tbody>
            <c:forEach items="${ranking}" var="rank" >
                <tr>
                    <td>
                        <c:out value="${rank}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
    </table>
    <div class="centered-view">
        <a class="btn btn-title" href="${rankWins}">Total Wins Ranking</a>
        <a class="btn btn-title" href="${welcome}">Back</a>
    </div>
</bossmonster:layout>