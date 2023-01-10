<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="createGameLobby" backgroundImage="/resources/images/backgrounds/createGameBackground.png">

    <body class="inicio">

        <c:out value="${message}"/>
    <div class="white-panel">
        <h1>Create Game</h1>

        <form:form modelAttribute="gameLobby" class="form-horizontal">

        <div class="form-group">
            <form:label class="radio-inline" path="maxPlayers"><b>Number of players:</b> </form:label>
            <label class="radio-inline">
                <form:radiobutton class="" path="maxPlayers" value="2"/> 2
            </label>
            <label class="radio-inline">
                <form:radiobutton class="" path="maxPlayers" value="3"/> 3
            </label>
            <label class="radio-inline">
                <form:radiobutton class="" path="maxPlayers" value="4"/> 4
            </label>

            <form:errors path="maxPlayers"></form:errors>
        </div>

        <div class="form-group">
            <div class="col-sm-2">
            <button class="btn btn-default" type="submit">Create</button>
            <a class="btn btn-default" href="/">Cancel</a>
            </div>
        </div>

        </form:form>
    </div>
    </body>

</bossmonster:layout>
