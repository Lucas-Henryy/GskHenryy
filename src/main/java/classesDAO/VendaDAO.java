
package classesDAO;

import classes.Cliente;
import classes.JPAUtil;
import classes.Produto;
import classes.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public Cliente listarCPF(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Cliente> consulta = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class);
            consulta.setParameter("cpf", cpf);
            return consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Produto listarProdutos(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Produto> consulta = em.createQuery(
                    "SELECT p FROM Produto p WHERE p.codigo = :codigo", Produto.class);
            consulta.setParameter("codigo", codigo);
            return consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Venda> listarVendas(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Venda> listaVenda = new ArrayList<>();

        try {
            Query consulta;
            if (nome == null || nome.isBlank()) {
                consulta = em.createQuery("SELECT v FROM Vendas v", Venda.class);
            } else {
                consulta = em.createQuery("SELECT v FROM Vendas v WHERE v.cliente.nome = :nomeC", Venda.class);
                consulta.setParameter("nomeC", nome);
            }
            listaVenda = consulta.getResultList();
        } finally {
            em.close();
        }

        return listaVenda;
    }
    
    
    
    public void cadastrarVenda(Venda vendas) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(vendas);               
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluirVendas(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Venda vendaRemover = em.find(Venda.class, id);
            if (vendaRemover != null) {
                em.remove(vendaRemover);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
   
}
