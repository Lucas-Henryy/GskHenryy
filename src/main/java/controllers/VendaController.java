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
            Alerta.Erro(e.getMessage(), "Erro ao buscar cliente");
            return null;
        }
    }

    public Produto buscarProdutoPorCodigo(String codigo) {
        try {
            return vendaService.buscarProdutoPorCodigo(codigo);
        } catch (IllegalArgumentException e) {
            Alerta.Erro(e.getMessage(), "Erro ao buscar produto");
            return null;
        }
    }

    public List<Vendas> listarVendas(String nomeCliente) {
        try {
            return vendaService.listarVendas(nomeCliente);
        } catch (IllegalArgumentException e) {
            Alerta.Erro(e.getMessage(), "Erro ao listar vendas");
            return null;
        }
    }

    public void excluirVenda(String id) {
        try {
            vendaService.excluirVenda(id);
            Alerta.Erro("Venda excluída com sucesso!", "Sucesso");
        } catch (IllegalArgumentException e) {
            Alerta.Erro(e.getMessage(), "Erro ao excluir venda");
        }
    }

    public boolean validarVenda(Vendas venda) {
        try {
            vendaService.validarVenda(venda);
            return true;
        } catch (IllegalArgumentException e) {
            Alerta.Erro(e.getMessage(), "Erro de validação");
            return false;
        }
    }
}

