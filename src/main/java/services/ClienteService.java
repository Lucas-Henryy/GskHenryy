package Services;

import DTO.ClienteDTO;
import classes.Cliente;
import classesDAO.ClienteDAO;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }
    
    public void salvarCliente(ClienteDTO clienteDTO) {
       Cliente cliente = new Cliente (clienteDTO.getNome(), clienteDTO.getSexo(), clienteDTO.getEmail(), clienteDTO.getTelefone(), clienteDTO.getCpf());

        clienteDAO.salvar(cliente);
    }
    
    public void editarCliente(ClienteDTO clienteDTO, Long id) { 
        Cliente clienteEditar = clienteDAO.buscarPorId(id);
        
        clienteEditar.setNome(clienteDTO.getNome());
        clienteEditar.setCpf(clienteDTO.getCpf());
        clienteEditar.setSexo(clienteDTO.getSexo());
        clienteEditar.setTelefone(clienteDTO.getTelefone());
        clienteDAO.atualizar(clienteEditar);
    }

    public Cliente buscarPorId(Long id) {
        return clienteDAO.buscarPorId(id);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }
    
    public Cliente buscarPorCPF(String cpf) {

        return clienteDAO.buscarPorCPF(cpf);
    }

        public void excluirCliente(String id) {
        clienteDAO.excluir(id);
    }

}
