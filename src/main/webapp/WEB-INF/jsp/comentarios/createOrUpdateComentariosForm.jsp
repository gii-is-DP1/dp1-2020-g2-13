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
		comentario
	</h2>
	<form:form modelAttribute="comentario" class="form-horizontal"
		id="add-comentario-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Contenido" name="contenido" />
			<input value="${hilo.id}" name="hilo" type=hidden>
			<div class="form-group">
				<label class="col-sm-2 control-label">Creador</label>
				<div class="col-sm-10">
					<form:select path="usuario">
						<form:options itemValue="id" itemLabel="nombre"
							items="${usuarios}" />
					</form:select>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${comentario['new']}">
						<button class="btn btn-default" type="submit">Add Comment</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Update
							Comment</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>
