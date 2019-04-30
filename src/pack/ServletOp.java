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
			int nbPersonnes= Integer.parseInt(request.getParameter("response5"));
			String budget = request.getParameter("response6");
			int radius = Integer.parseInt(request.getParameter("response7"));
			
			
//			// Obtenir le cityCode
//			destination.toLowerCase();
			String cityCode = new String();

			try {
				Amadeus amadeus = Amadeus
			              .builder("9fu39sHfnYgpnuoADIwAYhs4PZfO6iLq", "66cmOYECyA6mwb01")
			              .build();
				Location[] location = amadeus.referenceData.locations.get(Params
		    			.with("keyword",destination)
		    			.and("subType",Locations.CITY));
		    	cityCode = location[0].getAddress().getCityCode();
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			// Chercher un vol
			// Faire choisir le vol à l'utilisateur
			// Calculer le budget restant 
			// Chercher un logement 
			///!\ ce date départ doit être le j d'arrivée à l'aéroport
			// /!\Date retour est le jour où le vol retour part pas celui où l'utilisateur veut etre rentre
			
				List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
			try {
				// Initialiser la liste de logement
				Amadeus amadeus = Amadeus
			              .builder("9fu39sHfnYgpnuoADIwAYhs4PZfO6iLq", "66cmOYECyA6mwb01")
			              .build();
		    	//Recherche de logement dans l'API
				//response.getWriter().append("Served at: " + cityCode + dateDepart + dateRetour );
				HotelOffer[] offers=amadeus.shopping.hotelOffers.get(Params
						.with("cityCode", cityCode)
						.and("checkInDate", dateDepart)
						.and("checkOutDate",dateRetour)
						.and("adults",nbPersonnes)
						.and("priceRange",budget)
						.and("sort","PRICE")
						.and("radius",radius)
						.and("currency", "EUR"));
			
		    	// Récupération des données
				int i;
				
		    	for (i=0; i<offers.length;i++){
		    		Logement logement = new Logement();
		    		logement.setCityCode(offers[i].getHotel().getCityCode());
		    		logement.setNom(offers[i].getHotel().getName());
		    		logement.setAdresse(offers[i].getHotel().getAddress().getLines());
		    		logement.setRadius(offers[i].getHotel().getHotelDistance().getDistance());
		    		logement.setRadiusUnit(offers[i].getHotel().getHotelDistance().getDistanceUnit());
		    		// On garde la première offre = la plus intéressante pour chaque hôtel
		    		// On pourrait choisir entre plusierus offres dans la suite
		    		logement.setPrix(offers[i].getOffers()[0].getPrice().getTotal());
		    		logement.setMonnaire(offers[i].getOffers()[0].getPrice().getCurrency());
		    		listeLogements.add(logement);
		    	}
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("listeLogement", listeLogements);
			request.getRequestDispatcher("logement.jsp").forward(request, response);
			
			
			// Faire choisir le logement à l'utilisateur
			// Calculer le budget restant
			// Proposer des activités
			
			// Créer le voyage et instancier les attributs
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
