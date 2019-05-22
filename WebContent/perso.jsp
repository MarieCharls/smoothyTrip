<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Espace personnel - SmoothyTrip</title>
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
	<link rel = "stylesheet" href="https://adminlte.io/themes/AdminLTE/bower_components/Ionicons/css/ionicons.min.css">
	<style type="text/css" id="illdy-about-section-css">#header.header-front-page {background-image: url(images/planner.jpg) !important;}#header.header-front-page .bottom-header .header-button-one {background-color: rgba( 0, 0, 0, .4 );}#header.header-front-page .bottom-header .header-button-one:hover {background-color: rgba( 0, 0, 0, .1 );}#header.header-front-page .bottom-header h1 {color: #ffffff;}#header.header-front-page .bottom-header .section-description {color: #ffffff;}</style>
	<style>
		#myDIV {
			  width: 100%;
			  padding: 50px 0;
			  text-align: center;
			  background-color: lightblue;
			  margin-top: 20px;
			  ddisplay: none;
		}
	</style>
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

		<div class="bottom-header front-page">
				<div class="container">
					<div class="row">
							<div class="col-sm-12">
								<h1>Voici vos voyages prévus ! </h1>
							</div><!--/.col-sm-12-->
										<div class="col-sm-8 col-sm-offset-2">
											<div class="section-description">Vous pouvez aussi ajouter un nouveau voyage ! </div>
											<a href="questionnairebis.jsp" title="Commencer l'Aventure" class="header-button-two">Nouvelle Aventure</a>
										</div><!--/.col-sm-8.col-sm-offset-2-->
					</div><!--/.row-->
				</div><!--/.container-->
			</div><!--/.bottom-header.front-page-->
</div>
</header><!--/#header-->

<section id="services" class="front-page-section">
			<div class="section-header">
			<div class="container">
				<div class="row">
											<div class="col-sm-12">
							<h3>Voyages prévus</h3>
						</div><!--/.col-sm-12-->
						</div><!--/.row-->
			</div><!--/.container-->
		</div><!--/.section-header-->
		<%Voyageur voyageur = (Voyageur) request.getAttribute("voyageur");
		List<Voyage> listVoyage = voyageur.getListVoyage();
		if (listVoyage.isEmpty()){%>
		Vous n'avez pas encore de voyages prévus !
		<%	
		}else {
		for (Voyage voyage : listVoyage){%>
			
			<button onclick="affVoyage(voyage)">
	 				<div class="service-title"> 
							<h5> Voyage <%=voyage.getNom() %> </h5>
		 			</div>
		 	</button> 
		 	<div class="myDiv" id="test" >
		 		<div class="section-content">
				<div class="container">
				<div class="row inline-columns">				
				<div id="illdy_service-2" class="col-sm-4 widget_illdy_service">
					<div class="service" data-service-color="#f18b6d">
<!-- 						<div class="service-icon"> -->
<!-- 							<span class="fa fa-plane"></span> -->
<!-- 						</div> -->
						<div class="service-title">
							<h5> Vols </h5>
						</div>
						<div class="service-entry">
							<%Vols vols = voyage.getVols();%>
							<label>Vol aller</label>
							 De <%=vols.getVolAller().getOrigine() %> départ prévu à <%=vols.getVolAller().getDateDepart().toString() %> <br> 
							 Vers <%=vols.getVolAller().getDestination() %> arrivée prévue à <%=vols.getVolAller().getDateArrivee().toString() %> <br>
							 <label>Vol retour</label>
							 De <%=vols.getVolRetour().getOrigine()%> départ prévu à <%=vols.getVolRetour().getDateDepart().toString() %> <br>
							 Vers <%=vols.getVolRetour().getDestination() %> arrivée prévue à <%=vols.getVolRetour().getDateArrivee().toString() %>
						</div>
					</div>
				</div>
				<div id="illdy_service-3" class="col-sm-4 widget_illdy_service">
					<div class="service" data-service-color="#f1d204">
<!-- 						<div class="service-icon"> -->
<!-- 							<i class="fa fa-code"></i> -->
<!-- 						</div>/ -->
						<div class="service-title">
							<h5>Logement</h5>
						</div>
						<div class="service-entry">
							<%Logement logement = voyage.getLogement();%>
							<label>Nom: </label> <%=logement.getNom()%> <br>
							<label>Adresse: </label> <%=logement.getAdresse()%> <br>
							<label>Prix de la nuité : </label> <%=logement.getPrix()%>
						</div>
					</div>
				</div>
				<div id="illdy_service-4" class="col-sm-4 widget_illdy_service">
					<div class="service" data-service-color="#6a4d8a">
<!-- 						<div class="service-icon"> -->
<!-- 							<i class="fa fa-search"></i> -->
<!-- 						</div> -->
						<div class="service-title">
							<h5>Activités</h5>
						</div><!--/.service-title-->
						<div class="service-entry">
						<%List<Activite> activites = voyage.getListeActivites();
						System.out.println("------------------------------------------" + listVoyage.size());%>
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
		</div>
		</div>
		<%}%>
		<%}%>		
		<div class="container">
					<div class="row">
							<div class="col-sm-12">
								<h1>Voyager<span class="span-dot">.</span>Rêver<span class="span-dot">.</span> En toute simplicité </h1>
							</div><!--/.col-sm-12-->
					</div><!--/.row-->
				</div><!--/.container-->
</section><!--/#services.front-page-section-->
		<script type="text/javascript">
		function affVoyage(voyage) {
  			var x = document.getElementById(voyage.getNom());
  			if (x.style.display === "none") {
    			x.style.display = "block";
  			} else {
    			x.style.display = "none";
  			}
		}
		</script>
</body>
</html>