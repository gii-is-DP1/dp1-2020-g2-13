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
        <c:if test="${pregunta['new']}">New </c:if> Pregunta
    </h2>
    <form:form modelAttribute="pregunta" class="form-horizontal" id="add-pregunta-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Contenido" name="contenido"/>          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${pregunta['new']}">
                        <button class="btn btn-default" type="submit">Add Question</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Question</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 