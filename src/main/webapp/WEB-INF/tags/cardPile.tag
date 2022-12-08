<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="cards" required="true" rtexprvalue="true" type="java.util.List"%>
<%@ attribute name="type" required="false" rtexprvalue="true"%>
<%@ attribute name="pileId" required="true" rtexprvalue="true"%>
<%@ attribute name="pileName" required="true" rtexprvalue="true"%>
<%@ attribute name="cardStyle" required="false" rtexprvalue="true"%>
<%@ attribute name="facedown" required="false" rtexprvalue="true"%>

<c:choose>
    <c:when test="${not empty cards}">

        <c:set var="displayedCard" value="${cards[fn:length(cards)-1]}"/>

        <bossmonster:modalButton modalId="${pileId}" style="${facedown == true? 'disabled':''}">
            <bossmonster:card card="${displayedCard}" facedown="${facedown == true}" type="${type}"/>
        </bossmonster:modalButton>

        <bossmonster:modal modalId="${pileId}" modalName="${pileName}" style="${pile-modal}">
            <div class="expandable">
                <c:forEach items="${cards}" var="card">
                    <bossmonster:card card="${card}" style="${cardStyle}"/>
                </c:forEach>
            </div>
        </bossmonster:modal>

    </c:when>
    <c:otherwise>
        Empty
    </c:otherwise>
</c:choose>


