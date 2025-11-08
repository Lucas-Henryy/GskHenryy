package services;

import classes.Cliente;
import classes.Produto;
import classes.Vendas;
import classesDAO.VendaDAO;
import java.util.List;

public class VendaService {

    private final VendaDAO vendaDAO;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
    }

    public Cliente buscarClientePorCPF(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF é obrigatório para buscar o cliente.");
        }

        Cliente cliente = vendaDAO.listarCPF(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Nenhum cliente encontrado com o CPF informado.");
        }

        return cliente;
    }

   
    public Produto buscarProdutoPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código do produto é obrigatório.");
        }

        Produto produto = vendaDAO.listarProdutos(codigo);
        if (produto == null) {
            throw new IllegalArgumentException("Nenhum produto encontrado com o código informado.");
        }

        return produto;
    }

   
    public List<Vendas> listarVendas(String nomeCliente) {
        return vendaDAO.listarVendas(nomeCliente);
    }

    
    public void excluirVenda(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID da venda é obrigatório para exclusão.");
        }

        vendaDAO.excluirVendas(id);
    }

  
    public void validarVenda(Vendas venda) {
        if (venda == null) {
            throw new IllegalArgumentException("A venda não pode ser nula.");
        }

        if (venda.getDataVenda() == null) {
            throw new IllegalArgumentException("A data da venda é obrigatória.");
        }

        if (venda.getTotalVenda() == null || venda.getTotalVenda() <= 0) {
            throw new IllegalArgumentException("O total da venda deve ser maior que zero.");
        }

        if (venda.getQtdVenda() <= 0) {
            throw new IllegalArgumentException("A quantidade vendida deve ser maior que zero.");
        }

        if (venda.getCliente() == null || venda.getCliente().getId() == null) {
            throw new IllegalArgumentException("O cliente da venda é obrigatório.");
        }

        if (venda.getFuncionario() == null || venda.getFuncionario().getId() == null) {
            throw new IllegalArgumentException("O funcionário responsável pela venda é obrigatório.");
        }
    }

}
