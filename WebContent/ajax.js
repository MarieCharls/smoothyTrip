// Fonction pour l'appel de requêtes 

function ajaxGet(url){
	// Création de la requête 
	var req = new XMLHttpRequest();
	// Mise en place de la requête
	req.open("GET",url);
	// Regarder si la requête a été traitée 
	req.addEventListener("load", function () {})
	// Envoi de la requête => null = requête GET
	req.send(null);
}