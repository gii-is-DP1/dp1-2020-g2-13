<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="hilo">
    <h2>${hilo.nombre}</h2>
    <p>${hilo.contenido}</p>
    
    <form:form modelAttribute="comentario" class="form-horizontal" id="add-comentario-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Contenido" name="contenido"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Comentar</button>
            </div>
        </div>
    </form:form>

</petclinic:layout>