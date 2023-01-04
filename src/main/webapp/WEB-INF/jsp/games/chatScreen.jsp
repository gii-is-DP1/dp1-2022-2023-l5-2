<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<div class="float-right">
    <button class="buttonChat" type="button" class="btn btn-primary" data-toggle="modal" data-target="#chatModal">
        <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
      </button>
      <div class="modal fade" id="chatModal" tabindex="-1" role="dialog" aria-labelledby="chatModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
          <div class="modal-content">
            <div class="modal-header" style="background-color: rgba(22, 216, 38, 0.664);">
              <h5 class="modal-title" id="chatModalLabel" style="font-size: medium;">Chat</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body" id="chatBody" style="background-color: rgba(11, 179, 11, 0.919);">
              <div class="container-fluid">
                <div class="row">
                    <div>
                        <c:forEach items="${messages}" var="message">
                            <b class="textoGordo">
                                <c:out value="${message.sender.username}: "/>
                            </b>
                            <c:out value="${message.words} "/>
                            <br>
                        </c:forEach>
                    </div>
                </div>
              </div>
            </div>
            <div class="modal-footer" style="background-color: rgba(30, 223, 46, 0.586)">   
            <form class="form-horizontal" method="post" accept-charset="UTF-8">
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="chatId" value="${chatActual}"/>
                        <input name="words"/>
                        <input class="btn btn-default" type="submit" value="Send">
                            <!--<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>-->
                        </div>
                    </div>
                </form>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" style="background-color: rgba(22, 216, 38, 0.664);">Cerrar</button>
            </div>
          </div>
        </div>
      </div>
</div>

<style>
    .modal-lg {
      max-width: 800px;
    }
    
    .modal-body {
      max-height: calc(100vh - 210px);
      overflow-y: auto;
    }
    .buttonChat{
        width: 50px;
        height: 50px;
        background-color: gray;
        font-size: x-large;
        padding-top: 12px;
        border-radius: 30px;
        color: lightgray;
        margin-bottom: 10px;
        margin-top: 10px;
        float: right;
        text-align: center;
    }
</style>
  