package pack;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	/** Services proposes **/
	@Column
	private List<Object> services;
	
	public Professionnel(){}
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
	public void setServ(List<Object> serv){
		this.services = serv;
	}
	public List<Object> getServ(){
		return this.services;
	}
	public void addServ(Object serv){
		this.services.add(serv);
	}
}
