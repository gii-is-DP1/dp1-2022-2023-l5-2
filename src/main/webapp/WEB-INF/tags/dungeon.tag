<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="player" required="true" rtexprvalue="true" type="org.springframework.samples.bossmonster.game.player.Player"%>

<spring:url value="/resources/images/blank_card.png" var="blank"/>

<div class="dungeon">
    <div>
        <span class="glyphicon glyphicon-star">
            <c:out value="${player.souls}"/>
        </span>
        <span class="glyphicon glyphicon-heart">
            <c:out value="${player.health}"/>
        </span>
    </div>
    <div>
        <bossmonster:card card="${player.dungeon.bossCard}"/>
        <c:forEach items="${player.dungeon.roomSlots}" var="slot">
            <bossmonster:card card="${slot.room}"/>
        </c:forEach>
    </div>
</div>
<bossmonster:modalButton style="btn btn-default" modalId="player${player.user.username}">
    View Dungeon
</bossmonster:modalButton>

<bossmonster:modal modalId="player${player.user.username}" modalName="${player.user.nickname}'s Dungeon">
    <div class="expandable">
        <c:forEach items="${player.dungeon.roomSlots}" var="slot">
            <bossmonster:card card="${slot.room}"/>
        </c:forEach>
        <bossmonster:card card="${player.dungeon.bossCard}"/>
    </div>
</bossmonster:modal>
