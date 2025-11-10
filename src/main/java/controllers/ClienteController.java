package controllers;

import Services.ClienteService;
import classes.Cliente;
import java.util.List;
import validacoes.Alerta;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    public void cadastrarCliente(Cliente cliente) {
        try {
            clienteService.salvarCliente(cliente);
            Alerta.Sucesso("Cadastro concluído!", "Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro de validação", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro ao cadastrar", "Ocorreu um erro ao salvar o cliente no banco de dados.");
        }
    }

    public void editarCliente(Cliente cliente) {
        try {
            clienteService.atualizarCliente(cliente);
            Alerta.Sucesso("Atualização concluída!", "Cliente atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro de validação", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro ao atualizar", "Ocorreu um erro ao editar o cliente no banco de dados.");
        }
    }

    public void excluirCliente(String id) {
        try {
            clienteService.excluirCliente(id);
            Alerta.Sucesso("Exclusão concluída!", "Cliente excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro de validação", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro ao excluir", "Ocorreu um erro ao excluir o cliente do banco de dados.");
        }
    }

    public Cliente buscarClientePorId(String id) {
        try {
            return clienteService.buscarPorId(id);
        } catch (Exception e) {
            Alerta.Erro("Erro na busca", "Não foi possível encontrar o cliente.");
            return null;
        }
    }

    public List<Cliente> listarClientes() {
        try {
            return clienteService.listarClientes();
        } catch (Exception e) {
            Alerta.Erro("Erro na listagem", "Ocorreu um erro ao listar os clientes.");
            return null;
        }
    }

    public List<Cliente> buscarClientePorCPF(String cpf) {
        try {
            return clienteService.buscarPorCPF(cpf);
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro de validação", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro na busca", "Ocorreu um erro ao buscar cliente pelo CPF.");
        }
        return null;
    }
}
