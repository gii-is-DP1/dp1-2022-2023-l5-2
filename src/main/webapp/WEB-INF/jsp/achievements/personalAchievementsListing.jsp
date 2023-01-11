<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="achievements">
<spring:url value="/users/statistics" htmlEscape="true" var="global"/>
<spring:url value="/" htmlEscape="true" var="welcome"/>
<div class="white-panel">
    <h2>My Achievements</h2>
    <table id="achievements" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Image</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievements">
            <tr>
                <td>
                    <c:out value="${achievements.name}"/>
                </td>
                <td>                    
                      <c:out value="${achievements.description} "/>                                        
                </td>
                <td>                    
                    <c:if test="${achievements.image == ''}">none</c:if>
                    <c:if test="${achievements.image != ''}">
                        <img src="${achievements.image}" width="100px"  /> 
                    </c:if>
                </td>        
            </tr>
        </c:forEach>
        </tbody>
    </table>
  
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
        <c:forEach items="${availableAchievements}" var="availableAchievements">
            <tr>
                <td>
                    <c:out value="${availableAchievements.name}"/>
                </td>
                <td>                    
                      <c:out value="${availableAchievements.description} "/>                                        
                </td>
                <td>                    
                    <c:if test="${availableAchievements.image == ''}">none</c:if>
                    <c:if test="${availableAchievements.image != ''}">
                        <img src="${availableAchievements.image}" width="100px"  /> 
                    </c:if>
                </td>
                
                <td>       
                    <c:out value="${availableAchievements.threshold} "/>
                </td>
                <td>       
                    <c:out value="${availableAchievements.metric} "/>
                </td>         
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="centered-view">
        <a class="btn btn-title" href="${global}">My Statistics</a>
        <a class="btn btn-title" href="${welcome}">Back</a>
    </div>
</div>
</bossmonster:layout>