<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="global_statistics">

<spring:url value="/" htmlEscape="true" var="welcome"/>
<spring:url value="/users/statistics" htmlEscape="true" var="individual"/>

    <h1>Global Statistics</h1>
    <table>
    <h2>Games:</h2>
    <tr>
        <th>Total:</th>
        <td> <c:out value="${totalGames}"/> games</td>
    </tr>
    <tr>
        <th>Min:</th>
        <td> <c:out value="${minPartidas}"/> games</td>
    </tr>
    <tr>
        <th>Max:</th>
        <td> <c:out value="${maxPartidas}"/> games</td>
    </tr>
    <tr>
        <th>Average:&nbsp;</th>
        <td> <c:out value="${promedioNumPartidas}"/> games</td>
    </tr>
</table>

<table>
    <h2>Duration:</h2>
    <tr>
        <th>Min:</th>
        <td> <c:out value="${minDuracion}"/> hours</td>
    </tr>
    <tr>
        <th>Max:</th>
        <td> <c:out value="${maxDuracion}"/> hours</td>
    </tr>
    <tr>
        <th>Average:&nbsp;</th>
        <td> <c:out value="${promedioDuracion}"/> hours</td>
    </tr>
</table>
<table>
    <h2>Players:</h2>
    <tr>
        <th>Average:&nbsp;</th>
        <td> <c:out value="${promedioJugadoresPartida}"/> players</td>
    </tr>
</table>
<div class="centered-view">
    <a class="btn btn-title" href="${individual}">Individual Statistics</a>
    <a class="btn btn-title" href="${welcome}">Back</a>
</div>
<div class="float-right">
    <a class="buttonChat" href="/chat/0">
        <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
    </a> 
</div>

<style>
    .buttonChat{
        width: 50px;
        height: 50px;
        background-color: gray;
        font-size: x-large;
        padding-top: 12px;
        border-radius: 30px;
        color: lightgray;
        margin-bottom: 10px;
        margin-top: 10px;
        float: right;
        text-align: center;
    }
</style>
</bossmonster:layout>