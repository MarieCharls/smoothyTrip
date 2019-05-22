package pack;

import java.text.SimpleDateFormat;
import java.io.IOException;
import java.text.ParseException; 
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
import com.amadeus.resources.FlightOffer;
import com.amadeus.resources.FlightOffer.FlightStop;
import com.amadeus.resources.FlightOffer.Segment;
import com.amadeus.resources.Location;

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
     * @param budget du voyage par pers
     * @param nombre de personnes
     * @param rayon proximite du centre de la ville
     * @return int id du voyage dans la BD*/
    public int creerVoyage(String nom,String destination, double budget,int nbPersonnes,double radius){
    	Voyage voyage = new Voyage();
    	voyage.setNom(nom);
    	voyage.setBudgetRestantIndiv(budget);
    	voyage.setNbPersonnes(nbPersonnes);
    	voyage.setRadius(radius);
    	voyage.setDestination(destination);
    
    	em.persist(voyage);
    	return voyage.getId();
    }
    
    /** Recherche tous les logements disponibles sous certaines contraintes posées en entrée
     * @param int identifiant du voyage
     * @return List<Logement> liste des logements correspondant à la recherche
     * @throws ResponseException
     * */
	public List<Logement> chercherLogement(int idVoyage) throws ResponseException{
    	System.out.println("Rentre dans chercher");
		//Initialisation de la connection
    	Amadeus amadeus =this.initialiserAmadeusHotel();
    	
    	// Initialiser la liste de logement
    	List<Logement> listeLogements = Collections.synchronizedList(new ArrayList<Logement>());
    	
    	// Récupération du budget restant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	System.out.println("Après recup voyage");
    	double budget = voyage.getBudgetRestantIndiv();
    	System.out.println("budgetRestant : "+budget);
    	//Recuperer paramètre de recherches
//    	TypedQuery<Date> req = em.createQuery("SELECT dateArrivee FROM Vol v WHERE v.estAller = true and VOYAGE_ID="+idVoyage,Date.class);
//    	Date checkInDate = req.getSingleResult();
//    	req = em.createQuery("SELECT dateDepart FROM Vol v WHERE v.estAller = false and VOYAGE_ID="+idVoyage,Date.class);
//    	Date checkOutDate = req.getSingleResult();
    	Date checkInDate = voyage.getVols().getVolAller().getDateArrivee();
    	Date checkOutDate = voyage.getVols().getVolRetour().getDateDepart();
    	int nbAdults = voyage.getNbPersonnes();
    	String cityCode = voyage.getCityCodeDestination();
    	double radius = voyage.getRadius();
    	// Recherche du budget par nuit, pour tout le monde
    	double nbJours = Math.abs((checkOutDate.getTime()-checkInDate.getTime())/(1000*60*60*24))+1;
    	double budgetNuite=budget*nbAdults/nbJours;
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
    	Amadeus amadeus = this.initialiserAmadeusActivite();
    	Location[] location = amadeus.referenceData.locations.get(Params
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
    	Amadeus amadeus = this.initialiserAmadeusHotel();
    	Location[] location = amadeus.referenceData.locations.get(Params
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
	public List<Activite> chercherActivite(int idVoyage) throws ResponseException, ParseException, InterruptedException, IOException, FoursquareApiException{ 
		
		// Récupérer le voyage
		Voyage voyage=em.find(Voyage.class,idVoyage);
		String cityName = voyage.getDestination().toLowerCase();
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
		vols.setVoyage(voyage);
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
    	voyage.setCityCodeDestination(cityCode_destination);

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
    			.and("max", 7)
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
    		volAller.setDestination(segmentAller[nbAller-1].getFlightSegment().getArrival().getIataCode());
    		volAller.setOrigine(segmentAller[0].getFlightSegment().getDeparture().getIataCode());
    		volAller.setMonnaie("EUR");
    		volAller.setEstAller(true);

    		
    		Vol volRetour = new Vol();
    		int nbRetour = segmentRetour.length; 
    		volRetour.setDateDepart(toDate(segmentRetour[0].getFlightSegment().getDeparture().getAt()));
    		volRetour.setDateArrivee(toDate(segmentRetour[nbRetour-1].getFlightSegment().getArrival().getAt()));
    		volRetour.setDestination(segmentRetour[nbRetour-1].getFlightSegment().getArrival().getIataCode());
    		volRetour.setOrigine(segmentRetour[0].getFlightSegment().getDeparture().getIataCode());
    		volRetour.setMonnaie("EUR");
    		volRetour.setEstAller(false);

    		em.persist(volAller);
    		em.persist(volRetour);
    		volAller.setDeplacement(deplacement);
    		volRetour.setDeplacement(deplacement);
    		deplacement.setVolAller(volAller);
    		deplacement.setVolRetour(volRetour);
    		em.persist(deplacement);
    		listeVols.add(deplacement);
    	}
		return listeVols;
    }
	/** Authentification du voyageur **/
	
	/** Creer un nouveau compte voyageur 
	 * @param prenom 
	 * @param nom **/
	public int creerVoyageur(String login, String pwd, String nom, String prenom) {
		TypedQuery<Integer> req = em.createQuery("SELECT id FROM Voyageur v WHERE v.login = '"+login+"' and v.password= '"+pwd+"'",Integer.class);
		int idVoyageur;
		try{
			req.getSingleResult();
			idVoyageur = 0;
		}catch (NoResultException e){
			Voyageur voyageur = new Voyageur();
	    	voyageur.setLogin(login);
	    	voyageur.setPassword(pwd);  
	    	voyageur.setNom(nom);
	    	voyageur.setPrenom(prenom);
	    	em.persist(voyageur);
	    	idVoyageur = voyageur.getId();
		}
		return idVoyageur;
    }

	/** Associer un voyage a un voyageur authentifié **/
	public void associerVoyage(int idVoyageur, int idVoyage) {
		// On récupère le voyage courant
    	Voyage voyage = em.find(Voyage.class, idVoyage);
    	
    	// On récupère le voyageur
    	Voyageur voyageur = em.find(Voyageur.class, idVoyageur);

    	// On associe le voyage au voyageur
//		List<Voyage> listVoy = voyageur.getListVoyage();
//		System.out.println(listVoy.size());
//		listVoy.add(voyage);
//		System.out.println(listVoy.size());
//		voyageur.setListVoyage(listVoy);
		voyageur.getListVoyage().add(voyage);
		voyage.setVoyageur(voyageur);
	}

	public Voyageur accederCompte(int idVoyageur) {
		Voyageur voyageur = em.find(Voyageur.class, idVoyageur);
		return voyageur;
	}
	
	public int getIdVoyageur(String login, String pwd) {
		TypedQuery<Integer> req = em.createQuery("SELECT id FROM Voyageur v WHERE v.login = '"+login+"' and v.password= '"+pwd+"'",Integer.class);
		int idVoyageur;
		try{
			idVoyageur = req.getSingleResult();
		}catch (NoResultException e){
			idVoyageur = 0;
		}
		return idVoyageur;
	}

	public Logement getLogement(int idVoyage){
		Voyage voyage=em.find(Voyage.class, idVoyage);
		return voyage.getLogement();
	}
	
	public Vols getVols(int idVoyage){
		Voyage voyage=em.find(Voyage.class, idVoyage);
		return voyage.getVols();
	}
	
	public List<Activite> getActivites(int idVoyage){
		Voyage voyage=em.find(Voyage.class, idVoyage);
		return voyage.getListeActivites();
	}
}
