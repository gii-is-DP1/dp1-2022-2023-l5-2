<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="editUser">

<spring:url value="admin/users" htmlEscape="true" var="avatar"/>

<h1>User Management</h1>

<form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
    <div class="form-group has-feedback">
        <form:hidden path = "username" value = "${user.username}"/>
        <bossmonster:inputField label="Password" name="password"/>
        <bossmonster:inputField label="Nickname" name="nickname"/>
        <bossmonster:inputField label="Email" name="email"/>
        <bossmonster:inputField label="Description" name="description"/>
        <form:label class="col-sm-2" path="avatar">Available avatars: </form:label>
        <table>
            <tr>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_00.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_00.png"/></td>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_01.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_01.png"/></td>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_02.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_02.png"/></td>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_03.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_03.png"/></td>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_04.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_04.png"/></td>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_05.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_05.png"/></td>
                <td><img src="http://localhost:8080/resources/images/avatars/avatar_06.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_06.png"/></td>  
            </tr>
        </table>
        <form:errors path="avatar"></form:errors>
    </div>
    <div class="form-group">
        <button class="btn btn-default" type="submit">Save Changes</button>
    </div>
    <div class="form-group">
        <button class="btn btn-default" type="reset">Reset Changes</button>
    </div>
    <div class="form-group">
        <a class="btn btn-default" href="/admin/users?page=0">Back</a>
    </div>
</form:form>

<style>
    .btn{
        width:150px;
    }
</style>

</bossmonster:layout>