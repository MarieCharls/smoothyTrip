<!DOCTYPE html>
<html lang="fr-FR">
<head>
<meta charset="UTF-8">
<title>Accueil - SmoothyTrip</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/bootstrap-theme.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/jquery.fancybox.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/owl-carousel.css">
<link rel="stylesheet" href="css/pace.css">
<style type="text/css" id="illdy-about-section-css">
#header.header-front-page {
	background-image: url(images/landscape.jpg) !important;
}

#header.header-front-page .bottom-header .header-button-one {
	background-color: rgba(0, 0, 0, .4);
}

#header.header-front-page .bottom-header .header-button-one:hover {
	background-color: rgba(0, 0, 0, .1);
}

#header.header-front-page .bottom-header h1 {
	color: #ffffff;
}

#header.header-front-page .bottom-header .section-description {
	color: #ffffff;
}
</style>
</head>
<body>
	<header id="header" class="header-front-page"
		style="backgroung-image: url(images/landscape.jpg); background-attachment: fixed;">
		<div style="background-color: rgba(0, 0, 0, 0.3)">
			<div class="top-header" style="background-color: rgba(0, 0, 0, 0.3)">
				<div class="container">
					<div class="row">
						<div class="col-sm-4 col-xs-8">

							<a href="accueil.html" title="SmoothyTrip - Accueil">
								<h3>
									Smoothy <br> Trip
								</h3>
							</a>

						</div>
						<!--/.col-sm-2-->
						<div class="col-sm-8 col-xs-4">
							<nav class="header-navigation">
								<ul id="menu-illdy-main" class="clearfix">
									<li id="menu-item-18"
										class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-16"><a
										href="accueil.html" aria-current="page">About</a></li>
									<li id="menu-item-19"
										class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-17"><a
										href="accueil.html" aria-current="page">Accueil</a></li>
									<li id="menu-item-20"
										class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-18"><a
										href="questionnairebis.jsp" aria-current="page">Commencer
											l'aventure</a></li>
									<li id="menu-item-22"
										class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-22"><a
										href="questionnaire.jsp" aria-current="page">Our Team</a></li>
									<li id="menu-item-23"
										class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-23"><a
										href="contact.html" aria-current="page">Contact Us</a></li>
								</ul>
							</nav>
							<!-- 					<button class="open-responsive-menu"><i class="fa fa-bars"></i></button> -->
						</div>
						<!--/.col-sm-10-->
					</div>
					<!--/.row-->
				</div>
				<!--/.container-->
			</div>
			<!--/.top-header-->

			<div class="bottom-header front-page">
				<div class="container">
					<div class="row">
						<div class="col-sm-12">
							<h1>
								Voyager<span class="span-dot">.</span>R�ver<span
									class="span-dot">.</span> En toute simplicit�
							</h1>
						</div>
						<!--/.col-sm-12-->
						<div class="col-sm-8 col-sm-offset-2">
							<div class="section-description">Erreur lors de
								l'identification... R�essaie !</div>
							<form action="/smoothy_trip/ServletOp" method="post">
								<input type="hidden" name="idVoyage"
									value="<%=request.getAttribute("idVoyage")%>"> <input
									type="submit" name="op" value="Nouveau Compte"> <input
									type="submit" name="op" value="Connexion"> <input
									type="hidden" name="Validation" value="Valider">
							</form>
						</div>
						<!--/.col-sm-8.col-sm-offset-2-->
					</div>
					<!--/.row-->
				</div>
				<!--/.container-->
			</div>
			<!--/.bottom-header.front-page-->
		</div>
	</header>
	<!--/#header-->


</body>
</html>