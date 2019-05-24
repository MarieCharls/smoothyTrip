package pack;

import java.util.*;

import javax.persistence.*;

@Entity
public class Vols {
	

	public Vols() {
		super();
	}
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private Vol volAller;
	
	@OneToOne
	private Vol volRetour;
	
	@OneToOne
	private Voyage voyage;
	
	@Column
	private double prix;


	public Vol getVolAller() {
		return volAller;
	}

	public void setVolAller(Vol v) {
		this.volAller = v;
	}

	public Vol getVolRetour() {
		return volRetour;
	}

	public void setVolRetour(Vol v) {
		this.volRetour = v;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
}