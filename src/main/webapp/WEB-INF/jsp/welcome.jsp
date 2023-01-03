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
    <spring:url value="/lobby/listCurrentGames" htmlEscape="true" var="currentGames"/>
    <spring:url value="/statistics/listPlayedGames" htmlEscape="true" var="playedGames"/>



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
            <sec:authorize access="hasAuthority('admin')">
                <div class="centered-view">
                    <a class="btn btn-title" href="${playedGames}">All Played Games</a>
                </div>
            </sec:authorize>
            <div class="buttonview">
                <a class="cuteButton" href="${friendModule}">
                    <span class="glyphicon glyphicon-user" aria-hidden="true"><div class="text1">Friends</div> </span>
                </a>
                <a class="cuteButton" href="/users/statistics/rankings/winRate">
                    <span class="glyphicon glyphicon-king" aria-hidden="true"><div class="text1">Rankings</div> </span>
                </a>
            </div>

    </body>

    <style>
        .centered-view {
            margin: 25px;
            border-radius: 30px;
        }
        .buttonview{
            float: right;
        }
        .cuteButton{
            background-color: lawngreen;
            color: black;
            text-align: center;
            font-size: xx-large;
            padding-top: 10px;
            width: 80px;
            height: 80px;
            border-radius: 10px;
            display: block;
            margin-bottom: 10px;
        }
        .text1{
            font-size: medium;
        }
    </style>
            
</bossmonster:layout>

