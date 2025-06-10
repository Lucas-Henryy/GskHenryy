
package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT= "GskHenryy-PU";
    
    static EntityManager em;
    static EntityManagerFactory factory;
    
    
    public static EntityManager getEntityManager() {
        
        if(factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        
        if(em == null || !em.isOpen()) {
             em = factory.createEntityManager();
        }
        return em;
    }
    
    public static void closeEntityManager() {
        if(em.isOpen() && em != null) {
            em.close();
            factory.close();
        }
    }
}
