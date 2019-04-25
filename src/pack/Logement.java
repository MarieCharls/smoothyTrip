package pack;
import java.util.*;

import javax.persistence.*;

@Entity
public class Logement {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/** Code IATA de la ville*/
	@Column
	private String cityCode;
	
	/** Nom de l'hôtel*/
	@Column
	private String nom;
	
	/** Adresse*/
	@Column
	private String adresse;
	
	/** Distance du centre */
	@Column
	private int radius;
	
	/** Liste de commodités*/
	/**@Column
	List<String> commodites;*/
	
	/** Voyage associé au logement*/
	@OneToOne
	Voyage voyage;
	
	/** Contructeur de base */
	public Logement() {}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**public List<String> getCommodites() {
		return commodites;
	}

	public void setCommodites(List<String> commodites) {
		this.commodites = commodites;
	}*/

	
	
	/**public void demandeToken(){
		String[] commande;
		commande = new String[]{"curl", "\" ,"-X"};
	}
	public void rechercherHotel(){
		String[] commande;
		String token = tokenff;
		commande = new String[]{"curl","-X","GET","https://test.api.amadeus.com/v2/shopping/hotel-offers?"};
		// Ajouter les critères de recherches
		
		// Ajout de l'authentification
		
	}*/
}
