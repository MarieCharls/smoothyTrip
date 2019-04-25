package pack;


import javax.persistence.*;

@Entity
public class Activite {
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	Voyage voyage;
	

}
