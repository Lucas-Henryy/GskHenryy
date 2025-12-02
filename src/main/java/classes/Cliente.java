package classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    @Entity
    @Table(name = "tbCliente")
    public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomeC")
    private String nome;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "cpf")
    private String cpf;

    public Cliente() {
    }

    public Cliente (String nome, String sexo, String email, String telefone, String cpf) {
        
      if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode estar vazio!");
        }
       
      if (nome == null || nome.isBlank()){
          throw new IllegalArgumentException("Nome não pode estar vazio!");
      }
      
      if (sexo == null || sexo.isBlank()){
      throw new IllegalArgumentException("Sexo não pode estar vazio!");
      }
      
      if (email == null || email.isBlank()){
      throw new IllegalArgumentException("Email não pode estar vazio!");
      }
      if (telefone == null || telefone.isBlank()){
      throw new IllegalArgumentException("Telefone não pode estar vazio!");
      }
      
        
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    
    
}
