<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="preguntas">
    <h2>Preguntas</h2>
        <table id="preguntasTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Contenido</th>
            <th style="width: 80%;">Tipo de contenido</th>

            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${preguntas}" var="preguntas">
            <tr>
                <td>
                    <c:out value="${preguntas.contenido}"/>
                </td>

                <td>

                	<a href="/preguntas/${preguntas.id}/edit">

                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
                <td>

                	<a href="/preguntas/${preguntas.id}/delete">

                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/preguntas/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Question</a>
    </p>

</petclinic:layout>