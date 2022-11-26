<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="card" required="true" rtexprvalue="true" type="org.springframework.samples.bossmonster.game.card.Card"%>


<spring:url value="${card.cardImage}" var="image"/>

<img src="${image}" class="card"/>
