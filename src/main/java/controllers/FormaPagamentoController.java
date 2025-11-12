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
            Alerta.Erro("Forma de pagamento cadastrada com sucesso!", "Sucesso");
        } catch (IllegalArgumentException e) {
            Alerta.Erro(e.getMessage(), "Erro ao cadastrar forma de pagamento");
        } catch (Exception e) {
            Alerta.Erro("Ocorreu um erro inesperado ao salvar a forma de pagamento.", "Erro do Sistema");
        }
    }

    public boolean validarFormaPagamento(FormaPagamento formaPagamento) {
        try {
            formaPagamentoService.cadastrarVenda(formaPagamento);
            return true;
        } catch (IllegalArgumentException e) {
            Alerta.Erro(e.getMessage(), "Erro de validação");
            return false;
        }
    }
}
