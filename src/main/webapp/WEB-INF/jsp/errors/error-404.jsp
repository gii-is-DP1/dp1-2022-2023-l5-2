<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="error">

    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}"/>

    <h2>Something happened...</h2>

    <p><h1>404</h1> Sorry, the page you are looking for could not be found.</p>

    <a class="btn btn-default" href="${previousUrl}">Go Back</a>


</bossmonster:layout>