<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="perfil">

    <h2>Detalles del examen</h2>


    <table class="table table-striped">
        <tr>
            <th>Títulos</th>
            <td><b><c:out value="${examen.titulos}"/></b></td>
        </tr>
        <tr>
            <th>Puntuación Máxima</th>
            <td><c:out value="${examen.puntuacionMaxima}"/></td>
        </tr>
        <tr>
            <th>Puntuación Mínima</th>
            <td><c:out value="${examen.puntuacionMinima}"/></td>
        </tr>
        <tr>
            <th>Preguntas</th>
             <c:forEach var="pregunta" items="${examen.preguntas}">
    			<td>
            		 <td>
            		 <c:out value="${pregunta.contenido}"/>
  					 <c:out value="${pregunta.tipoContenido}"/>
            		 </td>
            	</td>
     		</c:forEach>
        </tr>
    </table>
    
   

</petclinic:layout>