package controllers;

import classes.Cliente;
import classes.Produto;
import classes.Vendas;
import services.VendaService;
import validacoes.Alerta;
import java.util.List;

public class VendaController {

    private final VendaService vendaService;

    public VendaController() {
        this.vendaService = new VendaService();
    }

    public Cliente buscarClientePorCPF(String cpf) {
        try {
            return vendaService.buscarClientePorCPF(cpf);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar cliente");
            return null;
        }
    }

    public Produto buscarProdutoPorCodigo(String codigo) {
        try {
            return vendaService.buscarProdutoPorCodigo(codigo);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar produto");
            return null;
        }
    }

    public List<Vendas> listarVendas(String nomeCliente) {
        try {
            return vendaService.listarVendas(nomeCliente);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao listar vendas");
            return null;
        }
    }

    public void excluirVenda(String id) {
        try {
            vendaService.excluirVenda(id);
            System.out.println("Venda excluída com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir venda");
        }
    }

    public boolean validarVenda(Vendas venda) {
        try {
            vendaService.validarVenda(venda);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação");
            return false;
        }
    }
}

