<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Vols</title>
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
	<style type="text/css" id="illdy-about-section-css">#header.header-front-page {background-image: url(images/aeroplane.jpg) !important;}#header.header-front-page .bottom-header .header-button-one {background-color: rgba( 0, 0, 0, .2 );}#header.header-front-page .bottom-header .header-button-one:hover {background-color: rgba( 0, 0, 0, .1 );}#header.header-front-page .bottom-header h1 {color: #ffffff;}#header.header-front-page .bottom-header .section-description {color: #ffffff;}</style>
</head>
<body>

	<header id="header" class="header-front-page" style ="backgroung-image: url(images/aeroplane.jpg); background-attachment:fixed;">
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
								<<form action="/smoothy_trip/ServletOp" method="post"> 
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
								<h3 style="color:#fff;"> <i class="fas fa-plane fa-lg"></i> Trouve ton vol idéal </h3>
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
											Tu trouveras ci-dessous tous les vols que nous avons trouvé correspondant à tes critères. Il te faut juste sélectionner le vol que tu préfère pour passer
											à la suite.
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
								<%Collection<Vols> vols = (Collection<Vols>) request.getAttribute("listeVol");
									if (vols==null){
									%> 
	
										<div class="container">
											<label>Aucun vol disponible avec le budget restant</label> <br>
											<input type="submit" name="Validation" value="Recommencer la recherche">
										</div> <!-- container -->
	
								<%
								}else{
								for (Vols vol : vols){ 
									%>
									<div class="container">
										<div class="backgroundResultat">
											<span id="prixVol"><input id="range-price" type="radio" name="idVol" value="<%= vol.getId() %>" checked>  <label ><%=String.valueOf(vol.getPrix())%>  <%=vol.getVolAller().getMonnaie() %></label></span>
											  	<div class="volProposition">
												  	<label><%=vol.getVolAller().getOrigine() %> to <%=vol.getVolAller().getDestination() %></label> <br>
							 						<%=vol.getVolAller().getDateDepart().toString()%> - <%=vol.getVolAller().getDateArrivee().toString()%> <br>
							 						
							 						<label><%=vol.getVolRetour().getOrigine() %> to <%=vol.getVolRetour().getDestination() %></label>  <br>
							 						<%=vol.getVolRetour().getDateDepart().toString()%> - <%=vol.getVolRetour().getDateArrivee().toString()%> <br>
												</div>
										</div> <!-- backgroundQuest -->
									</div><!--/.container-->
								<% } %>
								<%} %>
								<input type="submit" name="Validation" value="Valider">  
								<input type="hidden" name="op" value="validerVol">
								<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
							</form>
						</div> <!--/.col-sm-12-->
					</div><!--/.container --->	
				</div><!--/.row-->
			</div>
	</section>
</body>

</html>
