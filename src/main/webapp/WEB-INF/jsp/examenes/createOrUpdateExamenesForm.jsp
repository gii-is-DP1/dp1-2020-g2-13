<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="examenes">
    <h2>
        <c:if test="${examen['new']}">New </c:if> Examen
    </h2>
    <form:form modelAttribute="examen" class="form-horizontal" id="add-examen-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Titulos" name="titulos"/>
            <petclinic:inputField label="Puntuacion Maxima" name="puntuacionMaxima"/>
             <petclinic:inputField label="Puntuacion Minima" name="puntuacionMinima"/>
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
                    <c:when test="${examen['new']}">
                        <button class="btn btn-default" type="submit">Add Examen</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Examen</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 