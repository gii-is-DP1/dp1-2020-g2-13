<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="usuarioHistory">
	<h2>Hilos creados por ${usuario.nombre}</h2>
	<p>
	
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


            </tr>
        </c:forEach>
        </tbody>
    </table>

	</p>
</petclinic:layout>