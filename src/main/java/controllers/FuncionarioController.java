package controllers;

import classes.Funcionario;
import services.FuncionarioService;
import validacoes.Alerta;
import java.util.List;

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController() {
        this.funcionarioService = new FuncionarioService();
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        try {
            funcionarioService.salvarFuncionario(funcionario);
            Alerta.Sucesso("Cadastro realizado com sucesso!", "Funcionário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro no cadastro", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Ocorreu um erro ao cadastrar o funcionário.");
            
        }
    }

    public void atualizarFuncionario(Funcionario funcionario) {
        try {
            funcionarioService.atualizarFuncionario(funcionario);
            Alerta.Sucesso("Atualização concluída", "Dados do funcionário atualizados com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na atualização", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Ocorreu um erro ao atualizar o funcionário.");
            
        }
    }

    public void excluirFuncionario(String id) {
        try {
            funcionarioService.excluirFuncionario(id);
            Alerta.Sucesso("Exclusão concluída", "Funcionário removido com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na exclusão", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Ocorreu um erro ao excluir o funcionário.");
            
        }
    }

    public List<Funcionario> listarFuncionarios() {
        try {
            return funcionarioService.listarFuncionarios();
        } catch (Exception e) {
            Alerta.Erro("Erro na listagem", "Ocorreu um erro ao listar os funcionários.");
            
        }
        return null;
    }

    public List<Funcionario> buscarPorCPF(String cpf) {
        try {
            return funcionarioService.buscarPorCPF(cpf);
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na busca", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Ocorreu um erro ao buscar o funcionário pelo CPF.");
           
        }
        return null;
    }
}
