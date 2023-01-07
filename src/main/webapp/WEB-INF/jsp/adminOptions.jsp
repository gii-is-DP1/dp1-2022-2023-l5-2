<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<bossmonster:layout pageName="Admin options">

    <spring:url value="/statistics/achievements" htmlEscape="true" var="achievements"/>
    <spring:url value="/admin/users" htmlEscape="true" var="userManagement"/>
    <spring:url value="/lobby/listCurrentGames" htmlEscape="true" var="currentGames"/>
    <spring:url value="/statistics/listPlayedGames" htmlEscape="true" var="playedGames"/>
    <spring:url value="/" htmlEscape="true" var="welcome"/>


    <body class="inicio">
        <br>
        <br>
        <br>
        <br>
        <br>
        <div class="centered-view">
        <sec:authorize access="hasAuthority('admin')">
            
                <a class="cuteButton1" href="${achievements}">
                    <span class="glyphicon glyphicon-star" aria-hidden="true">
                        <div class="text1">Achievements</div> 
                    </span>
                </a>
            
        </sec:authorize>
        <sec:authorize access="hasAuthority('admin')">
            
                <a class="cuteButton2" href="${userManagement}">
                    <span class="glyphicon glyphicon-user" aria-hidden="true">
                        <div class="text1">User Management</div> 
                    </span>
                </a>
            
        </sec:authorize>
        <sec:authorize access="hasAuthority('admin')">
            
                <a class="cuteButton3" href="${currentGames}">
                    <span class="glyphicon glyphicon-signal" aria-hidden="true">
                        <div class="text1">Current Games</div> 
                    </span>
                </a>
            
        </sec:authorize>
        <sec:authorize access="hasAuthority('admin')">
            
                <a class="cuteButton4" href="${playedGames}">
                    <span class="glyphicon glyphicon-flag" aria-hidden="true">
                        <div class="text1">All Played Games</div> 
                    </span>
                </a>
            
        </sec:authorize>
    </div>
        
        <br>
        <br>
        <br>
        <br>
        <br>
        <div class="centered-view">
            
            <a class="btn btn-title" href="${welcome}">Back</a>
        </div>
    
    </body>



    <style>
        .centered-view {
            margin: 25px;
            border-radius: 30px;
            justify-content: space-evenly;
            flex-direction: row;
        }
        .cuteButton1{
            background-color: rgba(209, 159, 143, 0.952);
            color: black;
            text-align: center;
            font-size: xx-large;
            padding-top: 10px;
            width: 100px;
            height: 85px;
            border-radius: 10px;
            display: block;
            margin-bottom: 10px;
        }
        .cuteButton2{
            background-color: rgba(166, 212, 151, 0.966);
            color: black;
            text-align: center;
            font-size: xx-large;
            padding-top: 10px;
            width: 100px;
            height: 85px;
            border-radius: 10px;
            display: block;
            margin-bottom: 10px;
        }
        .cuteButton3{
            background-color: rgb(156, 177, 204);
            color: black;
            text-align: center;
            font-size: xx-large;
            padding-top: 10px;
            width: 100px;
            height: 85px;
            border-radius: 10px;
            display: block;
            margin-bottom: 10px;
        }
        .cuteButton4{
            background-color: rgb(192, 146, 190);
            color: black;
            text-align: center;
            font-size: xx-large;
            padding-top: 10px;
            width: 100px;
            height: 85px;
            border-radius: 10px;
            display: block;
            margin-bottom: 10px;
        }
        .text1{
            font-size: medium;
        }
        body{
            background-image: url("/resources/images/background.png");
            background-size: cover;
            background-position: center;
        }
    </style>
</bossmonster:layout>