<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="usuarios">
    <h2>Usuarios</h2>
        <table id="usuariosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 20%;">Nombre</th>
            <th style="width: 80%;">Apellidos</th>
            <th style="width: 80%;">Localidad</th>
            <th style="width: 80%;">Colegio</th>
            <th style="width: 80%;">Email</th>


        </tr>
        </thead>
        <tbody>
        <c:forEach items="${usuarios}" var="usuarios">
            <tr>
                <td>
                    <c:out value="${usuarios.nombre}"/>
                </td>
                <td>
                    <c:out value="${usuarios.apellidos}"/>
                </td>
                <td>
                    <c:out value="${usuarios.localidad}"/>
                </td>
                <td>
                    <c:out value="${usuarios.colegio}"/>
                </td>
                <td>
                    <c:out value="${usuarios.email}"/>
                </td>
                

            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>