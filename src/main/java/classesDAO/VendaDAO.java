
package classesDAO;

import classes.Cliente;
import classes.JPAUtil;
import classes.Produto;
import classes.Vendas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import validacoes.Alerta;

public class VendaDAO {
    
    public static Cliente listarCPF(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        Cliente cli;
        TypedQuery<Cliente> consulta;
        
        try {
                consulta = em.createQuery("SELECT c from Cliente c WHERE c.cpf = :cpf", Cliente.class);
                consulta.setParameter("cpf", cpf);
                cli = consulta.getSingleResult();
                  
            return cli;
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro as buscar o CPF");
        }
        return null;  
    }
    
    public static Produto listarProdutos(String cod) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto prod;
        TypedQuery<Produto> consulta;
        
        try {
                consulta = em.createQuery("SELECT p from Produto p WHERE p.codigo = :codigo", Produto.class);
                consulta.setParameter("codigo", cod);
                prod = consulta.getSingleResult();
                  
            return prod;
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro as buscar o Código");
        }
        return null;  
    }
    
     public static List<Vendas> listarVendas(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Vendas> listaVenda = new ArrayList<>();
        Query consulta;

        try {
            if (nome.equals("")) {
                consulta = em.createQuery("SELECT venda FROM Vendas venda");
                listaVenda = consulta.getResultList();
            } else {
                consulta = em.createQuery("SELECT v from Vendas v WHERE v.cliente.nome = :nomeC");
                consulta.setParameter("nomeC", nome);
                listaVenda = consulta.getResultList();
            }

            return listaVenda;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alerta.Erro("Erro listagem", "Erro ao buscar as informação para lista");
        }

        return listaVenda;
    }
        public static void excluirVendas(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Vendas vendaRemover = em.find(Vendas.class, id);
            em.remove(vendaRemover);
            em.getTransaction().commit();
            
            Alerta.Erro("Erro excluir", "Venda excluida com sucesso!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alerta.Erro("Erro excluir", "Erro ao excluir a venda no banco");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
}
