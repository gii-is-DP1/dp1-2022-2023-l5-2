<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="createGameLobby">

    <spring:url value="/" htmlEscape="true" var="homeScreen"/>
    <spring:url value="/lobby/${lobby.id}/newGame" htmlEscape="true" var="newGame"/>

    <h1>Waiting in lobby...</h1>
    Current players:
    <c:forEach items="${lobby.joinedUsers}" var="user">
        <li>
            <c:out value="${user.nickname}" /> <c:if test="${lobby.leaderUser == user}"> (Leader)</c:if>
        </li>
    </c:forEach>
    <c:if test="${lobby.leaderUser == currentUser}">
        <a href="${newGame}" class="btn btn-default">Start game</a>
    </c:if>
    <form method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <button class="btn btn-default" type="submit">
            <c:choose>
                <c:when test="${lobby.leaderUser == currentUser}">
                    Cancel game
                </c:when>
                <c:otherwise>
                    Leave game
                </c:otherwise>
            </c:choose>
        </button>
    </form>

</bossmonster:layout>
