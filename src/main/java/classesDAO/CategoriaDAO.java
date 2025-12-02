
package classesDAO;

import classes.Categoria;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CategoriaDAO {
    
    public List<Categoria> pegarCategorias(){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            TypedQuery<Categoria> query = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
            return query.getResultList();
        } finally{
            em.close();
        }
    }

    public Categoria buscarPorId(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            return em.find(Categoria.class, id);
        } finally{
            em.close();
        }
    }
}
