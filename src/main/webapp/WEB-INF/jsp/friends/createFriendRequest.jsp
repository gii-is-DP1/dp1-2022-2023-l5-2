<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="createUser">

    <spring:url value="/users/friends/" htmlEscape="true" var="cancel"/>

<h1>Add New Friend</h1>

<form:form modelAttribute="friendRequest" class="form-horizontal">
        <bossmonster:inputField label="Your username" name="requester"/>
        <bossmonster:inputField label="Friend username" name="receiver"/>
    <div class="form-group">
        <button class="btn btn-default" type="submit">Send Friend Request</button>
    </div>
</form:form>

<div class="centered-view">
    <a class="btn btn-title" href="${cancel}">Cancel</a>
</div>

</bossmonster:layout>