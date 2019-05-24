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
	
	/** Login de l'utilisateur **/
	private String login;
	
	/** Mot de passe de l'utilisateur **/
	private String password;
	
	/**private List<Voyage>;*/
	@OneToMany(mappedBy="voyageur", fetch = FetchType.LAZY)
	private List<Voyage> listVoyage;

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

	public List<Voyage> getListVoyage() {
		return listVoyage;
	}

	public void setListVoyage(List<Voyage> listVoyage) {
		this.listVoyage = listVoyage;
	}

	public void addVoyage (Voyage voyage){
		this.listVoyage.add(voyage);
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getId() {
		return this.id;
	}
		
}