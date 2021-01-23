<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="videoVisualize">

<a href="/videos">
		   		<button class="btn btn-default">Volver a Lista de Vídeos</button>
		</a>

	<h2>${video.nombre}</h2>
	<iframe width="560" height="315"
		src="${embedLink}"
		allowfullscreen></iframe>

</petclinic:layout>