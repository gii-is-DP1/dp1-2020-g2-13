<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="examenTry">
    <h2>Pregunta <c:out value="${numero_pregunta+1}"/> de <c:out value="${size}"/>:</h2>
    </br>
    <p style="font-size:18px"><c:out value="${pregunta}"/></p>
    </br>
    <table class="table table-striped">
    
   	<form:form modelAttribute="respuesta" class="form-horizontal" id="add-opcion-form">
        <div class="form-group has-feedback">
        	<c:forEach items="${opciones}" var="opcion">
            <form:radiobutton path="textoRespuesta" value="${opcion.texto}"/>  <c:out value="${opcion.texto}"/>
            </br>
        </c:forEach>
        </div>
        <input name="numeroPregunta" type=hidden value="${numero_pregunta}">
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button href="examenes/${examen.id}/${intento.id}/newTry" class="btn btn-default" type="submit">Send Answer</button>
            </div>
        </div>
    </form:form>
    
    
    
</petclinic:layout>
