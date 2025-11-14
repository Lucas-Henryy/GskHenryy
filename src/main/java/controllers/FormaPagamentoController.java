package controllers;

import classes.FormaPagamento;
import services.FormaPagamentoService;
import validacoes.Alerta;

public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController() {
        this.formaPagamentoService = new FormaPagamentoService();
    }

    public void cadastrarFormaPagamento(FormaPagamento formaPagamento) {
        try {
            formaPagamentoService.cadastrarVenda(formaPagamento);
            System.out.println("Forma de pagamento cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar forma de pagamento");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao salvar a forma de pagamento.");
        }
    }

    public boolean validarFormaPagamento(FormaPagamento formaPagamento) {
        try {
            formaPagamentoService.cadastrarVenda(formaPagamento);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação");
            return false;
        }
    }
}
