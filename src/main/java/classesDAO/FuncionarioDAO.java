package classesDAO;

import classes.Funcionario;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class FuncionarioDAO {

    public void salvar(Funcionario funcionario) {
        executarTransacao(em -> em.persist(funcionario));
    }

    public void atualizar(Funcionario cliente) {
        executarTransacao(em -> em.merge(cliente));
    }

    public Funcionario buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Funcionario.class, id);

        } finally {
            em.close();
        }
    }

    public List<Funcionario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
            return query.getResultList();
        } finally {
            em.close();
        }

    }

    public List<Funcionario> buscarPorCPF(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f WHERE f.cpfF :cpfF", Funcionario.class);
            query.setParameter("cpfF", cpf);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void excluirFuncionario(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Funcionario funcionario = em.find(Funcionario.class, id);
            if (funcionario != null) {
                em.remove(funcionario);
            }
            em.getTransaction().commit();
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
