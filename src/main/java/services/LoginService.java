package services;

import DTO.LoginDTO;
import classes.Login;
import classesDAO.LoginDAO;

public class LoginService {

    private final LoginDAO loginDAO;

    public LoginService() {
        this.loginDAO = new LoginDAO();
    }

    public Login autenticar(LoginDTO loginDTO) {
        return loginDAO.autenticar(loginDTO.getLogin(), loginDTO.getSenha());
    }
}
