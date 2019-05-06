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
import com.amadeus.resources.FlightOffer;
import com.amadeus.resources.FlightOffer.FlightStop;
import com.amadeus.resources.FlightOffer.Segment;
import com.amadeus.resources.PointOfInterest;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

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
    
    /** Initialiser/créer un voyage nommé
     * @param nom Nom du voyage 
     * @return int id du voyage dans la BD*/
    public int creerVoyage(String nom, double budget,int nbPersonnes){
    	Voyage voyage = new Voyage();
    	voyage.setNom(nom);
    	voyage.setBudgetRestantIndiv(budget);
    	voyage.setNbPersonnes(nbPersonnes);
    	em.persist(voyage);
    	return voyage.getId();
    }
    
    /** Recherche tous les logements disponibles sous certaines contraintes posées en entrée
     * @param String cityCode Code de la ville destination
     * @param Date checkInDate Date de début de séjour dans le logement
     * @param Date checkOutDate Date de fin de séjour dans le logement
     * @param int nbAdults Nombre de personnes à loger
     * @param double budget budget total pour le logement par personnes
     * @param int radius distance au centre ville (en km)
     * @return List<Logement> liste des logements correspondant à la recherche
     * @throws ResponseException
     * */
	public List<Logement> chercherLogement(String cityCode, Date checkInDate, Date checkOutDate, int nbAdults, int idVoyage, int radius) throws ResponseException{ 
    	//Initialisation de la connection
    	Amadeus amadeus =this.initialiserAmadeusHotel();
    	
    	// Initialiser la liste de logement
    	List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
    	
    	// Récupération du budget restant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	double budget = voyage.getBudgetRestantIndiv();
    	
    	// Recherche du budget par nuit, pour tout le monde
    	double nbJours = checkInDate.compareTo(checkOutDate);
    	double budgetNuite=budget*nbAdults/Math.abs(nbJours);
    	
    	//Conversion en integer
    	budget = Math.floor(budgetNuite);
    	
    	//Recherche de logement dans l'API
    	String budget_string = "0-"+String.valueOf(budget);
    	
		HotelOffer[] offers=amadeus.shopping.hotelOffers.get(Params
				.with("cityCode", cityCode)
				.and("checkInDate", checkInDate)
				.and("checkOutDate",checkOutDate)
				.and("adults",nbAdults)
				.and("priceRange",budget_string)
				.and("sort","PRICE")
				.and("radius",radius)
				.and("currency", "EUR"));
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
    		logement.setPrixBase(offers[i].getOffers()[0].getPrice().getBase());
    		logement.setMonnaire(offers[i].getOffers()[0].getPrice().getCurrency());
    		em.persist(logement);
    		listeLogements.add(logement);
    	}
		return listeLogements;
    }
    /** Récupérer le city code d'une ville à partir de son nom
     * @param String cityName nom de la ville 
     * @throws ResponseException */
    public String toCityCode(String cityName) throws ResponseException{
    	cityName.toLowerCase();
    	Amadeus amadeus = this.initialiserAmadeusHotel();
    	Location[] location = amadeus.referenceData.locations.get(Params
    			.with("keyword",cityName)
    			.and("subType",Locations.CITY));
    	String cityCode = location[0].getAddress().getCityCode();
    	return cityCode;
    }
    
    /** Initialiser une connection AmadeusHotel**/
    public Amadeus initialiserAmadeusHotel(){
    	Amadeus amadeus = Amadeus
              .builder("9fu39sHfnYgpnuoADIwAYhs4PZfO6iLq", "66cmOYECyA6mwb01")
              .build();
    	return amadeus;
    }

    /** Vérifier la validité des dates fournies 
     * @param Date dateDepart date début du voyage
     * @param Date dateRetour date de fin du voyage
     * @return boolean true = dates valides*/
    public boolean checkDate(Date dateDepart, Date dateRetour){
    	Date dateCourante = new Date();
    	return dateDepart.after(dateCourante) && dateRetour.after(dateDepart);
    }
    
    /** Faire le lien BD entre logement choisi et voyage courant. Et
     * maj le budget restant sur le voyage
     * @param idLogement
     * @param idVoyage
     */
    public void associerLogement(int idLogement,int idVoyage){
    	// On récupère le voyage courant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	
    	// On récupère le logement
    	Logement logement = em.find(Logement.class, idLogement);
    	
    	// Maj du budget restant
    	double budget = voyage.getBudgetRestantIndiv();
    	int nbPersonnes = voyage.getNbPersonnes();
    	double coutLogementIndiv;
    	if (logement.getPrix()== null){
    		coutLogementIndiv = Double.parseDouble(logement.getPrixBase())/nbPersonnes;
    	}else{
    		coutLogementIndiv = Double.parseDouble(logement.getPrix())/nbPersonnes;
    	}
		budget = budget - coutLogementIndiv;
		voyage.setBudgetRestantIndiv(budget);
    	// On associe le logement au voyage
    	logement.setVoyage(voyage); 	
    }
    public void ajouterLogement(Logement logement){
    	em.persist(logement);
    }
    
    /** Traduire la ville de français à Anglais*/
    public String frToAnglais(String nomVille){
    	Translate translate = TranslateOptions.getDefaultInstance().getService();
    	Translation translation = translate.translate(nomVille,
    			TranslateOption.sourceLanguage("fr"),
    			TranslateOption.targetLanguage("en") );
    	return translation.getTranslatedText();
    }
    
    /** Initialiser une connection AmadeusActivite**/
    public Amadeus initialiserAmadeusActivite(){
    	Amadeus amadeus = Amadeus
              .builder("jvcgO6WcMxZkKmDQYrPQ9l0XG1LBkKPy", "OIJ03moU3renCAPs")
              .build();
    	return amadeus;
    }
    /** Récupérer la longitude d'une ville à partir de son nom
     * @param String cityCode code de la ville 
     * @throws ResponseException */
    public double toLong(String cityCode) throws ResponseException{
    	Amadeus amadeus = this.initialiserAmadeusHotel();
    	Location[] location = amadeus.referenceData.locations.get(Params
    			.with("keyword",cityCode)
    			.and("subType",Locations.CITY));
    	double longitude = location[0].getGeoCode().getLongitude();
    	return longitude;
    }
    /** Récupérer la latitude d'une ville à partir de son nom
     * @param String cityName nom de la ville 
     * @throws ResponseException */
    public double toLat(String cityName) throws ResponseException{
    	/**cityName.toLowerCase();**/
    	Amadeus amadeus = this.initialiserAmadeusHotel();
    	Location[] location = amadeus.referenceData.locations.get(Params
    			.with("keyword",cityName)
    			.and("subType",Locations.CITY));
    	double latitude= location[0].getGeoCode().getLongitude();
    	return latitude;
    }
    /** Recherche toutes les activites sous certaines contraintes posées en entrée
     * @param String cityCode Code de la ville destination
     * @param int nbAdults Nombre de personnes à loger
     * @param double budget budget total pour le logement par personnes
     * @param int radius distance au centre ville (en km)
     * @return List<Logement> liste des logements correspondant à la recherche
     * @throws ResponseException
     * */
	public List<Activite> chercherActivite(String cityName) throws ResponseException{ 
    	//Initialisation de la connection
	Amadeus amadeusAct = this.initialiserAmadeusActivite();
    	
    	// Initialiser la liste d'activites
    	List<Activite> listeActivites = Collections.synchronizedList(new ArrayList<Activite>());
    	double lat = toLat(cityName);
    	double longi = toLong(cityName);
		PointOfInterest[] offers=amadeusAct.referenceData.locations.pointsOfInterest.get(Params.with("longitude", 41).and("latitude", 2.4));
		int i;
		
    	for (i=0; i<offers.length;i++){
    		Activite activite = new Activite();
    		activite.setName(offers[i].getName());
    		activite.setType(offers[i].getCategory());
    		activite.setTags(offers[i].getTags());
    		em.persist(activite);
    		listeActivites.add(activite);
    	}
		return listeActivites;
    }
	
	/** Initialiser une connection AmadeusVol**/
    public Amadeus initialiserAmadeusVol(){
    	Amadeus amadeus = Amadeus
              .builder("6nRL5xnhTjIla3lB9DZDozVolhFxQWtH", "oKTXhjPY2rFKMoGs")
              .build();
    	return amadeus;
    }
	
    
    /** Faire le lien BD entre vol choisi et voyage courant. Et
     * maj le budget restant sur le voyage
     * @param idVol
     * @param idVoyage
     */
    public Vol associerVol(int idVol,int idVoyage){
    	// On récupère le voyage courant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	
    	// On récupère le vol
    	Vol vol = em.find(Vol.class, idVol);
    	
    	// Maj du budget restant
    	double budget = voyage.getBudgetRestantIndiv();
    	int nbPersonnes = voyage.getNbPersonnes();
    	double coutVolIndiv = Double.parseDouble(vol.getPrix())/nbPersonnes;
		budget = budget - coutVolIndiv;
		voyage.setBudgetRestantIndiv(budget);
    	// On associe le logement au voyage
    	vol.setVoyage(voyage); 
    	return vol;
    }
	
    
    
	 /** Recherche tous les vols disponibles sous certaines contraintes posées en entrée
     * @param String cityCodeD Code de la ville de départ
     * @param Date checkInDate Date de début de séjour dans le logement
     * @param Date checkOutDate Date de fin de séjour dans le logement
     * @param int nbAdults Nombre de personnes à loger
     * @param double budget budget total pour le logement par personnes
     * @return List<Vol> liste des vols correspondant à la recherche
     * @throws ResponseException
     * */
	public List<Vol> chercherVol(String cityCode_origine,String cityCode_destination, Date departDate, Date retourDate, int nbAdults, int idVoyage) throws ResponseException{ 
    	//Initialisation de la connection
    	Amadeus amadeus =this.initialiserAmadeusVol();
    	
    	// Initialiser la liste des vols
    	List<Vol> listeVols = Collections.synchronizedList(new ArrayList<Vols>());
    	
    	// Récupération du budget restant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	double budget = voyage.getBudgetRestantIndiv();
    	
    	// Recherche du budget vol pour tout le monde
    	double budgetVol=budget*nbAdults;
    	
    	//Conversion en integer
    	budget = Math.floor(budgetVol);
    	
    	//Recherche de vol dans l'API
    	String budget_string = "0-"+String.valueOf(budget);
    	FlightOffer[] offers =amadeus.shopping.flightOffers.get(Params
    			.with("origin", cityCode_origine)
    			.and("destination",cityCode_destination)
    			.and("departureDate", departDate)
    			.and("returnDate", retourDate)
    			.and("maxPrice", budget_string)
    			.and("currency", "EUR")
    			.and("adults", nbAdults)
    			);
    			
    			
		int i;
		
    	for (i=0; i<vols.length;i++){
    		Vol vol = new Vol();
    		Segment[] sousVols = offers[i].getOfferItems()[0].getServices()[0].getSegments();
    		FlightStop premierSousVol = sousVols[0].getFlightSegment().getStops()[0];
    		FlightStop dernierSousVol = sousVols[sousVols.length-1].getFlightSegment().getStops()[(sousVols[sousVols.length-1].getFlightSegment().getStops().length)-1];
    		vol.setDateDepart(premierSousVol.getDepartureAt());
    		vol.setDateArrivee(dernierSousVol.getArrivalAt());
    		vol.setDestination(sousVols[sousVols.length-1].getFlightSegment().getArrival().getIataCode());
    		vol.setOrigine(sousVols[0].getFlightSegment().getDeparture().getIataCode());
    		vol.setPrix(offers[i].getOfferItems()[1].getPrice().toString());
    		vol.setMonnaie("EUR");
    		em.persist(vol);
    		listeVols.add(vol);
    	}
		return listeLogements;
    }
	
}
