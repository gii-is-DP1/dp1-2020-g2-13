<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="perfil">

    <h2>Mi perfil</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${usuario.nombre} ${usuario.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>Localidad</th>
            <td><c:out value="${usuario.localidad}"/></td>
        </tr>
        <tr>
            <th>Colegio</th>
            <td><c:out value="${usuario.colegio}"/></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${usuario.email}"/></td>
        </tr>
        <tr>
            <th>Logros</th>
            <td>
            <ul>
           	 <c:forEach items="${usuario.logros}" var="logro">
           	 	<li>${logro.nombre}</li>
           	 </c:forEach>
            </ul>
			</td>
        </tr>
        <tr>
            <th>Exámenes realizados</th>
            <td>
            <ul>
           	 <c:forEach items="${usuario.examenes}" var="examen">
           	 	<li>${examen.titulos}</li>
           	 </c:forEach>
            </ul>
			</td>
        </tr>
    </table>

</petclinic:layout>
