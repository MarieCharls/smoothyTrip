package pack;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amadeus.Amadeus;
import com.amadeus.Params;
//import com.amadeus.Amadeus;
//import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
//import com.amadeus.referenceData.Locations;
//import com.amadeus.resources.HotelOffer;
//import com.amadeus.resources.Location;
import com.amadeus.resources.HotelOffer;

import fi.foyt.foursquare.api.FoursquareApiException;

/**
 * Servlet implementation class ServletOp
 */
@WebServlet("/ServletOp")
public class ServletOp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	Facade facade;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletOp() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operation = request.getParameter("op");
		if (operation.equals("questionnaire")){
			String nom= request.getParameter("nom");
			String destination= request.getParameter("destination");
			destination=destination.toLowerCase();
			

			
			String origine = request.getParameter("origine");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dateDepart = null;
			Date dateRetour = null;
			try {
				dateDepart = formatter.parse(request.getParameter("dateDebut"));
				dateRetour = formatter.parse(request.getParameter("dateFin"));
				
			} catch(ParseException e) {
				e.printStackTrace();
			}
			// Vérification de la validité des dates insérées
			boolean villeDepartValide = facade.checkVille(origine);
			boolean villeDestiValide = facade.checkVille(destination);
			boolean dateValide = facade.checkDate(dateDepart,dateRetour);
			
			if (!villeDepartValide){
				request.setAttribute("ville_demande", origine);
				request.getRequestDispatcher("questionnairebis_erreur_ville1.jsp").forward(request, response);
			}
			else if (!villeDestiValide){
				request.setAttribute("ville_demande", destination);
				request.getRequestDispatcher("questionnairebis_erreur_ville2.jsp").forward(request, response);
			}
			else if (!dateValide){
				request.getRequestDispatcher("questionnairebis_erreur_date.jsp").forward(request, response);
			} 
			
			int nbJours = dateDepart.compareTo(dateRetour);
			
			int nbPersonnes= Integer.parseInt(request.getParameter("response5"));
			double budget = Double.parseDouble(request.getParameter("response6"));
			double radius = Integer.parseInt(request.getParameter("response7"));
			int idVoyage = facade.creerVoyage(nom,destination,budget,nbPersonnes,radius);
			
			// Obtenir le cityCode
			String cityCode_destination = new String();
			String cityCode_origine = new String();
			try {
				cityCode_destination = facade.toCityCode(destination);
				cityCode_origine = facade.toCityCode(origine);
			} catch (ResponseException e) {
				e.printStackTrace();
			}
				
			// Chercher un vol
			List<Vols> listeVols = Collections.synchronizedList(new ArrayList<Vols>());
			try {
				listeVols = facade.chercherVol(cityCode_origine, cityCode_destination, dateDepart, dateRetour, nbPersonnes, idVoyage);				
			} catch (ResponseException e) {
				e.printStackTrace();
			}
			// Faire choisir le vol à l'utilisateur
			request.setAttribute("listeVol", listeVols);
			request.setAttribute("idVoyage", idVoyage);
			request.getRequestDispatcher("vol.jsp").forward(request, response);	
		}
		if (operation.equals("validerVol")) {
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				int idVol = Integer.parseInt(request.getParameter("idVol"));
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				facade.associerVol(idVol ,idVoyage);
				
				List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
				try {
					listeLogements = facade.chercherLogement(idVoyage);
					
				} catch (ResponseException e) {
					e.printStackTrace();
				}
				
				
				// Faire choisir le logement à l'utilisateur
				request.setAttribute("listeLogement", listeLogements);
				request.setAttribute("idVoyage", idVoyage);
				request.getRequestDispatcher("logement.jsp").forward(request, response);
				
				
			} else {
				response.getWriter().append("Served at: else");

				request.getRequestDispatcher("questionnaire.jsp");
				
			}
		}
		if (operation.equals("validerLogement")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				//response.getWriter().append("Served at: " + request.getParameter("idLogement")+" "+ request.getParameter("idVoyage"));
				int idLogement = Integer.parseInt(request.getParameter("idLogement"));
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				facade.associerLogement(idLogement,idVoyage);
				// Envoyer la liste des activites
				List<Activite> listeActivites = Collections.synchronizedList(new ArrayList<Activite>());
				try {
					listeActivites = facade.chercherActivite(idVoyage);
				} catch (ResponseException | ParseException | InterruptedException | FoursquareApiException e) {
					((Throwable) e).printStackTrace();
}
				// Faire choisir le logement à l'utilisateur
				request.setAttribute("listeActivite", listeActivites);
				request.setAttribute("idVoyage", idVoyage);
				request.getRequestDispatcher("activites.jsp").forward(request,response);
				
			}else{
				request.getRequestDispatcher("questionnaire.jsp");
				
			}
		}
		if (operation.equals("validerActivites")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				String[] listId = request.getParameterValues("idActivite");
				Voyage voy = null;
				for(int i=0;i<listId.length;i++){
					int idAct = Integer.parseInt(listId[i]);
					voy = facade.associerActivite(idAct,idVoyage);
				}
				Logement logementChoisi=facade.getLogement(idVoyage);
				List<Activite> listeActivite =facade.getActivites(idVoyage);
				Vols vols=facade.getVols(idVoyage);
				request.setAttribute("logementChoisi", logementChoisi );
				request.setAttribute("listeActivite", listeActivite);
				request.setAttribute("vols", vols);
				request.setAttribute("idVoyage", idVoyage);
				request.getRequestDispatcher("recapitulatif.jsp").forward(request, response);
			}else{
				response.getWriter().append("Served at: else");
				request.getRequestDispatcher("questionnaire.jsp");	
			}
		}
		if (operation.equals("Nouveau Compte")){
			int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
			request.setAttribute("idVoyage",idVoyage);
			request.getRequestDispatcher("newUser.jsp").forward(request, response);
		}
		if (operation.equals("createUser")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				String login = request.getParameter("login");
				String pwd = request.getParameter("pwd");
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				int idVoyageur = facade.creerVoyageur(login,pwd,nom,prenom);
				if (idVoyageur == 0){
					request.setAttribute("idVoyage", idVoyage);
					request.getRequestDispatcher("identification.jsp").forward(request, response);
				} else {
					if (idVoyage != 0){
						facade.associerVoyage(idVoyageur,idVoyage);
					}
					Voyageur voyageur = facade.accederCompte(idVoyageur);
					request.setAttribute("voyageur", voyageur);
					request.getRequestDispatcher("perso.jsp").forward(request, response);
				}
			}else{
				response.getWriter().append("Served at: else");
				request.getRequestDispatcher("questionnaire.jsp");	
			}
		}
		if (operation.equals("Connexion")){
			int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
			request.setAttribute("idVoyage",idVoyage);
			request.getRequestDispatcher("auth.jsp").forward(request, response);
		}
		if (operation.equals("validerUser")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				String login = request.getParameter("login");
				String pwd = request.getParameter("pwd");
				int idVoyageur = facade.getIdVoyageur(login, pwd);
				if (idVoyageur == 0){
					request.setAttribute("idVoyage", idVoyage);
					request.getRequestDispatcher("identification.jsp").forward(request, response);
				} else {
					if (idVoyage != 0){
						facade.associerVoyage(idVoyageur,idVoyage);
					}
					Voyageur voyageur = facade.accederCompte(idVoyageur);
					request.setAttribute("voyageur", voyageur);
					request.getRequestDispatcher("perso.jsp").forward(request, response);
				}
			}else{
				response.getWriter().append("Served at: else");
				request.getRequestDispatcher("questionnaire.jsp");	
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}