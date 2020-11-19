<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="hilos">
    <h2>Hilos</h2>
        <table id="hilosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Nombre</th>
            <th style="width: 80%;">Categoria</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hilos}" var="hilos">
            <tr>
                <td>
                    <c:out value="${hilos.nombre}"/>
                </td>
                <td>
                    <c:out value="${hilos.categoria}"/>
                </td>

                <td>

                	<a href="/hilos/${hilos.id}/edit">

                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
                <td>

                	<a href="/hilos/${hilos.id}/delete">

                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/hilos/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Thread</a>
    </p>

</petclinic:layout>