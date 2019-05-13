<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="fr-FR">
<head>
	<meta charset="UTF-8">
	<title>Commencer l'aventure - SmoothyTrip</title>
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
	<style type="text/css" id="illdy-about-section-css">#header.header-front-page {background-image: url(images/questionnaire.jpg) !important;}#header.header-front-page .bottom-header .header-button-one {background-color: rgba( 0, 0, 0, .2 );}#header.header-front-page .bottom-header .header-button-one:hover {background-color: rgba( 0, 0, 0, .1 );}#header.header-front-page .bottom-header h1 {color: #ffffff;}#header.header-front-page .bottom-header .section-description {color: #ffffff;}</style>
</head>
<body>
	<header id="header" class="header-front-page" style ="backgroung-image: url(images/questionnaire.jpg); background-attachment:fixed;">
		<div class="top-header">
		<div class="container">
			<div class="row">
				<div class="col-sm-4 col-xs-8">

											<a href="accueil.html" title="SmoothyTrip - Accueil">
							Smoothy <br> Trip
						</a>
					
				</div><!--/.col-sm-2-->
				<div class="col-sm-8 col-xs-4">
					<nav class="header-navigation">
						<ul id="menu-illdy-main" class="clearfix"><li id="menu-item-18" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-16"><a href="accueil.html" aria-current="page">About</a></li>
							<li id="menu-item-19" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-17"><a href="accueil.html" aria-current="page">Accueil</a></li>
							<li id="menu-item-20" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-18"><a href="questionnairebis.jsp" aria-current="page">Commencer l'aventure</a></li>
							<li id="menu-item-22" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-22"><a href="questionnaire.jsp" aria-current="page">Our Team</a></li>
							<li id="menu-item-23" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-23"><a href="contact.html" aria-current="page">Contact Us</a></li>
						</ul>					</nav>
					<button class="open-responsive-menu"><i class="fa fa-bars"></i></button>
				</div><!--/.col-sm-10-->
			</div><!--/.row-->
		</div><!--/.container-->
	</div><!--/.top-header-->

		<div class="bottom-header front-page">
				<div class="container">
					<div class="row">
							<div class="backgroundQuest">
								<form action="/smoothy_trip/ServletOp" method="post">
									<label>Quel nom veux-tu donner au voyage </label> ? <input type="text" name="nom"/> <br>
									<label>Où souhaites-tu aller ? </label><input type="text" name="destination" required="required"/> <br>
	<label>D'où souhaites-tu partir ? </label><input type="text" name="origine" required="required"/> <br>
<%-- 	<%if(request.getAttribute("dateInvalide").equals("true")){%> DATES INVALIDES <br><%} %> --%>
	<br><label>A quelle date voulez-vous partir ? </label><br>
	<input type="date" name="dateDebut" required="required">
	
	<br><label> A quelle date voulez-vous revenir? </label><br>
	<input type="date" name="dateFin" required="required">
	
	<br> <label>Combien de personnes participent au voyage? </label>
	<br>
	<select name="response5" size="1" required="required">
	<option value="1"> 1
	<option value="2"> 2
	<option value="3"> 3
	<option value="4"> 4
	<option value="5"> 5
	</select>
	<br> <label>Quel est ton budget maximum par personnes ?</label> <br>
	<input style="display: inline;width:auto;" type="radio" name="response6" value="200" checked>  Max 200€ 
	<input style="display: inline;width:auto;" type="radio" name="response6" value="300" checked>  Max 300€ 
	<input style="display: inline;width:auto;" type="radio" name="response6" value="500" checked> Max 500€ 
	<input style="display: inline;width:auto;" type="radio" name="response6" value="700" checked> Max 700€ 
	<input style="display: inline;width:auto;" type="radio" name="response6" value="10000" checked> Plus de 700€
	<br> <label>A quelle distance du centre-ville veux tu loger (km)?</label> <br>
	<select name="response7" size="1">
	<option value="1"> 1
	<option value="2"> 2
	<option value="5"> 5
	<option value="7"> 7
	<option value="10"> 10
	</select> <br>
									<div class="col-sm-8 col-sm-offset-2">
											<a href="accueil.html" title="Annuler" class="header-button-one">Annuler</a>
											<input type ="submit" title="Cest parti" class="header-button-two" value="GO">	
									</div><!--/.col-sm-8.col-sm-offset-2-->
								</form>
							</div><!--/.col-sm-12-->
										
								
					</div><!--/.row-->
				</div><!--/.container-->
			</div><!--/.bottom-header.front-page-->

</header><!--/#header-->
</body>
</html>