<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="error">

    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}"/>

    <h2>An error occured...</h2>

    <p>${exception.message}</p>

</bossmonster:layout>
