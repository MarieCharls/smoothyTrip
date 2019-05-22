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
	<style type="text/css" id="illdy-about-section-css">#header.header-front-page {background-image: url(images/activites.jpg) !important;}#header.header-front-page .bottom-header .header-button-one {background-color: rgba( 0, 0, 0, .2 );}#header.header-front-page .bottom-header .header-button-one:hover {background-color: rgba( 0, 0, 0, .1 );}#header.header-front-page .bottom-header h1 {color: #ffffff;}#header.header-front-page .bottom-header .section-description {color: #ffffff;}</style>
</head>
<body>
	<header id="header" class="header-front-page" style ="backgroung-image: url(images/logement.jpg); background-attachment:fixed;">
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
								<ul id="menu-illdy-main" class="clearfix"><li id="menu-item-18" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-16"><a href="accueil.html" aria-current="page">About</a></li>
									<li id="menu-item-19" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-17"><a href="accueil.html" aria-current="page">Accueil</a></li>
									<li id="menu-item-20" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-18"><a href="questionnairebis.jsp" aria-current="page">Commencer l'aventure</a></li>
									<li id="menu-item-22" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-22"><a href="questionnaire.jsp" aria-current="page">Our Team</a></li>
									<li id="menu-item-23" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-23"><a href="contact.html" aria-current="page">Contact Us</a></li>
								</ul>					
							</nav>
						</div><!--/.col-sm-10-->
					</div><!--/.row-->
				</div><!--/.container-->
			</div><!--/.top-header-->
			
			<div class="bottom-header-prop front-page">
				<div class="row">
					<div class="container backgroundProp">
						<div class="col-sm-12">
							<h4> C'est le moment de choisir les activités que tu souhaites faire </h4>
							<div class="section-description" style="color:#000">
								Nous avons sélectionné une liste d'activités spécialement pour toi. Maintenant il est l'heure de faire un choix, <br>
								lesquelles souhaites-tu mettre au planning de ton voyage?
							</div>
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
										<span id="prixVol"><input id="range-price" type="checkbox" name="idActivite" value="<%=activite.getId()%>" unchecked> </span>
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
			</div><!--/.bottom-header.front-page-->
		</div>
	</header>
</body>
</html>