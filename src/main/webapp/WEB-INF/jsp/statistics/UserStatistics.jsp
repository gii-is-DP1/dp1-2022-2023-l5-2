<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="statistics">
    <h1>User Statistics</h1>
    <table>
        <tr>
            <th>Name:</th>
            <td>Pepito</td>

            <th>Nickname:</th>
            <td>xxx69</td>
        </tr>
        <tr>
            <th>Victories:</th>
            <td>50</td>

            <th>Win Rate:</th>
            <td>50%</td>


        </tr>
        <tr>
            <th>Total Games</th>
            <td>100</td>

            <th>Best Win Streak</th>
            <td>1</td>
            
            <th>Average Duration:</th>
            <td>1.23 hours</td>
        </tr>
    </table>

</bossmonster:layout>