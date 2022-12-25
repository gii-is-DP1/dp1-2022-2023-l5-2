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

<div class="test gameContainer">
    <div class="test player-hand">
        <bossmonster:cardPile cards="${currentPlayer.hand}" pileId="hand" pileName="Your Hand" />
        <br />
        Your Hand
    </div>
    <div class="test player-dungeon">
        <bossmonster:dungeon player="${currentPlayer}"/>
    </div>
    <div class="test opponents-dungeons">
        <div class="test dungeon1">
            <bossmonster:dungeon player="${players[0]}"/>
        </div>
        <div class="test dungeon3">
            <bossmonster:dungeon player="${players[1]}"/>
        </div>
        <div class="test dungeon2">
            <bossmonster:dungeon player="${players[2]}"/>
        </div>
        <div class="test dungeon4">
        </div>
    </div>
    <div class="test phase-display">
        <b><c:out value = "${game.state.phase}"/></b>
        <b><c:out value = "${game.state.subPhase}"/></b>

        <b><c:out value = "${game.currentPlayer.user.nickname}'s Turn"/></b>
    </div>
    <div class="test city-and-discard">
        <div class="test hero1">
            <bossmonster:cardPile cards="${bagHeroes}" pileId="bagPile" pileName="Thief Hero Pile" />
        </div>
        <div class="test hero2">
            <bossmonster:cardPile cards="${swordHeroes}" pileId="swordPile" pileName="Warrior Hero Pile" />
        </div>
        <div class="test hero3">
            <bossmonster:cardPile cards="${crossHeroes}" pileId="crossPile" pileName="Cleric Hero Pile" />
        </div>
        <div class="test hero4">
            <bossmonster:cardPile cards="${bookHeroes}" pileId="bookPile" pileName="Mage Hero Pile" />
        </div>
        <div class="test discard">
            <bossmonster:cardPile cards="${game.discardPile}" pileId="discardPile" pileName="Discard Pile" />
        </div>
    </div>
    <div class="test decks">
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
