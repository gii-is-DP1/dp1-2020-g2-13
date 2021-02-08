<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="cuestionarios">
    
    <h2>Herramientas</h2>
        <table id="HerramientasTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Nombre</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${surveys}" var="survey">
            <tr>
                <td>
                    <a href='http://www.golden5.org/limesurvey_3/${survey.sid}?lang=es'><c:out value="${survey.surveyls_title}"/></a>
                </td>
               
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    
    
</petclinic:layout>