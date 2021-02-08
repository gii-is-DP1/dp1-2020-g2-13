<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/stop.png" var="petsImage"/>
    <img src="${petsImage}"/>

    <h2>Usted no tiene permiso para acceder a esta funcionalidad</h2><br>
    <h2> Por favor contacte con el servicio técnico y consulte cómo obtener dichos privilegios.</h2>

   

</petclinic:layout>