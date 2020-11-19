<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="pdfs">
    <h2>Pdfs</h2>
        <table id="pdfsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 100%;">Pdf</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pdfs}" var="pdfs">
            <tr>
                <td>
                    <c:out value="${pdfs.archivo}"/>
                </td>

                <td>

                	<a href="/pdfs/${pdfs.id}/edit">

                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
                <td>

                	<a href="/pdfs/${pdfs.id}/delete">

                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <p>
    	<a href="/pdfs/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add PDF</a>
    </p>

</petclinic:layout>