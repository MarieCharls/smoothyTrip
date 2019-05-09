package pack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
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
    public int creerVoyage(String nom, double budget,int nbPersonnes,double radius){
    	Voyage voyage = new Voyage();
    	voyage.setNom(nom);
    	voyage.setBudgetRestantIndiv(budget);
    	voyage.setNbPersonnes(nbPersonnes);
    	voyage.setRadius(radius);
    	em.persist(voyage);
    	return voyage.getId();
    }
    
    /** Recherche tous les logements disponibles sous certaines contraintes posées en entrée
     * @param int identifiant du voyage
     * @return List<Logement> liste des logements correspondant à la recherche
     * @throws ResponseException
     * */
	public List<Logement> chercherLogement(int idVoyage) throws ResponseException{ 
    	//Initialisation de la connection
    	Amadeus amadeus =this.initialiserAmadeusHotel();
    	
    	// Initialiser la liste de logement
    	List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
    	
    	// Récupération du budget restant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	double budget = voyage.getBudgetRestantIndiv();
    	
    	//Recuperer paramètre de recherches
    	Date checkInDate = voyage.getVolAller().getDateArrivee();
    	Date checkOutDate = voyage.getVolRetour().getDateDepart();
    	int nbAdults = voyage.getNbPersonnes();
    	String cityCode = voyage.getDestination();
    	double radius = voyage.getRadius();
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
    /**  public String frToAnglais(String nomVille){
    	Translate translate = TranslateOptions.getDefaultInstance().getService();
    	Translation translation = translate.translate(nomVille,
    			TranslateOption.sourceLanguage("fr"),
    			TranslateOption.targetLanguage("en") );
    	return translation.getTranslatedText();
    } **/
    
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
    public void associerVol(int idVol,int idVoyage){
    	// On récupère le voyage courant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	
    	// On récupère le vol
    	Vols vols = em.find(Vols.class, idVol);
    	//On récupère le vol aller
    
    	// Maj du budget restant
    	double budget = voyage.getBudgetRestantIndiv();
    	int nbPersonnes = voyage.getNbPersonnes();
    	double coutVolIndiv = vols.getPrix()/nbPersonnes;
		budget = budget - coutVolIndiv;
		voyage.setBudgetRestantIndiv(budget);
    	// On associe le logement au voyage
    	vols.getVolAller().setVoyage(voyage);
    	vols.getVolRetour().setVoyage(voyage);
    }
	
    public Date toDate(String d){
    	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    	Date date=null;
		try {
			date = sd.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    
//    public Duration toDuree(String d){
//Duration date=null;
//		try {
//			date = sd.parse(d);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//    	return date;
//    } 
//    
//    public Duration calculDureeTotale(FlightOffer offer){
// 
//    	Duration duree=null;
//    	Segment[] segmentAller = offer.getOfferItems()[0].getServices()[0].getSegments();
//		Segment[] segmentRetour = offer.getOfferItems()[0].getServices()[1].getSegments();
//		for (int i=0;i<segmentAller.length;i++ ){
//			Date dSeg = toDuree(segmentAller[i].getFlightSegment().getDuration());
//			
//		}
//			
//		
//    	return duree
//    }
	 /** Recherche tous les vols disponibles sous certaines contraintes posées en entrée
     * @param String cityCodeD Code de la ville de départ
     * @param Date checkInDate Date de début de séjour dans le logement
     * @param Date checkOutDate Date de fin de séjour dans le logement
     * @param int nbAdults Nombre de personnes à loger
     * @param double budget budget total pour le logement par personnes
     * @return List<Vol> liste des vols correspondant à la recherche
     * @throws ResponseException
     * */
	public List<Vols> chercherVol(String cityCode_origine,String cityCode_destination, Date departDate, Date retourDate, int nbAdults, int idVoyage) throws ResponseException{ 
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		//Initialisation de la connection
    	Amadeus amadeus =this.initialiserAmadeusVol();
    	
    	// Initialiser la liste des vols
    	List<Vols> listeVols = Collections.synchronizedList(new ArrayList<Vols>());
    	
    	// Récupération du budget restant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	double budget = voyage.getBudgetRestantIndiv();
    	
    	// Envoi de la destination au voyage
    	voyage.setDestination(cityCode_destination);

    	// Recherche du budget vol pour tout le monde
    	double budgetVol=budget*nbAdults;
    	
    	//Conversion en integer
    	int budget_int = (int)Math.floor(budgetVol);
    	
    	//Recherche de vol dans l'API
    	String budget_string = String.valueOf(budget);
    	FlightOffer[] vols =amadeus.shopping.flightOffers.get(Params
    			.with("origin", cityCode_origine)
    			.and("destination",cityCode_destination)
    			.and("departureDate", formater.format(departDate))
    			.and("returnDate", formater.format(retourDate))
    			.and("maxPrice", budget_int)
    			.and("currency", "EUR")
    			.and("adults", nbAdults)
    			);
    			
    			
    	int i;
    	for (i=0; i<vols.length;i++){
    		Vols deplacement = new Vols();
    		deplacement.setPrix(vols[i].getOfferItems()[0].getPrice().getTotal());
    		
    		Segment[] segmentAller = vols[i].getOfferItems()[0].getServices()[0].getSegments();
    		Segment[] segmentRetour = vols[i].getOfferItems()[0].getServices()[1].getSegments();
    		
    		Vol volAller = new Vol();
    		int nbAller = segmentAller.length; 
    		volAller.setDateDepart(toDate(segmentAller[0].getFlightSegment().getDeparture().getAt()));
    		volAller.setDateArrivee(toDate(segmentAller[nbAller-1].getFlightSegment().getArrival().getAt()));
    		volAller.setDestination(segmentAller[0].getFlightSegment().getArrival().getIataCode());
    		volAller.setOrigine(segmentAller[0].getFlightSegment().getDeparture().getIataCode());
    		volAller.setMonnaie("EUR");

    		
    		Vol volRetour = new Vol();
    		int nbRetour = segmentRetour.length; 
    		volRetour.setDateDepart(toDate(segmentRetour[0].getFlightSegment().getDeparture().getAt()));
    		volRetour.setDateArrivee(toDate(segmentRetour[nbRetour-1].getFlightSegment().getArrival().getAt()));
    		volRetour.setDestination(segmentRetour[0].getFlightSegment().getArrival().getIataCode());
    		volRetour.setOrigine(segmentRetour[0].getFlightSegment().getDeparture().getIataCode());
    		volRetour.setMonnaie("EUR");

    		em.persist(volAller);
    		em.persist(volRetour);
    		deplacement.setVolAller(volAller);
    		deplacement.setVolRetour(volRetour);
    		em.persist(deplacement);
    		listeVols.add(deplacement);
    	}
		return listeVols;
    }
	
}
