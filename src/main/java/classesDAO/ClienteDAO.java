package classesDAO;

import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        executarTransacao(em -> em.persist(cliente));
    }

    public void atualizar(Cliente cliente) {
        executarTransacao(em -> em.merge(cliente));
    }

    public Cliente buscarPorId(String id) {
        executarTransacao(em = JPAUtil.getEntityManager());

        try {
            return em.find(Cliente.class, id);

        } finally {
            em.close();
        }
    }

    public list<cliente> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<cliente> query = em.createQuery("SELECT c FROM Cliente c", cliente.class);
            return query.getResultList();
        } finally {
            em.close();
        }

    }

    public list<cliente> buscarPorCPF() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpf :cpf", cliente.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    private void executarTransacao(java.util.function.Consumer<EntityManager> acao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            acao.accept(em);
            em.getTransaction().rollback();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

}
