<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="videos">
    <h2>
        <c:if test="${video['new']}">Nuevo </c:if> video
    </h2>
    <form:form modelAttribute="video" class="form-horizontal" id="add-video-form">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Link" name="link"/>
            <petclinic:inputField label="Descripcion" name="descripcion"/>     
            <petclinic:inputField label="Duracion" name="duracion"/>           
            <input name="usuario" type=hidden value="${usuario.id}">
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${video['new']}">
                        <button class="btn btn-default" type="submit">Subir vídeo</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar vídeo</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 