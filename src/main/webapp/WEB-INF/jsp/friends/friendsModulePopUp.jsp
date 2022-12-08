<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="friendsModule">
    <h1>Friends</h1>
    <table class="table">
        <thead class="thead-light">
            <tr>
                <th>Avatar</th>
                <th>Username</th>
                <th>Nickname</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${friends}" var="friends">
                <tr>
                    <td>
                        <img src="${friends.avatar}" height ="80" width="100"/>
                    </td>
                    <td>
                        <c:out value="${friends.username}"/>
                    </td>
                    <td>
                        <c:out value="${friends.nickname}"/>
                    </td>
                    <td>
                        <c:out value="${friends.description}"/>
                    </td>
                </tr>   
        </c:forEach>
        </tbody>
    </table>
</bossmonster:layout>
