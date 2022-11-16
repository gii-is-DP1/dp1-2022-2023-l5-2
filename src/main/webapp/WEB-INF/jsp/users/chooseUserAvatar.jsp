<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="chooseAvatar">

    <spring:url value="/users/edit" htmlEscape="true" var="cancel"/>

<h1>Choose your avatar</h1>

<form:form modelAttribute="user" class="form-horizontal">
    <input type="hidden" name="username" value="${user.username}"/>
    <input type="hidden" name="nickname" value="${user.nickname}"/>
    <input type="hidden" name="email" value="${user.email}"/>
    <input type="hidden" name="description" value="${user.description}"/>
    <input type="hidden" name="password" value="${user.password}"/>
    <input type="hidden" name="enabled" value="${user.enabled}"/>
        <div class="form-group">
            <table>
                <form:label class="col-sm-2" path="avatar">Aviable avatars: </form:label>
                <tr>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_00.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_00.png"/></td>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_01.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_01.png"/></td>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_02.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_02.png"/></td>
                </tr>
                <tr>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_03.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_03.png"/></td>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_04.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_04.png"/></td>
                </tr>  
                <tr>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_05.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_05.png"/></td>
                    <td><img src="http://localhost:8080/resources/images/avatars/avatar_06.png" height ="80" width="100"/> <form:radiobutton path="avatar" value="http://localhost:8080/resources/images/avatars/avatar_06.png"/></td>  
                </tr>
            </table>
            <form:errors path="avatar"></form:errors>
        </div>

    <div class="form-group">
       <div class="col-sm-2">
        <button class="btn btn-default" type="submit">Choose</button>
        <a class="btn btn-default" href="${cancel}">Cancel</a>
       </div>
    </div>

</form:form>

</bossmonster:layout>