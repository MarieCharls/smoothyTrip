package pack;
import java.util.*;

import javax.persistence.*;

@Entity
public class Vol {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String origine;
	
	@Column
	private String destination;
	
	@Column
	private Date dateDepart;
	
	@Column
	private Date dateArrivee;
	
	@OneToOne
	Voyage voyage;
}
