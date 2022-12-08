<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>

<bossmonster:layout pageName="achievements">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            Update achievements of <c:out value="${owner.firstName} ${owner.lastName}"/>
        </h2>
        <form:form modelAttribute="owner"
                   class="form-horizontal">            
            <div class="form-group has-feedback">                
                <form:checkboxes path="achievements" items="${availableAchievements}" delimiter="<br/>"/>   
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                    
                    <button class="btn btn-default" type="submit">Update Achievements</button>                
                </div>
            </div>
        </form:form>                
    </jsp:body>
</bossmonster:layout>