<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="createGameLobby">

<c:out value="${message}"/>

<h1>Create Game</h1>

<form:form modelAttribute="gameLobby" class="form-horizontal">


<div class="form-group has-feedback">

        <form:label path="maxPlayers">Number of players: </form:label>
            2 <form:radiobutton path="maxPlayers" value="2"/>
            3 <form:radiobutton path="maxPlayers" value="3"/>
            4 <form:radiobutton path="maxPlayers" value="4"/>
            <form:errors path="maxPlayers"></form:errors>

</div>

<div class="form-group">
   <div class="col-sm-offset-2 col-sm-10">
    <button class="btn btn-default" type="submit">Create</button>
    <a class="btn btn-default" href="/">Cancel</a>
   </div>
</div>

</form:form>

</bossmonster:layout>
