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
	
	/** Code IATA de la ville */
	@Column
	private String cityCode;
	
	/** Distance du centre */
	private int radius;
	
	/** Unité de distance (par défaut nous garderons le km */
	private String radiusUnit = "KM";
	
	/** Nom de l'activité */
	private String name;
	
	/** Type d'activité */
	private String type;

	/** Nombre de participants **/
	private int nbPart;
	
	/** Avis sur l'activite **/
	/**private List<Avis> listAvis;*/
	@OneToMany(mappedBy="act", fetch = FetchType.EAGER)
	Set<Avis> listeAvis;
	
	/** Professionel proposant l'activite **/
	@ManyToOne
	private Professionnel pro;
	
	/** Classement des résultats NONE, DISTANCE, PRICE*/
	private enum sort { 
		NONE, DISTANCE, PRICE;
		}
	private sort typeSort;
	/** Contructeur de base */
	public Activite() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getNbPart() {
		return nbPart;
	}

	public void setNbPart(int part) {
		this.nbPart = part;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getRadiusUnit() {
		return radiusUnit;
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
	public void setSort(sort typesort){
		this.typeSort = typesort;
	}
	public sort getSort(){
		return this.typeSort;
	}	
	public void setPro(Professionnel pro){
		this.pro = pro;
	}
	public Professionnel getPro(){
		return this.pro;
	}
	/**public void addAvis(Avis avis){
		this.listAvis.add(avis);
	}*/
}
