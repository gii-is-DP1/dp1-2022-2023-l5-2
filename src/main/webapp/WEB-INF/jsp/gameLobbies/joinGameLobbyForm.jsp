<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<body>
<bossmonster:layout pageName="joinGameRoom">

    <spring:url value="/" htmlEscape="true" var="homeScreen"/>

<div class="white-panel">
<h1 class="panel-heading">Join Game</h1>

<form class="form-horizontal panel-body" method="post">

   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="form-group">
        <label class="control-label col-md-2">Room Code:</label>
        <div class="col-md-10">
            <input class="form-control" name="roomCode"/>
        </div>
    </div>
   <div class="form-group">
       <div class="checkbox col-sm-offset-1">
           <label class="control-label">
               <input type="checkbox" name="spectate"/>Spectate?
           </label>
       </div>
   </div>
   <div class="form-group">
       <input class="btn btn-default" type="submit" value="Join"/>
       <a class="btn btn-default" href="${homeScreen}">Cancel</a>
   </div>
</form>
</div>
<style>
    body{
            background-image: url("/resources/images/backgrounds/joinGameBackGround.png");
            background-size: cover;
            background-position: center top;
        }
</style>

</bossmonster:layout>
</body>
