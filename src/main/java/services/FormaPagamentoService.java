package services;

import classes.FormaPagamento;
import classesDAO.FormaPagamentoDAO;

public class FormaPagamentoService {

    private final FormaPagamentoDAO formaPagamentoDAO;

    public FormaPagamentoService() {
        this.formaPagamentoDAO = new FormaPagamentoDAO();
    }

    public void cadastrarVenda(FormaPagamento formaPagamento) {
        validarCamposObrigatorios(formaPagamento);
        formaPagamentoDAO.cadastrarFormaPagamento(formaPagamento);
    }

    // ------------------ Validações ------------------
    private void validarCamposObrigatorios(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            throw new IllegalArgumentException("O pagamento não pode ser nulo.");
        }

        // método de pagamento (VARCHAR(10), NOT NULL)
        if (formaPagamento.getMetodoPag() == null || formaPagamento.getMetodoPag().trim().isEmpty()) {
            throw new IllegalArgumentException("O método de pagamento é obrigatório.");
        }
        if (formaPagamento.getMetodoPag().length() > 10) {
            throw new IllegalArgumentException("O método de pagamento deve ter no máximo 10 caracteres.");
        }

        // parcelasPag (INT, NOT NULL)
        if (formaPagamento.getParcelasPag() <= 0) {
            throw new IllegalArgumentException("O número de parcelas deve ser maior que zero.");
        }

        // venda_id (FK, obrigatório no contexto do pagamento)
        if (formaPagamento.getVenda() == null || formaPagamento.getVenda().getId() == null) {
            throw new IllegalArgumentException("É necessário associar uma venda ao pagamento.");
        }
    }
}
