<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="joinGameRoom">
<body>
    <spring:url value="/" htmlEscape="true" var="homeScreen"/>

<h1>Join Game</h1>

<form class="form-horizontal" method="post">

<div class="form-group">
   <div class="col-sm-offset-2 col-sm-10">
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

   <label>Room Code</label>
    <input name="roomCode"/>
   <br/>
       <label>Spectate?</label>
    <input type="checkbox" name="spectate"/>
        <br/>
       <input class="btn btn-default" type="submit" value="Join"/>
    <a class="btn btn-default" href="${homeScreen}">Cancel</a>
   </div>
</div>

</form>
</body>
<style>
    body{
            background-image: url("/resources/images/backgrounds/joinGameBackGround.png");
            background-size: cover;
            background-position: center top;
        }
</style>

</bossmonster:layout>
