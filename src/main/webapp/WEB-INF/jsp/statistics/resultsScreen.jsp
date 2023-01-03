<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="resultsScreen">
    <h1>Game Results</h1>
    <div class="row">
        <div class="col-md-6">
            <h3>Participants</h3>
            <ul>
                <c:forEach items="${result.participants}" var="user">
                    <li>
                        <img class="slot img-circle" src="${user.avatar}"/>
                        <c:if test="${user eq result.winner}">
                            <spring:url value="/resources/images/Crown.png" var="crown"/>
                            <img src="${crown}"/>
                        </c:if>
                        <b><c:out value="${user.nickname}"/></b>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-6">
            <h3>Statistics</h3>
            <ul>
                <li><b>Played on:</b><c:out value=" ${result.date}"/></li>
                <li><b>Duration:</b> <c:out value="${result.minutes}"/> minutes</li>
                <li><b>Total rounds:</b><c:out value=" ${result.rounds}"/></li>
                <li><b>Souls:</b><c:out value=" ${result.souls}"/></li>
                <li><b>Health:</b><c:out value=" ${result.healths}"/></li>
            </ul>

        </div>
    </div>
</bossmonster:layout>
