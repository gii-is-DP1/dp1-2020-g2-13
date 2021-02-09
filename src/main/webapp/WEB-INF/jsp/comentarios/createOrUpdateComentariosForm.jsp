<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="comentario">
	<h2>
		<c:if test="${comentario['new']}">Nuevo </c:if>
		Comentario
	</h2>
	<form:form modelAttribute="comentario" class="form-horizontal"
		id="add-comentario-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Contenido" name="contenido" />
			<c:if test="${not empty cita}">
				<input value="${cita}" name="cita" type=hidden>
			</c:if>
			<input value="${hilo.id}" name="hilo" type=hidden>
            <input name="usuario" type=hidden value="${usuario.id}">
             <input name="version" type=hidden value="${comentario.version}"> 
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${comentario['new']}">
						<button class="btn btn-default" type="submit">Añadir comentario</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							comentario</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>
