<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="friendsModule">

<spring:url value="/users/friends/" htmlEscape="true" var="friendList"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>
<div class="white-panel">
    <h1>Friends</h1>
    <table class="table">
        <thead class="thead-light">
            <tr>
                <th>Avatar</th>
                <th>Username</th>
                <th>Nickname</th>
                <th>Description</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${notAcceptedRequests}" var="notAccepted">
                <tr>
                    <td>
                        <img src="${notAccepted.requester.avatar}" height ="80" width="100"/>
                    </td>
                    <td>
                        <c:out value="${notAccepted.requester.username}"/>
                    </td>
                    <td>
                        <c:out value="${notAccepted.requester.nickname}"/>
                    </td>
                    <td>
                        <c:out value="${notAccepted.requester.description}"/>
                    </td>
                    <td> 
                        <a href="/users/friends/notAccepted/${notAccepted.requester.username}/delete"> 
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </a>
                        <a href="/users/friends/notAccepted/${notAccepted.requester.username}"> 
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </a>       
                    </td>
                </tr>   
        </c:forEach>
        </tbody>
    </table>
    <table class="button-table">
        <tr asd>
            <td class="form-group button-table-column">
                <a class="btn btn-title" href="${friendList}">Friend List</a>
            </td>
            <td class="form-group button-table-column">
                <a class="btn btn-title" href="${welcome}">Back</a>            
            </td>
        </tr>
    </table>
</div>

<style>
    .btn{
        width:150px;
    }
    .button-table{
        height: 70px;
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }
    .button-table-column{
        width:200px;
        vertical-align: bottom;
    }
</style>

</bossmonster:layout>
