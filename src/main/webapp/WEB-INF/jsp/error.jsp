<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/mariosad.png" var="marioImage"/>
    <img src="${petsImage}"/>

    <h2>ERROR</h2>

</petclinic:layout>
