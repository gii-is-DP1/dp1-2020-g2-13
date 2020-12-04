<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="comentarios">
	<h2>${hilo.nombre}</h2>
	<table id="hilosTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Usuario</th>
				<th style="width: 80%;">Contenido</th>
				<th></th>
				<th></th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${comentarios}" var="comentarios">
				<tr>
					<td><c:out value="${comentarios.usuario.nombre}" /></td>
					<td><c:out value="${comentarios.contenido}" /></td>

					<td><a href="/comentarios/${hilo.id}/${comentarios.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="/comentarios/${hilo.id}/${comentarios.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<a href="/comentarios/${hilo.id}/new" class="btn  btn-success"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Comment</a>
	</p>

</petclinic:layout>