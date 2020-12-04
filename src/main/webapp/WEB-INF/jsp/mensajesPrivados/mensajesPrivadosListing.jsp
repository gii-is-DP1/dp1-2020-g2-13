<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="mensajesPrivados">
    <h2>Mensajes privados con ${receptor.nombre}</h2>
        <table id="mensajesPrivadosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Emisor</th>
            <th style="width: 80%;">Contenido</th>

            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mensajesPrivados}" var="mensajePrivado">
            <tr>
                <td>
                    <c:out value="${mensajePrivado.emisor.nombre}"/>
                </td>
                <td>
                    <c:out value="${mensajePrivado.contenido}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/mensajesPrivados/${receptor.id}/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Enviar mensaje</a>
    </p>

</petclinic:layout>