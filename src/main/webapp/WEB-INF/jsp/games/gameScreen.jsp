<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags"%>


<bossmonster:layout pageName="gameScreen">

    <bossmonster:modal modalId="selectMenu" modalName="${game.state.subPhase.choiceMessage}" unclosable="true">
        <form method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <c:set value="${game.choice}" var="choice"/>
            <c:if test="${not empty choice}">
                <div class="expandable">
                    <c:forEach begin="0" end="${fn:length(choice)-1}" var="index">
                        <button class="invis" value="${index}" name="choice" ${!game.state.subPhase.isValidChoice(index,game)?'disabled':''}>
                        <bossmonster:card card="${choice[index]}" style="${!game.state.subPhase.isValidChoice(index,game)?'disabled':''}"/>
                        </button>
                    </c:forEach>
                </div>
            </c:if>
                <c:if test="${game.state.subPhase.isOptional()}">
                    <button class="btn btn-default btn-lg" name="choice" value="-1">
                        <c:out value="${not empty game.previousChoices?'Cancel':'Pass'}"/>
                    </button>
                </c:if>

        </form>
    </bossmonster:modal>

<div class="gameContainer" style="padding:10px;">
    <spring:url value="/games/{gameId}/chat" var="chatUrl">
        <spring:param name="gameId" value="${game.id}"/>
    </spring:url>
    <a href="${chatUrl}" class="buttonChat btn btn-primary">
        <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
    </a>

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
            <b><c:out value = "${game.state.phase.displayName}"/></b>
            <br>
            <c:out value = "${game.state.subPhase.contextualMessage.apply(game)}"/>
            <br>
        </div>
        <div class="col-md-5 opponents-dungeon">
            <c:forEach begin="0" end="${fn:length(players)-1}" var="index">
                <div class="dungeon dungeon${index} row">
                    <bossmonster:dungeon player="${players[index]}"/>
                </div>
            </c:forEach>
        </div>
    </div>
    <c:if test="${not empty currentPlayer}">
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
    </c:if>
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
