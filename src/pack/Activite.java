package pack;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Activite {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch=FetchType.EAGER)
	Voyage voyage;
	
	/** Nom de l'activité */
	private String name;
	
	/** Type d'activité */
	private String type;

	/** Avis sur l'activite **/
	@OneToMany(mappedBy="act", fetch = FetchType.EAGER)
	Set<Avis> listeAvis;
	
	/** Professionel proposant l'activite **/
	@ManyToOne
	private Professionnel pro;
	
	/** Coordonnees de l'activite **/
	private String tel;
	private String address;
	
	
	private String idAct;
	
	
	/** Contructeur de base */
	public Activite() {}

	public int getId() {
		return id;
	}

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
	/**public void setAvis(List<Avis> avis){
		this.listAvis = avis;
	}
	public List<Avis> getAvis(){
		return this.listAvis;
	}*/	
	public void setPro(Professionnel pro){
		this.pro = pro;
	}
	public Professionnel getPro(){
		return this.pro;
	}
	/**public void addAvis(Avis avis){
		this.listAvis.add(avis);
	}*/
//	public void setTags(String[] tags) {
//		this.tags = tags;
//	}
//	public String[] getTags(){
//		return this.tags;
//	}

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
