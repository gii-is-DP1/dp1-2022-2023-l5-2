<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="joinGameRoom">

<h1>Join Game</h1>

<form class="form-horizontal" method="post">

<div class="form-group">
   <div class="col-sm-offset-2 col-sm-10">
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   <label>Room Code</label>
    <input name="roomCode"/>
       <input class="btn btn-default" type="submit" value="Join"/>
    <a class="btn btn-default" href="/">Cancel</a>
   </div>
</div>

</form>

</bossmonster:layout>
