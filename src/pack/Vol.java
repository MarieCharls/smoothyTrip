package pack;
import java.util.*;

import javax.persistence.*;

@Entity
public class Vol {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/** Ville d'origine/de départ*/
	@Column
	private String origine;
	
	/** ville d'arrivée*/
	@Column
	private String destination;
	
	/** heure et date du départ*/
	@Column
	private Date dateDepart;
	
	/** heure et date de l'arrivée*/
	@Column
	private Date dateArrivee;
	
	/** Durée*/
	@Column
	private Date duree;
	
	/** prix du vol **/
	@Column
	private double prix;
	
	/** monnaie */
	@Column
	private String monnaie;

	@OneToOne
	Voyage voyage;

	@OneToOne
	Vols deplacement;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
	
	public String getMonnaie() {
		return monnaie;
	}
	public void setMonnaie(String m) {
		this.monnaie=m;
	}
	
	
	
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}

	
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	
	
	
	public Date getDateDepart() {
		return dateDepart;
	}
	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	
	
	
	public Date getDateArrivee() {
		return dateArrivee;
	}
	public void setDateArrivee(Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}
	

	
	
	public void setVoyage(Voyage voyage){
		this.voyage=voyage;
	}
	public Date getDuree() {
		return duree;
	}
	public void setDuree(Date duree) {
		this.duree = duree;
	}
}
