<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="PdfVisualizacion">

<a href="/pdfs">
		   		<button class="btn btn-default">Volver a Lista de pdfs</button>
		</a>

    <h2>${pdf.nombre}</h2>
    
    <embed src="${pdf.link}" width="800" height="800">    

</petclinic:layout>