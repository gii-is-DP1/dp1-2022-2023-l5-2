<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags"%>


<bossmonster:layout pageName="gameScreen">

    <bossmonster:modal modalId="selectMenu" modalName="Please select an option" unclosable="true">
        <form class="expandable" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <c:if test="${not empty game.choice}">
                <c:forEach begin="0" end="${fn:length(game.choice)-1}" var="index">
                    <button class="invis" value="${index}" name="choice" ${game.unplayableCards.contains(index)?'disabled':''}>
                        <bossmonster:card card="${game.choice[index]}" style="${game.unplayableCards.contains(index)?'disabled':''}"/>
                    </button>
                </c:forEach>
                <c:if test="${game.isChoiceOptional}">
                    <button class="btn btn-default" name="choice" value="-1">Pass</button>
                </c:if>
            </c:if>
        </form>
    </bossmonster:modal>

<div class="gameContainer">
    <div class="row">
        <div class="col-md-2">
            <bossmonster:cardPile cards="${game.city}" pileId="cityPile" pileName="Heroes in City" />
            <b>City</b>
        </div>
        <div class="col-md-2 discard">
            <bossmonster:cardPile cards="${game.discardPile}" pileId="discardPile" pileName="Discard Pile" />
            <b>Discard Pile</b>
        </div>
        <div class="col-md-3 alert alert-info phase-display">
            <b><c:out value = "${game.state.phase}"/></b>
            <b><c:out value = "${game.state.subPhase}"/></b>
            <b><c:out value = "${game.currentPlayer.user.nickname}'s Turn"/></b>
        </div>
        <div class="col-md-5 opponents-dungeon">
            <c:forEach begin="0" end="${fn:length(players)-1}" var="index">
                <div class="dungeon dungeon${index} row">
                    <bossmonster:dungeon player="${players[index]}"/>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="row">
        <div class="hand col-md-2 col-md-offset-1">
            <bossmonster:cardPile cards="${currentPlayer.hand}" pileId="hand" pileName="Your Hand" />
            <br />
            <b>Your Hand</b>
        </div>
        <div class="player-dungeon dungeon col-md-8">
            <bossmonster:dungeon player="${currentPlayer}"/>
        </div>
    </div>
    <div class="decks">
        Decks
        <br />
        <bossmonster:cardPile cards="${game.roomPile}" type="room" facedown="true" pileId="roomDeck" pileName="Rooms Deck"/>
        <bossmonster:cardPile cards="${game.spellPile}" type="spell" facedown="true" pileId="spellDeck" pileName="Spells Deck"/>
        <bossmonster:cardPile cards="${game.heroPile}" type="hero" facedown="true" pileId="heroDeck" pileName="Hero Deck"/>
    </div>
</div>
<script type="text/javascript">
    $("#modalTrigger").trigger("click");
</script>



<c:if test="${triggerModal eq 'true'}">
    <script type="text/javascript">
        window.onload = () => {
        $('#selectMenu').modal('show');
    }
    </script>
</c:if>


</bossmonster:layout>
