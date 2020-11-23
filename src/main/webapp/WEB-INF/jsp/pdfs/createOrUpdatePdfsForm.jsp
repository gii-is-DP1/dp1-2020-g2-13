<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="documentos">
    <h2>
        <c:if test="${pdf['new']}">New </c:if> Documento
    </h2>
    <form:form modelAttribute="pdf" class="form-horizontal" id="add-pdf-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="File" name="archivo"/>          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${pdf['new']}">
                        <button class="btn btn-default" type="submit">Añadir documento</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Editar documento</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 