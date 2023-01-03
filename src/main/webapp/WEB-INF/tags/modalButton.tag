<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="modalId" required="true" rtexprvalue="true"%>
<%@ attribute name="style" required="false" rtexprvalue="true"%>

<a data-toggle="modal" data-target="#${modalId}" class="${style}">
    <jsp:doBody/>
</a>
