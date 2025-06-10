
package classesDAO;

import classes.Cliente;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import validacoes.Alerta;

public class ClienteDAO {
    public static void cadastrarCliente(Cliente cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            Alerta.Erro("Cadastro concluído!", "Cliente cadastrado com sucesso!");

        } catch (Exception e) {
            em.getTransaction().rollback();
            Alerta.Erro("Erro no cadastro", "Erro ao inserir o cadastro no banco");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
    
   
    public static List<Cliente> listarClientes(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Cliente> listaCli = new ArrayList<>();
        Query consulta;

        try {
            if (cpf.equals("")) {
                consulta = em.createQuery("SELECT cliente FROM Cliente cliente");
                listaCli = consulta.getResultList();
            } else {
                consulta = em.createQuery("SELECT c from Cliente c WHERE c.cpf = :cpf");
                consulta.setParameter("cpf", cpf);
                listaCli = consulta.getResultList();
            }

            return listaCli;
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro as buscar informação para lista");
        }

        return listaCli;
    }

    public static Cliente listarCliente(String idCliente) {
        EntityManager em = JPAUtil.getEntityManager();
        Cliente cliente = null;

        try {
            cliente = em.find(Cliente.class, idCliente);
            return cliente;
        } catch (Exception e) {
            Alerta.Erro("Erro", "Erro ao listar o cliente.");
        } finally {
            em.close();
        }
        return cliente;
    }
    
    public static void editarCliente(Cliente cliente) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            Alerta.Erro("Sucesso!", "Edição realizada com sucesso!");

        } catch (Exception e) {
            em.getTransaction().rollback();
            Alerta.Erro("Erro ao editar", "Ocorreu um erro ao editar as informações");
        } finally {
            em.close();
        }
    }
    
    public static void excluirClientes(String id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Cliente clientesRemover = em.find(Cliente.class, id);
            em.remove(clientesRemover);
            em.getTransaction().commit();

        } catch (Exception e) {
            Alerta.Erro("Erro excluir", "Erro ao excluir o cliente no banco");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
}
