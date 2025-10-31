package services;

import classes.Cliente;
import classesDAO.ClienteDAO;
import java.util.List;
import validacao.Alerta;

public class ClienteService {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastrarCliente(Cliente cliente) {

        try {
            clienteDAO.salvar(cliente);
            Alerta.Sucesso("Cadastro concluído!!", "Cliente cadastrado com sucesso");
        } catch (Exception e) {
            Alerta.Erro("Erro ao Cadastrar", "Ocorreu um erro ao cadastrar o cliente");
        }
    }

    public void editarCliente(Cliente cliente) {

        try {
            clienteDAO.atualizar(cliente);
            Alerta.Sucesso("Sucesso!", "Edição realizada com sucesso");

        } catch (Exception e) {
            Alerta.Erro("Erro ao editar", "Ocorreu um erro ao editar as infromações");
        }
    }

    public List<Cliente> listarClientes(String cpf) {
        try {
            if(cpf == null || cpf.isEmpty()) {
                return clienteDAO.listarTodos();
            }
            return clienteDAO.buscarPorCPF(cpf);
        } catch (Exception e) {
            Alerta.Erro("Erro listagem", "Erro ao buscar informacoes para lista");
            return List.of();
        }
    }
    
        public void excluirCliente(String idCliente) {

        try {
            clienteDAO.excluir(idCliente);
            Alerta.Sucesso("Exclusão realizada!", "Exclusão realizada com sucesso");
        } catch (Exception e) {
            Alerta.Erro("Erro ao excluir", "Ocorreu um erro ao excluir as informações");
        }
    }
    
    public Cliente buscarCliente (String idCliente){
        
        try {
            return clienteDAO.buscarPorId(idCliente);
        } catch (Exception e) {
            Alerta.Erro("Erro", "Erro ao buscar o cliente");
            return null;
        }
    } 
}
