package pack;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.*;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;


/**
 * Session Bean implementation class Facade
 */

@Singleton
@LocalBean
public class Facade {
	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public Facade() {     
    }
    
    /** Initialiser/créer un voyage */
    public void creerVoyage(String destination, String origine, Date dateDepart, Date dateRetour, int nbPersonnes, int budgetMax){
    	// Initialisation du voyage
    	// Recherche de logements
    }
//    public List<Logement> chercherLogement(String cityCode, String checkInDate, String checkOutDate, String nbAdults, String budget){ 
//    	//Initialisation de la connection
//    	Amadeus amadeus = Amadeus
//                .builder("9fu39sHfnYgpnuoADIwAYhs4PZfO6iLq", "66cmOYECyA6mwb01")
//                .build();
//    	//Recherche de logement dans l'API
//    	try {
//			HotelOffer[] offers=amadeus.shopping.hotelOffers.get(Params
//					.with("cityCode", cityCode)
//					.and("checkInDate", checkInDate)
//					.and("checkOutDate",checkOutDate)
//					.and("adults",nbAdults)
//					.and("priceRange",budget)
//					.and("currency", "EUR"));
//		} catch (ResponseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	// Récupération des données
//    	
//		return offers;
//    }
}
