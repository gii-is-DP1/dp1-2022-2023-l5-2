<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="hero" required="true" rtexprvalue="true" type="org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon"%>

<div class="heroSlot">
    <span class="badge heroHealth health">
        <c:out value="${hero.healthInDungeon}"/>
    </span>
    <bossmonster:card card="${hero.heroCard}" style="hero"/>
</div>
