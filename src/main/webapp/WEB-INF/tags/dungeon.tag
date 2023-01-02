<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="player" required="true" rtexprvalue="true" type="org.springframework.samples.bossmonster.game.player.Player"%>

<spring:url value="/resources/images/blank_card.png" var="blank"/>

<div>
    <div>
        <span class="badge player-name">
        <c:out value="${player.user.nickname}"/>
        </span>

        <div class="badge souls">
            <span class="glyphicon glyphicon-star">
            </span>
            <c:out value="${player.souls}"/>
        </div>

        <div class="badge health">
        <span class="glyphicon glyphicon-heart">
        </span>
            <c:out value="${player.health}"/>
        </div>
        <bossmonster:modalButton style="btn btn-default col-md-offset-1" modalId="player${player.user.username}">
            View Dungeon
        </bossmonster:modalButton>
    </div>
    <div class="dungeonCards">
        <div class="slot">
            <bossmonster:card card="${player.dungeon.bossCard}"/>
        </div>
        <c:forEach items="${player.dungeon.roomSlots}" var="slot">
            <div class="slot">
            <bossmonster:card card="${slot.room}" facedown="${not slot.isVisible}" type="room"/>
            <c:forEach items="${slot.heroesInRoom}" var="hero">
                <bossmonster:hero hero="${hero}"/>
            </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>


<bossmonster:modal modalId="player${player.user.username}" modalName="${player.user.nickname}'s Dungeon">
    <div class="expandable">
        <c:forEach items="${player.dungeon.roomSlots}" var="slot">
            <bossmonster:card card="${slot.room}" type="room" facedown="${not slot.isVisible}"/>
        </c:forEach>
        <bossmonster:card card="${player.dungeon.bossCard}"/>
    </div>
</bossmonster:modal>
