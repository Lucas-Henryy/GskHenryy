package classesDAO;

import classes.Categoria;
import classes.JPAUtil;
import classes.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import validacoes.Alerta;

public class ProdutoDAO {

    public static List<Categoria> pegarCategoria() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Categoria> categ = new ArrayList<>();
        try {
            Query consulta = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
            categ = consulta.getResultList();
            return categ;
        } catch (Exception e) {
            Alerta.Erro("Erro de consulta", "Erro ao consultar os categoria");
        }
        return categ;
    }

    public static void cadastrarProduto(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
            Alerta.Erro("Cadastro concluído!", "Produto cadastrado com sucesso!");

        } catch (Exception e) {
            em.getTransaction().rollback();
            Alerta.Erro("Erro no cadastro", "Erro ao inserir o cadastro no banco");
        } finally {
            JPAUtil.closeEntityManager();
        }

    }
    
    public static List<Produto> listarProdutos(String cod) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Produto> listaProd = new ArrayList<>();
        Query consulta;

        try {
            if (cod.equals("")) {
                consulta = em.createQuery("SELECT produto FROM Produto produto");
                listaProd = consulta.getResultList();
            } else {
                consulta = em.createQuery("SELECT p from Produto p WHERE p.codigo = :codigo");
                consulta.setParameter("codigo", cod);
                listaProd = consulta.getResultList();
            }

            return listaProd;
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro as buscar informação para lista");
        }

        return listaProd;
    }

    public static Produto listarProduto(String idProduto) {
        EntityManager em = JPAUtil.getEntityManager();
        Produto produto = null;

        try {
            produto = em.find(Produto.class, idProduto);
            return produto;
        } catch (Exception e) {
            Alerta.Erro("Erro", "Erro ao listar o produto.");
        } finally {
            em.close();
        }
        return produto;
    }
    
    public static void editarProduto(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
            Alerta.Erro("Sucesso!", "Edição realizada com sucesso!");

        } catch (Exception e) {
            em.getTransaction().rollback();
            Alerta.Erro("Erro ao editar", "Ocorreu um erro ao editar as informações");
        } finally {
            em.close();
        }
    }
    
    public static void excluirProdutos(String id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Produto produtosRemover = em.find(Produto.class, id);
            em.remove(produtosRemover);
            em.getTransaction().commit();

        } catch (Exception e) {
            Alerta.Erro("Erro excluir", "Erro ao excluir o produto no banco");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
}
