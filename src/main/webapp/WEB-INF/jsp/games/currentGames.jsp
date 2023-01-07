<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="currentGames">
    <spring:url value="/adminOptions" htmlEscape="true" var="adminOptions"/>
    <h2>Current Games</h2>

    <table id="currentGamesListTable" class="table table-striped">
        <thead>
            <tr>
                <th>Game</th>
                <th>Host Player</th>
                <th>Joined Players</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${games}" var="gameLobby">
                <tr>
                    <td>
                        <c:out value="${gameLobby.game.id}"/>
                    </td>
                    <td>
                        <c:out value="${gameLobby.leaderUser.username}"/>
                    </td>
                    <td>
                        <c:forEach items="${gameLobby.joinedUsers}" var="joinedUser">
                            <c:out value="${joinedUser.username}"/>
                            <br>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="centered-view">     
        <a class="btn btn-title" href="${adminOptions}">Back</a>
    </div>
</bossmonster:layout>
