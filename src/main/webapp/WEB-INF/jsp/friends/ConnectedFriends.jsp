<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="friendsModule">

<spring:url value="/users/friends/notAccepted" htmlEscape="true" var="notAcceptedRequest"/>
<spring:url value="/users/friends/" htmlEscape="true" var="friendList"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>
<spring:url value="/invites" htmlEscape="true" var="invitations"/>
<div class="white-panel">
    <h1>Connected Friends</h1>
    <table class="table">
        <thead class="thead-light">
            <tr>
                <th>Avatar</th>
                <th>Username</th>
                <th>Nickname</th>
                <th>Description</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${connectedFriends}" var="user">
                <tr>
                    <td>
                        <img src="${user.avatar}" height ="80" width="100"/>
                    </td>
                    <td>
                        <c:out value="${user.username}"/>
                    </td>
                    <td>
                        <c:out value="${user.nickname}"/>
                    </td>
                    <td>
                        <c:out value="${user.description}"/>
                    </td>
                </tr>   
        </c:forEach>
        </tbody>
    </table>
    <table class="button-table">
        <tr asd>
            <td class="form-group">
                <a class="btn btn-title" href="${friendList}">Friend List</a>
            </td>
            <td class="form-group">
                <a class="btn btn-title" href="${notAcceptedRequest}">Pending Requests</a>              
            </td>
            <td class="form-group">
                <a class="btn btn-title" href="${invitations}">Invitations List</a>           
            </td>
        </tr>
    </table>
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
</style>

</bossmonster:layout>