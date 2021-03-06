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
import javax.servlet.http.HttpSession;

import com.amadeus.Amadeus;
import com.amadeus.Params;

import com.amadeus.exceptions.ResponseException;

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
			boolean villeDepartValide = facade.checkVilleOrigine(origine);
			boolean villeDestiValide = facade.checkVilleDestination(destination);
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
				int idLogement = Integer.parseInt(request.getParameter("idLogement"));
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				
				facade.associerLogement(idLogement,idVoyage);
				// Initialiser la liste des activites
				List<Activite> listeActivites = Collections.synchronizedList(new ArrayList<Activite>());
				// Récupérer les activités disponibles
				try {
					listeActivites = facade.chercherActivite(idVoyage);
				} catch (ResponseException | ParseException | InterruptedException | FoursquareApiException e) {
					// TODO Auto-generated catch block
					((Throwable) e).printStackTrace();
				}
				// Envoyer la liste des activités
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
				// Récupéer l'id du voyage
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				// Récupérer la liste d'activités choisies
				String[] listId = request.getParameterValues("idActivite");
				facade.associerActivite(listId,idVoyage);
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
			// Envoyer la page d'inscription 
			request.getRequestDispatcher("newUser.jsp").forward(request, response);
		}
		if (operation.equals("createUser")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				// Récupérer l'id du voyage
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				// Récupérer les informations de l'utilisateur
				String login = request.getParameter("login");
				String pwd = request.getParameter("pwd");
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				// Créer le compte
				int idVoyageur = facade.creerVoyageur(login,pwd,nom,prenom);
				// Si erreur:compte existe déjà, recommencer
				if (idVoyageur == 0){
					request.setAttribute("idVoyage", idVoyage);
					request.getRequestDispatcher("identification.jsp").forward(request, response);
				} else {
					// sinon, associer le voyage au nouveau compte
					if (idVoyage != 0){
						facade.associerVoyage(idVoyageur,idVoyage);
					}
					// Accéder à la page personnelle
					Voyageur voyageur = facade.accederCompte(idVoyageur);
					HttpSession session = request.getSession();
					session.setAttribute("user", voyageur);
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
		if (operation.equals("Deconnexion")){
			HttpSession session = request.getSession();
			if(session!= null){
				session.removeAttribute("user");
				session.invalidate();
			}
			response.sendRedirect("accueil.html");
		}
		if (operation.equals("validerUser")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Valider")){
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				// Récupérer les identifiants de connexion
				String login = request.getParameter("login");
				String pwd = request.getParameter("pwd");
				// Connecter au compte et associer le voyage si possible, sinon recommencer
				int idVoyageur = facade.getIdVoyageur(login, pwd);
				if (idVoyageur == 0){
					request.setAttribute("idVoyage", idVoyage);
					request.getRequestDispatcher("identification.jsp").forward(request, response);
				} else {
					if (idVoyage != 0){
						facade.associerVoyage(idVoyageur,idVoyage);
					}
					HttpSession session = request.getSession();
					Voyageur voyageur = facade.accederCompte(idVoyageur);
					session.setAttribute("user", voyageur);
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
