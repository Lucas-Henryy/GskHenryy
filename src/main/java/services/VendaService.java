package services;

import DTO.VendaDTO;
import classes.Cliente;
import classes.FormaPagamento;
import classes.Funcionario;
import classes.Produto;
import classes.Venda;
import classesDAO.ClienteDAO;
import classesDAO.FuncionarioDAO;
import classesDAO.VendaDAO;
import java.util.List;

public class VendaService {

    private final VendaDAO vendaDAO;
    private final ClienteDAO clienteDAO;
    private final FuncionarioDAO funcionarioDAO;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.clienteDAO = new ClienteDAO();
        this.funcionarioDAO = new FuncionarioDAO();
    }

    public void cadastrarVenda(VendaDTO vendaDTO) {
        Cliente cliente = clienteDAO.buscarPorCPF(vendaDTO.getCpfCliente());
        Funcionario funcionario = funcionarioDAO.buscarPorId(vendaDTO.getIdFuncionario());
        FormaPagamento formapag = new FormaPagamento(vendaDTO.getFormaPagamento(), vendaDTO.getParcelasPagamento());
        Venda venda = new Venda(vendaDTO.getData(), vendaDTO.getTotal(), vendaDTO.getQtd(), cliente, funcionario, formapag);
    }

    public Cliente buscarClientePorCPF(String cpf) {
        Cliente cliente = vendaDAO.listarCPF(cpf);
        return cliente;
    }
    
    public Produto buscarProdutoPorCodigo (String codigo){
    Produto produto = vendaDAO.listarProdutos(codigo);
    return produto;
    }

    public List<Venda> listarVendas(String nomeCliente) {
        return vendaDAO.listarVendas(nomeCliente);
    }

    public void excluirVenda(String id) {
        vendaDAO.excluirVendas(id);
    }

}
