package controllers;

import DTO.FuncionarioDTO;
import classes.Funcionario;
import services.FuncionarioService;
import validacoes.Alerta;
import java.util.List;

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController() {
        this.funcionarioService = new FuncionarioService();
    }

    public void cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            funcionarioService.salvarFuncionario(funcionarioDTO);
            System.out.println("Funcionário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro no cadastro");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao cadastrar o funcionário.");
            
        }
    }

    public void atualizarFuncionario(FuncionarioDTO funcionarioDTO, Long id) {
        try {
            funcionarioService.atualizarFuncionario(funcionarioDTO, id);
            System.out.println("Dados do funcionário atualizados com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na atualização");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao atualizar o funcionário.");
            
        }
    }

    public void excluirFuncionario(String id) {
        try {
            funcionarioService.excluirFuncionario(id);
            System.out.println("Funcionário removido com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na exclusão");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao excluir o funcionário.");
            
        }
    }

    public List<Funcionario> listarFuncionarios() {
        try {
            return funcionarioService.listarFuncionarios();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao listar os funcionários.");
            
        }
        return null;
    }

    public List <Funcionario> buscarPorCPF(String cpf) {
        try {
            return funcionarioService.buscarPorCPF(cpf);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na busca");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar o funcionário pelo CPF.");
           
        }
        return null;
    }
}
