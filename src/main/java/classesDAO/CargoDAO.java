package classesDAO;

import classes.Cargo;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CargoDAO {

    public List<Cargo> listarCargos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Cargo> query = em.createQuery("SELECT c FROM Cargo c", Cargo.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Cargo buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }
    
     private void executarTransacao(java.util.function.Consumer<EntityManager> acao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            acao.accept(em);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }


}
