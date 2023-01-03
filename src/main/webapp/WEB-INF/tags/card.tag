<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="card" required="true" rtexprvalue="true" type="org.springframework.samples.bossmonster.game.card.Card"%>
<%@ attribute name="facedown" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="type" required="false" rtexprvalue="true"%>
<%@ attribute name="style" required="false" rtexprvalue="true"%>

<c:choose>
    <c:when test="${empty card}">
        <spring:url value="/resources/images/blank_card.png" var="image"/>
    </c:when>
	<c:when test="${facedown}">
	    <c:choose>
	        <c:when test="${type == 'room'}">
	            <spring:url value="/resources/images/rooms/face_down.png" var="image"/>
	        </c:when>
	        <c:when test="${type == 'spell'}">
	            <spring:url value="/resources/images/spells/face_down.png" var="image"/>
	        </c:when>
	        <c:otherwise>
	            <spring:url value="/resources/images/heroes/face_down.png" var="image"/>
	        </c:otherwise>
	    </c:choose>
	</c:when>
	<c:otherwise>
    	<spring:url value="${card.cardImage}" var="image"/>
	</c:otherwise>
</c:choose>
<img src="${image}" class="card ${style}"/>
