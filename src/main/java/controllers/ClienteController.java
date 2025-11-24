package controllers;

import DTO.ClienteDTO;
import Services.ClienteService;
import classes.Cliente;
import java.util.List;
import validacoes.Alerta;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    public void cadastrarCliente(ClienteDTO clienteDTO) {
        try {
            clienteService.salvarCliente(clienteDTO);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao salvar o cliente no banco de dados.");
        }
    }

    public void editarCliente(ClienteDTO clienteDTO, Long id) {
        try {
            clienteService.editarCliente(clienteDTO, id);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao editar o cliente no banco de dados.");
        }
    }

    public void excluirCliente(String id) {
        try {
            clienteService.excluirCliente(id);
            System.out.println("Cliente excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao excluir o cliente do banco de dados.");
        }
    }

    public Cliente buscarClientePorId(Long id) {
        try {
            return clienteService.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Não foi possível encontrar o cliente");
            return null;
        }
    }

    public List<Cliente> listarClientes() {
        try {
            return clienteService.listarClientes();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao listar os clientes.");
            return null;
        }
    }

    public Cliente buscarClientePorCPF(String cpf) {
        try {
            return clienteService.buscarPorCPF(cpf);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar cliente pelo CPF.");
        }
        return null;
    }
}
