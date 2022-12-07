<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="modalId" required="true" rtexprvalue="true"%>
<%@ attribute name="modalName" required="true" rtexprvalue="true"%>
<%@ attribute name="style" required="false" rtexprvalue="true"%>

<div id="${modalId}" class="modal fade in">
    <div class="${style}">
        <c:out value="${modalName}"/>
        <a class="btn btn-default" data-dismiss="modal">x</a>
        <br/>
        <jsp:doBody/>
    </div>
</div>
