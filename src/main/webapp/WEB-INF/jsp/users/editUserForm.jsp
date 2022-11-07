<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="editUser">

<spring:url value="/userManagement/avatar" htmlEscape="true" var="avatar"/>

<h1>User Management</h1>

<form class="form-horizontal" method="post">
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <label>User Name</label>
            <input name="username"/>

            <label>Password</label>
            <input name="password"/>

            <label>Nickname</label>
            <input name="nickname"/>

            <label>Email</label>
            <input name="email"/>

            <label>Description</label>
            <input name="description"/>
            
            <input class="btn btn-default" type="submit" value="Save"/>
            <a class="btn btn-default" href="/">Cancel</a>
        </div>
    </div>
</form>

<div class="centered-view">
    <a class="btn btn-title" href="${avatar}">Change Avatar</a>
</div>

</bossmonster:layout>