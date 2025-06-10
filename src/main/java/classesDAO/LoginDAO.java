
package classesDAO;

import classes.JPAUtil;
import classes.Login;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LoginDAO {
    public Login usuario(String login, String senha){
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Login> consul;

        try{
            consul = em.createQuery("SELECT l FROM Login l where l.login = :login AND l.senha = :senha", Login.class);
            consul.setParameter("login", login);
            consul.setParameter("senha", senha);
            return consul.getSingleResult();

        } catch (Exception e) {
            return null;
        } finally {
            JPAUtil.closeEntityManager();
        }
    }
}
