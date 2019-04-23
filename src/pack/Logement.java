package pack;
import java.util.*;

import javax.persistence.*;

@Entity
public class Logement {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/** Code IATA de la ville*/
	@Column
	private String cityCode;
	
	/** Date de début */
	@Column
	private Date checkInDate;
	
	/** Date de fin*/
	@Column
	private Date checkOutDate;
	
	/** Nombre de chambres */
	@Column
	private int roomQuantity;
	
	/** Nombre d'adultes */
	@Column
	private int adults;
	
	/** Distance du centre */
	private int radius;
	
	/** Unité de distance (par défaut nous garderons le km */
	private String radiusUnit = "KM";
	
	/** Echelon de prix sous la forme 'min-max' */
	private String priceRange;
	
	/** Monnaie (par défaut EUR) */
	private String currency = "EUR";
	
	/** Classement des résultats NONE, DISTANCE, PRICE*/
	private enum sort { 
		NONE, DISTANCE, PRICE;
		}
	
	/** Voyage associé au logement*/
	@OneToOne
	Voyage voyage;
	
	/** Contructeur de base */
	public Logement() {}

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

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
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

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getCurrency() {
		return currency;
	};
	
	
	/**public void demandeToken(){
		String[] commande;
		commande = new String[]{"curl", "\" ,"-X"};
	}
	public void rechercherHotel(){
		String[] commande;
		String token = tokenff;
		commande = new String[]{"curl","-X","GET","https://test.api.amadeus.com/v2/shopping/hotel-offers?"};
		// Ajouter les critères de recherches
		
		// Ajout de l'authentification
		
	}*/
}
