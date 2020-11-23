<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="examenes">
    <h2>Exámenes creados</h2>
        <table id="examenesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Titulos</th>
            <th style="width: 80%;">Puntuacion Maxima</th>
            <th style="width: 80%;">Puntuacion Minima</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${examenes}" var="examenes">
            <tr>
                <td>
                    <c:out value="${examenes.titulos}"/>
                </td>
                <td>
                    <c:out value="${examenes.puntuacionMaxima}"/>
                </td>
                <td>
                    <c:out value="${examenes.puntuacionMinima}"/>
                </td>

                <td>

                	<a href="/examenes/${examenes.id}/edit">

                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
                <td>

                	<a href="/examenes/${examenes.id}/delete">

                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/examenes/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Exam</a>
    </p>

</petclinic:layout>