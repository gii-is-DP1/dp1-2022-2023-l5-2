<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="statistics">
    <table class="table table-striped">
        <tr><h1>Ranking Por Win Rate</h1></tr>
        <tr><h1>Ranking Por Win Streak</h1></tr>
        <tbody>
            <c:forEach items="${ranking}" var="rank" >
                <tr>
                    <td>
                        <c:out value="${rank}"/>
                    </td>
                    <th>
                        <td>
                            <c:out value="${rank}"/>
                        </td>
                    </th>
                </tr>
            </c:forEach>
            </tbody>
    </table>
</bossmonster:layout>