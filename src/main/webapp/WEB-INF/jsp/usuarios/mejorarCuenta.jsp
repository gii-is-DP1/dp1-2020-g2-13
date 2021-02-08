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
        Mejorar cuenta
    </h2>
    <h3>
    	Cuota no c qué, datos bancarios y no c qué más
    </h3>
    <a href="mejorarCuenta/confirma">
        <button class="btn btn-default" type="submit">Mejorar cuenta</button>
    </a>
    <a href="empeorarCuenta">
        <button class="btn btn-default" type="submit">Empeorar cuenta</button>
    </a>
</petclinic:layout> 