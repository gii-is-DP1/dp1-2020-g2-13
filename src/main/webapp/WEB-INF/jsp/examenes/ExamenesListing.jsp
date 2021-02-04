<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="examenes">
	<h2>Ex�menes creados</h2>
	<table id="examenesTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Examen</th>
				<th style="width: 40%;">Puntuaci�n m�xima</th>
				<th style="width: 40%;">Puntuaci�n m�nima</th>
				<th></th>
				<th></th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${examenes}" var="examenes">
				<tr>
					<td><a href="/examenes/${examenes.id}/newTry"> <c:out
								value="${examenes.titulos}" /></a></td>
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
			</c:forEach>
		</tbody>
	</table>

	<p>
		<a href="/examenes/new" class="btn  btn-success"><span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span>A�adir Examen</a>

	</p>

</petclinic:layout>