<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Recapitulatif - SmoothyTrip</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href="css/bootstrap-theme.css">
	<link rel = "stylesheet" href="css/bootstrap.css">
	<link rel = "stylesheet" href="css/custom.css">
	<link rel = "stylesheet" href="css/font-awesome.css">
	<link rel = "stylesheet" href="css/jquery.fancybox.css">
	<link rel = "stylesheet" href="css/main.css">
	<link rel = "stylesheet" href="css/owl-carousel.css">
	<link rel = "stylesheet" href="css/pace.css">
	<link rel = "stylesheet" href="css/IonicsBis.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
	<style type="text/css" id="illdy-about-section-css">#header.header-front-page {background-image: url(images/planner.jpg) !important;}#header.header-front-page .bottom-header .header-button-one {background-color: rgba( 0, 0, 0, .4 );}#header.header-front-page .bottom-header .header-button-one:hover {background-color: rgba( 0, 0, 0, .1 );}#header.header-front-page .bottom-header h1 {color: #ffffff;}#header.header-front-page .bottom-header .section-description {color: #ffffff;}</style>

</head>
<body>
	<header id="header" class="header-front-page" style ="backgroung-image:url(images/planner.jpg); background-attachment:fixed;">
		<div style="background-color:rgba(0,0,0,0.3)">
			<div class="top-header" style="background-color:rgba(0,0,0,0.3)">
			<div class="container">
				<div class="row">
					<div class="col-sm-4 col-xs-8">
	
												<a href="accueil.html" title="SmoothyTrip - Accueil">
								<h3>Smoothy <br> Trip</h3>
							</a>
						
					</div><!--/.col-sm-2-->
					<div class="col-sm-8 col-xs-4">
						<nav class="header-navigation" >
						<form action="/smoothy_trip/ServletOp" method="post"> 
							<ul id="menu-illdy-main" class="clearfix">
								<li id="menu-item-18" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-16"><a href="accueil.html" aria-current="page">About</a></li>
								<li id="menu-item-19" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-17"><a href="accueil.html" aria-current="page">Accueil</a></li>
								<li id="menu-item-20" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-18"><a href="questionnairebis.jsp" aria-current="page">Commencer l'aventure</a></li>
								<li id="menu-item-22" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-22"><input type="submit" name="op" value="Nouveau Compte"> 
								<li id="menu-item-23" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-23"><input type="submit" name="op" value="Connexion">
							</ul>	
							<input type="hidden" name="idVoyage" value="0">
							<input type="hidden" name="Validation" value="Valider">
						</form>
						</nav>
	<!-- 					<button class="open-responsive-menu"><i class="fa fa-bars"></i></button> -->
					</div><!--/.col-sm-10-->
				</div><!--/.row-->
			</div><!--/.container-->
		</div><!--/.top-header-->
	
			<div class="bottom-header front-page" style="padding-bottom:125px;" >
					<div class="container">
						<div class="row">
								<div class="col-sm-12">
									<h1>Votre récapitulatif de voyage est prêt ! </h1>
								</div><!--/.col-sm-12-->
											<div class="col-sm-8 col-sm-offset-2">
												<div class="section-description">Si celui-ci ne convient pas à votre recherche, n'hésitez pas à recommncer</div>
												<a href="questionnairebis.jsp" title="Commencer l'Aventure" class="header-button-two">Recommencer</a>
											</div><!--/.col-sm-8.col-sm-offset-2-->
						</div><!--/.row-->
					</div><!--/.container-->
				</div><!--/.bottom-header.front-page-->
		</div>
	</header><!--/#header-->

	<section id="services" class="front-page-section">
		<div class="section-header">
			<div class="container backgroundProp">
				<div class="row">
					<div class="col-sm-12">
							<h3>Récapitulatif</h3>
					</div><!--/.col-sm-12-->
				</div><!--/.row-->
			</div><!--/.container-->
		</div><!--/.section-header-->
		<div class="section-content">
			<div class="container">
				<div class="row inline-columns">
					<div id="illdy_service-2" class="col-sm-4 widget_illdy_service" ">
						<div class="service" data-service-color="#f18b6d">
	 						<div class="service-icon"> 
	 							<i class="fas fa-plane"></i> 
	 						</div> 
							<div class="service-title">
								<h5> Vols </h5>
							</div><!--/.service-title-->
							<div class="service-entry">
								<%Vols vols = (Vols) request.getAttribute("vols");%>
								<label><%=vols.getVolAller().getOrigine() %> to <%=vols.getVolAller().getDestination() %></label> <br>
		 						<%=vols.getVolAller().getDateDepart().toString()%> - <%=vols.getVolAller().getDateArrivee().toString()%> <br>
		 						
		 						<label><%=vols.getVolRetour().getOrigine() %> to <%=vols.getVolRetour().getDestination() %></label>  <br>
		 						<%=vols.getVolRetour().getDateDepart().toString()%> - <%=vols.getVolRetour().getDateArrivee().toString()%> <br>
		 						
		 						<label>Prix total : </label>  <%=String.valueOf(vols.getPrix())%><%=String.valueOf(vols.getVolAller().getMonnaie())%>
							</div><!--/.service-entry-->
						</div><!--/.service-->
					</div>
					<div id="illdy_service-3" class="col-sm-4 widget_illdy_service">
						<div class="service" data-service-color="#f1d204">
	 						<div class="service-icon"> 
	 							<i class="fas fa-hotel"></i> 
	 						</div><!-- /.service-icon -->
							<div class="service-title">
								<h5>Logement</h5>
							</div><!--/.service-title-->
							<div class="service-entry">
								<%Logement logement = (Logement) request.getAttribute("logementChoisi");%>
								<label>Nom: </label> <%=logement.getNom()%> <br>
								<label>Distance au centre : </label> <%=logement.getRadius()%><%= logement.getRadiusUnit() %> <br>
								<label>Prix de la nuité : </label> <%=logement.getPrix()%>
							</div><!--/.service-entry-->
						</div><!--/.service-->
					</div>
					<div id="illdy_service-4" class="col-sm-4 widget_illdy_service">
						<div class="service" data-service-color="#6a4d8a">
	 						<div class="service-icon"> 
	 							<i class="fas fa-camera"></i> 
	 						</div><!--/.service-icon -->
							<div class="service-title">
								<h5>Activités</h5>
							</div><!--/.service-title-->
							<div class="service-entry">
							<%Collection<Activite> activites = (Collection<Activite>) request.getAttribute("listeActivite"); %>
							<%if (activites.isEmpty()){
								%> 
								Aucune activite disponible <br>
								<input type="submit" name="Validation" value="Recommencer la recherche">
							<%
							}else{ %>
							<% for (Activite activite : activites){ %>
								<label><%=activite.getName()%></label> <br>
								Type: <%=activite.getType()%> <br>
								Adresse : <%=activite.getAddress()%> <br>
							<% }} %>
							</div><!--/.service-entry-->
						</div><!--/.service-->
					</div>			
				</div><!--/.row-->
			</div><!--/.container-->
			<div class="container" style="margin-top:100px;">
				<div class="row">
					<div class="col-sm-8 col-sm-offset-2">
								<h4>Identifie-toi ou crée un compte pour sauvegarder ton voyage !</h4>
													<form action="/smoothy_trip/ServletOp" method="post">
													<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
													<input type="submit" name="op" value="Nouveau Compte">  
													<input type="submit" name="op" value="Connexion">  
													<input type="hidden" name="Validation" value="Valider"> 
													</form> 
					</div><!--/.col-sm-8.col-sm-offset-2-->
				</div><!--/.row-->
			</div><!--/.container-->
		</div><!--/.section-content-->
	</section><!--/#services.front-page-section-->
</body>
</html>