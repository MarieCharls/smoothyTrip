package pack;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Professionnel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	/** Nom du professionnel **/
	@Column
	private String name;
	/** Adresse **/
	@Column
	private String address;
	/** Telephone **/
	@Column
	private String tel;
	
	@OneToMany(mappedBy="pro", fetch = FetchType.EAGER)
	Set<Activite> listeActivite;
	
	@OneToMany(mappedBy="pro", fetch = FetchType.EAGER)
	Set<Avis> listeAvis;
	/** Constructeur de base **/
	public Professionnel(){}
	// Setter,getter
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setAddress(String add){
		this.address = add;
	}
	public String getAdd(){
		return this.address;
	}
	public void setTel(String tel){
		this.tel = tel;
	}
	public String getTel(){
		return this.tel;
	}
}
