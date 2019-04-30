package pack;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation = request.getParameter("op");
		if (operation.equals("questionnaire")){
			int idVoyage = facade.creerVoyage();
			String destination= request.getParameter("destination");
			
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
			int nbJours = dateDepart.compareTo(dateRetour);
			
			int nbPersonnes= Integer.parseInt(request.getParameter("response5"));
			double budget = Integer.parseInt(request.getParameter("response6"));
			int radius = Integer.parseInt(request.getParameter("response7"));
			
			
			// Obtenir le cityCode
			String cityCode_destination = new String();
			String cityCode_origine = new String();
			try {
				cityCode_destination = facade.toCityCode(destination);
				cityCode_origine = facade.toCityCode(origine);
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			// Chercher un vol
			// Faire choisir le vol à l'utilisateur
			// Calculer le budget restant (AVOIR UN STRING POUR AMAEDUS)
			// budget_int = budget_int - prix avions
			// budget_string = "0-"+toString(budget_int)
			// Donner le jour de l'arrivée du vol aller, et jour départ du vol retour
			// Chercher un logement 
			///!\ ce date départ doit être le j d'arrivée à l'aéroport
			// /!\Date retour est le jour où le vol retour part pas celui où l'utilisateur veut etre rentre
			
				List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
			try {
				// ------------------ DATEALLER ET DATE RETOUR  ET BUDGET A MAJ APRES APPEL DE VOLS --------------
				listeLogements = facade.chercherLogement(cityCode_destination, dateDepart, dateRetour, nbPersonnes, budget, radius);
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// Faire choisir le logement à l'utilisateur
			request.setAttribute("listeLogement", listeLogements);
			request.setAttribute("idVoyage", idVoyage);
			request.getRequestDispatcher("logement.jsp").forward(request, response);
			// Calculer le budget restant (AVOIR UN STRING POUR AMAEDUS) /!\ monnaie
			// int prix_logement = logementChoisi.getPrix()*nbJours;
			// budget_int = budget_int - prix_logement
			// budget_string = "0-"+toString(budget_int)
			// Calculer le budget restant
			// Proposer des activités
			
			// Créer le voyage et instancier les attributs
		}
		if (operation.equals("ValiderLogement")){
			String validation = request.getParameter("Validation");
			if (validation.equals("Recommencer la recherche")){
				request.getRequestDispatcher("questionnaire.jsp");
			}else{
				int idLogement = Integer.parseInt(request.getParameter("idLogement"));
				int idVoyage = Integer.parseInt(request.getParameter("idVoyage"));
				facade.associerLogement(idLogement,idVoyage);
				
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
