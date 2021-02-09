<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="examenes">
	<h2>Exámenes creados</h2>
	<table id="examenesTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Examen</th>
				<th style="width: 40%;">Puntuación máxima</th>
				<th style="width: 40%;">Puntuación mínima</th>
				<th></th>
				<th></th>

			</tr>
		</thead>
		<tbody>
			<c:set var="i" value='0'/>
			<c:forEach items="${examenes}" var="examenes">
				<tr>
					<c:if test="${realizable[i] eq 't' }">
						<td><a href="/examenes/${examenes.id}/newTry"> <c:out
								value="${examenes.titulos}" /></a></td>
					</c:if>
					<c:if test="${realizable[i] eq 'f' }">
						<td><c:out
								value="${examenes.titulos}" /></td>
					</c:if>
					<td><c:out value="${examenes.puntuacionMaxima}" /></td>
					<td><c:out value="${examenes.puntuacionMinima}" /></td>


					<c:if
						test="${examenes.usuario.equals(usuario) || 
					authority.equals('admin')}">
						<td><a href="/examenes/${examenes.id}/edit"> <span
								class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
						</a></td>
						<td><a href="/examenes/${examenes.id}/delete"> <span
								class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</a></td>
						<td><a href="/examenes/${examenes.id}/details"> <span
								class="glyphicon glyphicon-search" aria-hidden="true"></span>
						</a></td>
					</c:if>



				</tr>
				<c:set var="i" value="${i + 1}"/>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<a href="/examenes/new" class="btn  btn-success"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>Añadir Examen</a>

	</p>

</petclinic:layout>