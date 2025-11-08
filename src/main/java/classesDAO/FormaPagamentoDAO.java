package classesDAO;

import classes.FormaPagamento;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;

public class FormaPagamentoDAO {

    public void cadastrarFormaPagamento(FormaPagamento formaPagamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(formaPagamento.getVenda());  
            em.persist(formaPagamento);             
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

