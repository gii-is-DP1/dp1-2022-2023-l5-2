<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="joinGameRoom">

<c:out value="${message}"/>

<h1>Join Game</h1>

<form:form modelAttribute="gameRoom" class="form-horizontal">


<div class="form-group has-feedback">
        <input type="hidden" name="id" value="${gameRoom.id}"/>
        <bossmonster:inputField name="code" label="Room code" />
</div>

<div class="form-group">
   <div class="col-sm-offset-2 col-sm-10">
    <button class="btn btn-default" type="submit">Join</button>
    <a class="btn btn-default" href="/">Cancel</a>
   </div>
</div>

</form:form>

</bossmonster:layout>
