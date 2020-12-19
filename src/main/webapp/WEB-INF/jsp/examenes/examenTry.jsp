<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="examenTry">
    <h2>Examen <c:out value="${examen.titulos}"/></h2>
    </br>
    <table class="table table-striped">
            <c:forEach var="pregunta" items="${examen.preguntas}" varStatus="loop">
            <tr> 
            <th>Pregunta <c:out value="${loop.index + 1}"/></th>

            <td><b href="preguntas/${pregunta.id}"><c:out value="${pregunta.contenido}"/></b></td>

            <td>
        	<form:select path="respuesta<c:out value="${pregunta.id}"/>">
            	<form:options itemValue="id" itemLabel="texto" items="${opciones[loop.index]}" />
            </form:select>
        	</td>
        	</tr>
     		</c:forEach>
     </table>
     <p>
    	<a href="/examenes/${examen.id}/newTry" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Send Answers</a>
    </p>
</petclinic:layout>