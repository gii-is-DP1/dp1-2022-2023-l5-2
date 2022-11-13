<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="statistics">
    <h1>User Statistics</h1>
    <table>
        <tr>
            <th>Name:</th>
            <td><c:out value="${user.username}"/></td>

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
            <td><c:out value="${averageDuration}"/></td>

        </tr>
    </table>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Date</th>
            <th>Duration</th>
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
                        <c:out value="${game.duration} "/>
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
                        Souls Left
                    </td>
                    <td>
                        Health Left
                    </td>
                </tr>
            </c:forEach>
            </tbody>
    </table>
</bossmonster:layout>