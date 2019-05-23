package pack;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
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
	String cityCode_destination;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletOp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation = request.getParameter("op");;
		if (operation.equals("questionnaire")){
			String nom= request.getParameter("nom");
			String destination= request.getParameter("destination");
			destination=destination.toLowerCase();
			
//			String destination_uk = facade.frToAnglais(destination);
//			response.getWriter().append("Served at: "+destination+" uk version "+destination_uk);
					
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
			boolean dateValide = facade.checkDate(dateDepart,dateRetour);
			if (dateValide==false){
				request.setAttribute("dateInvalide", "true");
				request.getRequestDispatcher("questionnaire.jsp").forward(request, response);
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
			System.out.println("ATTENTIOOOON VILLE OR:"+cityCode_origine);
			try {
				listeVols = facade.chercherVol(cityCode_origine, cityCode_destination, dateDepart, dateRetour, nbPersonnes, idVoyage);				
			} catch (ResponseException e) {
				e.printStackTrace();
			}
			// Faire choisir le vol à l'utilisateur
			request.setAttribute("listeVol", listeVols);
			request.setAttribute("idVoyage", idVoyage);
			request.getRequestDispatcher("vol.jsp").forward(request, response);
			// Calculer le budget restant (AVOIR UN STRING POUR AMAEDUS)
			
			// budget_int = budget_int - prix avions
			// budget_string = "0-"+toString(budget_int)
			// Donner le jour de l'arrivée du vol aller, et jour départ du vol retour
			
			// Chercher un logement 
			///!\ ce date départ doit être le j d'arrivée à l'aéroport
			// /!\Date retour est le jour où le vol retour part pas celui où l'utilisateur veut etre rentre
		}
		if (operation.equals("validerVol")) {
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				//response.getWriter().append("Served at: " + request.getParameter("idLogement")+" "+ request.getParameter("idVoyage"));
				int idVol = Integer.parseInt(request.getParameter("idVol"));
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				facade.associerVol(idVol ,idVoyage);
				
				List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
				try {
					// ------------------ DATEALLER ET DATE RETOUR  ET BUDGET A MAJ APRES APPEL DE VOLS --------------

					listeLogements = facade.chercherLogement(idVoyage);
					
				} catch (ResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				// Faire choisir le logement à l'utilisateur
				request.setAttribute("listeLogement", listeLogements);
				request.setAttribute("idVoyage", idVoyage);
				request.setAttribute("vols",facade.getVols(idVoyage));
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
					// TODO Auto-generated catch block
					((Throwable) e).printStackTrace();
}
				// Faire choisir le logement à l'utilisateur
				request.setAttribute("listeActivite", listeActivites);
				request.setAttribute("idVoyage", idVoyage);
				request.setAttribute("vols",facade.getVols(idVoyage));
				request.setAttribute("logement",facade.getLogement(idVoyage));
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