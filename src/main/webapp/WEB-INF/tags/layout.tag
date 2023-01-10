<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>
<%@ attribute name="backgroundImage" required="false"%>

<!doctype html>
<html>
<bossmonster:htmlHeader/>

<body>

<c:set var="backgroundImage" value="${(empty backgroundImage) ? '/resources/images/backgrounds/emptyBackground.png' : backgroundImage}" />

<bossmonster:bodyHeader menuName="${pageName}"/>

<div class="container-fluid">
    <div class="container xd-container">
	<c:if test="${not empty message}" >
	<div class="alert alert-${not empty messageType ? messageType : 'info'}" role="alert">
  		<c:out value="${message}"></c:out>
   		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
  		</button> 
	</div>
	</c:if>

        <jsp:doBody/>
    </div>
</div>
<bossmonster:footer/>
<jsp:invoke fragment="customScript" />

</body>

<style>
    body{
            background-image: url("${backgroundImage}");
            background-size: cover;
            background-position: center top;
        }
</style>

</html>
