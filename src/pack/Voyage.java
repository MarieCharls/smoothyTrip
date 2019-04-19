package pack;

import java.util.*;

import javax.persistence.*;

@Entity
public class Voyage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(mappedBy="voyage",fetch=FetchType.EAGER)
	private Vol volAller;
	
	@OneToOne(mappedBy="voyage",fetch=FetchType.EAGER)
	private Vol volRetour;
	
	@OneToMany(mappedBy="voyage",fetch=FetchType.EAGER)
	List<Activite> listeActivites;
	
	@OneToOne(fetch=FetchType.EAGER)
	Logement logement;

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
	
	

}
