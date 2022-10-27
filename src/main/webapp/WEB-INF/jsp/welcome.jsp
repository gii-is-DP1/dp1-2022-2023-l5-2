<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">

    <style>
        .inicio{
            background-color:rgba(255, 255, 127, 0.759);
            color:brown;
        }
        .prueba{
            background-color:rgb(63, 8, 244);
        }
        .hola{
            padding: 0.85em 1.5em;
            font-size: 1 em;
            cursor: pointer;
            text-decoration: none;
            color: #ffffff;
            background-color: #2bff5d;
            border: 1px solid #222;
            border-radius: 0;
        }
        .botonimagen{
        background-image:url(src\main\webapp\WEB-INF\jsp\welcome.jsp);
        background-repeat:no-repeat;
        height:100px;
        width:100px;
        background-position:center;
        }

    </style>

    <body class="inicio">
        <div class="prueba">
            <button class="botonimagen" type="submit"></button>
            <spring:url value="src\main\resources\static\resources\images\homeScreen.jpg" htmlEscape="true" var="homeScreen"/>
            <img src="${homeScreen}"/>
            <button class="botonimagen" type="submit"></button>
        </div>
        <div class="prueba">
            <a class="btn btn-default" href="/lobby/">Create Game</a>
        </div>
        <div>
            <a class="btn btn-default" href="/">Join Game</a>  
        </div>
        <div>
            <a class="btn btn-default" href="/">Exit</a>  
        </div>
        <div>
            <a href="">
                <button class="botonimagen" type="submit"></button>
            </a>
        </div>
    </body>
        
</petclinic:layout>





