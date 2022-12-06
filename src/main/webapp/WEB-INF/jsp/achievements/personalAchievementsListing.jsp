<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="achievements">
    <h2>My Achievements</h2>

    <ul>
        <c:forEach items="${achievements}" var="achievement">
            <li>               
                    <c:out value="${achievement.name}"/>
                    <c:if test="${achievement.image == ''}">none</c:if>
                    <c:if test="${achievement.image != ''}">
                        <img src="${achievement.image}" width="100px"  /> 
                    </c:if>
            </li>                                
        </c:forEach>
    </ul>

    <h2>Available achievements</h2>
    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Image</th>
            <th>Threshold</th>
            <th>Metric</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                <td>
                    <c:out value="${achievement.name}"/>
                </td>
                <td>                    
                      <c:out value="${achievement.description} "/>                                        
                </td>
                <td>                    
                    <c:if test="${achievement.image == ''}">none</c:if>
                    <c:if test="${achievement.image != ''}">
                        <img src="${achievement.image}" width="100px"  /> 
                    </c:if>
                </td>
                
                <td>       
                    <c:out value="${achievement.threshold} "/>
                </td>
            
                <td>       
                    <c:out value="${achievement.metric} "/>
                </td>         
            </tr>
        </c:forEach>
        </tbody>
    </table>    
</bossmonster:layout>