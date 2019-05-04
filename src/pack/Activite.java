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
	/**private List<Avis> listAvis;*/
	@OneToMany(mappedBy="act", fetch = FetchType.EAGER)
	Set<Avis> listeAvis;
	
	/** Professionel proposant l'activite **/
	@ManyToOne
	private Professionnel pro;
	
	/** Tags decrivant l'activite **/
	private String[] tags;
	
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
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String[] getTags(){
		return this.tags;
	}
}
