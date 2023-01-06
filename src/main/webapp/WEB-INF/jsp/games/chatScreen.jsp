<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="chatScreen">

<spring:url value="/games/${gameId}" htmlEscape="true" var="game"/>

  <h1>Chat</h1>
  <table class="table table-striped">
    <tbody>
      <c:forEach items="${messages}" var="message">
        <b class="textoGordo">
          <img class="avatar" src="${message.sender.avatar}"/>
          <c:out value="${message.sender.nickname}: "/>
      </b>
      <c:out value="${message.words}"/>
      <br>
      </c:forEach>
      <form class="form-horizontal" method="post" accept-charset="UTF-8">
        <div class="form-group">
            <div class="float-left">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="chatId" value="${chatActual}"/>
                <input name="words"/>
                <input class="btn btn-default" type="submit" value="Send">
                </div>
            </div>
        </form>
    </tbody>
  </table>
  <div>
  <a class="btn btn-title" href="${game}">Back</a>
</div>
</bossmonster:layout>

<style>
    .avatar{
        width:  35px;
        height: 35px;
        border-radius: 50px;
    }
    .textoGordo{
      font-size: medium;
    }
</style>
  