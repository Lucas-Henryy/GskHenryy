package classesDAO;

import classes.Funcionario;
import classes.Cargo;
import classes.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import validacoes.Alerta;

public class FuncionarioDAO {
    
    public static List<Cargo> pegarCargos(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Cargo> cargos = new ArrayList<>();
        try{
            Query consulta = em.createQuery("SELECT c FROM Cargo c", Cargo.class);
            cargos = consulta.getResultList();
            return cargos;
        } catch(Exception e){
             Alerta.Erro("Erro ao consultar os cargos", "Erro de consulta");
    }
        return cargos;
  }
    public static void cadastrarFuncionario(Funcionario funcionario){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(funcionario);
            em.getTransaction().commit();
            Alerta.Erro("Cadastro concluído!", "Funcionario cadastrado com sucesso!");            
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            Alerta.Erro("Erro ao inserir o cadastro no banco", "Erro no cadastro");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
    
    public static List<Funcionario> listarFuncionarios(String cpf){
        EntityManager em = JPAUtil.getEntityManager();
        List<Funcionario> listaFunc = new ArrayList<>();
        Query consulta;

        try {
            if (cpf.equals("")) {
                consulta = em.createQuery("SELECT funcionario FROM Funcionario funcionario");
                listaFunc = consulta.getResultList();
            } else {
                consulta = em.createQuery("SELECT f from Cliente f WHERE f.cpfC = :cpfF");
                consulta.setParameter("cpfF", cpf);
                listaFunc = consulta.getResultList();
            }
            return listaFunc;
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro as buscar informação para lista");
        }
        return listaFunc;
    }
    
    public static Funcionario listarFuncionario(String idFuncionario) {
    EntityManager em = JPAUtil.getEntityManager();
    Funcionario funcionario = null;

    try {
        Query query = em.createQuery(
            "SELECT f FROM Funcionario f LEFT JOIN FETCH f.login WHERE f.id = :id",
            Funcionario.class
        );
        query.setParameter("id", Long.parseLong(idFuncionario));
        funcionario = (Funcionario) query.getSingleResult();
        return funcionario;
    } catch (Exception e) {
        Alerta.Erro("Erro ao listar", "Erro ao listar o funcionario");
    } finally {
        em.close();
    }
    return funcionario;
}
    
    public static void editarFuncionario(Funcionario funcionario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
            Alerta.Erro("Sucesso!", "Edição realizada com sucesso!");

        } catch (Exception e) {
            em.getTransaction().rollback();
            Alerta.Erro("Erro ao editar", "Ocorreu um erro ao editar as informações do cliente.");
        } finally {
            em.close();
        }
    }
    
    public static void excluirFuncionario(String id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Funcionario funcionariosRemover = em.find(Funcionario.class, id);
            em.remove(funcionariosRemover);
            em.getTransaction().commit();

        } catch (Exception e) {
            Alerta.Erro("Erro excluir", "Erro ao excluir o cliente no banco");
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
    
    
}
