<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>


<bossmonster:layout pageName="gameScreen">
<spring:url value="${card.cardImage}" var="image"/>



<img src="${image}" class="card"/>
<bossmonster:cardPile cards="${cards}" pileId="discardPile" pileName="Discard Pile"/>

</bossmonster:layout>
