<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
	<title>Activites</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel = "stylesheet" href="css/bootstrap-theme.css">
	<link rel = "stylesheet" href="css/bootstrap.css">
	<link rel = "stylesheet" href="css/custom.css">
	<link rel = "stylesheet" href="css/font-awesome.css">
	<link rel = "stylesheet" href="css/jquery.fancybox.css">
	<link rel = "stylesheet" href="css/main.css">
	<link rel = "stylesheet" href="css/owl-carousel.css">
	<link rel = "stylesheet" href="css/pace.css">
	<link rel ="stylesheet" href="css/stylePerso.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
	<style type="text/css" id="illdy-about-section-css">#header.header-front-page {background-image: url(images/activites.jpg) !important;}#header.header-front-page .bottom-header .header-button-one {background-color: rgba( 0, 0, 0, .2 );}#header.header-front-page .bottom-header .header-button-one:hover {background-color: rgba( 0, 0, 0, .1 );}#header.header-front-page .bottom-header h1 {color: #ffffff;}#header.header-front-page .bottom-header .section-description {color: #ffffff;}</style>
</head>
<body>
	<header id="header" class="header-front-page" style ="backgroung-image: url(images/activites.jpg); background-attachment:fixed;">
		<div style="background-color:rgba(0,0,0,0.3)">
			<div class="top-header" style="background-color:rgba(0,0,0,0.3)">
				<div class="container">
					<div class="row">
						<div class="col-sm-4 col-xs-8">
		
													<a href="accueil.html" title="SmoothyTrip - Accueil">
									<h3 >Smoothy <br> Trip</h3>
								</a>
							
						</div><!--/.col-sm-2-->
						<div class="col-sm-8 col-xs-4">
							<nav class="header-navigation">
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
						</div><!--/.col-sm-10-->
					</div><!--/.row-->
				</div><!--/.container-->
			</div><!--/.top-header-->
			<div class="bottom-header front-page" >
				<div class="container">
					<div class="row">
							<div class="col-sm-12">
								<h3 style="color:#fff;"><i class="fas fa-camera fa-lg"></i> Dernière étape : les activités </h3>
							</div><!--/.col-sm-12-->
					</div><!--/.row-->
				</div><!--/.container-->
			</div><!--/.bottom-header.front-page-->
		</div>
	</header>
	<section id="services" class="front-page-section">
		<div class="section-header">
			<div class="container backgroundProp">
				<div class="row">
					<div class="col-sm-12">
							<div class="section-description">
										C'est le moment de choisir les activités que tu souhaites faire
							</div>
					</div><!--/.col-sm-12-->
				</div><!--/.row-->
			</div><!--/.container-->
		</div><!--/.section-header-->
		<div class="section-content">
			<div class="row">
					<div class="container backgroundProp">
						<div class="col-sm-12">
							<form action="/smoothy_trip/ServletOp" method="post">
								<%Collection<Activite> activites = (Collection<Activite>) request.getAttribute("listeActivite");%>
								<%if (activites.isEmpty()){
									%> 
									<div class="container">
										<label>Aucune activité disponible ici</label> <br>
										<input type="submit" name="Validation" value="Recommencer la recherche">
									</div> <!-- container -->
								<%
								}else{ %>
								<% for (Activite activite : activites){ %>
								<div class="container">
									<div class="backgroundResultat">
										<span id="prixVol"><input id="range-price" type="checkbox" name="idActivite" value="<%=activite.getId()%>" checked> </span>
										<div class="volProposition">
											<label><%=activite.getName()%></label><br>
											<label> Type d'activite : </label> <%=activite.getType()%> <br>
											<label> Adresse de l'activite :</label> <%=activite.getAddress()%> <br>	
										</div>
									</div> <!-- backgroundQuest -->
								</div><!--/.container-->
								<%} %>
								<input type="submit" name="Validation" value="Valider">  
								<%} %>
								<input type="hidden" name="op" value="validerActivites">
								<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
							</form>
						</div><!--/.col-sm-12-->
					</div><!--/.container --->	
				</div><!--/.row-->
			</div>
		</section>
<button class="button-panier" onclick="affVoyage()">
		<div class="notification">
		1
		</div>
		<i class="fas fa-plane"></i>/<i class="fas fa-hotel"></i>
</button> 
<div id="idPanierVolLog" class="idPanier">
	<label class="onglet"><i class="fas fa-plane"></i> Vols</label> <br>
	<%Vols vols = (Vols) request.getAttribute("vols");%>
	<label><%=vols.getVolAller().getOrigine() %> to <%=vols.getVolAller().getDestination() %></label> <br>
	<%=vols.getVolAller().getDateDepart().toString()%> - <%=vols.getVolAller().getDateArrivee().toString()%> <br>
	<label><%=vols.getVolRetour().getOrigine() %> to <%=vols.getVolRetour().getDestination() %></label>  <br>
	<%=vols.getVolRetour().getDateDepart().toString()%> - <%=vols.getVolRetour().getDateArrivee().toString()%> <br>
	<label>Prix total : </label>  <%=String.valueOf(vols.getPrix())%><%=String.valueOf(vols.getVolAller().getMonnaie())%> <br>
	<%Logement logement = (Logement) request.getAttribute("logement");%>
	<label class="onglet"><i class="fas fa-hotel"></i> Hôtel</label><br>
	<%=logement.getNom()%> <br>
	<label>Distance au centre : </label> <%=logement.getRadius()%><%= logement.getRadiusUnit() %> <br>
	<label>Prix de la nuité : </label> <%=logement.getPrix()%>
</div>
<script type="text/javascript">
function affVoyage() {
		var x = document.getElementById("idPanierVolLog");
		if (x.style.display === "none") {
 			x.style.display = "block";
		} else {
 			x.style.display = "none";
		}
}
</script>		
</body>
</html>