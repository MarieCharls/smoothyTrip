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
	
	@Column
	private double prix;

	
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
	
	
	
}
