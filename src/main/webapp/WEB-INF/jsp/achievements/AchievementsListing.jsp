<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="achievements">
<spring:url value="/users/statistics" htmlEscape="true" var="global"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>

<div class="white-panel">
    <h2>Achievements</h2>
    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Image</th>
            <th>Threshold</th>
            <th>Metric</th>
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
    <a class="btn btn-default" href="/statistics/achievements/new">Create new achievement</a>
    <div class="centered-view">
        <a class="btn btn-title" href="${global}">My Statistics</a>
        <a class="btn btn-title" href="${welcome}">Back</a>
    </div>
</div>

</bossmonster:layout>

