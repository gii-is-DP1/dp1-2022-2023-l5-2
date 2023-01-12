<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="newInvite">
<div class="white-panel">
  <h1>New Invitation</h1>
<form class="form-horizontal" method="post" accept-charset="UTF-8">
    <div class="form-group">
        <div class="float-left">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <label class="control-label col-md-2">
                Username
            </label>
            <div class="col-md-10">
                <input class="form-control" name="username"/>
            </div>
            <input class="btn btn-default" type="submit" value="Send">
            </div>
        </div>
    </form>
</div>
</bossmonster:layout>
