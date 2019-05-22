package pack;

import java.util.*;

import javax.persistence.*;

@Entity
public class Voyage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nom;
	
	@Column
	/** budget restant par personnes*/
	private double budgetRestantIndiv;
	
	@Column
	/** Distance logement-centre*/
	private double radius;
	
	@Column
	/** nbPersonnes participant au voyage*/
	private int nbPersonnes;
	
	@Column
	/**CityCode destination*/
	private String cityCodeDestination;
	
	@Column
	/** Destination du voyage*/
	private String destination;
	
	@OneToOne(mappedBy="voyage",fetch=FetchType.EAGER)
	private Vols vols;
	
	@OneToMany(mappedBy="voyage",fetch=FetchType.EAGER)
	List<Activite> listeActivites;
	
	@OneToOne(mappedBy="voyage",fetch=FetchType.EAGER)
	Logement logement;
	
	@ManyToOne
	Voyageur voyageur;

	public void setNom(String nom){
		this.nom = nom;
	}
	public String getNom(){
		return nom;
	}
	public int getId(){
		return id;
	}
	public Vols getVols() {
		return vols;
	}

	public void setVols(Vols v) {
		this.vols = v;
	}

	
	public List<Activite> getListeActivites() {
		return listeActivites;
	}

	public void setListeActivites(List<Activite> listeActivites) {
		this.listeActivites = listeActivites;
	}

	public Logement getLogement() {
		return logement;
	}

	public void setLogement(Logement logement) {
		this.logement = logement;
	}
	public int getNbPersonnes() {
		return nbPersonnes;
	}
	public void setNbPersonnes(int nbPersonnes) {
		this.nbPersonnes = nbPersonnes;
	}
	public double getBudgetRestantIndiv() {
		return budgetRestantIndiv;
	}
	public void setBudgetRestantIndiv(double budgetRestantIndiv) {
		this.budgetRestantIndiv = budgetRestantIndiv;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public String getCityCodeDestination() {
		return cityCodeDestination;
	}
	public void setCityCodeDestination(String cityCodeDestination) {
		this.cityCodeDestination = cityCodeDestination;
	}
	public void setVoyageur(Voyageur voyageur){
		this.voyageur = voyageur;
	}
	public Voyageur getVoyageur(){
		return this.voyageur;
	}
}