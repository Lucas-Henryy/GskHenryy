package services;

import classes.Funcionario;
import classesDAO.FuncionarioDAO;
import java.util.List;
import validacao.Alerta;

public class FuncionarioService {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void cadastrarFuncionario(Funcionario funcionario) {

        try {
            funcionarioDAO.salvar(funcionario)
            Alerta.Sucesso("Cadastro concluído!!", "Funcionario cadastrado com sucesso");
            
        } catch (Exception e) {
            Alerta.Erro("Erro ao Cadastrar", "Ocorreu um erro ao Cadastrar");
        }
    }

    public void editarFuncionario(Funcionario funcionario) {

        try {
            funcionarioDAO.atualizar(funcionario)
            Alerta.Sucesso("Sucesso!", "Edição realizada com sucesso");

        } catch (Exception e) {
            Alerta.Erro("Erro ao editar", "Ocorreu um erro ao editar as informações");
        }
    }

    public List<Funcionario> listarFuncionarios(String cpf) {
        try {
            if (cpf == null || cpf.isEmpty()) {
                return funcionarioDAO.listarTodos();
            }
            return funcionarioDAO.buscarPorCpf(cpf)
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro ao buscar informacoes para lista");
            return List.of();
        }
    }
    
    public Funcionario buscarCliente (String idFuncionario){
        
        try {
            return funcionarioDAO.buscarPorId(idFuncionario)
        } catch (Exception e) {
            Alerta.Erro("Erro", "Erro ao listar o cliente");
            return null;
        }
    } 
}