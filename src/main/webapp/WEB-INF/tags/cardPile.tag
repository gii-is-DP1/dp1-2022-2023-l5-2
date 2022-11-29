<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<%@ attribute name="cards" required="true" rtexprvalue="true" type="java.util.List"%>
<%@ attribute name="pileId" required="true" rtexprvalue="true"%>
<%@ attribute name="pileName" required="true" rtexprvalue="true"%>

<c:set var="firstCard" value="${cards[0]}"/>

<a data-toggle="modal" data-target="#${pileId}">
    <bossmonster:card card="${firstCard}"/>
</a>

<div id="${pileId}" class="modal fade in">
    <div class="pile-modal">
            <c:out value="${pileName}"/>
            <a class="btn btn-default" data-dismiss="modal">x</a>
            <br/>
        <div class="expandable">
            <c:forEach items="${cards}" var="card">
                <bossmonster:card card="${card}"/>
            </c:forEach>
        </div>
    </div>
</div>


