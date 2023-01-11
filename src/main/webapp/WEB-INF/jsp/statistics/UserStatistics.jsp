<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="statistics">

<spring:url value="/users/statistics/global" htmlEscape="true" var="global"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>
<div class="white-panel">
    <h1>User Statistics</h1>
    <table>
        <tr>
            <th>Name:</th>
            <td><c:out value="${user.username}"/>&nbsp;</td>

            <th>Nickname:</th>
            <td><c:out value="${user.nickname}"/></td>
        </tr>
        <tr>
            <th>Victories:</th>
            <td><c:out value="${winned}"/></td>

            <th>Win Rate:</th>
            <td><c:out value="${winRate}"/>%</td>


        </tr>
        <tr>
            <th>Total Games: </th>
            <td><c:out value="${total}"/></td>

            <th>Best Win Streak:</th>
            <td><c:out value="${winStreak}"/></td>
            
            <th>Average Duration:</th>
            <td><c:out value="${averageDuration}"/> minutes</td>

        </tr>
    </table>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Date</th>
            <th>Duration</th>
            <th>Rounds</th>
            <th>Players</th>
            <th>Winner</th>
            <th>Souls Left</th>
            <th>Health Left</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${gamesResult}" var="game">
                <tr>
                    <td>
                        <c:out value="${game.date}"/>
                    </td>
                    <td>
                        <c:out value="${game.minutes} "/>
                    </td>
                    <td>
                        <c:out value="${game.rounds}"/>
                    </td>                
                    <td>
                        <c:forEach items="${game.participants}" var="player">
                            <c:out value="${player.username} ,"/>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${game.winner.username} "/>
                    </td>
                    <td>
                        <c:out value="${game.souls} "/>
                    </td>
                    <td>
                        <c:out value="${game.healths} "/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
    </table>
    <div class="centered-view">
        <a class="btn btn-title" href="${global}">Global Statistics</a>
        <a class="btn btn-title" href="${welcome}">Back</a>
    </div>
</div>
</bossmonster:layout>