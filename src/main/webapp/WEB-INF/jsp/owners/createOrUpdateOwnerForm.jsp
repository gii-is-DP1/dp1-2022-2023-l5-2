<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="users">
    <h2>
        <c:if test="${user['new']}">New </c:if> User
    </h2>
    <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
        <div class="form-group has-feedback">
            <bossmonster:inputField label="First Name" name="firstName"/>
            <bossmonster:inputField label="Last Name" name="lastName"/>
            <bossmonster:inputField label="Address" name="address"/>
            <bossmonster:inputField label="City" name="city"/>
            <bossmonster:inputField label="Telephone" name="telephone"/>
            <bossmonster:inputField label="Username" name="user.username"/>
            <bossmonster:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${owner['new']}">
                        <button class="btn btn-default" type="submit">Add Owner</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Owner</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</bossmonster:layout>
