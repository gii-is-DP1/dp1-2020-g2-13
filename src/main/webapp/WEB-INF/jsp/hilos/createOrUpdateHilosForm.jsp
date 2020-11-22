<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="hilos">
    <h2>
        <c:if test="${hilo['new']}">Nuevo </c:if> hilo
    </h2>
    <form:form modelAttribute="hilo" class="form-horizontal" id="add-hilo-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Categoria" name="categoria"/>
            <petclinic:inputField label="Contenido" name="contenido"/>
			<div class="form-group">
            	<label class="col-sm-2 control-label">Creador</label>
            	<div class="col-sm-10">
				<form:select path="usuario">
            		<form:options itemValue="id" itemLabel="nombre" items="${usuarios}" />
            	</form:select>
            	</div>                        
            </div>          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${hilo['new']}">
                        <button class="btn btn-default" type="submit">Add Thread</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Thread</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 