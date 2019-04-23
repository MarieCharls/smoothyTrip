package pack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 * Session Bean implementation class Facade
 */

@Singleton
@LocalBean
public class Facade {
	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public Facade() {     
    }
	
	
	
	

}
