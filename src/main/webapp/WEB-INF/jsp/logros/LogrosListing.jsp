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

            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>