<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="manageUser">
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
                        <c:out value="${user.avatar}"/>
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

</bossmonster:layout>