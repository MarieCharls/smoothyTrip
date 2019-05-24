package pack;

import javax.persistence.*;

@Entity
public class Activite{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch=FetchType.EAGER)
	Voyage voyage;
	
	/** Nom de l'activité */
	private String name;
	
	/** Type d'activité */
	private String type;
	
	/** Professionel proposant l'activite **/
	@ManyToOne
	private Professionnel pro;
	
	/** Coordonnees de l'activite **/
	private String tel;
	private String address;

	/** Descripteur de l'activité **/
	private String idAct;
	
	/** Contructeur de base */
	public Activite() {}

	public int getId() {
		return id;
	}
	// Setter,getter
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setType (String type){
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setPro(Professionnel pro){
		this.pro = pro;
	}
	public Professionnel getPro(){
		return this.pro;
	}

	public void setIdAct(String id) {
		this.idAct = id;
		
	}
    	public String getIdAct(){
    		return this.idAct;
    	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	public void setTel(String phone) {
		this.tel = phone;	
	}
	public String getTel(){
		return this.tel;
	}
	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;	
	}
}
