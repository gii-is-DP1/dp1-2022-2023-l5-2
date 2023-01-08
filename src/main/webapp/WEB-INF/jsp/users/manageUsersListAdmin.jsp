<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="manageUser">

<spring:url value="/" htmlEscape="true" var="welcome"/>

    <h2>User Management</h2>

    <table id="usersListTable" class="table table-striped">
        <thead>
            <tr>
                <th>Username</th>
                <th>Nickname</th>
                <th>Email</th>
                <th>Description</th>
                <th>Avatar</th>
                <th>Password</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${user}" var="user">
                <tr>
                    <td>
                        <c:out value="${user.username}"/>
                    </td>
                    <td>
                        <c:out value="${user.nickname}"/>
                    </td>
                    <td>
                        <c:out value="${user.email}"/>
                    </td>
                    <td>
                        <c:out value="${user.description}"/>
                    </td>
                    <td>
                        <img src="${user.avatar}" height ="80" width="100"/>
                    </td>
                    <td>
                        <c:out value="${user.password}"/>
                    </td>
                    <td>
                        <a href="users/${user.username}/edit"> 
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </a>
                    </td>
                    <td> 
                        <a href="users/${user.username}/delete"> 
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </a>      
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <table>
        <tr>
            <td>
                <c:choose>
                    <c:when test="${param.page=='0'}">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true" style="color:gray"></span>
                    </c:when>    
                    <c:otherwise>
                        <a href="users?page=${param.page - 1}"> 
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <a href="users?page=${param.page + 1}"> 
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
    </table>
    <div class="centered-view">
        <a class="btn btn-title" href="${welcome}">Back</a>
    </div>
    <style>
        table{
            margin-right: auto;
            margin-left: auto;
        }
    </style>
</bossmonster:layout>