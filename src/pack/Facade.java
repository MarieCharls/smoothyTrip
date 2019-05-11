package pack;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.Recommendation;
import fi.foyt.foursquare.api.entities.RecommendationGroup;
import fi.foyt.foursquare.api.entities.Recommended;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
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
              .builder("jvcgO6WcMxZkKmDQYrPQ9l0XG1LBkKPy", "OIJ03moU3renCAPs")
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
    
    /** Ajout des activites **/

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
    public double toLong(String cityName) throws ResponseException{
    	Amadeus amadeusLong = this.initialiserAmadeusActivite();
    	Location[] location = amadeusLong.referenceData.locations.get(Params
    			.with("keyword",cityName)
    			.and("subType",Locations.CITY));
    	double longitude = location[0].getGeoCode().getLongitude();
       	return longitude;
    }
    /** Récupérer la latitude d'une ville à partir de son nom
     * @param String cityName nom de la ville 
     * @throws ResponseException */
    public double toLat(String cityName) throws ResponseException{
    	/**cityName.toLowerCase();**/
    	Amadeus amadeusLat = this.initialiserAmadeusActivite();
    	Location[] location = amadeusLat.referenceData.locations.get(Params
    			.with("keyword",cityName)
    			.and("subType",Locations.CITY));
    	double latitude= location[0].getGeoCode().getLatitude();
    	return latitude;
    }
    public static double roundDown1(double d) {
        return ((long)(d * 1e1)) / 1e1;
        //Long typecast will remove the decimals
    }
    /** Recherche toutes les activites sous certaines contraintes posées en entrée
     * @param String cityCode Code de la ville destination
     * @param int nbAdults Nombre de personnes à loger
     * @param double budget budget total pour le logement par personnes
     * @param int radius distance au centre ville (en km)
     * @return List<Logement> liste des logements correspondant à la recherche
     * @throws ResponseException
     * @throws ParseException 
     * @throws IOException 
     * @throws InterruptedException 
     * @throws FoursquareApiException 
     * @throws ApiException 
     * */
	public List<Activite> chercherActivite(String cityName) throws ResponseException, ParseException, InterruptedException, IOException, FoursquareApiException{ 
		// Initialisation de la connection à Foursquare
		FoursquareApi client = new FoursquareApi("VRJSV30LMWA4M0YFLPDAQRWCE1ZI1E4KZJPQL4B5SOYZP1G5","DPECN41FFRIW2YFAEGKJGU2LE3DJQPPIKSWDFX2CP5VUL1SP", null);
    	// Initialiser la liste d'activites
    	List<Activite> listeActivites = Collections.synchronizedList(new ArrayList<Activite>());
    	// Recuperer la latitude et la longitude de la destination
        double longi = toLong(cityName);
      	double lat = toLat(cityName);
      	String ll = Double.toString(lat)+","+Double.toString(longi);
      	// Recuperer les musees disponibles
    	Result<VenuesSearchResult> offersMusee = client.venuesSearch(ll,"museum",50000,null,null,null,null,null);
    	for (CompactVenue musee : offersMusee.getResult().getVenues()) {
    		Activite activite = new Activite();
    		activite.setName(musee.getName());
    		activite.setType(musee.getCategories()[0].getName());
    		activite.setIdAct(musee.getId());
    		activite.setAddress(musee.getLocation().getAddress());
    		activite.setTel(musee.getContact().getPhone());
    		em.persist(activite);
    		listeActivites.add(activite);
    	}
    	// Recuperer les parcs disponibles
    	Result<VenuesSearchResult> offersPark = client.venuesSearch(ll,null,50000,null,null,null,null,null);
    	for (CompactVenue park : offersPark.getResult().getVenues()) {
    		Category[] cat = park.getCategories();
    		for (int i=0;i<cat.length;i++) {
    			if (cat[i].getName().equals("Park")){
    				Activite activite = new Activite();
    	    		activite.setName(park.getName());
    	    		activite.setType(park.getCategories()[0].getName());
    	    		activite.setIdAct(park.getId());
    	    		activite.setAddress(park.getLocation().getAddress());
    	    		activite.setTel(park.getContact().getPhone());
    	    		em.persist(activite);
    	    		listeActivites.add(activite);
    			}
              }
    	}
    	return listeActivites;
    }
	/** Faire le lien BD entre activites choisies et voyage courant. 
     * @param idActivite
     * @param idVoyage
     */
    public Voyage associerActivite(int idActivite,int idVoyage){
    	// On récupère le voyage courant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	
    	// On récupère l'activite
    	Activite activite = em.find(Activite.class, idActivite);
 
    	// On associe l'activite au voyage
    	activite.setVoyage(voyage); 
    	
    	return voyage;
    }
    public void ajouterActivite(Activite activite){
    	em.persist(activite);
    }
}
