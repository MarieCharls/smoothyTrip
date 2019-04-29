package pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.*;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;

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
    /** Recherche tous les logements disponibles sous certaines contraintes posées en entrée*/
	public List<Logement> chercherLogement(String cityCode, Date checkInDate, Date checkOutDate, int nbAdults, String budget, int radius) throws ResponseException{ 
    	//Initialisation de la connection
    	Amadeus amadeus =this.initialiserAmadeus();
    	
    	// Initialiser la liste de logement
    	List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
    	//Recherche de logement dans l'API
    	
		HotelOffer[] offers=amadeus.shopping.hotelOffers.get(Params
				.with("cityCode", cityCode)
				.and("checkInDate", checkInDate)
				.and("checkOutDate",checkOutDate)
				.and("adults",nbAdults)
				.and("priceRange",budget)
				.and("sort","PRICE")
				.and("radius",radius)
				.and("currency", "EUR"));
	
    	// Récupération des données
		int i;
		Logement logement = new Logement();
    	for (i=0; i<=offers.length;i++){
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
		return listeLogements;
    }
    /** Récupérer le city code d'une ville à partir de son nom
     * @throws ResponseException */
    public String toCityCode(String cityName) throws ResponseException{
    	Amadeus amadeus = this.initialiserAmadeus();
    	Location[] location = amadeus.referenceData.locations.get(Params
    			.with("keyword",cityName)
    			.and("subType",Locations.CITY));
    	String cityCode = location[0].getAddress().getCityCode();
    	return cityCode;
    }
    
    /** Initialiser une connection Amadeus**/
    public Amadeus initialiserAmadeus(){
    	Amadeus amadeus = Amadeus
              .builder("9fu39sHfnYgpnuoADIwAYhs4PZfO6iLq", "66cmOYECyA6mwb01")
              .build();
    	return amadeus;
    }
    
    public boolean checkDate(Date dateDepart, Date dateRetour){
    	Date dateCourante = new Date();
    	return dateDepart.after(dateCourante) && dateRetour.after(dateDepart);
    }
}
