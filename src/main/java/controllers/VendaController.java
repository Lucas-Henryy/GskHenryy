package controllers;

import DTO.VendaDTO;
import classes.Cliente;
import classes.Produto;
import classes.Venda;
import services.VendaService;
import validacoes.Alerta;
import java.util.List;

public class VendaController {
    private final VendaService vendaService;

    public VendaController() {
        this.vendaService = new VendaService();
    }

      public void cadastrarVendas(VendaDTO vendaDTO) {
        try {
            vendaService.cadastrarVenda(vendaDTO);
            System.out.println("Forma de pagamento cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar forma de pagamento");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao salvar a forma de pagamento.");
        }
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

    public List<Venda> listarVendas(String nomeCliente) {
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
   
}

