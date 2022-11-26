<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>


<bossmonster:layout pageName="gameScreen">
<spring:url value="${card.cardImage}" var="image"/>

<div class="test gameContainer">
    <div class="test Player-hand">Player Hand</div>
    <div class="test Player-Dungeon">Player Dungeon</div>
    <div class="test Opponents-Dungeons">
        <div class="test Dungeon1">D1</div>
        <div class="test Dungeon3">D2</div>
        <div class="test Dungeon2">D3</div>
        <div class="test Dungeon4">D4</div>
    </div>
    <div class="test Phase-Display">Phase</div>
    <div class="test City-and-Discard">
        <div class="test Hero1">H1</div>
        <div class="test Hero2">H2</div>
        <div class="test Hero3">H3</div>
        <div class="test Hero4">H4</div>
        <div class="test Discard"></div>
    </div>
    <div class="test Decks">Decks</div>
</div>

</bossmonster:layout>
