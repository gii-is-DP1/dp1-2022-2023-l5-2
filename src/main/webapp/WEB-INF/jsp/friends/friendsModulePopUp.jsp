<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="friendsModule">
    <h1>Friends</h1>
    <table>
        <tr>
            <td>
                <c:forEach items="${friends}" var="friends">
                    <li>
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        <c:out value="${friends}" />
                    </li>
                </c:forEach>
            </td>
        </tr>
    </table>
</bossmonster:layout>