<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags" %>
<bossmonster:layout pageName="createUser">
<spring:url value="/users/friends/" htmlEscape="true" var="cancel"/>
<div class="white-panel">
    <h1>Add New Friend</h1>

    <form:form modelAttribute="friendRequest" class="form-horizontal">
            <input  type="hidden"name="requester" value="${friendRequest.requester.username}"/>
            <bossmonster:inputField label="Friend username" name="receiver"/> 

        <table class="button-table">
            <tr asd>
                <td class="form-group">
                    <button class="btn btn-default" type="submit">Send Friend Request</button>
                </td>
                <td class="form-group">
                    <a class="btn btn-title" href="${cancel}">Cancel</a>              
                </td>
            </tr>
        </table>

    </form:form>
</div>

<style>
    .btn{
        width:200px;
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

</bossmonster:layout>