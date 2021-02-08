<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="perfil">

	<a href="/usuarios">
		<button class="btn btn-default">Volver a Lista de Usuarios</button>
	</a>
	
			<a href="/intentos/${usuario.id}">
			<button class="btn btn-default">Ver intentos</button>
	</a>

	<c:if
		test="${usuario.user.username.equals(usuarioLoggeado.user.username)}">
		<h2>Mi perfil</h2>
	</c:if>

	<c:if
		test="${!usuario.user.username.equals(usuarioLoggeado.user.username)}">
		<h2>Perfil de ${usuario.nombre}</h2>
	</c:if>


	<table class="table table-striped">
		<tr>
			<th>Nombre</th>
			<td><b><c:out value="${usuario.nombre} ${usuario.apellidos}" /></b></td>
		</tr>
		<tr>
			<th>Localidad</th>
			<td><c:out value="${usuario.localidad}" /></td>
		</tr>
		<tr>
			<th>Colegio</th>
			<td><c:out value="${usuario.colegio}" /></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><c:out value="${usuario.email}" /></td>
		</tr>
		<tr>
			<th>Logros</th>
			<td><c:forEach var="logro" items="${usuario.logros}">
					<c:out value="${logro.nombre}" />
					</br>
				</c:forEach>
			</td>
		</tr>
		
		
	</table>

	<c:if
		test="${!usuario.user.username.equals(usuarioLoggeado.user.username)}">
		<a href="/mensajesPrivados/${usuario.id}">
			<button class="btn btn-default">Mensaje privado</button>
		</a>
	</c:if>

	<c:if
		test="${authority.equals('admin') && !visitingUserAuthority.equals('admin')}">
		<a href="/usuarios/${usuario.id}/hacerAdministrador">
			<button class="btn btn-default">Hacer administrador</button>
		</a>
	</c:if>

	<c:if
		test="${usuario.user.username.equals(usuarioLoggeado.user.username)}">
		<c:if test="${authority.equals('registrado')}">
			<a href="/usuarios/mejorarCuenta">
				<button class="btn btn-default">Mejorar cuenta</button>
			</a>
		</c:if>
		<c:if test="${authority.equals('admin')}">
			<a href="/usuarios/quitarAdministrador">
				<button class="btn btn-default">Quitar administrador</button>
			</a>
		</c:if>
	</c:if>

</petclinic:layout>