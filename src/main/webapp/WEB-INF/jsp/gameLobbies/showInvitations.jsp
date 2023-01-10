<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="showInvitations">

<spring:url value="/invites/toLobby" htmlEscape="true" var="toLobby"/>

    <h1>Invitations</h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Sender</th>
            <th>Room Code</th>
            <th>Participants</th>
        </tr>
        <tbody>
            <c:forEach items="${invitations}" var="i">
                <tr>
                    <td>
                        <c:out value="${i.user.username}"/>
                    </td>
                    <td>
                        <c:out value="${i.lobby.id}"/>
                    </td>
                    <td>
                        <c:forEach items="${i.lobby.joinedUsers}" var="player">
                            <c:out value="${player.username} ,"/>
                        </c:forEach>
                    </td>
                    <td>
                        <a href="${toLobby}"> 
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
</bossmonster:layout>