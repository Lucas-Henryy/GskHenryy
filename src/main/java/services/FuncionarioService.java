package services;

import DTO.FuncionarioDTO;
import classes.Cargo;
import classes.Funcionario;
import classes.Login;
import classesDAO.CargoDAO;
import classesDAO.FuncionarioDAO;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO;
    private final CargoDAO cargoDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
        this.cargoDAO = new CargoDAO();
    }

    public void salvarFuncionario(FuncionarioDTO funcionarioDTO) {
        Login login = new Login(funcionarioDTO.getLogin(), funcionarioDTO.getSenha());
        Cargo cargo = new CargoDAO().buscarPorId(funcionarioDTO.getCargo());

        Funcionario funcionario = new Funcionario(funcionarioDTO.getNome(), funcionarioDTO.getCpf(), funcionarioDTO.getLogradouro(),
                funcionarioDTO.getCep(), funcionarioDTO.getNumero(), funcionarioDTO.getComplemento(), funcionarioDTO.getTelefone(),
                login, cargo);

        funcionarioDAO.salvar(funcionario);
    }

    public void atualizarFuncionario(FuncionarioDTO funcionarioDTO, Long id) {
        Funcionario funcionarioEditar = funcionarioDAO.buscarPorId(id);
        Cargo cargo = new CargoDAO().buscarPorId(funcionarioDTO.getCargo());

        funcionarioEditar.setNomeF(funcionarioDTO.getNome());
        funcionarioEditar.setCpfF(funcionarioDTO.getCpf());
        funcionarioEditar.setLogradouro(funcionarioDTO.getLogradouro());
        funcionarioEditar.setCep(funcionarioDTO.getCep());
        funcionarioEditar.setNumero(funcionarioDTO.getNumero());
        funcionarioEditar.setComplemento(funcionarioDTO.getComplemento());
        funcionarioEditar.setTelefoneF(funcionarioDTO.getTelefone());
        funcionarioEditar.getLogin().setSenha(funcionarioDTO.getSenha());
        funcionarioEditar.setCargo(cargo);
        funcionarioDAO.atualizar(funcionarioEditar);
    }
    
    
    public Funcionario buscarPorId(Long id) {
        return funcionarioDAO.buscarPorId(id);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioDAO.listarTodos();
    }

    public List<Funcionario> buscarPorCPF(String cpf) {
        return funcionarioDAO.buscarPorCPF(cpf);
    }

    public void excluirFuncionario(String id) {
        funcionarioDAO.excluirFuncionario(id);
    }

}
