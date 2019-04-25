package pack;

import java.util.List;

import javax.persistence.*;

@Entity
public class Voyageur {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/** Nom du voyageur*/
	@Column
	private String nom;
	
	/** Prenom du voyageur*/
	@Column
	private String prenom;
	
	@Column
	private List<String> centresInterets;
	
	@OneToMany(mappedBy="voyageur", fetch = FetchType.EAGER)
	List<Voyage> listVoyage;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<String> getCentresInterets() {
		return centresInterets;
	}

	public void setCentresInterets(List<String> centresInterets) {
		this.centresInterets = centresInterets;
	}

	public List<Voyage> getListVoyage() {
		return listVoyage;
	}

	public void setListVoyage(List<Voyage> listVoyage) {
		this.listVoyage = listVoyage;
	}
		
}
