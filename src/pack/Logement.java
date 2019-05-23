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
	private String[] adresse;
	
	/** Distance du centre */
	@Column
	private double radius;
	
	/** Unité de distance*/
	@Column
	private String radiusUnit;
	
	/** Prix */
	@Column
	private String prix;
	
	/** Prix base*/
	@Column
	private String prixBase;
	
	
	/** monnaire */
	@Column
	private String monnaire;
	
	/** Liste de commodités*/
	/**@Column
	List<String> commodites;*/
	
	/** Voyage associé au logement*/
	@OneToOne
	Voyage voyage;
	
	/** Contructeur de base */
	public Logement() {}

	public int getId(){
		return id;
	}
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

	public String[] getAdresse() {
		return adresse;
	}

	public void setAdresse(String[] adresse) {
		this.adresse = adresse;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getRadiusUnit() {
		return radiusUnit;
	}

	public void setRadiusUnit(String radiusUnit) {
		this.radiusUnit = radiusUnit;
	}

	public String getMonnaire() {
		return monnaire;
	}

	public void setMonnaire(String monnaire) {
		this.monnaire = monnaire;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getPrixBase() {
		return prixBase;
	}

	public void setPrixBase(String prixBase) {
		this.prixBase = prixBase;
	}

	public void setVoyage(Voyage voyage){
		this.voyage=voyage;
	}
}