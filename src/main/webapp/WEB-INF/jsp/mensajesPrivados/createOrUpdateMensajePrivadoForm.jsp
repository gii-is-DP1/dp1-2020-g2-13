<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="mensajesPrivados">
    <h2>
        <c:if test="${mensajePrivado['new']}">Nuevo </c:if> Mensaje Privado para ${receptor.nombre}
    </h2>
    <form:form modelAttribute="mensajePrivado" class="form-horizontal" id="add-mensajePrivado-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Contenido" name="contenido"/>
            <input name="emisor" type=hidden value="${emisor.id}">
            <input name="receptor" type=hidden value="${receptor.id}">
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${mensajePrivado['new']}">
                        <button class="btn btn-default" type="submit">Enviar mensaje privado</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar mensaje privado</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 