<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="currentGames">
    <h2>User Management</h2>

    <table id="currentGamesListTable" class="table table-striped">
        <thead>
            <tr>
                <th>Game</th>
                <th>Host Player</th>
                <th>players</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${game}" var="game">
                <tr>
                    <td>
                        <c:out value="${game.game.id}"/>
                    </td>
                    <td>
                        <c:out value="${game.leaderUser}"/>
                    </td>
                    <td>
                        <c:forEach items="${game.joinedUsers}" var="joinedUser">
                            <c:out value="${joinedUser}"/>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</bossmonster:layout>