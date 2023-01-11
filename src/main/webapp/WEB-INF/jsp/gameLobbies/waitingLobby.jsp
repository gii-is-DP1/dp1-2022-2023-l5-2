<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="createGameLobby">

    <spring:url value="/" htmlEscape="true" var="homeScreen"/>
    <spring:url value="/lobby/${lobby.id}/newGame" htmlEscape="true" var="newGame"/>
    <spring:url value="/invites/${lobby.id}/new" htmlEscape="true" var="sendInvite"/>

<body class="inicio" background="/resources/images/backgrounds/emptyBackground.png">

<div class="white-panel">
    <h1>Waiting in lobby...</h1>
    <span class="badge player-name">Room code: ${lobby.id}</span>
    <h3>Current players:</h3>
    <ul style="list-style:none">
        <c:forEach items="${lobby.joinedUsers}" var="user">
            <li>
                <div class="row vert-align" style="margin: 0 auto">
                    <img class="img-circle col-md-2" src="${user.avatar}"/>
                    <c:if test="${user eq lobby.leaderUser}">
                        <span class="badge player-name">Leader</span>
                    </c:if>
                    <b><c:out value="${user.nickname}"/></b>
                </div>
            </li>
        </c:forEach>
    </ul>
    <form method="post">
        <c:if test="${lobby.leaderUser == currentUser}">
            <a href="${newGame}" class="btn btn-default">Start game</a>
        </c:if>
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
        <a class="btn btn-default" href="${sendInvite}">
            Send Invitations
        </a>
        </form>
        </div>
</body>

</bossmonster:layout>
