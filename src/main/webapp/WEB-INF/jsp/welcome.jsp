<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">

	<h1>
	Bienvenido
	</h1>  
	
	<h2>
	El Proyecto Golden5
	</h2>

	<p>
	El proyecto Golden5 tiene como objetivo crear, durante tres años (2004-2007), un programa educativo dirigido al profesorado que le ayude a tener un ambiente escolar mas agradable y efectivo en el aula. Este proyecto está fundamentado en la creencia de que las escuelas tienen importantes responsabilidades en la educación de los futuros ciudadanos y en el desarrollo de actitudes saludables hacia ellos mismos, los otros y la sociedad, teniendo especialmente en cuenta que los niños en riesgo de exclusión social necesitan una atención especial. Los docentes necesitan ser competentes dirigiendo estrategias y mejorando el desarrollo social de los niños y niñas, y de las clases como un grupo. En este sentido, el programa Golden5 propone un principio, unas áreas y unos pasos claves que ayudan a conseguirlo.
	</p>
	
	<br>
	<h2>
	El Equipo
	</h2>

	<p>
	El equipo implicado en este proyecto tiene una larga experiencia previa en proyectos relacionados con problemas de comportamiento, y con problemas de convivencia, abordando siempre la diversidad dentro del aula. Nuestro objetivo ha sido organizar e integrar nuestros antecedentes y nuestra formación educativa en un enfoque sistémico llamado programa GOLDEN.
	</p>
	
	<br>
	<h2>
	Implementación
	</h2>
	
	<p>
	El programa y la estrategia serán experimentadas, analizadas e implementadas por el profesorado a partir de un curso de formación (Resultados). Se creará una página Web y una red donde se difundirán el programa, los cursos, etc. El programa se extenderá hacia la formación inicial del profesorado y otros agentes educativos, que podrá ser presentado como curso a nivel europeo (Comenius 2.2).
	</p>
<!--     <img class="img-responsive" src="https://www.us.es/sites/default/files/logoPNG_3.png"/>


    

    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>
    -->
</petclinic:layout>
