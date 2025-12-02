
package DTO;

import java.time.LocalDate;

public class VendaDTO {
    
    private LocalDate data;
    private Double total;
    private int qtd;
    private String cpfCliente;
    private Long idFuncionario;
    private int codProduto;
    private String formaPagamento;
    private int parcelasPagamento;

    public VendaDTO(LocalDate data, Double total, int qtd, String cpfCliente, Long idFuncionario, int codProduto, String formaPagamento, int parcelasPagamento) {
        this.data = data;
        this.total = total;
        this.qtd = qtd;
        this.cpfCliente = cpfCliente;
        this.idFuncionario = idFuncionario;
        this.codProduto = codProduto;
        this.formaPagamento = formaPagamento;
        this.parcelasPagamento = parcelasPagamento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getParcelasPagamento() {
        return parcelasPagamento;
    }

    public void setParcelasPagamento(int parcelasPagamento) {
        this.parcelasPagamento = parcelasPagamento;
    }
    
    
}
