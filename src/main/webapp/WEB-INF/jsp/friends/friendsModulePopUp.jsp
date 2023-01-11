<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="friendsModule">

<spring:url value="/users/friends/new" htmlEscape="true" var="newFriend"/>
<spring:url value="/users/friends/notAccepted" htmlEscape="true" var="notAcceptedRequest"/>
<spring:url value="/users/friends/connected" htmlEscape="true" var="connected"/>
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
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
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
                    <td> 
                        <a href="${user.username}/delete"> 
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </a>      
                    </td>
                </tr>   
        </c:forEach>
        </tbody>
    </table>
    <div class="centered-view">
        <a class="btn btn-title" href="${newFriend}">Add new Friend</a>
        <a class="btn btn-title" href="${notAcceptedRequest}">Pending Requests</a>
        <a class="btn btn-title" href="${connected}"> Connected Friends</a>
    </div>
    <div class="centered-view">
        <a class="btn btn-title" href="${welcome}">Back</a>
    </div>
</div>
</bossmonster:layout>
