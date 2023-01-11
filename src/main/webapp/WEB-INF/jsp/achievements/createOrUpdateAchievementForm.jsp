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
    <div class="white-panel">
        <h2>
            <c:if test="${achievement['new']}">New </c:if> Achievement
        </h2>
        <form:form modelAttribute="achievement"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${achievement.id}"/>
            <div class="form-group has-feedback">                
                <bossmonster:inputField label="Name" name="name"/>
                <bossmonster:inputField label="Description" name="description"/>
                <bossmonster:inputField label="Image" name="image"/>
                <bossmonster:inputField label="Threshold" name="threshold"/>
                <bossmonster:selectField label="Metric"  name="metric" names="${metrics}" size="1"/>
            </div>
            <table class="button-table">
                <tr>
                    <td class="form-group">
                        <c:choose>
                        <c:when test="${achievement['new']}">
                            <button class="btn btn-default" type="submit">Add Achievement</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Achievement</button>
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td class="form-group">
                        <a class="btn btn-default" href="/statistics/achievements">Cancel</a>
                    </td>
                </tr>
            </table>
            </div>
        </form:form>
    </div>
    <style>
        .btn{
            width:150px;
        }
        td{
            width:300px;
        }
        .button-table{
            height: 70px;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
        }
    </style>        
    </jsp:body>
    
</bossmonster:layout>
