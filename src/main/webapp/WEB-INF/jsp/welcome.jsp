<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<bossmonster:layout pageName="home">

    <spring:url value="/resources/images/pets.png" htmlEscape="true" var="homeScreen"/>
    <spring:url value="/lobby/new" htmlEscape="true" var="newLobby"/>
    <spring:url value="/lobby/" htmlEscape="true" var="joinLobby"/>
    <spring:url value="/users/${userName}" htmlEscape="true" var="editUser"/>
    <spring:url value="/" htmlEscape="true" var="spectateLobby"/>


    <body class="inicio">

            <img src="${homeScreen}"/>

            <div class="centered-view">
                <a class="btn btn-title" href="${newLobby}">Create Game</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${joinLobby}">Join Game</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${editUser}">User Management (Provisional)</a>
            </div>

            <div class="centered-view">
                <a class="btn btn-title" href="${spectateLobby}">Spectate Game</a>
            </div>

    </body>

    <style>
        .centered-view {
            margin: 25px;
            border-radius: 30px;
        }
    </style>
            
</bossmonster:layout>

