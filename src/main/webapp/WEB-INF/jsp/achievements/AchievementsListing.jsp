<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="achievements">
    <h2>Achievements</h2>

    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>BadgeImage</th>
            <th>Threshold</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                <td>
                    <c:out value="${achievement.name}"/>
                </td>
                <td>
                    <c:out value="${achievement.actualDescription} "/>
                </td>
                <td>
                    <c:if test="${achievement.badgeImage == ''}">none</c:if>
                    <c:if test="${achievement.badgeImage != ''}">
                        <img src="${achievement.badgeImage}" width="100px"  /> 
                    </c:if>
                </td>
                <td>
                    <c:out value="${achievement.threshold} "/> 
                </td>
                <td>
                    <a href="/statistics/achievements/${achievement.id}/edit"> 
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>                            
                    </a>       
                </td>
                <td> 
                    <a href="/statistics/achievements/${achievement.id}/delete"> 
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>      
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
