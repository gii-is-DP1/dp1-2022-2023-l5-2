<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="editUser">

<spring:url value="/users/avatar" htmlEscape="true" var="avatar"/>

<h1>User Management</h1>

<form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
    <div class="form-group has-feedback">
        <bossmonster:inputField label="User Name" name="username"/> 
        <bossmonster:inputField label="Password" name="password"/>
        <bossmonster:inputField label="Nickname" name="nickname"/>
        <bossmonster:inputField label="Email" name="email"/>
        <bossmonster:inputField label="Description" name="description"/>
    </div>
    <div class="centered-view">
        <a class="btn btn-title" href="${avatar}">Change Avatar</a>
    </div>
    <div class="form-group">
        <button class="btn btn-default" type="submit">Update Owner</button>
    </div>
</form:form>

</bossmonster:layout>