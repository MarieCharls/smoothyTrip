package pack;

import java.util.*;

import javax.persistence.*;

@Entity
public class Activite {
	
	@ManyToOne(fetch=FetchType.EAGER)
	Voyage voyage;
	

}
