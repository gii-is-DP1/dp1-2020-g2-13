<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="mejorarCuenta">
    <h2>
        Cambiar el nivel de la cuenta
    </h2>
    <h3>
    	Para acceder a ciertas funcionalidades de la página es necesario pagar. En esta página habría una opción de pago mediante la api de paypal, pero aún no está implementado, por lo que pondremos botones de prueba.
    </h3>
    
    <c:if test="${nivel.equals('admin')}">
        <p>Los administradores de la página no pueden bajar ni subir el nivel.</p>
	</c:if>
    
    <c:if test="${nivel.equals('registrado')}">
        <a href="mejorarCuenta/confirma">
        	<button class="btn btn-default" type="submit">Subir de nivel la cuenta</button>
    	</a>
	</c:if>
	
	<c:if test="${nivel.equals('pagado')}">
        <a href="empeorarCuenta">
        	<button class="btn btn-default" type="submit">Bajar de nivel la cuenta</button>
    	</a>
	</c:if>
    
</petclinic:layout> 