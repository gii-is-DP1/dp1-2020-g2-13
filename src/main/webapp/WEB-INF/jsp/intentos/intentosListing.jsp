<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="perfil">

	<a href="usuarios/${usuario.id}/perfil">
		<button class="btn btn-default">Volver al perfil del usuario</button>
	</a>

	<h2>Intentos del usuario <c:out value="${usuario.nombre}" /></h2>

	<table class="table table-striped">
		<c:forEach var="intento" items="${intentos}" varStatus="loop">
			<tr>
				<th>Intento del examen: <c:out value="${titulos[loop.index]}" /></th>
				
				<td><c:forEach var="respuesta" items="${respuestas[loop.index]}" varStatus="loop2">
						<p>Respuesta a la pregunta <c:out value="${respuesta.numeroPregunta}" /></p>
						<c:out value="${respuesta.textoRespuesta}" />
						</br>
					</c:forEach></td>
				
			</tr>
		</c:forEach>
	</table>

</petclinic:layout>