<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<bossmonster:layout pageName="home">

    <spring:url value="/resources/images/pets.png" htmlEscape="true" var="homeScreen"/>
    <spring:url value="/lobby/new" htmlEscape="true" var="newLobby"/>
    <spring:url value="/lobby/" htmlEscape="true" var="joinLobby"/>
    <spring:url value="/users/edit" htmlEscape="true" var="editUser"/>
    <spring:url value="/users/new" htmlEscape="true" var="newUser"/>
    <spring:url value="/users/statistics" htmlEscape="true" var="statistics"/>
    <spring:url value="/statistics/achievements" htmlEscape="true" var="achievements"/>
    <spring:url value="/statistics/achievements/me" htmlEscape="true" var="myAchievements"/>
    <spring:url value="/admin/users" htmlEscape="true" var="adminOptions"/>
    <spring:url value="/users/friends/" htmlEscape="true" var="friendModule"/>
    <spring:url value="/games/listCurrentGames" htmlEscape="true" var="currentGames"/>



    <body class="inicio" background="/resources/images/background6.png">

            <!-- <img src="${homeScreen}"/> -->

            <div class="centered-view">
                <a class="btn btn-title" href="${newLobby}">Create Game</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${joinLobby}">Join Game</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${editUser}">User Management</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${newUser}">Create New User</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${statistics}">Statistics</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${myAchievements}">My Achievements</a>
            </div>

            <sec:authorize access="hasAuthority('admin')">
                <div class="centered-view">
                    <a class="btn btn-title" href="${achievements}">Achievements</a>
                </div>
            </sec:authorize>

            <div class="centered-view">
                <a class="btn btn-title" href="${friendModule}">Friends</a> <!-- Dirección provisional -->
            </div>
            <sec:authorize access="hasAuthority('admin')">
                <div class="centered-view">
                    <a class="btn btn-title" href="${adminOptions}">Admin Options</a>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAuthority('admin')">
                <div class="centered-view">
                    <a class="btn btn-title" href="${currentGames}">Current Games</a>
                </div>
            </sec:authorize>

    </body>

    <style>
        .centered-view {
            margin: 25px;
            border-radius: 30px;
        }
    </style>
            
</bossmonster:layout>

