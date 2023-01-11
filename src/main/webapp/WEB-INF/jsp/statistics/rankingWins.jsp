<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="statistics">

<spring:url value="/users/statistics/rankings/winRate" htmlEscape="true" var="rankWinrate"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>

<div class="white-panel">
    <table class="table table-striped">
        <tr><h1>Ranking By Total Wins</h1></tr>
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
    <table class="button-table">
        <tr asd>
            <td class="form-group">
                <a class="btn btn-title" href="${rankWinrate}">Win Rate Ranking</a>
            </td>
            <td class="form-group">
                <a class="btn btn-title" href="${welcome}">Back</a>
            </td>
        </tr>
    </table>
</div>

<style>
    .btn{
        width:150px;
    }
    td{
        width:300px;
    }
    .button-table{
        height: 70px;
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }
</style>

</bossmonster:layout>