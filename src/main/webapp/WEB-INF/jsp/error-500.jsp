<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/500.png" var="petsImage"/>
    <img src="${petsImage}"/>

    <h2>Tenemos un problema interno del sistema</h2><br>
    <h2> Porfavor acceda con el enlace correcto o contacte con el servicio técnico.</h2>

   

</petclinic:layout>