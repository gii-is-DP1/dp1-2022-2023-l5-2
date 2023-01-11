<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="editUser">

<spring:url value="/users/edit/avatars" htmlEscape="true" var="avatar"/>

<div class="white-panel">
    <h1>User Management</h1>

    <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
        <div class="form-group has-feedback">
            <form:hidden path = "username" value = "${user.username}" />
            <form:hidden path = "password" value = "${user.password}"/>
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
        <table class="button-table">
            <tr asd>
                <td class="form-group button-table-column">
                    <button class="btn btn-default" type="submit">Save Changes</button>
                </td>
                <td class="form-group button-table-column">
                    <button class="btn btn-default" type="reset">Reset Changes</button>              
                </td>
                <td class="form-group button-table-column">
                    <a class="btn btn-default" href="/">Cancel</a>            
                </td>
            </tr>
        </table>
    </form:form>
</div>

<style>
    .btn{
        width:150px;
    }
    td{
        width:300px;
    }
    .button-table{
        height: 70px;
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }
    .button-table-column{
        vertical-align: bottom;
    }
</style>

</bossmonster:layout>