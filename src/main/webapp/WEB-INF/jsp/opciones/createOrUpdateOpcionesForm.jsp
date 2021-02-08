<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="preguntas">
    <h2>
        <c:if test="${opcion['new']}">New </c:if> Nueva Opción
    </h2>
    <form:form modelAttribute="opcion" class="form-horizontal" id="add-opcion-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Texto" name="texto"/>      
            <form:checkbox path="esCorrecta" label="Es la correcta" name="esCorrecta"/>  
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${opcion['new']}">
                        <button class="btn btn-default" type="submit">Add Option</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Option</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 