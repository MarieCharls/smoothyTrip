package pack;
import java.util.*;

import javax.persistence.*;

@Entity
public class Vol {

	@OneToOne
	Voyage voyage;
}
