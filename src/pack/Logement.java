package pack;
import java.util.*;

import javax.persistence.*;

@Entity
public class Logement {

	String Lieu;
	
	@OneToOne
	Voyage voyage;
}
