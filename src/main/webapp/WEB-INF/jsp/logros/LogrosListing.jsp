<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="logros">
    <h2>Logros</h2>
        <table id="logrosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Nombre</th>
            <th style="width: 80%;">Descripción</th>

            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${logros}" var="logros">
            <tr>
                <td>
                    <c:out value="${logros.nombre}"/>
                </td>
                <td>
                    <c:out value="${logros.descripcion}"/>
                </td>

                <td>

                	<a href="/logros/${logros.id}/edit">

                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
                <td>

                	<a href="/logros/${logros.id}/delete">

                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/logros/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Goal</a>
    </p>

</petclinic:layout>