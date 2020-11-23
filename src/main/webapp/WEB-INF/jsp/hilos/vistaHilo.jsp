<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="hilo">
	<h1>${hilo.nombre}</h1>
	<p>${hilo.contenido}</p>
	<c:forEach items="${comentarios}" var="comentarios">
		<h4>${comentarios.usuario.nombre}</h4> 
		<p>${comentarios.contenido}</p>
	</c:forEach>

	<form:form modelAttribute="comentario" class="form-horizontal"
		id="add-comentario-form">
		<div class="form-group has-feedback">
			<div class="form-group">
            	<label class="col-sm-2 control-label">Escritor</label>
            	<div class="col-sm-10">
				<form:select path="usuario">
            		<form:options itemValue="id" itemLabel="nombre" items="${usuarios}" />
            	</form:select>
            	</div>                        
            </div>          
			<petclinic:inputField label="Contenido" name="contenido" />
			<input value="${hilo.id}" hidden=true name="hilo"></input>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Comentar</button>
			</div>
		</div>
	</form:form>

</petclinic:layout>