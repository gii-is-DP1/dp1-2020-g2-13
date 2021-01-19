<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="videos">
	<h2>Videos</h2>
	<table id="usuariosTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 40%;">Nombre</th>
				<th style="width: 20%;">Descripcion</th>
				<th style="width: 20%;">Duracion</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${videos}" var="videos">
				<tr>
					<td><a href="/videos/${videos.id}/visualize">${videos.nombre}</a></td>
					
					<td><c:out value="${videos.descripcion}" /></td>
					<td><c:out value="${videos.duracion}" /></td>
					<c:if test="${videos.usuario.user.username.equals(usuario.user.username) 
					|| authority.equals('admin')}">
					<td><a href="/videos/${videos.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></td>
					<td><a href="/videos/${videos.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></td>
					</c:if>
					
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>
		<a href="/videos/new" class="btn  btn-success"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>Añadir
			Video</a>
			
		<a href="/videos">
		   		<button class="btn btn-default">Volver a Lista de Videos</button>
		</a>
	</p>

</petclinic:layout>