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
    </table>
    </br>
    </br>
    </br>
    <h3>Preguntas</h3>
    <table class="table table-striped">
   		
            <c:forEach var="pregunta" items="${examen.preguntas}" varStatus="loop"> 
            <tr> 
            <th>Pregunta <c:out value="${loop.index + 1}"/></th>
            <td><b><c:out value="${pregunta.contenido}"/></b></td>
             <td>
             <c:forEach var="opcion" items="${opciones[loop.index]}">
             <c:out value="${opcion.texto}"/></br>
        	 </c:forEach>
        	 </td>
        	 </tr>
     		 </c:forEach>
     </table>
   

</petclinic:layout>