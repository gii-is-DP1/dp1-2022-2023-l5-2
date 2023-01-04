<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="playedGames">
    <h2>Played Games</h2>

    <table id="playedGamesListTable" class="table table-striped">
        <thead>
            <tr>
                <th>Game</th>
                <th>Rounds</th>
                <th>Date</th>
                <th>Winner</th>
                <th>Participants</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${playedGames}" var="gameResult">
                <tr>
                    <td>
                        <c:out value="${gameResult.id}"/>
                    </td>
                    <td>
                        <c:out value="${gameResult.rounds}"/>
                    </td>
                    <td>
                        <c:out value="${gameResult.date}"/>
                    </td>
                    <td>
                        <c:out value="${gameResult.winner.username}"/>
                    </td>
                    <td>
                        <c:forEach items="${gameResult.participants}" var="participant">
                            <c:out value="${participant.username}"/>
                            <br>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</bossmonster:layout>
