package classesDAO;

import classes.Cargo;
import classes.Funcionario;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void salvar(Funcionario funcionario) {
        executarTransacao(em -> em.persist(funcionario));
    }

    public void atualizar(Funcionario cliente) {
        executarTransacao(em -> em.merge(cliente));
    }

    public Funcionario buscarPorId(String id) {
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
            TypedQuery<Funcionario> query = em.createQuery("SELECT c FROM Cliente c", Funcionario.class);
            return query.getResultList();
        } finally {
            em.close();
        }

    }

    public List<Funcionario> buscarPorCPF(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Funcionario> query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpfF :cpfF", Funcionario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cargo> ListarCargos(String cargo) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Cargo> cargos = new ArrayList<>();
        try {
            TypedQuery<Cargo> query = em.createQuery("SELECT c FROM Cargo c", Cargo.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void excluirFuncionario(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Funcionario cliente = em.find(Funcionario.class, id);
            if (cliente != null) {
                em.remove(cliente);
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
            em.getTransaction().rollback();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

}
