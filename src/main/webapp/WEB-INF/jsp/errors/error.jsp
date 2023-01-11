<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="error">

    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}"/>

    <p><h1></h1> Something happened...</p>

    <a class="btn btn-default" href="${previousUrl}">Go Back</a>

</bossmonster:layout>