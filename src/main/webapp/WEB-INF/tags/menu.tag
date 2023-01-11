<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="bossmonster" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
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

				<bossmonster:menuItem active="${name eq 'home'}" url="/"
					title="Home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</bossmonster:menuItem>

				<bossmonster:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="Games" dropdown="${true}">
					<ul class="dropdown-menu">
                        <li>
                            <a href="<c:url value="/lobby/new" />">
                                <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>
                                Create a game
                            </a>
                        </li>
                        <li class="divider"/>
                        <li>
                            <a href="<c:url value="/lobby/" />">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                Join a game
                            </a>
                        </li>
                        <li class="divider"/>
                        <li>
                            <a href="<c:url value="/lobby/listCurrentGames" />">
                                <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
                                View active games
                            </a>
                        </li>
                    </ul>
                </bossmonster:menuItem>

				<bossmonster:menuItem active="${name eq 'achievements'}" url="/statistics/achievements/"
					title="Achievements" dropdown="${true}">										
						<ul class="dropdown-menu">
							<li>
								<a href="<c:url value="/statistics/achievements/" />">
                                    <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
                                    Achievements listing
                                </a>
							</li>
							<li class="divider"></li>
							<li>
                                <a href="<c:url value="/statistics/achievements/" />">
                                    <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span>
                                    My Achievements
                                </a>
                            </li>
						</ul>					
				</bossmonster:menuItem>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>�
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
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
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>

                            <li>
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="/users/edit" class="btn btn-primary btn-block">My Profile</a>
												<a href="/users/friends/" class="btn btn-success btn-block">My Friends</a>
                                                <a href="/users/statistics" class="btn btn-danger btn-block">My Statistics</a>
                                            </p>
										</div>
									</div>
								</div>
							</li>

						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
