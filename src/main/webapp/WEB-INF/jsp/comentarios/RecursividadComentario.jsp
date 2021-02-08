<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<c:forEach var="comentarios" items="${comentarios.citas}">
	<c:forEach begin="1" end="${comentarios.nivel}">
		<c:set var="arrows" value="->${arrows}" scope="request"/>
	</c:forEach>
    <td>${arrows} ${comentarios.usuario.nombre}</td>
    <td>${comentarios.contenido}</td>
    <td><a href="/hilos/${hilo.id}/${comentarios.id}/new"> <span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>
	</a></td>
    <td><a href="/hilos/${hilo.id}/edit/${comentarios.id}"> <span
			class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
	</a></td>
	<td><a href="/hilos/${hilo.id}/delete/${comentarios.id}"> <span
			class="glyphicon glyphicon-trash" aria-hidden="true"></span>
	</a></td>
	<tr>
		<c:set var="arrows" value="" scope="request"/>
		<c:set var="comentarios" value="${comentarios}" scope="request"/>
		<jsp:include page="RecursividadComentario.jsp"/>
	</tr>
</c:forEach>