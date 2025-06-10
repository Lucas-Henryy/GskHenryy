
package classes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbVendas")
public class Vendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVendas")
    private Long id;

   @Column(name = "dataVenda")
    private LocalDate dataVenda;
    
    @Column(name = "totalVenda")
    private Double totalVenda;
    
    @Column(name = "qtdVenda")
    private int qtdVenda;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", unique = true)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "funcionario_id", unique = true)
    private Funcionario funcionario;
    
    @OneToOne(mappedBy = "venda", cascade=CascadeType.ALL)
    private FormaPagamento formapagamento;
    
    @ManyToMany
    @JoinTable(
        name = "tbItemVenda",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    ) 
    private List<Produto> prod = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(Double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public int getQtdVenda() {
        return qtdVenda;
    }

    public void setQtdVenda(int qtdVenda) {
        this.qtdVenda = qtdVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public FormaPagamento getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(FormaPagamento formapagamento) {
        this.formapagamento = formapagamento;
    }

    public List<Produto> getProd() {
        return prod;
    }

    public void setProd(List<Produto> prod) {
        this.prod = prod;
    }

    

}
