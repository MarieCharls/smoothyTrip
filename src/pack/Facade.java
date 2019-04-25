package pack;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.*;

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
    public List<Logement> chercherLogement(){ return null; }
}
