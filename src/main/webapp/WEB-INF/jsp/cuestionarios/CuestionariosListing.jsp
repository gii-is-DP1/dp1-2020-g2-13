<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="cuestionarios">
    <h2>Cuestionarios</h2>
        <table id="cuestionariosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Título</th>
            <th style="width: 80%;">Descripción</th>

            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cuestionarios}" var="cuestionarios">
            <tr>
                <td>
                    <c:out value="${cuestionarios.titulo}"/>
                </td>
                <td>
                    <c:out value="${cuestionarios.descripcion}"/>
                </td>

                <td>

                	<a href="/cuestionarios/${cuestionarios.id}/edit">

                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
                <td>

                	<a href="/cuestionarios/${cuestionarios.id}/delete">

                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/cuestionarios/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Añadir Cuestionario</a>
    </p>

</petclinic:layout>