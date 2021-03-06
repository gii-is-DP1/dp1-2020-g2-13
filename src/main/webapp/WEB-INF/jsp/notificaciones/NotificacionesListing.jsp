<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="comentarios">
	<h2>Notificaciones de ${usuario.nombre}</h2>
	<table id="notificacionesTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 50%;">Notificación</th>
				<th style="width: 50%;">Vista previa</th>
				<th></th>
				<th></th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${notificaciones}" var="notificacion">
				<tr>
					<td>
					<c:if test="${not empty notificacion.mensajePrivado}">
						Mensaje privado de  
						<a href="/mensajesPrivados/${notificacion.mensajePrivado.emisor.id}">
						<c:out value="${notificacion.mensajePrivado.emisor.nombre}" />
						</a> 
					</c:if>
					<c:if test="${not empty notificacion.comentario}">
						Comentario en
						<a href="/hilos/${notificacion.comentario.hilo.id}"> 
						<c:out value="${notificacion.comentario.hilo.nombre}" /> 
						</a>
					</c:if>
					</td>
					<td>
					<c:if test="${not empty notificacion.mensajePrivado}">
						<c:out value="${notificacion.mensajePrivado.contenido}" /> 
					</c:if>
					<c:if test="${not empty notificacion.comentario}">
						<c:out value="${notificacion.comentario.contenido}" /> 
					</c:if>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>