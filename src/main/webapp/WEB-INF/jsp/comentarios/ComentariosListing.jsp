<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="comentarios">
   	<a href="/hilos">
   		<button class="btn btn-default">Volver a la lista de hilos</button>
   	</a>
   	<c:if test="${suscrito}">
	   	<a href="/hilos/${hilo.id}/unsubscribe">
	   		<button class="btn btn-default">Eliminar suscripci�n</button>
	   	</a>
   	</c:if>
   	<c:if test="${!suscrito}">
	   	<a href="/hilos/${hilo.id}/subscribe">
	   		<button class="btn btn-default">Suscribirse al hilo</button>
	   	</a>
   	</c:if>
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
    				<td>${comentarios.usuario.nombre}</td>
    				<td>${comentarios.contenido}</td>

					<td><a href="/hilos/${hilo.id}/${comentarios.id}/new"> <span
							class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</a></td>
					<c:if test="${authority.equals('admin')}">
						<td><a href="/hilos/${hilo.id}/delete/${comentarios.id}"> <span
								class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</a></td>
					</c:if>
					
					
				</tr>
				<tr>
					<c:set var="comentarios" value="${comentarios}" scope="request"/>
					<c:set var="arrows" value="" scope="request"/>
    				<jsp:include page="RecursividadComentario.jsp"/>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<a href="/hilos/${hilo.id}/new" class="btn  btn-success"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>Añadir comentario</a>
	</p>
	

</petclinic:layout>