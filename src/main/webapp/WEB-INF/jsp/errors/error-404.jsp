<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="error">

<spring:url value="/" htmlEscape="true" var="welcomeScreen"/>

<div class="white-panel">
    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}"/>

    <h2>Something happened...</h2>

    <p><h1>404 Not Found</h1> Sorry, the page you are looking for could not be found.</p>

    <a class="btn btn-default" href="${previousUrl}">Try Again</a>
    <a class="btn btn-default" href="${welcomeScreen}">Cancel</a>
</div>

<style>
    .white-panel{
        margin-left: auto;
        margin-right: auto;
        text-align: center;
        width: 70%;
    }
</style>

</bossmonster:layout>