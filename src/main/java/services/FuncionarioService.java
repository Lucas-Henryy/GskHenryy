package services;

import classes.Cargo;
import classes.Funcionario;
import classesDAO.FuncionarioDAO;
import classesDAO.CargoDAO;
import java.util.List;
import java.util.regex.Pattern;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO;
    private final CargoDAO cargoDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
        this.cargoDAO = new CargoDAO();
    }

    public void salvarFuncionario(Funcionario funcionario) {
        validarFuncionario(funcionario);

        List<Funcionario> existentes = funcionarioDAO.buscarPorCPF(funcionario.getCpfF());
        if (!existentes.isEmpty()) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este CPF!");
        }

        funcionarioDAO.salvar(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) {
        if (funcionario == null || funcionario.getId() == null) {
            throw new IllegalArgumentException("Funcionário inválido para atualização!");
        }
        validarFuncionario(funcionario);
        funcionarioDAO.atualizar(funcionario);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioDAO.listarTodos();
    }

    public List<Funcionario> buscarPorCPF(String cpf) {
        return funcionarioDAO.buscarPorCPF(cpf);
    }

    public void excluirFuncionario(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido para exclusão!");
        }
        funcionarioDAO.excluirFuncionario(id);
    }

    // ====================== VALIDAÇÕES ======================

    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo!");
        }

        // Nome
        if (funcionario.getNomeF()== null || funcionario.getNomeF().isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        if (funcionario.getNomeF().length() < 3) {
            throw new IllegalArgumentException("O nome deve conter pelo menos 3 caracteres!");
        }

        // CPF
        String cpf = funcionario.getCpfF();
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF é obrigatório!");
        }
        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos!");
        }


        // Logradouro
        if (funcionario.getLogradouro() == null || funcionario.getLogradouro().isBlank()) {
            throw new IllegalArgumentException("O logradouro é obrigatório!");
        }
        if (funcionario.getLogradouro().length() < 5) {
            throw new IllegalArgumentException("O logradouro deve conter pelo menos 5 caracteres!");
        }

        // Número
        if (funcionario.getNumero() == null || funcionario.getNumero().isBlank()) {
            throw new IllegalArgumentException("O número é obrigatório!");
        }
        if (!Pattern.matches("[A-Za-z0-9]+", funcionario.getNumero())) {
            throw new IllegalArgumentException("O número deve conter apenas letras e números!");
        }

        // CEP
        String cep = funcionario.getCep();
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório!");
        }
        if (!cep.matches("^\\d{8}$|^\\d{5}-\\d{3}$")) {
            throw new IllegalArgumentException("O CEP deve estar no formato 00000000 ou 00000-000!");
        }

        // Telefone
        String telefone = funcionario.getTelefoneF();
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("O telefone é obrigatório!");
        }
        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("O telefone deve conter 10 ou 11 dígitos (com DDD)!");
        }

        // Cargo
        if (funcionario.getCargo() == null || funcionario.getCargo().getId() == null) {
            throw new IllegalArgumentException("O funcionário deve possuir um cargo válido!");
        }
        
        List<Cargo> cargos = cargoDAO.listarCargos();
        
        Cargo cargoExistente = cargoDAO.buscarPorId(funcionario.getCargo().getId());
        if (cargoExistente == null) {
            throw new IllegalArgumentException("O cargo informado não existe no banco de dados!");
        }
        
    }
}
