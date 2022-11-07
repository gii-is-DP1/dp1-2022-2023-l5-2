<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="StatisticsScreen">

<c:out value="${message}"/>
<table id="achievementsTable" class="table table-striped"></table>

</bossmonster:layout>
<h2>Statistics</h2>
    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Alias</th>
        </tr>
        <tr>
            <th>Victories</th>
            <th>TotalGames</th>
            <th>WinRate</th>
            <th>WinStreak</th>
        </tr>
        <tr> 
            <th>AverageDuration</th>
            <th>FinalBoss</th>
            <th>SpellCard</th>
        </tr>
        </thead>

        
    </table>

