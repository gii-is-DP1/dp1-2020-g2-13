<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="usuarios">
	<h2>Usuarios</h2>
	<table id="usuariosTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 40%;">Nombre</th>
				<th style="width: 20%;">Localidad</th>
				<th style="width: 20%;">Colegio</th>
				<th style="width: 20%;">Email</th>

				<th></th>
				<th></th>
				<th></th>



			</tr>
		</thead>
		<tbody>
			<c:forEach items="${usuarios}" var="usuarios">
				<tr>
					<td><c:out value="${usuarios.nombre} " />
					<c:out value="${usuarios.apellidos}" /></td>
					<td><c:out value="${usuarios.localidad}" /></td>
					<td><c:out value="${usuarios.colegio}" /></td>
					<td><c:out value="${usuarios.email}" /></td>


					<td><a href="/usuarios/${usuarios.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					<td><a href="/usuarios/${usuarios.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>

					<td><a href="usuarios/${usuarios.id}/perfil"> <span
							class="glyphicon glyphicon-search" aria-hidden="true"></span>
					</a></td>


				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 
	<p>
		<a href="/usuarios/new" class="btn  btn-success"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add
			Usuario</a>
	</p> -->


</petclinic:layout>