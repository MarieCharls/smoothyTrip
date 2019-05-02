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
	/** nbPersonnes participant au voyage*/
	private int nbPersonnes;
	
	@OneToOne(mappedBy="voyage",fetch=FetchType.EAGER)
	private Vol volAller;
	
	@OneToOne(mappedBy="voyage",fetch=FetchType.EAGER)
	private Vol volRetour;
	
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
	public Vol getVolAller() {
		return volAller;
	}

	public void setVolAller(Vol volAller) {
		this.volAller = volAller;
	}

	public Vol getVolRetour() {
		return volRetour;
	}

	public void setVolRetour(Vol volRetour) {
		this.volRetour = volRetour;
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
	
	

}
