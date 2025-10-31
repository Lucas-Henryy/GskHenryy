package Services;

import classes.Cliente;
import classesDAO.ClienteDAO;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public void salvarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo!");
        }

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório!");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório!");
        }

        clienteDAO.salvar(cliente);
    }

    public void atualizarCliente(Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente inválido para atualização!");
        }

        clienteDAO.atualizar(cliente);
    }

    public Cliente buscarPorId(String id) {
        return clienteDAO.buscarPorId(id);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }

    public List<Cliente> buscarPorCPF(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode estar vazio!");
        }

        return clienteDAO.buscarPorCPF(cpf);
    }

    public void excluirCliente(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do cliente inválido!");
        }

        clienteDAO.excluir(id);
    }
}
