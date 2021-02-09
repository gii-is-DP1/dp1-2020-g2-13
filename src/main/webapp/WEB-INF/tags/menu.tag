<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<nav class="navbar navbar-default" role="navigation">
	 
		<script>
		$.get("/cogerNotificaciones", function( data ) {
			if (data == 0){
				  $( ".result" ).html("");
			}
			else {
				  $( ".result" ).html( data );
			}
			});
		window.setInterval(function(){
			$.get("/cogerNotificaciones", function( data ) {
				if (data == 0){
					  $( ".result" ).html("");
				}
				else {
					  $( ".result" ).html( data );
				}
				});
		}, 5000);
		</script>
	
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

<!-- 				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>
				 

				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Find owners</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarians</span>
				</petclinic:menuItem>
				
				
				<petclinic:menuItem active="${name eq 'logros'}" url="/logros"
                    title="logro" dropdown="${true}">
                        <ul class="dropdown-menu">
                            <li>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/logros" />">Logros</a>
                                        </div>
                                    </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/logros" />">Logros2</a>
                                        </div>
                                    </div>
                            </li>
                        </ul>
                </petclinic:menuItem>
				-->
				
				<petclinic:menuItem active="${name eq 'contenido'}" url="/pdfs"
                    title="contenido" dropdown="${true}">
                        <ul class="dropdown-menu">
                            <li>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/pdfs" />">Documentos</a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/videos" />">Vídeos</a>
                                        </div>
                                    </div>
                            </li>
                        </ul>
                </petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'herramientas'}" url="/herramientas"
                    title="herramientas" >
                        <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Herramientas</span>
                </petclinic:menuItem>
                
                   <petclinic:menuItem active="${name eq 'examenes'}" url="/examenes"
                    title="examenes" >
                        <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Exámenes</span>
                </petclinic:menuItem>
                       
				
				<petclinic:menuItem active="${name eq 'foro'}" url="/hilos"
                    title="foro" dropdown="${true}">
                        <ul class="dropdown-menu">
                            <li>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/hilos" />">Hilos</a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/usuarios" />">Usuarios</a>
                                        </div>
                                    </div>
                            </li>
                        </ul>
                </petclinic:menuItem>
 		
				<!--
				<petclinic:menuItem active="${name eq 'pdfs'}" url="/pdfs"
                    title="pdf" dropdown="${true}">
                        <ul class="dropdown-menu">
                            <li>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/pdfs" />">PDF</a>
                                        </div>
                                    </div>
                            </li>
                        </ul>
                </petclinic:menuItem>

                <petclinic:menuItem active="${name eq 'usuarios'}" url="/usuarios"
                    title="usuario" dropdown="${true}">
                        <ul class="dropdown-menu">
                            <li>
                                    <div class="row">
                                        <div class="text-center">
                                                <a href="<c:url value="/usuarios" />">Usuarios</a>
                                        </div>
                                    </div>
                            </li>
                        </ul>
                </petclinic:menuItem>


				<petclinic:menuItem active="${name eq 'logros'}" url="/logros"
					title="ver logros disponibles">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Logros</span>
				</petclinic:menuItem>-->
				
				<!--<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>-->

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/usuarios/new" />">Registro</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>Â 
							<strong><sec:authentication property="principal.username" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
							
							<strong class="result"></strong>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/usuarios/miPerfil" />"
													class="btn btn-primary btn-block btn-sm">Mi perfil</a>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
											<p class="text-left">
												<a href="<c:url value="/notificaciones" />"
													class="btn btn-primary btn-block btn-sm">Notificaciones</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
