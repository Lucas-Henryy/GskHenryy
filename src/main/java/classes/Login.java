
package classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbLogin")
public class Login {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLogin")
    private Long id;
    
    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;
    
    @OneToOne
    @JoinColumn(name = "funcionario_id", unique = true)
    private Funcionario funcionario;
    
    public Login() {
    }

    public Login(String login, String senha) {
        
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("O campo 'login' é obrigatório!");
        }

        if (login.length() < 4) {
            throw new IllegalArgumentException("O login deve ter pelo menos 4 caracteres!");
        }

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("O campo 'senha' é obrigatório!");
        }

        if (senha.length() < 4) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 4 caracteres!");
        }
        
        
        this.login = login;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
    
}
