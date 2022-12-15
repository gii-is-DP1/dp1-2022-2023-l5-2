<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="slot" required="true" rtexprvalue="true" type="org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot"%>

<spring:url value="/resources/images/blank_card.png" var="blank"/>

<c:choose>
    <c:when test="${not empty slot.room}">
        <bossmonster:card card="${slot.room}" type="room" facedown="${not slot.isVisible}" />
    </c:when>
    <c:otherwise>
        <img src="${blank}" class="card"/>
    </c:otherwise>
</c:choose>
